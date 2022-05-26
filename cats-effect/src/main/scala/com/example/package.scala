package com

import cats.effect.IO

package object example {

  implicit class DebugWrapper[A](ioa: IO[A]) {

    def debug: IO[A] = {
      for {
        a <- ioa
        t = (Thread.currentThread().getName)
        _ = println(s"[$t] $a")
      } yield a
    }
  }

}
