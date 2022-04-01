package com.example.basics.ordered

import scala.util.Sorting

/*
  Classes that implement Ordered trait can be sorted with scala.util.Sorting and can be compared with standard comparison
  operators (< and >)

  ORDERED VS. ORDERING
  Ordered used for with a data with single, natural ordering
  vs.
  Ordering allows for multiple ordering implementations

  *****An Ordering instance will be implicitly created, if necessary*****

 */

object OrderedExercise extends App {

  case class OrderedClass(n: Int) extends Ordered[OrderedClass] {
    def compare(that: OrderedClass): Int = this.n - that.n
  }

  val x: Array[OrderedClass] = Array(OrderedClass(1), OrderedClass(5), OrderedClass(3))
  println("Unsorted")
  x.foreach(print)
  println()

  Sorting.quickSort(x)
  println("Sorted")
  x.foreach(print)

}
