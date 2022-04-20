package com.example.monix.observables

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.reactive.Observable

object BuildingAnObservable extends TaskApp {

  val obsNow: Observable[String] = Observable.now { println("Effect"); "Hello!" }
  val taskNow: Task[Unit] = obsNow.foreachL(println)

  val obsDelay: Observable[String] = Observable.delay { println("Effect"); "Hello!" }
  val taskDelay: Task[Unit] = obsDelay.foreachL(println)

  val obsEvalOnce: Observable[String] = Observable.evalOnce { println("Effect"); "Hello!" }
  val taskEvalOnce: Task[Unit] = obsEvalOnce.foreachL(println)

  val obsIterable: Observable[Int] = Observable.fromIterable(List(1,2,3))
  val taskIterable: Task[Unit] = obsIterable.foreachL(x => print(s"$x ")).map(_ => println())

  def run(args: List[String]): Task[ExitCode] = {
    for {
      _ <- Task { println("\nRunning taskNow twice...       (like a val)") }
      _ <- taskNow
      _ <- taskNow

      _ <- Task { println("\nRunning taskDelay twice...     (like a def)") }
      _ <- taskDelay
      _ <- taskDelay

      _ <- Task { println("\nRunning taskEvalOnce twice...  (like a lazy val)") }
      _ <- taskEvalOnce
      _ <- taskEvalOnce

      _ <- Task { println("\nRunning taskIterable twice...") }
      _ <- taskIterable
      _ <- taskIterable
    } yield ExitCode.Success
  }
}

/*
Running taskEvalOnce twice...
Effect
Hello
Hello
 */