package com.example.basics.multithreading

object ExecutorServicePart1a {

  def main(args: Array[String]): Unit = {
    // Print out the name of the main (calling) thread
    println(mainMethodString)

    val thread1: Thread = new Thread(new Task())
    thread1.start()
  }

}
