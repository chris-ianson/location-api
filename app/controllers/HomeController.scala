package controllers

import javax.inject._
import models.{London, Miles}
import play.api.mvc._
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  def index() = Action.async { implicit request: Request[AnyContent] =>

    val futureUsersLivingInLondon = userService.getUsersByCity("London")
    val futureWithinLondon = userService.getUsersByDistance(50, Miles, London)

    for {
      inLondon <- futureUsersLivingInLondon
      withinLondon <- futureWithinLondon
    } yield {

      Ok(views.html.index(inLondon, withinLondon))
    }

  }

}
