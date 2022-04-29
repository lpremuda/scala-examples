package com.example.cats.functors

import cats.instances.option._
//  object option extends OptionInstances with OptionInstancesBinCompat0
import cats.Functor

object FunctorExample extends App {

  val optionString: Option[String] = Some("Lucas")
  val listInt: List[Int] = List(1,2,3,4,5)

  /*
    @typeclass trait Functor[F[_]] extends Invariant[F] { self =>
      def map[A, B](fa: F[A])(f: A => B): F[B]

    F[_]  = Option
    A     = String
    B     = String
   */

  val optionStringOutput: Option[String] = Functor[Option].map(optionString)(_.toUpperCase)
  println(optionStringOutput)

  val listIntOutput: List[Int] = Functor[List].map(listInt)(_ + 1)
  println(listIntOutput)
  println(listInt.map(_ + 1))

  //  def lift[A, B](f: A => B): F[A] => F[B] = map(_)(f)
  println("\ndef lift[A, B](f: A => B): F[A] => F[B] = map(_)(f)")
  val add3: Int => Int = x => x + 3
  val add3List: List[Int] => List[Int] = Functor[List].lift(add3)
  val listIntPlus3: List[Int] = add3List(listInt)
  println(listIntPlus3)

}
