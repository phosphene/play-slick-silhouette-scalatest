package controllers

import java.util.UUID

import com.google.inject.AbstractModule
import com.mohiva.play.silhouette.api.{ Environment, LoginInfo }
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.test._
import models.User
import net.codingwell.scalaguice.ScalaModule
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import org.scalatestplus.play._
import org.scalatest.mock.MockitoSugar
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class ExampleControllerSpec extends PlaySpec with Results {

    "return 200 if user is authorized" in new Context {
      new WithApplication(application) {
        val Some(result) = route(FakeRequest(routes.ApplicationController.index())
          .withAuthenticator[CookieAuthenticator](identity.loginInfo)
        )
        status(result) mustBe(200)
      }
    }

  /**
   * The context.
   */
  trait Context {

    /**
     * A fake Guice module.
     */
    class FakeModule extends AbstractModule with ScalaModule {
      def configure() = {
        bind[Environment[User, CookieAuthenticator]].toInstance(env)
      }
    }

    /**
     * An identity.
     */
    val identity = User(
      userID = UUID.randomUUID(),
      loginInfo = LoginInfo("facebook", "user@facebook.com"),
      firstName = None,
      lastName = None,
      fullName = None,
      email = None,
      avatarURL = None
    )

    /**
     * A Silhouette fake environment.
     */
    implicit val env: Environment[User, CookieAuthenticator] = new FakeEnvironment[User, CookieAuthenticator](Seq(identity.loginInfo -> identity))

    /**
     * The application.
     */
    lazy val application = new GuiceApplicationBuilder()
      .overrides(new FakeModule)
      .build()
  }
}
