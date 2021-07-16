package io.lendline.example.service


import akka.actor.ActorSystem
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

trait DependencyWiring {
  def system: ActorSystem
  def databaseConfig: DatabaseConfig[JdbcProfile]

  implicit val ec: ExecutionContext

  lazy val userDao = new UserDao(databaseConfig)
  lazy val messageDao = new MessageDao(databaseConfig)
  lazy val userService = new UserService(userDao)
}
