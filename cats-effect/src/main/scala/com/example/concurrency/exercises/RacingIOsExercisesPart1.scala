package com.example
package concurrency.exercises

import cats.effect.{IO, IOApp}

import scala.concurrent.duration._

object RacingIOsExercisesPart1 extends IOApp.Simple {

  def timeout[A](io: IO[A], duration: FiniteDuration): IO[A] = {
    val raceResult: IO[Either[Unit, A]] = IO.race(IO.sleep(duration), io)
    raceResult.flatMap {
      case Left(_) => IO.raiseError(new RuntimeException("Timeout exceeded"))
      case Right(result) => IO.pure(result)
    }
  }

  val io: IO[Int] = IO("starting computation").debug >> IO.sleep(2.seconds) >> IO(42)

  override def run: IO[Unit] = timeout(io, 3.seconds).debug.void
}
