package com.example
package fibers.exercises

import cats.effect.kernel.Outcome._
import cats.effect.{IO, IOApp, Outcome}

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object Exercise3 extends IOApp.Simple {

  /**
   *  3. Write a function that adds a timeout to an IO:
   *    - IO runs on a fiber
   *    - if the timeout duration passes, then the fiber is canceled
   *    - the method returns an IO[A] which contains
   *      - the original value if the computation is successful before the timeout signal
   *      - the exception if the computation is failed before the timeout signal
   *      - a RuntimeException if it times out (i.e. cancelled by the timeout)
   */
  def timeout[A](io: IO[A], duration: FiniteDuration): IO[A] = {
    val fibComplete: IO[Outcome[IO, Throwable, A]] = for {
      fib <- io.start
      _ <- (IO.sleep(duration) >> fib.cancel).start // careful because resources within fiber could leak
      result <- fib.join
    } yield result

    fibComplete.flatMap {
      case Succeeded(fa) => fa
      case Errored(e) => IO.raiseError(e)
      case Canceled() => IO.raiseError(new RuntimeException("Cancelled by the timeout"))
    }
  }

  def testEx3(): IO[Unit] = {
    val io: IO[Int] = IO("starting").debug >> IO.sleep(1.seconds) >> IO("done!").debug >> IO(1)
    val ioe = IO.sleep(1.second) >> IO.raiseError(new RuntimeException("IO failed!"))
    timeout(io, 5.seconds).debug.void
  }

  override def run: IO[Unit] = testEx3()
}
