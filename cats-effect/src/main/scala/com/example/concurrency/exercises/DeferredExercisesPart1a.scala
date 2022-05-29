package com.example

package concurrency.exercises

import cats.effect.{IO, IOApp, Ref}

import scala.concurrent.duration._

object DeferredExercisesPart1a extends IOApp.Simple {

  /**
   *  Exercise:
   *  - (medium) write a small alarm notification with two simultaneous IOs
   *    - one that increments a counter every second (a clock)
   *    - one that waits for the counter to become 10, then prints a message "time's up!"
   */
  def incrementCounter(counter: Ref[IO, Int]): IO[Unit] = {
    for {
      _ <- IO.sleep(1.second)
      _ <- IO("[increment] incrementing counter").debug
      _ <- counter.update(_ + 1)
      _ <- incrementCounter(counter)
    } yield ()
  }

  def checkCounter(counter: Ref[IO, Int]): IO[Unit] = {
    for {
      _ <- IO.sleep(500.millis)
      c <- counter.get
      _ <- IO(s"[check] counter value: $c").debug
      _ <- {
        if (c >= 10) IO("times's up!").debug.void
        else checkCounter(counter)
      }
    } yield ()
  }

  def program: IO[Unit] = {
    for {
      counter <- Ref[IO].of(0)

      _ <- IO("starting incrementCounter").debug
      incrementFib <- incrementCounter(counter).start
      _ <- IO("starting checkCounter").debug
      checkFib <- checkCounter(counter).start

      _ <- IO("joining checkCounter").debug
      _ <- checkFib.join
      _ <- IO("joining incrementCounter").debug
      _ <- incrementFib.join
    } yield ()
  }

  override def run: IO[Unit] = program
}
