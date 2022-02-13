package com.example.designpatterns.typeclass.models

sealed trait CrustSize

object CrustSize {
  case object LargeCrust extends CrustSize
  case object MediumCrust extends CrustSize
  case object SmallCrust extends CrustSize
}
