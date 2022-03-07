package com.example.basics.multithreading

object ExecutorServicePart1b {

  def main(args: Array[String]): Unit = {
    val nThreads = 15

    // Print out the name of the main (calling) thread
    println(mainMethodString)

    val threads: Seq[Thread] = Seq.fill(nThreads)(new Thread(new Task()))
    threads.foreach(_.start())
  }

}
