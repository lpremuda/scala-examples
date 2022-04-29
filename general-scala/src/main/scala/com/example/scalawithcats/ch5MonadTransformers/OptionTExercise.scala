package com.example.scalawithcats.ch5MonadTransformers

import cats.Monad
import cats.data.OptionT
import cats.syntax.applicative._

import cats.instances.list._


object OptionTExercise extends App {

  /*
    OptionT is a case class
    OptionT is a monad transformer for Option

    final case class OptionT[F[_], A](value: F[Option[A]]) {
      F = List
      A = Int
      value = List[Option[Int]]
   */

  type ListOption[A] = OptionT[List, A]

  val result1: ListOption[Int] = OptionT(List(Option(10)))

  val result2: ListOption[Int] = 32.pure[ListOption]

  /*
    def flatMap[B](f: A => OptionT[F, B])(implicit F: Monad[F]): OptionT[F, B] =
      flatMapF(a => f(a).value)

    Using an implicit Monad[List]
    Int => OptionT[List,B]

    Calling:
      Monad[List].flatMap(List[Option[A]])(x => x.fold(Monad[List].pure[Option[B]])(Int => List[Option[B]])
   */
  val result3: ListOption[Int] =
    result1.flatMap { (x: Int) =>
      result2.map { (y: Int) =>
      x + y
      }
    }

  println(result1)
  println(result2)
  println(result3)

  // Alias Either to a type constructor with one parameter:
  type ErrorOr[A] = Either[String, A]

  // Build our final monad stack using OptionT:
  type ErrorOrOption[A] = OptionT[ErrorOr, A]

  val a: OptionT[ErrorOr, Int] = 10.pure[ErrorOrOption]

  val b: OptionT[ErrorOr, Int] = 32.pure[ErrorOrOption]

  val c: OptionT[ErrorOr, Int] = a.flatMap(x => b.map(y => x + y))

  println(a)
  println(b)
  println(c)
}
