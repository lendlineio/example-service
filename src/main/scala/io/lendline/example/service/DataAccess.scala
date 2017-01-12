package io.lendline.example.service

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class DataAccess(dc: DatabaseConfig[JdbcProfile])(implicit val ec : ExecutionContextExecutor) {
  private val log = LoggerFactory.getLogger(classOf[DataAccess])

  import dc.driver.api._

  private val db = dc.db

  val tables = new Schema(dc)

  val exampleTable = tables.exampleTable

  def logIt(r: Future[Option[Any]]): Unit = {
    r.onComplete(x =>
      x match {
        case Success(y) => y match {
          case Some(z) => log.info(z.toString)
          case None => log.info("Option None.")
        }
        case Failure(e) => log.info(e.getMessage)
      }
    )
  }

  def findData(user_id : String) : Future[Option[ExampleDataRow]] = {
    log.info(s"findData $user_id")
    val r = db.run(exampleTable.filter(_.userId === user_id).result.headOption)
    logIt(r)
    r
  }

  def updateData(row : ExampleDataRow) : Future[ExampleDataRow] = {
    log.info(s"updateData $row")
    val r = db.run(exampleTable.insertOrUpdate(row)).map(_ => row)
    r
  }
}
