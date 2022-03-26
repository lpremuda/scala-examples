package com.example.scalawithcats.ch4Monads

import cats.Id

object IdExercise extends App {

  /*

    For reference:

    type Id[A] = A

   */

  // Monad functions for Id
  def pure[A](a: A): Id[A] = a
  def map[A, B](fa: Id[A])(func: A => B): Id[B] =
    func(fa)
  def flatMap[A, B](fa: Id[A])(func: A => Id[B]): Id[B] =
    func(fa)

  // Note that we didn't have to write type annotations in the method bodies because the compiler is able to interpret
  // value of type A as Id[A] (and vice versa)
  println(pure(123))
  println(map(123)(_ * 2))
  println(flatMap(123)(_ * 3))

}
