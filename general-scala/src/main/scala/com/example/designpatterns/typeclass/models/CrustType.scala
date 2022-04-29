package com.example.designpatterns.typeclass.models

sealed trait CrustType

object CrustType {
  case object BrooklynStyle extends CrustType
  case object PanCrust extends CrustType
  case object ThinCrust extends CrustType
}
