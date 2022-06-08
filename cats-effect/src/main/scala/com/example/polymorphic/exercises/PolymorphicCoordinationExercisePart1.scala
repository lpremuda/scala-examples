package com.example.polymorphic.exercises

import cats.effect.{Concurrent, Fiber, IO, IOApp, Outcome}
import cats.implicits.toFlatMapOps

object PolymorphicCoordinationExercisePart1 extends IOApp.Simple {

  /**
   * Exercise: Generalize the racePair condition for any effect type
   */
  type RaceResult[F[_], A, B] = Either[
    (Outcome[F, Throwable, A], Fiber[F, Throwable, B]), // (winner result, loser fiber)
    (Fiber[F, Throwable, A], Outcome[F, Throwable, B])  // (loser fiber, winner result)
  ]

  type EitherOutcome[F[_], A, B] = Either[Outcome[F, Throwable, A], Outcome[F, Throwable, B]]

  import cats.effect.syntax.monadCancel._
  import cats.effect.syntax.spawn._
  import cats.implicits.toFunctorOps

  def ourRacePair[F[_], A, B](fa: F[A], fb: F[B])(implicit concurrent: Concurrent[F]): F[RaceResult[F, A, B]] = {
    concurrent.uncancelable { poll =>
      // concurrent.deferred did not like the <- for comprehension syntax, so I changed to a regular flatMap
      concurrent.deferred[EitherOutcome[F, A, B]].flatMap { signal =>
        for {
          fiba <- fa.guaranteeCase(outcomeA => signal.complete(Left(outcomeA)).void).start
          fibb <- fb.guaranteeCase(outcomeB => signal.complete(Right(outcomeB)).void).start
          result <- poll(signal.get).onCancel { // blocking call - should be cancelable
            for {
              cancelFibA <- fiba.cancel.start
              cancelFibB <- fibb.cancel.start
              _ <- cancelFibA.join
              _ <- cancelFibB.join
            } yield ()
          }
        } yield result match {
          case Left(outcomeA) => Left((outcomeA, fibb))
          case Right(outcomeB) => Right((fiba, outcomeB))
        }
      }
    }
  }

  override def run: IO[Unit] = ???
}
