package com.example.io.part1introduction.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object Exercise4_Convert extends App {

  def convert[A, B](ioa: IO[A], value: B): IO[B] = {
    ioa.map(_ => value)
  }

  def convert_v2[A, B](ioa: IO[A], value: B): IO[B] = {
    ioa.as(value)
  }

  def convertForComp[A, B](ioa: IO[A], value: B): IO[B] = {
    for {
      _ <- ioa
    } yield value
  }

  val ioa: IO[Int] = IO(42)
  val value = "Lucas"

  val iob: IO[String] = convert(ioa, value)
  val iobForComp: IO[String] = convertForComp(ioa, value)

  val result = iob.unsafeRunSync()
  println(result)

  val resultForComp = iob.unsafeRunSync()
  println(resultForComp)

}
