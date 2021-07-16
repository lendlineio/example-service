package io.lendline.example.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.scalalogging.StrictLogging
import slick.jdbc.JdbcProfile
import slick.basic.DatabaseConfig

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps

object Boot extends StrictLogging {
  def main(args: Array[String]): Unit = {
    implicit val _system = ActorSystem("example-service")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = _system.dispatcher

    logger.info("Starting ExampleService")

    val config = new Configuration()

    val modules = new DependencyWiring with Routes {
      lazy val system: ActorSystem = _system
      implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

      lazy val databaseConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("h2_dc")
    }

    Await.result(modules.userDao.create(), 10 seconds)
    Await.result(modules.messageDao.create(), 10 seconds)

    val bindingFuture = Http().newServerAt(config.http.host, config.http.port).bind(modules.getRoutes)

    logger.info(s"Server online at ${config.http.host}:${config.http.port}/\nPress RETURN to stop...")

    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => _system.terminate()) // and shutdown when done
  }
}
