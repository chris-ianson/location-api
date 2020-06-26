package services

import com.google.inject.{Inject, Singleton}
import connectors.BackendApiConnector
import models._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserService @Inject()(connector: BackendApiConnector) {

  def getUsersByCity(city: String): Future[List[User]] = {
    connector.get(Some(city))
  }

  def getUsersByDistance(within: Int, units: EarthRadius, target: Coordinates): Future[List[User]] = {
    connector.get(None).map {
      users =>

        val usersFiltered = users.filter(user => {

          val coordinates = UserCoordinates(user.latitude, user.longitude)

          DistanceCalculator(coordinates, target) <= within
        })

        usersFiltered
    }
  }

}
