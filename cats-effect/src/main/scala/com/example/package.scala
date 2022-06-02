package com

import cats.Functor
import cats.syntax.functor._
import cats.effect.{IO, MonadCancel}

import scala.concurrent.duration.FiniteDuration

package object example {

  implicit class DebugWrapper[A](ioa: IO[A]) {

    def debug: IO[A] = {
      for {
        a <- ioa
        t = Thread.currentThread().getName
        _ = println(s"[$t] $a")
      } yield a
    }
  }

  implicit class DebugWrapper2[F[_], A](fa: F[A]) {
    def debug2(implicit functor: Functor[F]): F[A] = {
      fa.map { a =>
        val t = Thread.currentThread().getName
        println(s"[$t] $a")
        a
      }
    }

    def unsafeSleep[E](finiteDuration: FiniteDuration)(implicit mc: MonadCancel[F, E]): F[Unit] = {
      mc.pure(Thread.sleep(finiteDuration.toMillis))
    }

  }

}
