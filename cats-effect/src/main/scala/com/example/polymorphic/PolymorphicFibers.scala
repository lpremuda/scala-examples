package com.example.polymorphic

import cats.effect.{Fiber, Outcome}

object PolymorphicFibers {

  trait MyGenSpawn[F[_], E] {
    def start[A](fa: F[A]): F[Outcome[F, E, A]]
    def never[A]: F[A] // a forever-suspending effect
    def cede: F[Unit] // a "yield" effect

    def racePair[A, B](fa: F[A], fb: F[B]): F[Either[
      (Outcome[F, E, A], Fiber[F, E, B]),
      (Fiber[F, E, A], Outcome[F, E, B])
    ]]
  }

  trait MySpawn[F[_]] extends MyGenSpawn[F, Throwable]

}
