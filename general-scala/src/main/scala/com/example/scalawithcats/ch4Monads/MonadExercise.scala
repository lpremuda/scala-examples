package com.example.scalawithcats.ch4Monads

import cats.{Id, Monad}
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.instances.option._
//import cats.instances.list._

object MonadExercise extends App {

  def sumSquare[F[_]](a: F[Int], b: F[Int])(implicit m: Monad[F]): F[Int] = {
    a.flatMap(x => b.map(y => x*x + y*y))
  }

  println(sumSquare[Option](Option(2), Option(3)))
  println(sumSquare(List(1,2), List(3,4,5)))

  // Implicitly calls catsInstancesforId, which is a type Monad[Id], in cats/package.scala
  println(sumSquare(3 : Id[Int],4 : Id[Int]))

  val num: Int = 3

  val id: Id[Int] = 3: Id[Int]
  println(id)
  val id2: Id[Int] = id.flatMap(x => x * 3)
  println(id2)

  val id3: Int = id.flatMap(x => x * 3)
  println(id3)

  // Now that id3 is an Int and not Id[Int], there is no flatMap method available
//val id4: Int = id3.flatMap(x => x + 2)

}
