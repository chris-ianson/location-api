package connectors

import com.google.inject.Inject
import config.FrontendAppConfig
import models.User
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BackendApiConnector @Inject()(config: FrontendAppConfig, ws: WSClient) {

  def getUsersByCity(city: String): Future[List[User]] = {

    val url = s"${config.arrivalsBackend}/city/$city/users/"

    ws.url(url).get.map {
      response => response.json.as[List[User]]
    }.recover {
      case _ => Nil
    }

  }

}
