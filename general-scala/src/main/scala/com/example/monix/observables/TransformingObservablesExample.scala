package com.example.monix.observables

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.reactive.Observable

import scala.concurrent.duration.DurationInt

object TransformingObservablesExample extends TaskApp {

  val stream: Observable[String] =
    Observable
      .range(1, 5)
      .mapParallelOrdered(parallelism = 3)(el => {
        for {
          _ <- Task {
                print(s"$el: starting asynchronously")
              }
          _ <- {
            if (el % 2 == 0) {
              println(" - sleep 4 second")
              Task.sleep(4.second)
            } else {
              println(" - sleep 1 second")
              Task.sleep(1.second)
            }
          }
        } yield el.toString
      })

  val task: Task[Unit] =
    stream
      .foreachL(el => println(el + ": done"))

  def run(args: List[String]): Task[ExitCode] = {
    for {
      _ <- task
    } yield ExitCode.Success
  }

}
