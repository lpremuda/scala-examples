package com.example.monix.observables

import monix.eval.Task
import monix.execution.schedulers.TestScheduler
import monix.reactive.Observable

import scala.concurrent.duration._


object SchedulingIntervalWithFixedDelay extends App {

  // using `TestScheduler` to manipulate time
  implicit val sc = TestScheduler()

  val stream: Task[Unit] = {
    Observable
      .intervalWithFixedDelay(2.second)
      .mapEval(l => Task.sleep(2.second).map(_ => l))
      .foreachL(println)
  }

  stream.runToFuture(sc)

  sc.tick(2.second) // prints 0
  sc.tick(4.second) // prints 1
  sc.tick(4.second) // prints 2
  sc.tick(4.second) // prints 3

}
