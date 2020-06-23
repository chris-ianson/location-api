package generators

import models.User
import org.scalacheck.Arbitrary._
import org.scalacheck.{Arbitrary, Gen}

trait Generators {

  def nonEmptyString: Gen[String] =
    arbitrary[String] suchThat (_.nonEmpty)

  implicit lazy val arbitraryUser: Arbitrary[User] =
    Arbitrary {
      for {
        id <- Gen.chooseNum(1,100)
        firstName <- nonEmptyString
        lastName  <- nonEmptyString
        email  <- nonEmptyString
        ipAddress  <- nonEmptyString
        latitude  <- Gen.chooseNum[Double](1, 200)
        longitude  <- Gen.chooseNum[Double](1, 200)
      } yield User(id,firstName, lastName, email, ipAddress, latitude, longitude)
    }
}
