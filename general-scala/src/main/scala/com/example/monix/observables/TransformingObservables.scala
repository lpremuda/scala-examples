package com.example.monix.observables

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.reactive.Observable

object TransformingObservables extends TaskApp {

  val parallelism = 2

  val f: Long => String = el => {
    println(s"Perform f on $el")
    s"new elem: ${el * 2}"
  }

  val fTask: Long => Task[String] = el => Task {
    println(s"Perform fTask on $el")
    s"new elem: ${el * 2}"
  }

  val stream: Observable[Long] = Observable.range(1,10)

  // map
  val stream_map: Observable[String] = stream.map(f)

  // mapEval
  val stream_mapEval: Observable[String] = stream.mapEval(fTask)

  // mapParallelOrdered
  // Note: parallelism=1 is equivalent to using mapEval
  val stream_mapParallelOrdered: Observable[String] = stream.mapParallelOrdered(parallelism)(fTask)

  // mapParallelOrdered
  // Note: parallelism=1 is equivalent to using mapEval
  // Note: See TransformingObservablesExample for example

  // flatMap
  val stream_flatMap: Observable[String] = stream.flatMap(
    el => Observable(s"${el}A", s"${el}B")
  )

  def run(args: List[String]): Task[ExitCode] = {
    for {
      _ <- Task { println("stream") }
      _ <- stream.foreachL(println)
      _ <- Task { println("\nstream.map") }
      _ <- stream_map.foreachL(println)
      _ <- Task { println("\nstream.mapEval") }
      _ <- stream_mapEval.foreachL(println)
      _ <- Task { println("\nstream.mapParallelOrdered") }
      _ <- stream_mapParallelOrdered.foreachL(println)
      _ <- Task { println("\nstream.flatMap") }
      _ <- stream_flatMap.foreachL(println)
    } yield ExitCode.Success
  }

}
