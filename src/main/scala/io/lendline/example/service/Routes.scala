package io.lendline.example.service

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging

trait Routes extends UserRoutes with StrictLogging with JsonSupport {
  val getRoutes: Route =
    pathPrefix("api") {
      pathPrefix("v1") {
        path("echo" / Segment) { s =>
          get {
            complete {
              logger.info(s"Echo $s")
              s
            }
          }
        } ~ userRoutes
      }
    }
}
