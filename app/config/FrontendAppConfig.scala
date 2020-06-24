package config

import com.google.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class FrontendAppConfig @Inject()(config: Configuration) {

  def arrivalsBackend = s"$protocol://$domain:$port"

  private val protocol = config.get[String]("microservice.services.backend-api.protocol")

  private val domain = config.get[String]("microservice.services.backend-api.host")

  private val port = config.get[String]("microservice.services.backend-api.port")
}
