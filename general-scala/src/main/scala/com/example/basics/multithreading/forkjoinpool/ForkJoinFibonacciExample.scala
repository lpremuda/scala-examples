package com.example.basics.multithreading.forkjoinpool

import java.util.concurrent.{ExecutorService, Executors, ForkJoinPool, ForkJoinTask, RecursiveTask}

object ForkJoinFibonacciExample extends App {

  private class Fibonacci(val n: Int) extends RecursiveTask[Int] {
    def compute: Int = {
      if (n <= 1) n
      else {
        val f1: Fibonacci = new Fibonacci(n-1)
        f1.fork()
        val f2: Fibonacci = new Fibonacci(n-2)
        f2.fork()
        f1.join() + f2.join()
      }
    }
  }

  // You could run by directly calling the compute method
  println(new Fibonacci(6).compute)

  // Or create a ForkJoinPool to invoke in
  val forkJoinPool: ForkJoinPool = new ForkJoinPool()

  // Create new ForkJoinTask (superclass of RecursiveTask)
  val newFibonacciTask: ForkJoinTask[Int] = new Fibonacci(7)

  // Have the ForkJoinPool invoke
  val fibResult: Int = forkJoinPool.invoke(newFibonacciTask)
  println(s"fibResult = ${fibResult}")

}
