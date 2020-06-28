package controllers

import javax.inject._
import models.{London, Miles}
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ApiController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  def livingInLondon: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>

    userService.getUsersByCity("London").map {
      users =>
        if(users.nonEmpty) {
          Ok(Json.toJson(users)).as("application/json")
        }
        else NotFound
    }
  }

  def within(miles: Int, city: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>

    city match {
      case "london" => {
        userService.getUsersByDistance(miles, Miles, London).map {
          users =>
            if(users.nonEmpty) {
              Ok(Json.toJson(users)).as("application/json")
            }
            else NotFound
        }
      }
      case _ => Future.successful(BadRequest)
    }
  }

}
