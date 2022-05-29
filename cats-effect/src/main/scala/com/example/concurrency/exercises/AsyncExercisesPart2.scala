package com.example
package concurrency.exercises

import cats.effect.{IO, IOApp}

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util._

object AsyncExercisesPart2 extends IOApp.Simple {

  type Callback[A] = Either[Throwable, A] => Unit
  val threadPool: ExecutorService = Executors.newFixedThreadPool(8)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(threadPool)

  /**
   * Exercise: lift an async computation as a Future to an IO.
   */
  def asyncFutureToIO[A](future: => Future[A])(implicit ec: ExecutionContext): IO[A] = {
    // (Either[Throwable, A] => Unit) => Unit
    IO.async_[A] { cb: Callback[A] =>
      future.onComplete(tryResult => {
        val result: Either[Throwable, A] = tryResult.toEither
        cb(result)
      })
    }
  }

  def computeMeaningOfLife(): Int = {
    Thread.sleep(1000)
    println(s"[${Thread.currentThread().getName}] computing the meaning of life on some other thread...")
    42
  }

  lazy val molFuture: Future[Int] = Future { computeMeaningOfLife() }

  val computationAsyncFutureToIO: IO[Int] = asyncFutureToIO(molFuture)

  override def run: IO[Unit] = computationAsyncFutureToIO.debug.void

}
