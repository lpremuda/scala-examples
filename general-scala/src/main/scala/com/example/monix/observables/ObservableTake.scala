package com.example.monix.observables

import monix.eval.Task
import monix.reactive.Observable
import monix.execution.Scheduler.Implicits.global

object ObservableTake extends App {

  val stream: Observable[Int] = Observable(10,1,2,3,4,5)

  val grabHead: Task[Int] = stream.headL
  val tail: Observable[Int] = stream.tail

  val mainTask: Task[Unit] =
    for {
      head <- grabHead
      _ <- tail.map(_ * head).foreachL(println)
    } yield ()
//    stream
////      .range(2, 6)
//      .tail
//      .foreachL(println)

  mainTask.runToFuture

}
