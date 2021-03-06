package controllers

import org.scalatest._

import play.api.test._
import play.api.test.Helpers._

/**
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationIT extends FunSpec with Matchers {

  describe ("Application") {

    it ("should send 404 on a bad request") {
      running(FakeApplication()) {
        val bad = route(FakeRequest(GET, "/boum")).get
        status(bad) should be (NOT_FOUND)
      }
    }

    it ("should render the index page") {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/")).get
        status(result) should be (SEE_OTHER)
        redirectLocation(result) should be (Some("/signIn"))
      }
    }
  }
}
