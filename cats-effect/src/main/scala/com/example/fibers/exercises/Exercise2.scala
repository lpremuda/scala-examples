package com.example
package fibers.exercises

import cats.effect.kernel.Outcome._
import cats.effect.{IO, IOApp, Outcome}

import scala.concurrent.duration.DurationInt

object Exercise2 extends IOApp.Simple {

  /**
   *  2. Write a function that takes two IOs, runs them on different fibers and returns an IO with a tuple containing both results.
   *    - if both IOs complete successfully, tuple their results
   *    - if the first IO returns an error, raise that error (ignoring the second IO's result/error)
   *    - if the first IO doesn't error but second IO returns an error, raise that error
   *    - if one (or both) canceled, raise a RuntimeException
   */
  def tupleIOs[A, B](ioa: IO[A], iob: IO[B]): IO[(A, B)] = {
    val fibsComplete: IO[(Outcome[IO, Throwable, A], Outcome[IO, Throwable, B])] = for {
      fibA <- ioa.start
      fibB <- iob.start
      resultA <- fibA.join
      resultB <- fibB.join
    } yield (resultA, resultB)

    fibsComplete.flatMap {
      case (Succeeded(fa), Succeeded(fb)) => for {
          a <- fa
          b <- fb
        } yield (a, b)
      case (Errored(ea), _) => IO.raiseError(ea)
      case (_, Errored(eb)) => IO.raiseError(eb)
      case _ => IO.raiseError(new RuntimeException("Some computation cancelled"))
    }
  }

  def testEx2(): IO[(Int, Int)] = {
    val ioa = IO.sleep(2.seconds) >> IO(1).debug
    val iob = IO.sleep(3.seconds) >> IO(2).debug
    val ioe = IO.sleep(1.second) >> IO.raiseError(new RuntimeException("IO failed!"))
    tupleIOs(ioa, iob).debug
  }

  override def run: IO[Unit] = testEx2().void
}
