package com.example.circe

import com.example.circe.AlertStatus._
import io.circe.syntax.EncoderOps
import io.circe.parser.decode

object CirceADTExample2 extends App {

  val person1 = Person("Jack Johnson", 40, isMale = true, Some(School("Rock Hill", "elementary")), Some(AlertFound))

  println(s"person1:\n${person1.asJson.spaces2}")
  println()

  val jsonString =
    """
      |{
      |  "name" : "Jack Johnson",
      |  "age" : 40,
      |  "is_male" : true,
      |  "school" : {
      |    "name" : "Rock Hill",
      |    "school_type" : "elementary"
      |  },
      |  "status" : "NoAlert"
      |}
      |""".stripMargin

  val person2 = decode[Person](jsonString).fold(
    err => throw new RuntimeException(s"Decoding failure: $err"),
    identity
  )

  println(s"person2:\n$person2")

}
