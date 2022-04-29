package com.example.basics

import java.time.LocalDateTime

package object multithreading {

  val mainMethodString = s"<${LocalDateTime.now().toLocalTime}> [main] Thread Name: ${Thread.currentThread.getName}"

}
