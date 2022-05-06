package com.example.io.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object SequenceTakeLast extends App {

  def sequenceTakeLast[A, B](ioa: IO[A], iob: IO[B]): IO[B] = {
    for {
      _ <- ioa
      b <- iob
    } yield b
  }

  def sequenceTakeLastFlatMap[A, B](ioa: IO[A], iob: IO[B]): IO[B] = {
    ioa.flatMap(_ => iob.map(identity))
  }

  val ioa: IO[Int] = IO.pure(42)
  val iob: IO[String] = IO {
    println("From iob")
    "hello"
  }

  val result = sequenceTakeLast(ioa, iob).unsafeRunSync()
  println(result)

  val result2 = sequenceTakeLastFlatMap(ioa, iob).unsafeRunSync()
  println(result2)

  val result3 = sequenceTakeLast(iob, ioa).unsafeRunSync()
  println(result3)

  val result4 = sequenceTakeLastFlatMap(iob, ioa).unsafeRunSync()
  println(result4)

}
