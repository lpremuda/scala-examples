package com.example.io.p2errorhandling.exercises

import cats.effect.IO

object Exercise4_HandleIOError extends App {

  def handleIOError[A](ioa: IO[A])(handler: Throwable => A): IO[A] = {
    ioa.redeem(handler, identity)
  }

}
