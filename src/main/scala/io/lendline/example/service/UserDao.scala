package io.lendline.example.service

import com.typesafe.scalalogging.StrictLogging
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcBackend.DatabaseDef

import scala.concurrent.Future

class UserDao(val db: DatabaseDef, val profile: JdbcProfile)
  extends Profile with Tables with BasicDao[User] with StrictLogging {

  def this(dc: DatabaseConfig[JdbcProfile]) = {
    this(dc.db.asInstanceOf[DatabaseDef], dc.profile)
  }

  import profile.api._

  def add(u: User) : Future[Long] = {
    db.run(userTable returning userTable.map(_.id) += u)
  }

  def get(userId: Long) : Future[Option[User]] = {
    logger.info(s"UserDao.get userId: $userId")
    db.run(userTable.filter(_.id === userId).result.headOption)
  }

  def delete(userId: Long) : Future[Int] = {
    db.run(userTable.filter(_.id === userId).delete)
  }

  def update(u: User) : Future[Int] = {
    db.run(userTable.insertOrUpdate(u))
  }

  def create() : Future[Unit] = {
    db.run(userTable.schema.create)
  }

  def drop() : Future[Unit] = {
    db.run(userTable.schema.drop)
  }
}
