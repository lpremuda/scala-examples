package com.example.cats.resources

import scala.io.Source

object ExampleWithoutResource extends App {

    println("Creating source (BufferedSource)")
    val source = Source.fromResource("src/main/resources/test.txt")
    println("mkString")
    val lines = source.mkString
    println("Closing source")
    source.close()
    println("Result:")
    println(lines)

}
