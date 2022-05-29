package com.example
package concurrency.exercises

import cats.effect.{Deferred, IO, IOApp, Ref}

import scala.concurrent.duration._

object DeferredExercisesPart1b extends IOApp.Simple {

  /**
   *  Exercise:
   *  - (medium) write a small alarm notification with two simultaneous IOs
   *    - one that increments a counter every second (a clock)
   *    - one that waits for the counter to become 10, then prints a message "time's up!"
   */
  def incrementCounter(counter: Ref[IO, Int], signal: Deferred[IO, Int]): IO[Unit] = {
    for {
      _ <- IO.sleep(1.second)
      _ <- IO("[increment] incrementing counter").debug
      latestCount <- counter.updateAndGet(_ + 1)
      _ <- {
        if (latestCount >= 10) IO("[increment] times's up!").debug >> signal.complete(latestCount)
        else incrementCounter(counter, signal)
      }
    } yield ()
  }

  def checkCounter(counter: Ref[IO, Int], signal: Deferred[IO, Int]): IO[Unit] = {
    for {
      _ <- IO("[check] waiting for signal.get")
      c <- signal.get
      _ <- IO(s"[check] counter value: $c").debug
      _ <- IO("[check] stopping checkCounter").debug
    } yield ()
  }

  def program: IO[Unit] = {
    for {
      counter <- Ref[IO].of(0)
      signal <- Deferred.apply[IO, Int]

      _ <- IO("[program] starting increment fiber").debug
      incrementFib <- incrementCounter(counter, signal).start
      _ <- IO("[program] starting check fiber").debug
      checkFib <- checkCounter(counter, signal).start

      _ <- IO("[program] waiting for increment fiber to finish").debug
      _ <- incrementFib.join
      _ <- IO("[program] waiting for check fiber to finish").debug
      _ <- checkFib.join

    } yield ()
  }

  override def run: IO[Unit] = program
}
