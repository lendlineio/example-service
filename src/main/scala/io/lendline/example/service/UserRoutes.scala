package io.lendline.example.service

import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging


trait UserRoutes extends JsonSupport with StrictLogging {
  def userService: UserService

  val userRoutes =
    path("GetData") {
      get {
        parameters('userid.as[Long]) { userId: Long =>
          pathEnd {
            complete {
              logger.info(s"Get Data for $userId")
              userService.getUser(userId)
            }
          }
        }
      }
    }
}
