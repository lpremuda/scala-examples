package com.example
package io.part4traversal.exercises

import cats.Traverse
import cats.effect.{IO, IOApp}
import scala.util.Random

object TraversalExample extends IOApp.Simple {

  def computeAsIO(string: String): IO[Int] = {
    IO {
      Thread.sleep(Random.nextInt(1000))
      string.split(" ").length
    }.debug
  }

  val listTraverse = Traverse[List]
  val workload: List[String] = List("I quite like CE", "Scala is cool", "Looking forward to some awesome stuff")

  val ios: List[IO[Int]] = workload.map(computeAsIO)

  // Existing sequence API (the exercises mimic the implementation of this)
  val singleIO_v2: IO[List[Int]] = listTraverse.sequence(ios)

  // Existing sequence API (the exercises mimic the implementation of this)
  import cats.syntax.parallel._
  val parallelSingleIO_v3: IO[List[Int]] = ios.parSequence

  override def run: IO[Unit] = {
    parallelSingleIO_v3.map(_.sum).debug.void
  }

}
