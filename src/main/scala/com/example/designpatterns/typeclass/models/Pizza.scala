package com.example.designpatterns.typeclass.models

case class Pizza (
                   crustSize: CrustSize,
                   crustType: CrustType,
                   toppings: Seq[Topping]
                 )
