package com.example.io.p1introduction.exercises

import cats.effect.IO

object Exercise7_Fibonacci extends App {

  // n      = 1, 2, 3, 4, 5, 6, 7,  8
  // result = 1, 1, 2, 3, 5, 8, 13, 21

  def fibonacci(n: Int): IO[BigInt] = {
    if (n <= 2) IO(1)
    else
      for {
        last <- IO.defer(fibonacci(n - 1)) // same as IO.delay(...).flatten
        prev <- IO.defer(fibonacci(n - 2)) // same as IO.delay(...).flatten
      } yield last + prev
  }

  val fib5: IO[BigInt] = fibonacci(5)
  val fib6: IO[BigInt] = fibonacci(6)
  val fib7: IO[BigInt] = fibonacci(7)

  println(fib5.unsafeRunSync())
  println(fib6.unsafeRunSync())
  println(fib7.unsafeRunSync())

  (1 to 10).foreach(i => println(fibonacci(i).unsafeRunSync()))

}
