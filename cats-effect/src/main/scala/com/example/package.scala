package com

import cats.Functor
import cats.effect.MonadCancel
import cats.syntax.functor._

import scala.concurrent.duration.FiniteDuration

package object example {

  implicit class DebugWrapper[F[_], A](fa: F[A]) {
    def debug(implicit functor: Functor[F]): F[A] = {
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
