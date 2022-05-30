package com.example
package concurrency.exercises

import cats.effect.kernel.Resource
import cats.effect.std.CountDownLatch
import cats.effect.{Deferred, IO, IOApp, Ref}
import cats.syntax.parallel._
import cats.syntax.traverse._

import java.io.{File, FileWriter}
import scala.collection.immutable.Queue
import scala.concurrent.duration.DurationInt
import scala.io.Source
import scala.util.Random

object CountDownLatchExercises extends IOApp.Simple {

  /**
   * Exercise: simulate a file downloader on multiple threads
   */
  object FileServer {

    val fileChunksList = Array(
      "I love Scala.",
      "Cats Effect seems quite fun.",
      "Never would I have thought I would do low-level concurrency WITH pure FP."
    )

    def getNumChunks: IO[Int] = IO(fileChunksList.length)
    def getFileChunk(n: Int): IO[String] = IO(fileChunksList(n))
  }

  def writeToFile(path: String, contents: String): IO[Unit] = {
    val fileResource = Resource.make(IO(new FileWriter(new File(path))))(writer => IO(writer.close()))
    fileResource.use { writer =>
      IO(writer.write(contents))
    }
  }

  def appendFileContents(fromPath: String, toPath: String): IO[Unit] = {
    val compositeResource = for {
      reader <- Resource.make(IO(Source.fromFile(fromPath)))(source => IO(source.close()))
      writer <- Resource.make(IO(new FileWriter(new File(toPath), true)))(writer => IO(writer.close()))
    } yield (reader, writer)

    compositeResource.use {
      case (reader, writer) => IO(reader.getLines().foreach(writer.write))
    }
  }

  def createFileDownloaderTask(id: Int, latch: CountDownLatch[IO], filename: String, destFolder: String): IO[Unit] = {
    for {
      _ <- IO(s"[task $id] downloading chunk...").debug
      _ <- IO.sleep(Random.nextInt(1000).millis)
      chunk <- FileServer.getFileChunk(id)
      _ <- writeToFile(s"$destFolder/$filename.part$id", chunk)
      _ <- IO(s"[task $id] chunk download complete").debug
      _ <- latch.release
    } yield ()
  }

  /*
    - call file server API and get the number of chunks (n)
    - start a CDLatch
    - start n fibers which download a chunk of the file (use the file server's download chunk API)
    - block on the latch until each task has finished
    - after all chunks are done, stitch the files together under the same file on disk
   */
  def downloadFile(filename: String, destFolder: String): IO[Unit] = {
    for {
      n <- FileServer.getNumChunks
      latch <- CountDownLatch[IO](n)
      _ <- IO(s"Download started on $n fibers.").debug
      chunks <- (0 until n).toList.parTraverse(id => createFileDownloaderTask(id, latch, filename, destFolder))
      _ <- latch.await
      _ <- (0 until n).toList.traverse(id => appendFileContents(s"$destFolder/$filename.part$id", s"$destFolder/$filename"))
    } yield {
      ()
    }
  }

  abstract class CDLatch {
    def await: IO[Unit]
    def release: IO[Unit]
  }

  object CDLatch {
    sealed trait State
    case object Done extends State
    case class Live(n: Int, signal: Deferred[IO, Unit]) extends State

    def apply(n: Int): IO[CDLatch] = for {
      signal <- Deferred.apply[IO, Unit]
      state <- Ref[IO].of[State](Live(n, signal))
    } yield {
      new CDLatch {
        def await: IO[Unit] = {
          state.get.flatMap { s =>
            if (s == Done) IO.unit
            else signal.get
          }
        }

        def release: IO[Unit] = {
          state.modify {
            case Done => Done -> IO.unit
            case Live(1, signal) => Done -> signal.complete(()).void
            case Live(n, signal) => Live(n-1, signal) -> IO.unit
          }
        }.flatten.uncancelable
      }
    }
  }

  // Note: You can replace CountDownLatch[IO] with the above implemented CDLatch and everything should work the same
  override def run: IO[Unit] = downloadFile("myScalafile.txt", "cats-effect/src/main/resources")
}
