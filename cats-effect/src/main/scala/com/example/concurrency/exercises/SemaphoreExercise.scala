package com.example
package concurrency.exercises

import cats.effect.std.Semaphore
import cats.effect.{IO, IOApp}
import cats.syntax.parallel._

import scala.concurrent.duration._
import scala.util.Random

object SemaphoreExercise extends IOApp.Simple {

  // example: limiting the number of concurrent sessions on a server
  def doWorkWhileLoggedIn(): IO[Int] = IO.sleep(1.second) >> IO(Random.nextInt(100))

  val mutex: IO[Semaphore[IO]] = Semaphore[IO](1)
  def users(sem: Semaphore[IO]): IO[List[Int]] = (1 to 10).toList.parTraverse { id =>
    for {
      _ <- IO(s"[session $id] waiting to log in...").debug
      _ <- sem.acquire
      // critical section
      _ <- IO(s"[session $id] logged in, working...").debug
      res <- doWorkWhileLoggedIn()
      _ <- IO(s"[session $id] done: $res, logging out...").debug
      // end of critical section
      _ <- sem.release
    } yield res
  }

  override def run: IO[Unit] = for {
    sem <- mutex
    _ <- users(sem)
  } yield ()
}
