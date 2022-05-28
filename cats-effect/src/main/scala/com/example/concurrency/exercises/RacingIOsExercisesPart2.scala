package com.example
package concurrency.exercises

import cats.effect.kernel.Outcome._
import cats.effect.{Fiber, IO, IOApp, Outcome}

import scala.concurrent.duration._

object RacingIOsExercisesPart2 extends IOApp.Simple {

  // Give result of the loser of the race
  def unrace[A, B](ioa: IO[A], iob: IO[B]): IO[Either[A, B]] = {
    val racePairResult: IO[Either[
      (Outcome[IO, Throwable, A], Fiber[IO, Throwable, B]),
      (Fiber[IO, Throwable, A], Outcome[IO, Throwable, B])
    ]] = IO.racePair(ioa, iob)

    racePairResult.flatMap {
      case Left((_, fibB)) => fibB.join.flatMap {
        case Succeeded(fb) => fb.map(Right(_))
        case Errored(e) => IO.raiseError(e)
        case Canceled() => IO.raiseError(new RuntimeException("fibB cancelled"))
      }
      case Right((fibA, _)) => fibA.join.flatMap {
        case Succeeded(fa) => fa.map(Left(_))
        case Errored(e) => IO.raiseError(e)
        case Canceled() => IO.raiseError(new RuntimeException("fibA cancelled"))
      }
    }
  }

  val ioa: IO[Int] = IO("starting ioa computation").debug >> IO.sleep(3.seconds) >> IO(42)
  val iob: IO[String] = IO("starting iob computation").debug >> IO.sleep(2.seconds) >> IO("Scala")

  override def run: IO[Unit] = unrace(ioa, iob).debug.void
}
