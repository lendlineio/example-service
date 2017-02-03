package io.lendline.example.service

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class DataAccess(dc: DatabaseConfig[JdbcProfile])(implicit val ec : ExecutionContextExecutor)
  extends Schema(dc.driver) {

  private val log = LoggerFactory.getLogger(classOf[DataAccess])

  import dc.driver.api._

  private val db = dc.db

  object User {
    def get(id: Long): Future[Option[User]] = {
      log.info(s"Phone get: $id")
      db.run(userTable.filter(_.id === id).result.headOption)
    }

    def insert(u : User) : Future[Long] = {
      log.info(s"Usert insert: $u")
      val q = userTable returning userTable.map(_.id) += u
      db.run(q)
    }

    def insertOrUpdate(u : User) : Future[Int] = {
      log.info(s"User update: $u")
      db.run( userTable.insertOrUpdate(u) )
    }
  }

  object Message {
    def get(id: Long): Future[Option[Message]] = {
      log.info(s"Message get: $id")
      db.run(messageTable.filter(_.id === id).result.headOption)
    }

    def insert(m : Message) : Future[Long] = {
      log.info(s"Message insert: $m")
      val q = messageTable returning messageTable.map(_.id) += m
      db.run(q)
    }

    def insertOrUpdate(m : Message) : Future[Int] = {
      log.info(s"Message update: $m")
      db.run( messageTable.insertOrUpdate(m) )
    }
  }
}
