package io.lendline.example.service

import slick.jdbc.JdbcBackend.Database
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import slick.jdbc.JdbcBackend

import scala.concurrent.ExecutionContext


class UserRoutesSpec extends FlatSpecWithDB with ScalatestRouteTest with JsonSupport {
  val db: JdbcBackend.DatabaseDef =
    Database.forURL("jdbc:h2:mem:UserRoutesSpec;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")

  val userDao = new UserDao(db, slick.jdbc.H2Profile)
  val usesService = new UserService(userDao)

  val userRoutes: Route = Route.seal(new UserRoutes {
    override def userService: UserService = usesService
    implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  }.userRoutes)

  override def createTables(): Unit = {
    userDao.create().futureValue
  }

  override def dropTables(): Unit = {
    userDao.drop().futureValue
  }

  "User service GET request to /user?userid={userId}" should "return user info" in {
    Get("/user?userid=1") ~> userRoutes ~> check {
      status should be(StatusCodes.OK)
      logger.info(response.toString)
      logger.info(responseAs[String])
    }
  }

  "User service POST request to /user?name=Anton" should "return userId" in {
    import spray.json._

    val user = User("Anton").toJson.toString()

    println(user)

    val entity = HttpEntity(ContentTypes.`application/json`, user)

    Post("/user?userid=1", entity) ~> userRoutes ~> check {
      status should be(StatusCodes.OK)
      logger.info(response.toString)
      logger.info(responseAs[String])
    }
  }
}
