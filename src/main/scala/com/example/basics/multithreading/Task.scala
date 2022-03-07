package com.example.basics.multithreading

import java.time.LocalDateTime

class Task(val number: Int = 0) extends Runnable {
  val formattedNumber: String = {
    if (number < 10) s"00${number}"
    else if (number < 100) s"0${number}"
    else s"${number}"
  }
  def run: Unit = println(s"<${LocalDateTime.now().toLocalTime}> [Task] Task ${formattedNumber}, Thread Name: ${Thread.currentThread().getName()}")
}
