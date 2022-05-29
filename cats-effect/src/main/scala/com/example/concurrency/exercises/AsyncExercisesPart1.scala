package com.example
package concurrency.exercises

import cats.effect.{IO, IOApp}

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.Try

object AsyncExercisesPart1 extends IOApp.Simple {

  type Callback[A] = Either[Throwable, A] => Unit
  val threadPool: ExecutorService = Executors.newFixedThreadPool(8)
  val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(threadPool)
  /**
   * Exercise: lift an async computation on ec to an IO.
   */
  def asyncToIO[A](computation: () => A)(ec: ExecutionContext): IO[A] = {
    IO.async_[A] { cb: Callback[A] =>
      ec.execute { () =>
        val result: Either[Throwable, A] = Try {
          computation()
        }.toEither
        cb(result)
      }
    }
  }

  val computation: () => Int = () => {
    println(s"[${Thread.currentThread.getName}] Computing for 1 second")
    Thread.sleep(1000)
    42
  }

  val computationAsyncToIO: IO[Int] = asyncToIO(computation)(ec)

  override def run: IO[Unit] = computationAsyncToIO.debug.void

}
