package connectors

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Suite}
import play.api.Application
import play.api.inject.Injector
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceableModule}

trait WireMockSuite extends BeforeAndAfterAll with BeforeAndAfterEach {
  this: Suite =>

  protected val server: WireMockServer = new WireMockServer(wireMockConfig().dynamicPort())

  protected def hostConfigKey: String
  protected def portConfigKey: String
  protected def protocolConfigKey: String

  protected lazy val app: Application = new GuiceApplicationBuilder()
    .configure(portConfigKey -> server.port().toString)
    .configure(hostConfigKey -> "localhost")
    .configure(protocolConfigKey -> "http")
    .overrides(bindings: _*)
    .build()

  protected def bindings: Seq[GuiceableModule] = Seq.empty

  protected lazy val injector: Injector = app.injector

  override def beforeAll(): Unit = {
    server.start()
    app
    super.beforeAll()
  }

  override def beforeEach(): Unit = {
    server.resetAll()
    app
    super.beforeEach()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    server.stop()
  }

}
