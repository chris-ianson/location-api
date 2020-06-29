package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.util.{Success, Try}

case class User(
                 id: Int,
                 firstName: String,
                 lastName: String,
                 email: String,
                 ipAddress: String,
                 latitude: Double,
                 longitude: Double)

object User {

  //TODO: Update test for lat and long that are strings
  private val readDoubleFromString: Reads[Double] = implicitly[Reads[String]]
    .map(x => Try(x.toDouble))
    .collect (JsonValidationError(Seq("Parsing error"))){
      case Success(a) => a
    }

  private val readDouble: Reads[Double] = implicitly[Reads[Double]].orElse(readDoubleFromString)

  implicit val reads: Reads[User] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "ip_address").read[String] and
      (JsPath \ "latitude").read[Double](readDouble) and
      (JsPath \ "longitude").read[Double](readDouble)
    ) (User.apply _)

  implicit val writes: Writes[User] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "first_name").write[String] and
      (JsPath \ "last_name").write[String] and
      (JsPath \ "email").write[String] and
      (JsPath \ "ip_address").write[String] and
      (JsPath \ "latitude").write[Double] and
      (JsPath \ "longitude").write[Double]
    ) (unlift(User.unapply))

}
