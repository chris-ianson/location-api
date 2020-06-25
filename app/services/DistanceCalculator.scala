package services

import models.{Coordinates, EarthRadius, Miles}

object DistanceCalculator {

  def apply(pointA: Coordinates, pointB: Coordinates, earthRadius: EarthRadius = Miles): Double = {

    val pointALatitude = Math.toRadians(pointB.latitude)
    val pointALongitude = Math.toRadians(pointB.longitude)
    val pointBLatitude = Math.toRadians(pointA.latitude)
    val pointBLongitude = Math.toRadians(pointA.longitude)

    // Haversine formula
    val longitude: Double = pointBLongitude - pointALongitude
    val latitude: Double = pointBLatitude - pointALatitude

    val a = Math.pow(Math.sin(latitude / 2), 2) +
      Math.cos(pointALatitude) *
        Math.cos(pointBLatitude) *
          Math.pow(Math.sin(longitude / 2),2)

    (2 * Math.asin(Math.sqrt(a))) * earthRadius.value
  }

}
