package com.example.circe

import io.circe._
import io.circe.parser.{decode, parse}
import io.circe.syntax._

object CirceExample extends App {

  val school: School = School("San Marcos", "elementary")

  val lucas: Person = Person("Lucas Premuda", 30, true, Option(school))
  val chelsea: Person = Person("Chelsea Wieland", 27, false)

  /*
    final def asJson(implicit encoder: Encoder[A]): Json = encoder(value)
   */
  val lucasJson: Json = lucas.asJson

  /*
    Decodes Json value to A
    final def as[A](implicit d: Decoder[A]): Decoder.Result[A] = d(hcursor)
   */
  val lucasJsonDecodeResult: Either[DecodingFailure, Person] = lucasJson.as[Person]

  val lucasJsonDecoded: Person = lucasJsonDecodeResult.fold(
    decodingFailure => throw new RuntimeException(s"A decoding failure has occurred: $decodingFailure"),
    identity
  )

  println("Example decoding Person object to json string:")
  println("-----------------------------------------------------------------------------")

  println(s"Person object:\n$lucas\n")

  println(s"Person object (Json.toString):\n$lucasJson\n")

  println(s"Json.noSpaces:\n${lucasJson.noSpaces}\n")

  println(s"Json.spaces2:\n${lucasJson.spaces2}\n")

  println(s"Json.spaces4:\n${lucasJson.spaces4}\n")

  println(s"lucasJsonDecoded:\n$lucasJsonDecoded\n")

  val jsonString: String =
    """{
      |  "name":"John Smith",
      |  "age":45,
      |  "isMale":true,
      |  "school": {
      |    "name":"San Ynez",
      |    "school_type":"high school"
      |  }
      |}
      |""".stripMargin

  val json: Json = parse(jsonString).fold(
    parsingFailure => throw new RuntimeException(s"A parsing failure has occurred: $parsingFailure"),
    identity
  )

  val john: Person = decode[Person](jsonString).fold(
    decodingFailure => throw new RuntimeException(s"A decoding failure has occurred: $decodingFailure"),
    identity)

  println("Example encoding json string to Person object:")
  println("-----------------------------------------------------------------------------")

  println(s"json string:\n$jsonString\n")

  println(s"Json object:\n$json\n")

  println(s"Person object:\n$john\n")

  val dropout: Person = Person("Tanner Jackson", 19, isMale = true, None)

  println("Example decoding Person object with Option set to None:")
  println("-----------------------------------------------------------------------------")

  println(s"Person object:\n$dropout\n")

  println(s"Json.spaces2:\n${dropout.asJson.spaces2}\n")

  println(s"Json.spaces2 (deepDropNullValues:\n${dropout.asJson.deepDropNullValues.spaces2}\n")

}
