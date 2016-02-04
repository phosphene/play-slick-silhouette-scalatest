package controllers

import org.scalatest._

import play.api.test._
import play.api.test.Helpers._
import org.openqa.selenium.htmlunit.HtmlUnitDriver

/**
 * A functional test will fire up a whole play application in a real (or headless) browser
 */
class ApplicationFT extends FunSpec with Matchers {
  
  describe ("Application") {
    
    it ("should work from within a browser") {
      running(TestServer(3333), new HtmlUnitDriver()) { browser =>

        browser.goTo("http://localhost:3333/")

        browser.pageSource should include ("Sign In")
      }
    }
    
  }
  
}
