package com.example.basics.multithreading

import java.time.LocalDateTime
import java.util.concurrent.{ExecutorService, Executors}

object ExecutorServicePart2cSingleTP {

  def main(args: Array[String]): Unit = {
    val nTasks = 20

    // Print out the name of the main (calling) thread
    println(mainMethodString)

    // Create a single thread pool
    // Underlying call: new FinalizableDelegatedExecutorService(
    //              new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
    //            );
    val service: ExecutorService = Executors.newSingleThreadExecutor()

    // Create sequence of tasks with numbers 0-9
    val tasks: Seq[Task] = (0 to nTasks-1).map(new Task(_))
    val t0 = System.nanoTime()
    tasks.foreach(service.execute(_))
    val t1 = System.nanoTime()
    println("Shutting down executor service...")
    service.shutdown()
    val elapsedTimeUs: Double = ((t1 - t0)  / 1000).doubleValue
    println(s"Total time: ${elapsedTimeUs} us")

  }

}
