package com.example.io.p1introduction.exercises

import cats.effect.IO

object Exercise3_Forever extends App {

  def forever[A](io: IO[A]): IO[A] = {
    io.flatMap(_ => forever(io))
  }

  def forever_v2[A](io: IO[A]): IO[A] = {
    io >> forever_v2(io) // >> evaluates lazily
  }

  // Explanation:
  // The way chained IO.FlatMap instances are evaluated is through a tail recursive algorithm
  // When you run the forever function, you are effectively creating an infinite linked list of IO chains, and Cats
  // Effect evaluates the linked list of instructions in a tail recursive fashion

  def forever_v3[A](io: IO[A]): IO[A] = {
    io *> forever_v3(io) // *> evaluates eagerly, so it will throw StackOverflowError
  }

  def forever_v4[A](io: IO[A]): IO[A] = {
    io.foreverM
  }

  // It appears you can't do a for comprehension for this
  //  def foreverForComp[A](io: IO[A]): IO[A] = {
  //    for {
  //      _ <- io
  //    } yield forever(io) // this will yield IO[IO[A]]
  //  }

  val io: IO[Int] = IO {
    println("Running IO")
    Thread.sleep(500)
    3
  }

  forever_v4(io).unsafeRunSync()

}
