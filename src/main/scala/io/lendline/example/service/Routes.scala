package io.lendline.example.service

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.Segment
import org.slf4j.LoggerFactory
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

object Routes {
  private val log = LoggerFactory.getLogger("Routes")

  import scala.concurrent.ExecutionContext.Implicits.global

  private val dc = DatabaseConfig.forConfig[JdbcProfile]("postgersql_dc")
  private val da = new DataAccess(dc)

  val getRoutes =
    path("echo" / Segment) { s =>
      get {
        complete {
          log.info(s"Echo $s")
          s
        }
      }

    } ~
      pathPrefix("GetData") {
        get {
          path(Segment) { userId: String =>
            pathEnd {
              complete {
                log.info(s"Get Data for $userId")
                val r = da.findData(userId)
                "GetData " + userId
              }
            }
          }
        }
      } ~
      pathPrefix("SaveData") {
        pathEnd {
          post {
            formFields('userId, 'info) { (userId: String, info: String) =>
              complete {
                log.info(s"Start Verification $userId $info")

                //                  da.updateData(ExampleDataRow(userId, Some(123), info, DateTime.now().toTimestamp))
                "SaveData " + userId + " " + info
              }
            }
          }
        }
      }
}
