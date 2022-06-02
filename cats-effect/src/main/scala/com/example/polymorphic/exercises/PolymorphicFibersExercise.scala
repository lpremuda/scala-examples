package com.example

package polymorphic.exercises

import cats.effect.kernel.Outcome.{Canceled, Errored, Succeeded}
import cats.effect.{IO, IOApp, Spawn}
import cats.implicits.{toFlatMapOps, toFunctorOps}

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object PolymorphicFibersExercise extends IOApp.Simple {

  def runWithSleep[A](value: A, duration: FiniteDuration): IO[A] =
    (
      IO(s"starting computation: $value").debug >>
        IO.sleep(duration) >>
        IO(s"computation for $value: done") >>
        IO(value)
    ).onCancel(IO(s"computation CANCELED for $value").debug.void)

  /**
   * Exercise - generalize the following code
   */

  def simpleRace[A, B](ioa: IO[A], iob: IO[B]): IO[Either[A, B]] = {
    IO.racePair(ioa, iob).flatMap {
      case Left((outA, fibB)) => outA match {
        case Succeeded(effectA) => fibB.cancel >> effectA.map(a => Left(a))
        case Errored(e) => fibB.cancel >> IO.raiseError(e)
        case Canceled() => fibB.join.flatMap {
          case Succeeded(effectB) => effectB.map(b => Right(b))
          case Errored(e) => IO.raiseError(e)
          case Canceled() => IO.raiseError(new RuntimeException("Both computations canceled."))
        }
      }
      case Right((fibA, outB)) => outB match {
        case Succeeded(effectB) => fibA.cancel >> effectB.map(b => Right(b))
        case Errored(e) => fibA.cancel >> IO.raiseError(e)
        case Canceled() => fibA.join.flatMap {
          case Succeeded(effectA) => effectA.map(a => Left(a))
          case Errored(e) => IO.raiseError(e)
          case Canceled() => IO.raiseError(new RuntimeException("Both computations canceled."))
        }
      }
    }
  }

  def generalRace[F[_], A, B](ioa: F[A], iob: F[B])(implicit spawn: Spawn[F]): F[Either[A, B]] =
    spawn.racePair(ioa, iob).flatMap {
      case Left((outA, fibB)) =>
        outA match {
          case Succeeded(effectA) => fibB.cancel.flatMap(_ => effectA.map(a => Left(a)))
          case Errored(e)         => fibB.cancel.flatMap(_ => spawn.raiseError(e))
          case Canceled() =>
            fibB.join.flatMap {
              case Succeeded(effectB) => effectB.map(b => Right(b))
              case Errored(e)         => spawn.raiseError(e)
              case Canceled()         => spawn.raiseError(new RuntimeException("Both computations canceled."))
            }
        }
      case Right((fibA, outB)) =>
        outB match {
          case Succeeded(effectB) => fibA.cancel.flatMap(_ => effectB.map(b => Right(b)))
          case Errored(e)         => fibA.cancel.flatMap(_ => spawn.raiseError(e))
          case Canceled() =>
            fibA.join.flatMap {
              case Succeeded(effectA) => effectA.map(a => Left(a))
              case Errored(e)         => spawn.raiseError(e)
              case Canceled()         => spawn.raiseError(new RuntimeException("Both computations canceled."))
            }
        }
    }

  val meaningOfLife: IO[Int] = IO.sleep(1.second) >> IO(42).debug
  val favLang: IO[String] = IO.sleep(2.seconds) >> IO("Scala").debug
  val race = IO("running simpleRace...").debug >> simpleRace(meaningOfLife, favLang)
  val race_v2 = IO("running generalRace...").debug >> generalRace(meaningOfLife, favLang)

  override def run: IO[Unit] = race_v2.void
}
