package com.example.io.part2errorhandling.exercises

import cats.effect.IO

object Exercise5_HandleIOErrorWith extends App {

  def handleIOErrorWith[A](ioa: IO[A])(handler: Throwable => IO[A]): IO[A] = {
    ioa.redeemWith(handler, IO.pure)
  }

}
