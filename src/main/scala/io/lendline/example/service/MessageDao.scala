package io.lendline.example.service

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend.DatabaseDef
import scala.concurrent.Future

class MessageDao (val db: DatabaseDef, val profile: JdbcProfile)
  extends Profile with Tables with BasicDao[Message] {

  def this(dc: DatabaseConfig[JdbcProfile]){
    this(dc.db.asInstanceOf[DatabaseDef], dc.driver)
  }

  import profile.api._

  def add(m: Message) : Future[Long] = {
    db.run(messageTable returning messageTable.map(_.id) += m)
  }

  def get(messageId: Long) : Future[Option[Message]] = {
    db.run(messageTable.filter(_.id === messageId).result.headOption)
  }

  def delete(messageId: Long) : Future[Int] = {
    db.run(messageTable.filter(_.id === messageId).delete)
  }

  def update(m: Message) : Future[Int] = {
    db.run(messageTable.insertOrUpdate(m))
  }

  def create() : Future[Unit] = {
    db.run(messageTable.schema.create)
  }

  def drop() : Future[Unit] = {
    db.run(messageTable.schema.drop)
  }
}
