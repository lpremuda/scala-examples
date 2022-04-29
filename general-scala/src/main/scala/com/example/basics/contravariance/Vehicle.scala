package com.example.basics.contravariance

sealed class Vehicle

class Car extends Vehicle
class Truck(val hasTruckBed: Boolean) extends Vehicle

class Volvo extends Car
class Corvette extends Car

class F150 extends Truck(true)
class Yukon extends Truck(false)
class Tacoma extends Truck(true)
