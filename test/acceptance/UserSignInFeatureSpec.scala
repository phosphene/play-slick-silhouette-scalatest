package acceptance

import org.scalatest._
import play.test._
import org.openqa.selenium.phantomjs.PhantomJSDriver
import play.api.test.FakeApplication
import play.api.test.TestServer
import play.api.test.Helpers._
import play.Application

/**
  * Acceptance testing using PhantomJS and FeatureSpec syntax
  * Note that Given, When, and Then are capitalized as then is  
  * a reserved word in latter day Scala
  */

class UserSignInFeatureSpec extends FeatureSpec with GivenWhenThen with BeforeAndAfter with Matchers {

  var browser: TestBrowser = _
  var server: TestServer = _
  


  before {
    val appWithMemoryDatabase  = FakeApplication(additionalConfiguration = inMemoryDatabase("test"))

   server = new TestServer(3333, appWithMemoryDatabase)
   server.start()
   browser = Helpers.testBrowser(new PhantomJSDriver())
  }

  after {
    server.stop()
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
