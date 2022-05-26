package com.example
package fibers

import cats.effect.kernel.Outcome._
import cats.effect.{Fiber, IO, IOApp, Outcome}

import scala.concurrent.duration._

object Fibers extends IOApp.Simple {

  val meaningOfLife = IO.pure(42)
  val favLang = IO.pure("Scala")

  def sameThreadIOs() = for {
    _ <- meaningOfLife.debug
    _ <- favLang.debug
  } yield ()

  // The fiber is not actually started, but the fiber allocation is wrapped in another effect (i.e. IO)
  // Note: 2nd type argument can be anything, it doesn't have to be a Throwable
  val aFiber: IO[Fiber[IO, Throwable, Int]] = meaningOfLife.debug.start

  def differentThreadIOs() = for {
    _ <- aFiber
    _ <- favLang.debug
  } yield ()

  // Joining a fiber
  def runOnSomeOtherThread[A](io: IO[A]): IO[Outcome[IO, Throwable, A]] = for {
    fib <- io.start
    result <- fib.join // also an effect that when performed, we be waiting on the fiber to terminate
  } yield result

  val someIOOnAnotherThread = runOnSomeOtherThread(meaningOfLife)
  val someResultFromAnotherThread = someIOOnAnotherThread.flatMap {
    case Succeeded(effect) => effect
    case Errored(e) => IO(0)
    case Canceled() => IO(0)
  }

  def throwOnAnotherThread() = for {
    fib <- IO.raiseError[Int](new RuntimeException("no number for you")).start
    result <- fib.join
  } yield result

  def testCancel() = {
    val task: IO[String] = IO("starting").debug >> IO.sleep(1.second) >> IO("done").debug
    val taskWithCancellationHandler = task.onCancel(IO("cancelling").debug.void)

    for {
      fib <- taskWithCancellationHandler.start        // running on separate thread
      _ <- IO.sleep(500.millis) >> IO("cancel").debug // running on calling thread
      _ <- fib.cancel                                 // running on calling thread
      result <- fib.join                              // running on calling thread
    } yield result
  }

//  override def run = sameThreadIOs()
//  override def run = differentThreadIOs()
//  override def run = runOnSomeOtherThread(meaningOfLife).debug.void
//  override def run = throwOnAnotherThread().debug.void
  override def run = testCancel().debug.void


}
