//package com.example.classes.impl.main
//
//import cats.effect.Sync
//
////object GenericContainer extends App {
//
////  implicit class WithSyncSyntax[F[_], A](fa: F[A])(implicit F: Sync[F]) {
////
////    def withMdc(map: Map[String, String]): F[A] =
////      F.bracket {
////        F.delay {
////          val map: Map[String,String] = Map("name" -> "Lucas")
////          map
////        }
////      } { _ =>
////        // run the code
////        fa
////      } {
////          oldContext = F.delay(oldContext)
////      }
////
////    def withMdc(key: String, value: String): F[A] = withMdc(Map(key -> value))
////  }
//
//}
