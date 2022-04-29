package com.example.basics.multithreading

import java.util.concurrent.{ExecutorService, Executors}

object ExecutorServicePart2aCachedTP {

  def main(args: Array[String]): Unit = {
    val nTasks = 100

    // Print out the name of the main (calling) thread
    println(s"Thread Name: ${Thread.currentThread.getName} [main]")

    // Create a cached thread pool
    // Underlying call: new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>())
    val service: ExecutorService = Executors.newCachedThreadPool()

    // Create sequence of new tasks
    val tasks: Seq[Task] = Seq.fill(nTasks)(new Task())

    // For each task, execute in thread pool
    tasks.foreach(service.execute(_))
  }

}
