package com.example.io.p2errorhandling.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object Exercise1_OptionFailureToIO extends App {

  def option2IO[A](opt: Option[A])(ifEmpty: Throwable): IO[A] = {
    opt match {
      case Some(a) => IO.pure(a)
      case None => IO.raiseError(ifEmpty)
    }
  }

  //////////////
  // TESTS
  //////////////
  val ifEmpty: Throwable = new RuntimeException("Option is empty!")

  val optSome: Option[Int] = Some(42)
  val optNone: Option[Int] = None

  val optSomeIO: IO[Int] = option2IO(optSome)(ifEmpty)
  val optNoneIO: IO[Int] = option2IO(optNone)(ifEmpty)

  val result = optNoneIO.unsafeRunSync()
  println(result)

}
