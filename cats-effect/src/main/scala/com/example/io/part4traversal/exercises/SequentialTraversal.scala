package com.example.io.part4traversal.exercises

import cats.Traverse
import cats.effect.{IO, IOApp}

object SequentialTraversal extends IOApp.Simple {

  val listTraverse = Traverse[List]

  def sequence[A](listOfIOs: List[IO[A]]): IO[List[A]] = {
    listTraverse.traverse(listOfIOs)(x => x)
  }

  def sequence_v2[F[_] : Traverse, A](wrapperOfIOs: F[IO[A]]): IO[F[A]] = {
    Traverse[F].traverse(wrapperOfIOs)(x => x)
  }

  override def run = IO.unit
}
