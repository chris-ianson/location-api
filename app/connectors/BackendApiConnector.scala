package connectors

import com.google.inject.Inject
import config.FrontendAppConfig
import models.User
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BackendApiConnector @Inject()(config: FrontendAppConfig, ws: WSClient) {

  def get(city: Option[String]): Future[List[User]] = {

    val url = s"${config.arrivalsBackend}${buildUri(city)}"

    ws.url(url).get.map {
      response => {
        response.json.as[List[User]]
      }
    }.recover {
      case _ => Nil
    }

  }

  private def buildUri(city: Option[String]) = city match {
    case Some(value) if value.nonEmpty => s"/city/$value/users"
    case _ => s"/users"
  }

}
