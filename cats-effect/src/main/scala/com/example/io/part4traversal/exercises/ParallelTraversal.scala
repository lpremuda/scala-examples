package com.example.io.part4traversal.exercises

import cats.Traverse
import cats.effect.{IO, IOApp}

object ParallelTraversal extends IOApp.Simple {

  val listTraverse = Traverse[List]

  def parSequence[A](listOfIOs: List[IO[A]]): IO[List[A]] = {
    import cats.syntax.parallel._
    listOfIOs.parTraverse(x => x)
  }

  def parSequence_v2[F[_] : Traverse, A](wrapperOfIOs: F[IO[A]]): IO[F[A]] = {
    import cats.syntax.parallel._
    wrapperOfIOs.parTraverse(x => x)
  }

  override def run = IO.unit
}
