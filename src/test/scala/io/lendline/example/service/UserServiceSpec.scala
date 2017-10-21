package io.lendline.example.service

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext


class UserServiceSpec extends FlatSpecWithDB {
  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit val _system = ActorSystem("example-service")
  implicit val _materializer = ActorMaterializer()

  val db: JdbcBackend.DatabaseDef = Database.forURL("jdbc:h2:mem:UserServiceSpec;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")
  val userDao = new UserDao(db, slick.jdbc.H2Profile)
  val userService = new UserService(userDao)

  override def createTables(): Unit = {
    userDao.create().futureValue
  }

  override def dropTables(): Unit = {
    userDao.drop().futureValue
  }

  "UserService.getUser" should "get user by userId" in {
    val result = userService.getUser(-1L).futureValue
    result shouldBe (None)
  }
}
