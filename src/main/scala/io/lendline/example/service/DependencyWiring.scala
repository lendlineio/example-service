package io.lendline.example.service


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

trait DependencyWiring {
  def system: ActorSystem
  val databaseConfig: DatabaseConfig[JdbcProfile]

  implicit val materializer: ActorMaterializer
  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  lazy val userDao = new UserDao(databaseConfig)
  lazy val messageDao = new MessageDao(databaseConfig)

  lazy val userService = new UserService(userDao)
}
