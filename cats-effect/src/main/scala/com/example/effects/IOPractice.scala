package com.example.effects

import cats.effect.{ExitCode, IO, IOApp}

object IOPractice extends IOApp {
  val a = IO(println("df"))

  val pureIO = IO.pure(42)
  val delayIO = IO.delay({
    println("Printing out 3")
    3
  })

  val pureIOBad = IO.pure({
    println("Printing out 5")
    5
  })

  def test[A](thunk: => A): Function0[A] = {
    val fn: Function0[A] = () => thunk
    fn
  }


  def run(args: List[String]): IO[ExitCode] = {
    pureIO.map(_ => ExitCode.Success)
  }
}
