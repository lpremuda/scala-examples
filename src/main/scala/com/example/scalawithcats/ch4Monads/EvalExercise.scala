package com.example.scalawithcats.ch4Monads

import cats.Eval

object EvalExercise extends App {

  val now: Eval[Double] = Eval.now(math.random + 1000)
  val always: Eval[Double] = Eval.always(math.random + 2000)
  val later: Eval[Double] = Eval.later(math.random + 3000)

  (0 to 1).toList.map(_ => println(now.value))
  (0 to 1).toList.map(_ => println(always.value))
  (0 to 1).toList.map(_ => println(later.value))

  println("\nSecond part")
  println("Declaring x")
  val x: Eval[Double] = Eval.now {
    println("Computing x")
    math.random + 1000
  }

  println("Declaring y")
  val y: Eval[Double] = Eval.always {
    println("Computing y")
    math.random + 2000
  }

  println("Declaring z")
  val z: Eval[Double] = Eval.later {
    println("Computing z")
    math.random + 3000
  }

  (0 to 1).toList.map(_ => println(x.value))
  (0 to 1).toList.map(_ => println(y.value))
  (0 to 1).toList.map(_ => println(z.value))

  /*
    Declaring x
    Computing x
    Declaring y
    Declaring z
    1000
    1000
    Computing y
    2000
    Computing y
    2234
    Computing z
    3000
    3000
   */

  def foldRight[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] = {
    as match {
      case head :: tail =>
        Eval.defer(fn(head, foldRight(tail, acc)(fn)))
      case _ =>
        acc
    }
  }

}
