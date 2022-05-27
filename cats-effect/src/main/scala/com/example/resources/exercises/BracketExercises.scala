package com.example

package resources.exercises

import cats.effect.{IO, IOApp}

import java.io.{File, FileReader}
import java.util.Scanner
import scala.concurrent.duration.DurationInt

object BracketExercises extends IOApp.Simple {

  /**
   * Exercise: read the file with the bracket pattern
   *    - open a scanner
   *    - read the file line by line, every 100 millis
   *    - close the scanner
   *    - if cancelled/throws error, close the scanner
   */

  def openFileScanner(path: String): IO[Scanner] = {
    IO(new Scanner(new FileReader(new File(path))))
  }

  def readLineByLine(scanner: Scanner): IO[Unit] = {
    if (scanner.hasNextLine) IO(scanner.nextLine()).debug >> IO.sleep(100.millis) >> readLineByLine(scanner)
    else IO.unit
  }

  /*
    IO(scanner.nextLine()).debug

    for {
      a <- IO(scanner.nextLine())
      t = Thread.currentThread().getName
      _ = println(s"[$t] $a")
    } yield a

   */


  def bracketReadFile(path: String): IO[Unit] = {
    IO(s"opening file at $path") >>
      openFileScanner(path)
        .bracket(scanner => {
          IO("starting scan").debug >> readLineByLine(scanner)
        })(scanner => {
          IO(s"closing file at $path").debug >> IO(scanner.close())
        })
  }

  override def run: IO[Unit] = bracketReadFile("cats-effect/src/main/scala/com/example/resources/Resources.scala")
}
