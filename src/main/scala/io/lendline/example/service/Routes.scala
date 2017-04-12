package io.lendline.example.service

import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging

trait Routes extends UserRoutes with StrictLogging with JsonSupport {
  val getRoutes =
    path("echo" / Segment) { s =>
      get {
        complete {
          logger.info(s"Echo $s")
          s
        }
      }

    } ~
      pathPrefix("SaveData") {
        pathEnd {
          post {
            formFields('userId, 'info) { (userId: String, info: String) =>
              complete {
                logger.info(s"Start Verification $userId $info")

                //                  da.updateData(ExampleDataRow(userId, Some(123), info, DateTime.now().toTimestamp))
                "SaveData " + userId + " " + info
              }
            }
          }
        }
      } ~ userRoutes
}
