package com.example.circe

case class School(name: String, schoolType: String)

object School {

  // semiauto derivation
  object SchoolJsonCodecSemiauto {

    import io.circe._
    import io.circe.generic.extras.semiauto._
    import com.example.circe.circeConfig

    implicit val schoolEncoder: Encoder[School] = deriveConfiguredEncoder[School]
    implicit val schoolDecoder: Decoder[School] = deriveConfiguredDecoder[School]

  }

  // custom
  object SchoolJsonCodecCustom {
    import io.circe._

    implicit val schoolEncoder: Decoder[School] = new Decoder[School] {
      final def apply(c: HCursor): Decoder.Result[School] = {
        for {
          name <- c.downField("name").as[String]
          schoolType <- c.downField("schoolType").as[String]
        } yield School(name, schoolType)
      }
    }

    implicit val schoolDecoder: Encoder[School] = new Encoder[School] {
      final def apply(sch: School): Json = {
        Json.obj(
          "name" -> Json.fromString(sch.name),
          "schoolType" -> Json.fromString(sch.schoolType)
        )
      }
    }
  }

}
