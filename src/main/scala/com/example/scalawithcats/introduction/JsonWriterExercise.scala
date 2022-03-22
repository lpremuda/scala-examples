package com.example.scalawithcats.introduction

object JsonWriterExercise extends App {

  case class Person(name: String, email: String)

  sealed trait Json
  final case class JsObject(get: Map[String, Json]) extends Json
  final case class JsString(get: String) extends Json
  final case class JsNumber(get: Double) extends Json
  final case object JsNull extends Json

  trait JsonWriter[A] {
    def write(value: A): Json
  }

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] = new JsonWriter[String] {
      def write(value: String): Json = JsString(value)
    }

    implicit val personWriter: JsonWriter[Person] = new JsonWriter[Person] {
      def write(value: Person): Json = {
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
      }
    }
  }

  object JsonSyntax {
    implicit class JsonWriterOps[A](value: A) {
      def toJson(implicit writer: JsonWriter[A]) = {
        writer.write(value)
      }
    }
  }

  object Json {
    def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json = {
      writer.write(value)
    }
  }

  import JsonWriterInstances._
  val lucas: Person = Person("Lucas", "lucas@aol.com")
  val lucasJson: Json = Json.toJson(lucas)
  println(lucasJson)

  import JsonSyntax._
  val lucasJsonUsingSyntax: Json = lucas.toJson
  println(lucasJsonUsingSyntax)

  // Implicit method to construct instances (e.g. JsonWriter[Option[A]]) from other type class instances (e.g. JsonWriter[A])
  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = {
    new JsonWriter[Option[A]] {
      def write(option: Option[A]): Json = {
        option match {
          case Some(aValue) => writer.write(aValue)
          case None => JsNull
        }
      }
    }
  }

  val optionJson: Json = Json.toJson(Option("Optional string"))
  // Implicit resolution is doing this:
//val optionJson: Json = Json.toJson(Option("Optional string"))(optionWriter[String])
  println(optionJson)

  val noneJson: Json = Json.toJson(Option(""))
  println(noneJson)

}
