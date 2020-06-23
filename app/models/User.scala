package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}

case class User(
                 id: Int,
                 firstName: String,
                 lastName: String,
                 email: String,
                 ipAddress: String,
                 latitude: Double,
                 longitude: Double
               )

object User {

  implicit val reads: Reads[User] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "ip_address").read[String] and
      (JsPath \ "latitude").read[Double] and
      (JsPath \ "longitude").read[Double]
    ) (User.apply _)
}
