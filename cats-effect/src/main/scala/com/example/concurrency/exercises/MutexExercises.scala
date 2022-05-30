package com.example
package concurrency.exercises

import cats.effect
import cats.effect.kernel.Deferred
import cats.effect.kernel.Outcome.{Canceled, Errored, Succeeded}
import cats.effect.{Concurrent, IO, IOApp, Ref}

import scala.collection.immutable.Queue
import scala.concurrent.duration._
import scala.util.Random

import cats.syntax.parallel._


abstract class Mutex {
  def acquire: IO[Unit]
  def release: IO[Unit]
}

object Mutex {
  type Signal = Deferred[IO, Unit]
  case class State(locked: Boolean, waiting: Queue[Signal])
  val unlocked = State(locked = false, Queue())

  def createSignal(): IO[Signal] = Deferred[IO, Unit]

  def create: IO[Mutex] = Ref[IO].of(unlocked).map(createMutexWithCancellation)

  def createMutexWithCancellation(state: Ref[IO, State]): Mutex = {
    new Mutex {
      override def acquire: IO[Unit] = {

        val alreadyUnlocked = for {
          st <- state.get
        } yield st match {
          case State(false, _) => state.update(_ => State(true, Queue()))
          case State(true, queue) => ???
        }
      }

      override def release: IO[Unit] = {

      }
    }
  }
}

object MutexPlayground extends IOApp.Simple {

  def criticalTask(): IO[Int] = IO.sleep(1.second) >> IO(Random.nextInt(100))

  def createNonLockingTask(id: Int): IO[Int] = for {
    _ <- IO(s"[task $id] working...").debug
    res <- criticalTask()
    _ <- IO(s"[task $id] got result: $res").debug
  } yield res

  def demoNonLockingTasks(): IO[List[Int]] = (1 to 10).toList.parTraverse(id => createNonLockingTask(id))

  def createLockingTask(id: Int, mutex: MutexV2[IO]): IO[Int] = for {
    _ <- IO(s"[task $id] waiting for permission...").debug
    _ <- mutex.acquire // blocks if the mutex has been acquired by some other fiber
    // critical section
    _ <- IO(s"[task $id] working...").debug
    res <- criticalTask()
    _ <- IO(s"[task $id] got result: $res").debug
    // critical section end
    _ <- mutex.release
    _ <- IO(s"[task $id] lock removed.").debug
  } yield res

  def demoLockingTasks() = for {
    mutex <- MutexV2.create[IO]
    results <- (1 to 10).toList.parTraverse(id => createLockingTask(id, mutex))
  } yield results
  // only one task will proceed at one time

  def createCancellingTask(id: Int, mutex: MutexV2[IO]): IO[Int] = {
    if (id % 2 == 0) createLockingTask(id, mutex)
    else for {
      fib <- createLockingTask(id, mutex).onCancel(IO(s"[task $id] received cancellation!").debug.void).start
      _ <- IO.sleep(2.seconds) >> fib.cancel
      out <- fib.join
      result <- out match {
        case Succeeded(effect) => effect
        case Errored(_) => IO(-1)
        case Canceled() => IO(-2)
      }
    } yield result
  }

  def demoCancellingTasks() = for {
    mutex <- MutexV2.create[IO]
    results <- (1 to 10).toList.parTraverse(id => createCancellingTask(id, mutex))
  } yield results

  override def run = demoCancellingTasks().debug.void
}
