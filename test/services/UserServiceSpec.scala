package services

import connectors.BackendApiConnector
import generators.Generators
import models.{London, Miles, User}
import org.scalatest.{BeforeAndAfterEach, FreeSpec, MustMatchers}
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.concurrent.Future

class UserServiceSpec extends FreeSpec with MustMatchers with Generators with ScalaCheckPropertyChecks with MockitoSugar with ScalaFutures with BeforeAndAfterEach {

  private val mockConnector = mock[BackendApiConnector]

  override def beforeEach: Unit = {
    super.beforeEach
    reset(mockConnector)
  }

  private val service = new UserService(mockConnector)

  "UserServiceSpec" - {

    "`getUsers` method" - {

      "must return users" in {

        forAll(arbitrary(listOfUsers)) {
          userList =>

            when(mockConnector.get(Some("London"))).thenReturn(Future.successful(userList))

            service.getUsersByCity("London").futureValue mustBe
              userList
        }

      }

    }

    "`getUsersByDistance` method" - {

      "must return users when 50 miles of London" in {

        forAll(arbitrary[User]) {
          user =>

            val userWithinLondon = user.copy(
              latitude = 51.516755,
              longitude = -0.12428284
            )

            when(mockConnector.get(None)).thenReturn(Future.successful(List(userWithinLondon)))

            service.getUsersByDistance(50, Miles, London).futureValue mustBe
              List(userWithinLondon)
        }

      }

      "must not return users outside 50 miles of London" in {

        forAll(arbitrary[User]) {
          user =>

            val userOutsideLondon = user.copy(
              latitude = 52.276396,
              longitude = -0.228112
            )

            when(mockConnector.get(None)).thenReturn(Future.successful(List(userOutsideLondon)))

            service.getUsersByDistance(50, Miles, London).futureValue mustBe
              Nil
        }

      }

    }

  }

}
