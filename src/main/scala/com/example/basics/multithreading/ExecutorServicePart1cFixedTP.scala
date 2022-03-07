package com.example.basics.multithreading

import java.util.concurrent.{ExecutorService, Executors}

object ExecutorServicePart1cFixedTP {

  def main(args: Array[String]): Unit = {
    val nThreads = 10
    val nTasks = 20
    // If you wish to based the number of threads off of the number of available processors
    val numCores: Int = Runtime.getRuntime.availableProcessors()
    println(s"Number of available processors (cores): ${numCores}")

    // Print out the name of the main (calling) thread
    println(mainMethodString)

    // Create a fixed thread pool
    // Underlying call: new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    val service: ExecutorService = Executors.newFixedThreadPool(nThreads)

    val tasks: Seq[Task] = (0 to nTasks-1).map(new Task(_))
//    val tasks: Seq[Task] = Seq.fill(nTasks)(new Task())
    tasks.foreach(service.execute(_))
  }

}
