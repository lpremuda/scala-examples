package com.example
package resources.exercises

import cats.effect.kernel.Resource
import cats.effect.{IO, IOApp}

import java.io.{File, FileReader}
import java.util.Scanner

import scala.concurrent.duration.DurationInt

object ResourceExercises extends IOApp.Simple {

  /**
   * Exercise: Read a text file with one line every 100 millis, using Resource
   * (i.e. refactor the bracket exercise to use Resource)
   */

    def createFileResource(path: String): Resource[IO, Scanner] = {
      Resource.make(
        IO("opening scanner").debug >> IO(new Scanner(new FileReader(new File(path))))
      )(scanner => IO("closing scanner").debug >> IO(scanner.close())

      )
    }

    val fileResource: Resource[IO, Scanner] =
      createFileResource("cats-effect/src/main/scala/com/example/resources/Resources.scala")

    val readFile: IO[Unit] = fileResource.use( scanner => {
      def readLine: IO[Unit] = {
        if (scanner.hasNextLine) IO(scanner.nextLine()).debug >> IO.sleep(100.millis) >> readLine
        else IO.unit
      }

      readLine
    })

  override def run: IO[Unit] = readFile
}
