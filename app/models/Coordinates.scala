package models

trait Coordinates {
  val latitude: Double
  val longitude: Double
}

object London extends Coordinates {
  val latitude = 51.509865
  val longitude = -0.118092
}

case class UserCoordinates(latitude: Double, longitude: Double) extends Coordinates
