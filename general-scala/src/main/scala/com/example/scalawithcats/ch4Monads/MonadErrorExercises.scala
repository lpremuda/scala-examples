package com.example.scalawithcats.ch4Monads

import cats.MonadError

object MonadErrorExercises extends App {

  type ErrorOr[A] = Either[String, A]

  /*
    F = Either[String, A]
    E = String
   */
  val monadError: MonadError[ErrorOr, String] = MonadError[ErrorOr, String]

  /*
    From Applicative[F{_]]
    def pure[A](x: A): F[A]
      A = Int
      F[A] = Either[String, Int]
   */
  val success: Either[String, Int] = monadError.pure(42)
  // Result: Right(42)
  println(success)


  /*
    From ApplicativeError[F[_], E]
    def raiseError[A](e: E): F[A]
      E = String
      F[A] = Either[String, Nothing]
   */
  val failure: Either[String, Nothing] = monadError.raiseError("Badness")
  // Result: Left("Badness")
  println(failure)

  /*
    From ApplicativeError[F[_], E]
    def handleErrorWith[A](fa: F[A])(f: E => F[A]): F[A]
      A = Nothing
      F[A] = Either[String, Nothing]
      E = String

   */

  val result: Either[String, String] = monadError.handleErrorWith(failure) {
    case "Badness" =>
      monadError.pure("It's ok")

    case _ =>
      monadError.raiseError("It's not ok")
  }
  println(result)

}
