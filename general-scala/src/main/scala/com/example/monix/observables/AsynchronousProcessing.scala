package com.example.monix.observables

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.execution.Cancelable
import monix.reactive.{Observable, OverflowStrategy}
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.duration.DurationInt
import scala.util.Success

object AsynchronousProcessing extends App {

  val stream: Observable[Unit] = Observable("A", "B", "C", "D")
    .mapEval(
      i =>
        Task {
          println(s"1: Processing $i")
          i ++ i
        }
    )
//    .asyncBoundary(OverflowStrategy.BackPressure(2))
    .mapEval(
      i =>
        Task {
          println(s"2: Processing $i")
        }.delayExecution(200.millis)
    )

  stream.subscribe()
  stream.subscribe()
//  Task.create { (scheduler, callback) =>
//    f.onComplete({
//      case Success(value) =>
//        callback.onSuccess(value)
//      case Failure(ex) =>
//        callback.onError(ex)
//    })(scheduler)
//
//    // Scala Futures are not cancelable, so
//    // we shouldn't pretend that they are!
//    Cancelable.empty
//  }


  //  def run(args: List[String]): Task[ExitCode] = {
//    for {
//
//      _ <- stream.
//    } yield ExitCode.Success
//  }

}
