package io.lendline.example.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.StrictLogging
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn

object Boot extends StrictLogging {
  def main(args: Array[String]): Unit = {
    implicit val _system = ActorSystem("example-service")
    implicit val _materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = _system.dispatcher

    logger.info("Starting ExampleService")

    val config = new Configuration()

    val modules = new DependencyWiring with Routes {
      lazy val system = _system
      implicit lazy val materializer = _materializer

      lazy val databaseConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("h2_dc")
    }

    Await.result(modules.userDao.create(), 10 seconds)
    Await.result(modules.messageDao.create(), 10 seconds)

    val bindingFuture = Http().bindAndHandle(modules.getRoutes, config.http.host, config.http.port)

    logger.info(s"Server online at ${config.http.host}:${config.http.port}/\nPress RETURN to stop...")

    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => _system.terminate()) // and shutdown when done
  }
}
