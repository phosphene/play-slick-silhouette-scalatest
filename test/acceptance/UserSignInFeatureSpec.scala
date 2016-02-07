package acceptance

import org.scalatest._
import play.test._
import org.openqa.selenium.phantomjs.PhantomJSDriver
import play.api.test.FakeApplication
import play.api.test.TestServer
import play.api.test.Helpers._
import play.api.db.slick._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.profile._
import models.User


trait WithDatabaseConfig {
  lazy val (driver, db) = {
    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
    (dbConfig.driver, dbConfig.db)
  }
}


/**
  * Acceptance testing using PhantomJS and FeatureSpec syntax
  * Note that Given, When, and Then are capitalized as then is  
  * a reserved word in latter day Scala
  * note the use of before and after blocks which said convention is mixed in using
  * BeforeAndAfter trait
  */

class UserSignInFeatureSpec extends FeatureSpec with GivenWhenThen with BeforeAndAfter with Matchers {


  trait WithDatabaseConfig {
    lazy val (driver, db) = {
      val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
      (dbConfig.driver, dbConfig.db)
    }
  }

  var browser: TestBrowser = _
  var server: TestServer = _
  var db: Any = _
  
  before {
    val appWithMemoryDatabase  = FakeApplication(additionalConfiguration = inMemoryDatabase())
    
    server = new TestServer(3333, appWithMemoryDatabase)
    server.start()
    browser = Helpers.testBrowser(new PhantomJSDriver())
    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
    db = dbConfig.db
  }

  after {
    server.stop()
    browser.quit()
  }

  feature("Any new user can reach the SignIn page") {
    
    info("As a new user")
    info("I want to see the SignIn page")
    info("So that I can register")

    scenario("I'm a new user coming to the website") {
      
      Given("I am a new user")
      
      When("I browse to the website")
      browser.goTo("http://localhost:3333/")
      
      Then("I should see the Sign In page")
      browser.pageSource should include ("Sign In")
    }
  }
}
