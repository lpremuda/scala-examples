package com.example.io.p2errorhandling.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import scala.util.{Failure, Success, Try}

object Exercise2_TryFailureToIO extends App {

  def try2IO[A](myTry: Try[A]): IO[A] = {
    myTry match {
      case Success(suc) => IO.pure(suc)
      case Failure(exception) => IO.raiseError(exception)
    }
  }

  //////////////
  // TESTS
  //////////////
  val success: Success[Int] = Success(42)
  val failure: Failure[Int] = Failure(new RuntimeException("This is a Failure"))

  val successIO: IO[Int] = try2IO(success)
  val failureIO: IO[Int] = try2IO(failure)

  val result = failureIO.unsafeRunSync()
  println(result)

}
