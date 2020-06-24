package connectors

import com.github.tomakehurst.wiremock.client.WireMock.{get, okJson, status}
import models.User
import org.scalacheck.Gen
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{FreeSpec, MustMatchers}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json.Json

class BackendApiConnectorSpec extends FreeSpec with MustMatchers with WireMockSuite with ScalaFutures with IntegrationPatience with ScalaCheckPropertyChecks {

  import BackendApiConnectorSpec._

  override protected def portConfigKey: String = "microservice.services.backend-api.port"
  override protected def hostConfigKey: String = "microservice.services.backend-api.host"
  override protected def protocolConfigKey: String = "microservice.services.backend-api.protocol"

  private lazy val connector: BackendApiConnector = app.injector.instanceOf[BackendApiConnector]

  "UnloadingConnectorSpec" - {

    "GET" - {

      "should handle client and server errors" in {
        forAll(responseCodes) {
          code =>
            server.stubFor(
              get(getUri)
                .willReturn(status(code))
            )

            connector.getUsersByCity(city).futureValue mustBe Nil
        }
      }

      "should handle a 200 response" - {

        "containing malformed json" in {
          server.stubFor(
            get(getUri)
              .willReturn(okJson(malFormedJson)))

          connector.getUsersByCity(city).futureValue mustBe Nil
        }

        "containing no records" in {
          server.stubFor(
            get(getUri)
              .willReturn(okJson(Json.arr().toString())))

          connector.getUsersByCity(city).futureValue mustBe Nil
        }

        "containing a single record" in {
          server.stubFor(
            get(getUri)
              .willReturn(okJson(jsonSingle)
              ))

          val response: List[User] = connector.getUsersByCity(city).futureValue

          response.length mustBe 1
          response.head mustBe User(
            1,
            "first name",
            "last name",
            "email@test.com",
            "111.222.333.444",
            -6.0000000,
            105.000000
          )
        }

        "containing multiple records" in {
          server.stubFor(
            get(getUri)
              .willReturn(okJson(jsonMultiple)
              ))

          val response: List[User] = connector.getUsersByCity(city).futureValue

          response.length mustBe 2
          response.head mustBe User(
            1,
            "first name",
            "last name",
            "email@test.com",
            "111.222.333.444",
            -6.0000000,
            105.000000
          )
          response(1) mustBe User(
            2,
            "first name",
            "last name",
            "email@test.com",
            "111.222.333.444",
            -6.0000000,
            105.000000
          )
        }

      }

    }

  }
}

object BackendApiConnectorSpec {
  private val getUri = s"/city/London/users/"
  private val city = "London"

  private val user1 = Json.obj(
    "id" -> 1,
    "first_name" -> "first name",
    "last_name" -> "last name",
    "email" -> "email@test.com",
    "ip_address" -> "111.222.333.444",
    "latitude" -> -6.0000000,
    "longitude" -> 105.000000
  )

  private val user2 = Json.obj(
    "id" -> 2,
    "first_name" -> "first name",
    "last_name" -> "last name",
    "email" -> "email@test.com",
    "ip_address" -> "111.222.333.444",
    "latitude" -> -6.0000000,
    "longitude" -> 105.000000
  )

  private val jsonSingle =
    Json.arr(user1).toString()

  private val jsonMultiple =
    Json.arr(user1, user2).toString()

  private val malFormedJson =
    """
      ||{
      |[
      |}
    """.stripMargin

  private val responseCodes: Gen[Int] = Gen.chooseNum(400: Int, 599: Int)
}
