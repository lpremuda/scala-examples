package com.example.monix.observables

import monix.eval.Task
import monix.reactive.Observable
import monix.execution.Scheduler

object ObservableFromTaskPractice extends App {

  val scheduler: Scheduler = Scheduler.io()

  val task1: Task[List[Int]] = Task { List(1,2,3,4,5) }

  val observable1: Observable[List[Int]] = Observable.fromTask(task1)

  val observable2: Observable[List[Int]] = observable1.map(_.map(_ + 2))

  observable2.foreach(listInt => {
      println("foreach")
      listInt.foreach(x => {
        println(s"Printing $x")
        println(x)
      })
  })(scheduler)

}
