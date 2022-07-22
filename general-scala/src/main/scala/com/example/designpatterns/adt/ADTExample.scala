package com.example.designpatterns.adt

/**
 * This file shows an example of a data model (case class Message) representing an illegal state (i.e. "I want to go
 * to the movies!")
 */

sealed trait Communication
case class Message(str: String) extends Communication

object ADTExample extends App {

  val message1: Message = Message("Hello!")                             // valid state
  val message2: Message = Message("Goodbye!")                           // valid state
  val message3: Message = Message("What are you having for dinner?")    // valid state
  val message4: Message = Message("I want to go to the movies!")        // illegal state

  def respondToMessage(comm: Communication): String = {
    comm match {
      case Message("Hello!") => "Hello, what is your name?"
      case Message("Goodbye!") => "I'll see you tomorrow!"
      case Message("What are you having for dinner?") => "I'm going to have pizza!"
      // All illegal states will default to "case _"
      case _ => "What did you say?"
    }
  }

  println(respondToMessage(message1))
  println(respondToMessage(message2))
  println(respondToMessage(message3))
  println(respondToMessage(message4))

}

/*
  To avoid illegal states, we can restructure the data type to have the least amount of complexity it needs to model
  the information is carries.
*/

sealed trait CommunicationRestricted
case object Hello extends CommunicationRestricted
case object Goodbye extends CommunicationRestricted
case object HavingForDinner extends CommunicationRestricted

object ADTExampleRestricted extends App {

  val message1: Hello.type = Hello                              // valid state
  val message2: Goodbye.type = Goodbye                          // valid state
  val message3: HavingForDinner.type = HavingForDinner          // valid state

  // This also works
//  val message1: CommunicationRestricted = Hello               // valid state
//  val message2: CommunicationRestricted = Goodbye             // valid state
//  val message3: CommunicationRestricted = HavingForDinner     // valid state

  def respondToMessageRestricted(comm: CommunicationRestricted): String = {
    comm match {
      case Hello => "Hello, what is your name?"
      case Goodbye => "I'll see you tomorrow!"
      case HavingForDinner => "I'm going to have pizza!"
    }
  }

  println(respondToMessageRestricted(message1))
  println(respondToMessageRestricted(message2))
  println(respondToMessageRestricted(message3))


}