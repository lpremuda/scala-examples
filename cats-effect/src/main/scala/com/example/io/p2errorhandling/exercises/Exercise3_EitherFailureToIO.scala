package com.example.io.p2errorhandling.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object Exercise3_EitherFailureToIO extends App {

  def either2IO[A](either: Either[Throwable, A]): IO[A] = {
    either match {
      case Right(value) => IO.pure(value)
      case Left(exception) => IO.raiseError(exception)
    }
  }

  //////////////
  // TESTS
  //////////////
  val right: Right[Throwable, Int] = Right(42)
  val left: Left[Throwable, Int] = Left(new RuntimeException("This is a Left"))

  val rightIO: IO[Int] = either2IO(right)
  val leftIO: IO[Int] = either2IO(left)

  val result = leftIO.unsafeRunSync()
  println(result)

}
