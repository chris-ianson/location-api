package models

sealed trait EarthRadius {
  val value: Int
}

object Miles extends EarthRadius {
  val value = 3956
}

object Kilometers extends EarthRadius {
  val value = 6371
}
