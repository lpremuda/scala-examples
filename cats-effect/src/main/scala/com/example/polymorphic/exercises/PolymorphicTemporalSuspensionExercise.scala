package com.example
package polymorphic.exercises

import cats.effect.{IO, IOApp, Temporal}
import cats.implicits.toFlatMapOps

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object PolymorphicTemporalSuspensionExercise extends IOApp.Simple {

  /**
   * Exercise: generalize the following piece of code
   */
  def timeout[F[_], A](fa: F[A], duration: FiniteDuration)(implicit temporal: Temporal[F]): F[A] = {
    val timeoutEffect: F[Unit] = temporal.sleep(duration)
    val result: F[Either[A, Unit]] = temporal.race(fa, timeoutEffect)

    result.flatMap {
      case Left(v) => temporal.pure(v)
      case Right(_) => temporal.raiseError(new RuntimeException("Computation timed out."))
    }
  }

  val myIO: IO[Int] = IO.sleep(1.second) >> IO(42)

  override def run: IO[Unit] = timeout(myIO, 2.seconds).debug.void
}
