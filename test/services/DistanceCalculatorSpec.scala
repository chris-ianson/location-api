package services

import models.{Coordinates, Kilometers}
import org.scalatest.{FreeSpec, MustMatchers}

class DistanceCalculatorSpec extends FreeSpec with MustMatchers {

  object TestCoordinatesPointA extends Coordinates {
    val latitude = 53.32055555555556
    val longitude = -1.7297222222222221
  }

  object TestCoordinatesPointB extends Coordinates {
    val latitude = 53.31861111111111
    val longitude = -1.6997222222222223
  }

  "DistanceCalculatorSpec" - {

    "work out distance between 2 points" - {
      "in miles" in {
        DistanceCalculator(TestCoordinatesPointA, TestCoordinatesPointB) mustBe 1.2445894158220931
      }

      "in kms" in {
        DistanceCalculator(TestCoordinatesPointA, TestCoordinatesPointB, Kilometers) mustBe 2.004367838271627
      }

    }

  }

}
