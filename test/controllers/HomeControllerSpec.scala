package controllers

import models.User
import org.mockito.Mockito.{reset, _}
import org.mockito.ArgumentMatchers.any
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import services.UserService

import scala.concurrent.Future

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar with BeforeAndAfterEach {

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

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents(), mockService)

      when(mockService.getUsersByCity(any())).thenReturn(Future.successful(List(user)))
      when(mockService.getUsersByDistance(any(), any(), any())).thenReturn(Future.successful(List(user)))

      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Users living in London")
      contentAsString(home) must include ("Users currently within 50 miles of London")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get
      status(home) mustBe OK
    }

  }

}
