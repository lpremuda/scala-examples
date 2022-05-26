package com.example
package io.part3parallelism

import cats.Parallel
import cats.effect.{IO, IOApp}

object IOParallelism extends IOApp.Simple {

  val aniIO: IO[String] = IO(s"[${Thread.currentThread().getName}] Ani")
  val kamranIO: IO[String] = IO(s"[{${Thread.currentThread().getName}] Kamran")

  // Run sequentially
  val composedIO: IO[String] = for {
    ani <- aniIO
    kamran <- kamranIO
  } yield s"$ani and $kamran love each other"

  // For mapN extension method
  import cats.syntax.apply._
  val intIO: IO[Int] = IO.delay(42)
  val stringIO: IO[String] = IO.delay("Scala")
  // Run sequentially where first element in Tuple is always run first
  val composedIO_v2: IO[String] = (intIO.debug, stringIO.debug).mapN((num, string) => s"Number is $num and string is $string")

  // Parallel
  val parIO1: IO.Par[Int] = Parallel[IO].parallel(intIO.debug)
  val parIO2: IO.Par[String] = Parallel[IO].parallel(stringIO.debug)
  import cats.effect.implicits._
  val composedIOParallel: IO.Par[String] = (parIO1, parIO2).mapN((num, string) => s"Number is $num and string is $string")
  // Turn back into sequential
  val composedIOSequential = Parallel[IO].sequential(composedIOParallel)

  import cats.syntax.parallel._
  val composedIOParallel_v2: IO[String] = (intIO.debug, stringIO.debug).parMapN((num, string) => s"Number is $num and string is $string")

  override def run: IO[Unit] = {
    composedIOParallel_v2.debug.map(println)
  }

}
