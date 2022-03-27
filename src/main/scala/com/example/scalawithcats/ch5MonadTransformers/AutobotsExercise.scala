package com.example.scalawithcats.ch5MonadTransformers

import cats.data.EitherT
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object AutobotsExercise extends App {

  // For reference:
  // final case class EitherT[F[_], A, B](value: F[Either[A, B]]) {

  // Return this:
//type Response[A] = Future[Either[String, A]]
  // As this:
  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz"      -> 6,
    "Bumblebee" -> 8,
    "Hot Rod"   -> 10
  )

  def getPowerLevel(ally: String): Response[Int] =
    powerLevels.get(ally) match {
      case Some(num) => EitherT.right(Future(num))
      case None => EitherT.left(Future(s"$ally unreachable"))
    }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for {
      powerLevel1 <- getPowerLevel(ally1)
      powerLevel2 <- getPowerLevel(ally2)
    } yield (powerLevel1 + powerLevel2) > 15

  def tacticalReport(ally1: String, ally2: String): String = {
    val stackInter: Response[Boolean] = canSpecialMove(ally1, ally2)
    val stack: Future[Either[String, Boolean]] = stackInter.value

    Await.result(stack, 1.second) match {
      case Left(msg) => s"Comms error: $msg"
      case Right(false) => s"$ally1 and $ally2 need to recharge"
      case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
    }
  }

}
