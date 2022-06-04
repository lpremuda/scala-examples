package com.example.circe

import com.example.circe.AlertStatus._
import io.circe.Json
import io.circe.syntax._
import io.circe.parser.decode

object CirceADTExample extends App {

  val json: Json = AlertFound.asJson

  val jsonString: String = json.spaces2

  println(jsonString)

  println(NoAlert.asJson.spaces2)

  val noAlertString: String =
    """
      |{
      |  "status" : "NoDataFound"
      |}
      |""".stripMargin

  val noAlertDecoded: AlertStatus = decode[AlertStatus](noAlertString).fold(
    decodingError => throw new RuntimeException(s"Decoding error occurred: $decodingError"),
    identity
  )

  println(noAlertDecoded)
  println(noAlertDecoded == NoDataFound)
  println(noAlertDecoded == NoAlert)


}
