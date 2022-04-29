package com.example.basics.fileio

import java.io.File

object FileIOExampleUtils {

  def printFileInfo(file: File): Unit = {
    println(s"Printing info for $file")
    println(s"getName(): ${file.getName}")
    println(s"getParent(): ${file.getParent}")
    println(s"getParentFile(): ${file.getParentFile}")
    println(s"isAbsolute(): ${file.isAbsolute}")
    println(s"getAbsolutePath(): ${file.getAbsolutePath}")
    println(s"exists(): ${file.exists}")
    println(s"isDirectory(): ${file.isDirectory}")
    println(s"isFile(): ${file.isFile}")
    println(s"listFiles(): ${file.listFiles}")
    println(s"toString(): ${file.toString}")
    println()
  }

}
