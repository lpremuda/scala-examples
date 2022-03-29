package com.example.monix.observables

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.reactive.{Consumer, Observable}

object ObservablePractice extends TaskApp {

  /*
    ReactiveX Observable explanation: https://reactivex.io/documentation/observable.html

    Observable will pass through the generated items to the Observer by calling onNext.

    The observer "subscribes" to an Observable and "reacts" to whatever items or sequence of items the Observable "emits"
    This pattern uses concurrent operations because it does not need to block while waiting for the Observable to emit objects

    Creating an Observable implements the subscribe method.
    The subscribe method passes each element to its subscribers by calling its "onNext" method.
    Once the sequence is empty, the Observable calls "onComplete".

    Observable must wait for the Future[Ack] result before it can pass the next element
      Ack can either be:
        1. Continue (ok to send the next element)
        2. Stop (we should shut down)

    In obs below, the following sequence happens:
    (1) fromIterable calls map1.onNext(i)
    (2) map1 does some transformation and then calls map2.onNext(i + 2)
    (3) map2 does some transformation and then calls sum.onNext(i * 3)
    (4) sum saves and acknowledges the incoming items and holds off on calling firstL.onNext until it receives an onComplete signal
    (5) firstL waits for the first onNext signal to complete a Task

   */

  val dataSource: Range.Inclusive = 1 to 3
  val obs: Observable[Int] = Observable
    .fromIterable(dataSource) // creates an Observable. Hence, it implements subscribe. The subscribe method passes each element to its subscribers by calling its onNext method
    .map(i => {
      println("i + 2")
      i + 2
    })
    .map(i => {
      println("i * 3")
      i + 3
    })
    .sum

  val element: Task[Int] = obs.firstL

  val list: Observable[Long] = Observable
    .range(1, 1000)
    .take(10)
    .map(_ * 2)

  val consumer: Consumer[Long, Long] =
    Consumer.foldLeft(0L)(_ + _)

  // Consuming an Observable with a consumer returns a Task
  val task: Task[Long] =
    list.consumeWith(consumer)

  val observableOfStrings: Observable[String] = Observable.fromIterable((List("Hello","world!", "From", "Lucas")))
  val printStrings: Task[Unit] = observableOfStrings.foreachL(println)

  def run(args: List[String]): Task[ExitCode] = {
    for {
      num <- element
      _ <- Task { println(num) }
      long <- task
      _ <- Task { println(long) }
      _ <- printStrings
    } yield ExitCode.Success
  }

}
