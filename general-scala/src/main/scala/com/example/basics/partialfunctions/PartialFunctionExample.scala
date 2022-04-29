package com.example.basics.partialfunctions

object PartialFunctionExample extends App {

  val divide: PartialFunction[Int,Int] = new PartialFunction[Int,Int] {
    def apply(x: Int): Int = 42 / x
    def isDefinedAt(x: Int) = x != 0
  }

  println(divide.isDefinedAt(3))

  def runPartialFunction[A,B](pf: PartialFunction[A,B], value: A): Option[B] = {
    if (pf.isDefinedAt(value)) Some(pf(value))
    else None
  }

  println(runPartialFunction(divide, 7).getOrElse("Value not in scope for the partial function"))
  println(runPartialFunction(divide, 0).getOrElse("Value not in scope for the partial function"))

  val divide2: PartialFunction[Int,Int] = {
    case x: Int if x != 0 => 42 / x
  }

  println(divide2(3))
  println(divide2.isDefinedAt(30))
  println(divide2.isDefinedAt(0))

}
