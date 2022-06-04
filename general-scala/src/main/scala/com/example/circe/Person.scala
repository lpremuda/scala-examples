package com.example.circe

import com.example.circe.AlertStatus._

case class Person(
  name: String,
  age: Int,
  isMale: Boolean,
  school: Option[School] = None,
  status: Option[AlertStatus] = None
)

object Person {

  import io.circe._
  import io.circe.generic.extras.semiauto._

  // Choose only one of the two imports below to import implicit Decoder[_] and Encoder[_]
  import School.SchoolJsonCodecSemiauto._
//  import School.SchoolJsonCodecCustom._

  implicit val personEncoder: Encoder[Person] = deriveConfiguredEncoder[Person]
//  implicit val personDecoder: Decoder[Person] = deriveConfiguredDecoder[Person]
  implicit val personDecoder: Decoder[Person] = new Decoder[Person] {
    final def apply(c: HCursor): Either[DecodingFailure, Person] = {
      for {
        name <- c.downField("name").as[String]
        age <- c.downField("age").as[Int]
        isMale <- c.downField("is_male").as[Boolean]
        school <- c.downField("school").as[Option[School]]
        status <- c.downField("status").as[Option[String]]
      } yield {
        val alertStatus: Option[AlertStatus] = status.map {
          case "AlertFound" => AlertFound
          case "NoAlert" => NoAlert
          case "NoDataFound" => NoDataFound
          case _ => throw new RuntimeException("Invalid string")
        }
        Person(name, age, isMale, school, alertStatus)
      }
    }
  }

  lazy val Empty: Person = Person("", 0, isMale = true, None)

}
