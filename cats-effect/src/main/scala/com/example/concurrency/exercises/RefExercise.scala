package com.example
package concurrency.exercises

import cats.effect.{IO, IOApp, Ref}

import scala.concurrent.duration._

object RefExercise extends IOApp.Simple {

  import cats.syntax.parallel._

  /**
   * Exercise: Refactor tickingClockImpure() to use Ref (i.e. make it pure)
   */
  def tickingClockImpure(): IO[Unit] = {
    var ticks: Long = 0L
    def tickingClock: IO[Unit] = for {
      _ <- IO.sleep(1.second)
      _ <- IO(System.currentTimeMillis()).debug
      _ <- IO(ticks += 1) // not thread safe
      _ <- tickingClock
    } yield ()

    def printTicks: IO[Unit] = for {
      _ <- IO.sleep(5.seconds)
      _ <- IO(s"TICKS: $ticks").debug
      _ <- printTicks
    } yield ()

    for {
      _ <- (tickingClock, printTicks).parTupled
    } yield ()
  }

  def tickingClockPure(): IO[Unit] = {

    def tickingClock(ticks: Ref[IO, Int]): IO[Unit] = for {
      _ <- IO.sleep(1.second)
      _ <- IO(System.currentTimeMillis()).debug
      _ <- ticks.update(_ + 1) // thread safe effect
      _ <- tickingClock(ticks)
    } yield ()

    def printTicks(ticks: Ref[IO, Int]): IO[Unit] = for {
      _ <- IO.sleep(5.seconds)
      ticksValue <- ticks.get
      _ <- IO(s"TICKS: $ticksValue").debug
      _ <- printTicks(ticks)
    } yield ()

    for {
      ticks <- Ref[IO].of(0)
      _ <- (tickingClock(ticks), printTicks(ticks)).parTupled
    } yield ()
  }
  override def run: IO[Unit] = tickingClockImpure()
}
