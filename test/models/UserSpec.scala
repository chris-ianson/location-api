package models

import generators.Generators
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.{FreeSpec, MustMatchers}
import play.api.libs.json.Json
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class UserSpec extends FreeSpec with MustMatchers with Generators with ScalaCheckPropertyChecks {

  "UserSpec" - {

    "serialise from json" in {

      forAll(arbitrary[User]) {
        user =>

          val record = Json.obj(
              "id" ->  user.id,
              "first_name" ->  user.firstName,
              "last_name" ->  user.lastName,
              "email" ->  user.email,
              "ip_address" ->  user.ipAddress,
              "latitude" ->  user.latitude,
              "longitude" ->  user.longitude
            )

          record.as[User] mustBe user
      }

    }

  }

}
