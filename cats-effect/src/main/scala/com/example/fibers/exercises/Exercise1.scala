package com.example
package fibers.exercises

import cats.effect.kernel.Outcome._
import cats.effect.{IO, IOApp, Outcome}

import scala.concurrent.duration.DurationInt

object Exercise1 extends IOApp.Simple {

  /**
   * 1. Write a function that runs an IO on another thread, and, depending on the result of the fiber
   *      - return the result in an IO
   *      - if errored or canceled, return a failed IO
   */
  def processResultsFromFiber[A](io: IO[A]): IO[A] = {
    val fibComplete: IO[Outcome[IO, Throwable, A]] = for {
      fib <- io.debug.start
      result <- fib.join
    } yield result

    fibComplete.flatMap {
      case Succeeded(fa) => fa
      case Errored(e) => IO.raiseError(e)
      case Canceled() => IO.raiseError(new RuntimeException("Computation canceled"))
    }
  }

  def testEx1(): IO[Int] = {
    val aComputation: IO[Int] = IO("starting").debug >> IO.sleep(1.second) >> IO("done").debug >> IO(42)
    processResultsFromFiber(aComputation)
  }

  override def run: IO[Unit] = testEx1().void
}
