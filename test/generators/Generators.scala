package generators

import models.User
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen.{choose, listOfN}
import org.scalacheck.{Arbitrary, Gen}

trait Generators {

  def nonEmptyString: Gen[String] =
    arbitrary[String] suchThat (_.nonEmpty)

  def listWithMaxLength[A](maxLength: Int)(implicit a: Arbitrary[A]): Gen[List[A]] =
    for {
      length <- choose(1, maxLength)
      seq    <- listOfN(length, arbitrary[A])
    } yield seq

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

  implicit lazy val listOfUsers: Arbitrary[List[User]] =
    Arbitrary {
      for {
        listOfUsers <- listWithMaxLength[User](10)
      } yield listOfUsers
    }
}
