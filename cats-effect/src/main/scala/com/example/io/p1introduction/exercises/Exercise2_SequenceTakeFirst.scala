package com.example.io.p1introduction.exercises

import cats.effect.IO

object Exercise2_SequenceTakeFirst extends App {

  def sequenceTakeFirst[A, B](ioa: IO[A], iob: IO[B]): IO[A] = {
    for {
      a <- ioa
      _ <- iob
    } yield a
  }

  def sequenceTakeFirstFlatMap[A, B](ioa: IO[A], iob: IO[B]): IO[A] = {
    ioa.flatMap(a => iob.map(_ => a))
  }

  def sequenceTakeFirstFlatMap_v2[A, B](ioa: IO[A], iob: IO[B]): IO[A] = {
    ioa <* iob
  }

  val ioa: IO[Int] = IO.pure(42)

  val iob: IO[String] = IO {
    println("From iob")
    "hello"
  }

  val result = sequenceTakeFirst(ioa, iob).unsafeRunSync()
  println(result)

  val resultFlatMap = sequenceTakeFirstFlatMap(ioa, iob).unsafeRunSync()
  println(resultFlatMap)

  val resultRev = sequenceTakeFirst(iob, ioa).unsafeRunSync()
  println(resultRev)

  val resultFlatMapRev = sequenceTakeFirstFlatMap(iob, ioa).unsafeRunSync()
  println(resultFlatMapRev)

}
