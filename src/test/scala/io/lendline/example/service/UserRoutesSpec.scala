package io.lendline.example.service

import slick.jdbc.JdbcBackend.Database


import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._


class UserRoutesSpec extends FlatSpecWithDB with ScalatestRouteTest  {
  val db = Database.forURL("jdbc:h2:mem:UserRoutesSpec;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")
  val userDao = new UserDao(db, slick.driver.H2Driver)

  val usesService = new UserService(userDao)

  val userRoutes = Route.seal(new UserRoutes {
    override def userService: UserService = usesService
  }.userRoutes)

  override def createTables() = {
    userDao.create().futureValue
  }

  override def dropTables() = {
    userDao.drop().futureValue
  }

  "User service GET request to /GetData?userid={userId}" should "return user info" in {
    Get("/GetData?userid=1") ~> userRoutes ~> check {
      status should be(StatusCodes.OK)
      logger.info(response.toString)
      logger.info(responseAs[String])
    }
  }
}
