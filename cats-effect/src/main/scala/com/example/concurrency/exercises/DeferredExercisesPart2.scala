package com.example
package concurrency.exercises

import cats.effect.kernel.Deferred
import cats.effect.kernel.Outcome._
import cats.effect.{Fiber, IO, IOApp, Outcome, Ref}

import scala.concurrent.duration._

object DeferredExercisesPart2 extends IOApp.Simple {

  /**
   *  Exercise:
   *  - (mega hard) implement racePair with Deferred.
   *    - use a Deferred which can hold an Either[outcome for ioa, outcome for iob]
   *    - start two fibers, one for each IO
   *    - on completion (with any status), each IO needs to complete that Deferred
   *      (hint: use a finalizer from the Resources lesson)
   *      (hint2: use a guarantee call to make sure the fibers complete the Deferred)
   *    - what do you do in case of cancellation (the hardest part)?
   */
  type RaceResult[A, B] = Either[
    (Outcome[IO, Throwable, A], Fiber[IO, Throwable, B]),
    (Fiber[IO, Throwable, A], Outcome[IO, Throwable, B])
  ]

  type EitherOutcome[A, B] = Either[Outcome[IO, Throwable, A], Outcome[IO, Throwable, B]]

  def ourRacePair[A, B](ioa: IO[A], iob: IO[B]): IO[RaceResult[A, B]] = {
    IO.uncancelable { poll =>
      for {
        signal <- Deferred[IO, EitherOutcome[A, B]]
        fibA <- ioa.guaranteeCase(outcomeA => signal.complete(Left(outcomeA)).void).start
        fibB <- iob.guaranteeCase(outcomeB => signal.complete(Right(outcomeB)).void).start
        result <- poll(signal.get).onCancel { // semantically blocking while waiting for complete signal from Deferred
          for {
            fibCancelA <- fibA.cancel.start
            fibCancelB <- fibB.cancel.start
            _ <- fibCancelA.join
            _ <- fibCancelB.join
          } yield ()
        }
      } yield result match {
        case Left(outcomeA) => Left((outcomeA, fibB))
        case Right(outcomeB) => Right((fibA, outcomeB))
      }
    }
  }

  override def run: IO[Unit] = ???
}
