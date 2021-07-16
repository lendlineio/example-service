package io.lendline.example.service

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging
import scala.concurrent.ExecutionContext


trait UserRoutes extends JsonSupport with StrictLogging {
  def userService: UserService
  implicit val ec: ExecutionContext

  val userRoutes: Route =
    path("user") {
      get {
        parameters(Symbol("userid").as[Long]) { userId: Long =>
          pathEnd {
            complete {
              logger.info(s"Get Data for $userId")
              userService.getUser(userId)
            }
          }
        }
      }
    } ~
      pathPrefix("user") {
        post {
          entity(as[User]) { user: User =>
            complete {
              logger.info(s"add user: $user")

              val r = userService.addUser(user)
              r.map(_.toString)
            }
          }
        }
      }
}
