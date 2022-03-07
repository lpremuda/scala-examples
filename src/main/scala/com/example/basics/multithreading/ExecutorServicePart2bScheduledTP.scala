package com.example.basics.multithreading

import java.time.LocalDateTime
import java.util.concurrent.{ExecutorService, Executors, ScheduledExecutorService, TimeUnit}

object ExecutorServicePart2bScheduledTP {

  def main(args: Array[String]): Unit = {
    val nThreads = 10

    // Print out the name of the main (calling) thread
    println(mainMethodString)

    // Create a scheduled thread pool
    // Underlying call: new ScheduledThreadPoolExecutor(corePoolSize)
    //    super(corePoolSize, Integer.MAX_VALUE, DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS, new DelayedWorkQueue())
    val service: ScheduledExecutorService = Executors.newScheduledThreadPool(nThreads)

    // Schedule a task to run after 3 second delay
    service.schedule(new Runnable {
        def run: Unit = println(s"<${LocalDateTime.now().toLocalTime}> Thread Name: ${Thread.currentThread.getName} [3s delay]")
      },
      3,
      TimeUnit.SECONDS
    )

    // Schedule a task to run after 2 second delay, continuing running every 4 seconds after that
    service.scheduleAtFixedRate(new Runnable {
        def run: Unit = println(s"<${LocalDateTime.now().toLocalTime}> Thread Name: ${Thread.currentThread.getName} [2s initial delay, 4s period]")
      },
      2,
      4,
      TimeUnit.SECONDS
    )

    service.scheduleWithFixedDelay(new Runnable {
      def run: Unit = {
        println(s"<${LocalDateTime.now().toLocalTime}> Thread Name: ${Thread.currentThread.getName} [2s initial delay, 4s period]")
        Thread.sleep(1000)
      }
    },
      5,
      3,
      TimeUnit.SECONDS
    )

  }

}
