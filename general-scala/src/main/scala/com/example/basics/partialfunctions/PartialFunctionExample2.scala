package com.example.basics.partialfunctions

object PartialFunctionExample2 extends App {

  sealed trait Vehicle {
    def model: String
  }

  case class Volvo(safety: String) extends Vehicle { def model: String = "Volvo" }
  case object Corvette extends Vehicle { def model: String = "Corvette" }
  case object Minivan extends Vehicle { def model: String = "Minivan" }

  def getVehicleName(vehicle: Vehicle): String = vehicle match {
    case volvo: Volvo => s"${volvo.model}, and the safety is ${volvo.safety}"
    case corvette: Corvette.type => corvette.model
    case minivan: Minivan.type => minivan.model
  }

  val getVehicleName2: PartialFunction[Vehicle,String] = {
    case volvo: Volvo => s"${volvo.model}, it's awesome, and the safety is ${volvo.safety}"
    case corvette: Corvette.type => s"${corvette.model} is awesome"
    case minivan: Minivan.type => s"${minivan}.model} is also awesome"
  }

  println(getVehicleName(Corvette))
  println(getVehicleName(Volvo("excellent")))

  println(getVehicleName2(Corvette))
  println(getVehicleName2(Volvo("excellent")))

}
