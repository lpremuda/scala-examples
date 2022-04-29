package com.example.basics.fileio

import FileIOExampleUtils._
import java.io.{BufferedWriter,File,FileWriter,PrintWriter}

object FileIOExample extends App {

  // Get HOME environment variable
  val home = System.getProperty("user.home")

  val logDir: File = new File(home, "log_output")
  val pwFile: File = new File(home, "testFolder/hello.txt" )

  printFileInfo(logDir)
  logDir.mkdirs()
  printFileInfo(logDir)

  printFileInfo(pwFile)

  // PrintWriter
  val pw = new PrintWriter(pwFile)
  pw.write("Hello, world")
  pw.close

  printFileInfo(pwFile)

  // FileWriter
  val canonicalFilename = "fileio_example.txt"
  val text = "This is a test"
  val file = new File(canonicalFilename)
  val bw = new BufferedWriter(new FileWriter(file))
  bw.write(text)
  bw.close()

}
