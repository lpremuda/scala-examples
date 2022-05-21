package com.example.io.p1introduction.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.example.io.p1introduction.exercises.Exercise4_Convert.convert

object Exercise5_AsUnit extends App {

  def asUnit[A](io: IO[A]): IO[Unit] = {
    io.map(_ => ())
  }

  def asUnit_v2[A](io: IO[A]): IO[Unit] = {
    io.as(())
  }

  def asUnit_v3[A](io: IO[A]): IO[Unit] = {
    convert(io, ())
  }

  def asUnit_v4[A](io: IO[A]): IO[Unit] = {
    io.void // encourage implementation
  }

  def asUnitForComp[A](io: IO[A]): IO[Unit] = {
    for {
      _ <- io
    } yield ()
  }

  val io: IO[Int] = IO {
    println("Running IO")
    42
  }

  val newIO: IO[Unit] = asUnit(io)
  val newIOForComp: IO[Unit] = asUnitForComp(io)
  val newIO_v2: IO[Unit] = asUnit_v2(io)
  val newIO_v3: IO[Unit] = asUnit_v3(io)

  val result: Unit = newIO.unsafeRunSync()
  println(result)

  val resultForComp: Unit = newIOForComp.unsafeRunSync()
  println(resultForComp)

  val result_v2: Unit = newIO_v2.unsafeRunSync()
  println(result_v2)

  val result_v3: Unit = newIO_v3.unsafeRunSync()
  println(result_v3)

}
