package com.example

package concurrency.exercises

import cats.effect.kernel.Outcome._
import cats.effect.{Fiber, IO, IOApp, Outcome}

import scala.concurrent.duration._

object RacingIOsExercisesPart3 extends IOApp.Simple {

  // Implement race in terms of racePair
  def simpleRace[A, B](ioa: IO[A], iob: IO[B]): IO[Either[A, B]] = {
    val racePairResult: IO[Either[
      (Outcome[IO, Throwable, A], Fiber[IO, Throwable, B]),
      (Fiber[IO, Throwable, A], Outcome[IO, Throwable, B])
    ]] = IO.racePair(ioa, iob)

    racePairResult.flatMap {
      case Left((resA, fibB)) =>
        resA match {
          case Succeeded(fa) => fibB.cancel >> fa.map(Left(_))
          case Errored(e)    => fibB.cancel >> IO.raiseError(e)
          case Canceled() =>
            fibB.join.flatMap {
              case Succeeded(fb) => fb.map(Right(_))
              case Errored(e)    => IO.raiseError(e)
              case Canceled()    => IO.raiseError(new RuntimeException("Both fibers cancelled"))
            }
        }
      case Right((fibA, resB)) =>
        resB match {
          case Succeeded(fb) => fibA.cancel >> fb.map(Right(_))
          case Errored(e)    => fibA.cancel >> IO.raiseError(e)
          case Canceled()    => fibA.join.flatMap {
            case Succeeded(fa) => fa.map(Left(_))
            case Errored(e) => IO.raiseError(e)
            case Canceled() => IO.raiseError(new RuntimeException("Both fibers cancelled"))
          }
        }
    }
  }

  val ioa: IO[Int] =
    (IO("starting ioa computation").debug >> IO.sleep(3.seconds) >> IO(42)).onCancel(IO(println("cancelling ioa")))

  val iob: IO[String] =
    (IO("starting iob computation").debug >> IO.sleep(2.seconds) >> IO("Scala")).onCancel(IO(println("cancelling iob")))

  ioa.uncancelable

  override def run: IO[Unit] = simpleRace(ioa, iob).debug.void
}
