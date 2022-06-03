package com.example.circe

case class Person(
  name: String,
  age: Int,
  isMale: Boolean,
  school: Option[School] = None
)

object Person {

  import io.circe._
  import io.circe.generic.semiauto._

  // Choose only one of the two imports below to import implicit Decoder[_] and Encoder[_]
  import School.SchoolJsonCodecSemiauto._
//  import School.SchoolJsonCodecCustom._

  implicit val encoder: Encoder[Person] = deriveEncoder[Person]
  implicit val decoder: Decoder[Person] = deriveDecoder[Person]

  lazy val Empty: Person = Person("", 0, isMale = true, None)

}
