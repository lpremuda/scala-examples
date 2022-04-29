package com.example.basics.implicitconversions

import scala.language.implicitConversions

/*
  Source: https://www.baeldung.com/scala/implicit-conversions
 */

object ImplicitConversionExampleCentimeters extends App {

  case class Centimeters(value: Double)
  case class Meters(value: Double)
  case class Kilometers(value: Double)

  implicit def metersToCentimeters(m: Meters): Centimeters =
    Centimeters(m.value * 100)

  implicit def metersToKilometers(m: Meters): Kilometers =
    Kilometers(m.value / 1000)

  // cm can be cast as Centimeters because the Scala compiler see the implicit def converting from Meters to Centimeters
  val cm: Centimeters = Meters(2.5)
  val km: Kilometers = Meters(2160)

  println(cm)
  println(km)

  // Note: If you made this class implicit, you could remove double2richSyntax, and everything would still work.
  class LengthSyntax(x: Double) {
    def centimeters: Centimeters = Centimeters(x)
    def meters: Meters = Meters(x)
    def kilometers: Kilometers = Kilometers(x)
  }

  implicit def double2richSyntax(value: Double): LengthSyntax = {
    new LengthSyntax(value)
  }

  val length: Double = 4.5

  println(length.centimeters)
  println(length.meters)
  println(length.kilometers)

}
