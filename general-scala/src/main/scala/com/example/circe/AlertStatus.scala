package com.example.circe

import io.circe._
import io.circe.syntax._
import cats.syntax.functor._

sealed trait AlertStatus {
  def key: String = "status"
  def value: String
}

object AlertStatus {

  case object AlertFound extends AlertStatus {
    def value: String = "AlertFound"
  }

  case object NoAlert extends AlertStatus {
    def value: String = "NoAlert"
  }

  case object NoDataFound extends AlertStatus {
    def value: String = "NoDataFound"
  }

  implicit def alertStatusEncoder[A <: AlertStatus]: Encoder[A] = Encoder.instance[A] { a =>
   Json.fromString(a.value)
//    Json.obj(a.key -> Json.fromString(a.value))
  }

//  implicit val alertFoundEncoder: Encoder[AlertFound.type] = Encoder.instance[AlertFound.type] { status =>
//    Json.obj(status.key -> Json.fromString(status.value))
//  }
//
//  implicit val noAlertEncoder: Encoder[NoAlert.type] = Encoder.instance[NoAlert.type] { status =>
//    Json.obj(status.key -> Json.fromString(status.value))
//  }
//
//  implicit val noDataFoundEncoder: Encoder[NoDataFound.type] = Encoder.instance[NoDataFound.type] { status =>
//    Json.obj(status.key -> Json.fromString(status.value))
//  }

  implicit val alertStatusDecoder: Decoder[AlertStatus] = Decoder.instance[AlertStatus] { (c: HCursor) =>
    Right(NoDataFound)
//    {
//      c.downField("status")
//        .as[String]
//        .map {
//          case "AlertFound"  => AlertFound
//          case "NoAlert"     => NoAlert
//          case "NoDataFound" => NoDataFound
//          case _             => throw new RuntimeException("dfsdf")
//        }
//    }
  }

//  implicit val alertFoundDecoder: Decoder[AlertStatus] = Decoder.instance[AlertStatus] {
//  implicit def alertStatusDecoder[A <: AlertStatus]: Decoder[A] = Decoder.instance[A] { (c: HCursor) =>
//    {
//      for {
//        str <- c.downField("status").as[String]
//        //      } yield str match {
//        //        case "AlertFound" => AlertFound
//        //        case "NoAlert" => NoAlert
//        //        case "NoDataFound" => NoDataFound
//        //        case _ => throw new RuntimeException("dfsdf")
//        //      }
//      } yield AlertFound
//    }
//  }
//  }

//  implicit val alertStatusEncoder: Encoder[AlertStatus] = {
//    Encoder.instance[AlertStatus] {
//      case AlertFound  => Json.obj(key -> Json.fromString("AlertFound"))
//      case NoAlert     => Json.obj(key -> Json.fromString("NoAlert"))
//      case NoDataFound => Json.obj(key -> Json.fromString("NoDataFound"))
//    }
//  }

//  implicit val alertFoundEncoder: Encoder[AlertFound.type] = Encoder.instance[AlertFound.type] {
//    _ => Json.obj(key -> Json.fromString("AlertFound"))
//  }
//  implicit def alertStatusEncoder: Encoder[AlertStatus] = {
//    val alertFoundEncoder: Encoder[AlertFound.type] = Encoder.instance[AlertFound.type] { _ =>
//      Json.obj(key -> Json.fromString("AlertFound"))
//    }
//
//    val noAlertEncoder: Encoder[NoAlert.type] = Encoder.instance[NoAlert.type] { _ =>
//      Json.obj(key -> Json.fromString("NoAlert"))
//    }
//
//    val noDataFoundEncoder: Encoder[NoDataFound.type] = Encoder.instance[NoDataFound.type] { _ =>
//      Json.obj(key -> Json.fromString("NoDataFound"))
//    }
//
//    Encoder.instance[AlertStatus] {
//      case af @ AlertFound   => af.asJson(alertFoundEncoder.widen)
//      case na @ NoAlert      => na.asJson(noAlertEncoder)
//      case ndf @ NoDataFound => ndf.asJson(noDataFoundEncoder)
//    }
//  }

}
