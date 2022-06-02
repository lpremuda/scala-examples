package com.example
package polymorphic.exercises

import cats.effect.implicits.monadCancelOps_
import cats.effect.{IO, IOApp, MonadCancel}
import cats.implicits.toFunctorOps

object PolymorphicCancellationExercise extends IOApp.Simple {

  /**
   * Exercise - generalize a piece of code (the auth-flow example from the Cancellation lesson)
   */
  import scala.concurrent.duration._

  // hint: use this instead of IO.sleep
  def unsafeSleep[F[_], E](duration: FiniteDuration)(implicit mc: MonadCancel[F, E]): F[Unit] =
    mc.pure(Thread.sleep(duration.toMillis))

  def inputPassword[F[_], E](implicit mc: MonadCancel[F,E]): F[String] = for {
    _ <- mc.pure("Input password:").debug2
    _ <- mc.pure("(typing password)").debug2
    _ <- unsafeSleep[F, E](5.seconds)
    pw <- mc.pure("RockTheJVM1!")
  } yield pw

  def verifyPassword[F[_], E](pw: String)(implicit mc: MonadCancel[F, E]): F[Boolean] = for {
    _ <- mc.pure("verifying...").debug2
    _ <- unsafeSleep[F,E](2.seconds)
    check <- mc.pure(pw == "RockTheJVM1!")
  } yield check

  def authFlow[F[_], E](implicit mc: MonadCancel[F,E]): F[Unit] = mc.uncancelable { poll =>
    for {
      pw <- poll(inputPassword).onCancel(mc.pure("Authentication timed out. Try again later.").debug2.void) // this is cancelable
      verified <- verifyPassword(pw) // this is NOT cancelable
      _ <- if (verified) mc.pure("Authentication successful.").debug2 // this is NOT cancelable
      else mc.pure("Authentication failed.").debug2
    } yield ()
  }

  val authProgram: IO[Unit] = for {
    authFib <- authFlow[IO, Throwable].start
    _ <- IO.sleep(3.seconds) >> IO("Authentication timeout, attempting cancel...").debug >> authFib.cancel
    _ <- authFib.join
  } yield ()


  override def run = authProgram
}
