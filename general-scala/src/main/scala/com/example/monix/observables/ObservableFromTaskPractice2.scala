package com.example.monix.observables

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.reactive.Observable

object ObservableFromTaskPractice2 extends TaskApp {

  // Create Task
  val task1: Task[List[Int]] = Task { List(1,2,3,4,5) }

  // Create Observable from Task
  val observable1: Observable[List[Int]] = Observable.fromTask(task1)

  // Map Observable from another Observable
  val observable2: Observable[List[Int]] = observable1.map(_.map(_ + 2))

  def run(args: List[String]): Task[ExitCode] = {
    Task(println("hello")).map(_ => ExitCode.Success)
  }

}
