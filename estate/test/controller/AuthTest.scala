package controller

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import models.dbconf.AppDB._
import models.User
import controllers.Authentication
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

/**
 *          Date: 24.09.13
 */
@RunWith(classOf[JUnitRunner])
class AuthTest extends Specification {

    "User" should {
        "pass authentication" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
                val data = dal
                import data._
                import data.profile.simple._

                val email = "test@test.com"
                val pass = "test"

                database withSession { implicit session: Session =>
                    Users.insert(User(None, email, pass, "test", "test", None))
                }

                val exists = Authentication.check(email, pass)
                exists must beTrue
            }
        }

        "fail authentication" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
                val data = dal
                import data._
                import data.profile.simple._

                val exists = Authentication.check("test@test.com", "123")
                exists must beFalse
            }
        }
    }

}