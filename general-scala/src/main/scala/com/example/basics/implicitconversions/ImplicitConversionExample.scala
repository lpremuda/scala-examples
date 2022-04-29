package com.example.basics.implicitconversions

import scala.language.implicitConversions

/*

  Implicit conversions give the Scala compiler the ability to convert from one type to another

  Source: https://docs.scala-lang.org/tour/implicit-conversions.html

  2 limitations of implicit conversions:
    1. They cannot take multiple non-implicit arguments
    2. They cannot chain multiple implicit conversions

  2 ways to turn off Scala compiler warnings
    1. import scala.language.implicitConversions
    2. Invoke the compiler with -language:implicitConversions
        For example, in sbt, do this:
            scalacOptions += "-language:implicitConversions"

  Warning looks like this:
    scalac: there was one feature warning; re-run with -feature for details

  Code will not run when Scala compiler issues a warning

 */

object ImplicitConversionExample extends App {

  // Define a function that must take a String as an input parameter
  def printString(str: String): Unit =
    println(str)

  // This implicit function will be called by the Scala compiler
  implicit def intToString(i: Int): String =
    i.toString

  // Scala compiler will recognize that an Int is being passed into a function that accepts a String and will look for
  // an implicit conversion from Int to String
  printString(45)

}
