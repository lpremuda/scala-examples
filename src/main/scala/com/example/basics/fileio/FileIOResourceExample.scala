package com.example.basics.fileio

import cats.effect.{ExitCode, Resource}
import monix.eval.{Task, TaskApp}
import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

object FileIOResourceExample extends TaskApp {

  // Get HOME environment variable
  val home = System.getProperty("user.home")

  val logDir: File = new File(home, "log_output")
  if (!logDir.exists) logDir.mkdir()

  val outFile: File = new File(logDir, "output.txt")

  // Create BufferedWriter Resource
  val bufferedWriterResource = Resource
    .make(Task {
      println("Initializing BufferedWriter...")
      new BufferedWriter(new FileWriter(outFile, true))
    })(
      release = bufferedWriter =>
        Task {
          println("Closing BufferedWriter...")
          bufferedWriter.close()
        }
    )

  def createWriteTask(message: String): Task[Unit] = bufferedWriterResource.use(bufferedWriter =>
    Task {
      println("Write to file...")
      bufferedWriter.write(s"$message\n")
    }
  )

  val task1: Task[Unit] = createWriteTask("This is line 1")
  val task2: Task[Unit] = createWriteTask("This is the next line")

  def run(args: List[String]): Task[ExitCode] = {
    for {
      _ <- task1
      _ <- task2
    } yield ExitCode.Success
  }

}
