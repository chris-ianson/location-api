package controllers

import models.User
import org.mockito.Mockito.{reset, _}
import org.mockito.ArgumentMatchers.any
import org.scalatest.{BeforeAndAfterEach, FreeSpec, MustMatchers}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import services.UserService

import scala.concurrent.Future

class ApiControllerSpec extends FreeSpec with MustMatchers with GuiceOneAppPerTest with Injecting with MockitoSugar with BeforeAndAfterEach {

  private val mockService = mock[UserService]

  override def beforeEach: Unit = {
    super.beforeEach
    reset(mockService)
  }

  private val user = User(
    1,
    "first name",
    "last name",
    "email@test.com",
    "111.222.333.444",
    -6.0000000,
    105.000000)

  "ApiControllerSpec" - {

    "`livingInLondon` method should" - {

      "return OK from a new instance of controller" in {
        val controller = new ApiController(stubControllerComponents(), mockService)

        when(mockService.getUsersByCity(any())).thenReturn(Future.successful(List(user)))

        val home = controller.livingInLondon().apply(FakeRequest(GET, "/users/city/london"))

        status(home) mustBe OK
        contentType(home) mustBe Some("application/json")
      }

      "return OK from the router" in {
        val request = FakeRequest(GET, "/users/city/london")
        val home = route(app, request).get
        status(home) mustBe OK
      }

      "return NotFound if records aren't returned" in {
        val controller = new ApiController(stubControllerComponents(), mockService)

        when(mockService.getUsersByCity(any())).thenReturn(Future.successful(Nil))

        val home = controller.livingInLondon().apply(FakeRequest(GET, "/users/city/london"))

        status(home) mustBe NOT_FOUND
      }

    }

    "`within` method should" - {

      "return OK from a new instance of controller" in {
        val controller = new ApiController(stubControllerComponents(), mockService)

        when(mockService.getUsersByDistance(any(), any(), any())).thenReturn(Future.successful(List(user)))

        val home = controller.within(miles = 50, city = "london").apply(FakeRequest(GET, "/distance/50/london"))

        status(home) mustBe OK
        contentType(home) mustBe Some("application/json")
      }

      "return OK from the router" in {
        val request = FakeRequest(GET, "/distance/50/london")
        val home = route(app, request).get
        status(home) mustBe OK
      }

      "return NotFound if records aren't returned" in {
        val controller = new ApiController(stubControllerComponents(), mockService)

        when(mockService.getUsersByDistance(any(), any(), any())).thenReturn(Future.successful(Nil))

        val home = controller.within(miles = 50, city = "london").apply(FakeRequest(GET, "/distance/50/london"))

        status(home) mustBe NOT_FOUND
      }

      "return BadRequest for invalid city" in {
        val controller = new ApiController(stubControllerComponents(), mockService)

        when(mockService.getUsersByDistance(any(), any(), any())).thenReturn(Future.successful(List(user)))

        val home = controller.within(miles = 50, city = "newcastle").apply(FakeRequest(GET, "/distance/50/newcastle"))

        status(home) mustBe BAD_REQUEST
      }

    }



  }
}

