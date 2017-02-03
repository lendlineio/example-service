package io.lendline.example.service

import slick.driver.JdbcProfile
import org.joda.time._
import com.github.tototoshi.slick.GenericJodaSupport

trait Profile {
  val profile: JdbcProfile
}

trait Tables {
  this: Profile =>

  import profile.api._

  object PortableJodaSupport extends GenericJodaSupport(profile)
  import PortableJodaSupport._

  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def updated = column[DateTime]("updated")

    def * = (name, updated, id) <>(User.tupled, User.unapply)
  }

  lazy val userTable = TableQuery[UserTable]

  class MessageTable(tag: Tag) extends Table[Message](tag, "messages") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def message = column[String]("message")
    def userId = column[Long]("user_id")
    def updated = column[DateTime]("updated")

    def user = foreignKey("user_fk", userId, userTable)(_.id)
    def * = (message, userId, updated, id) <>(Message.tupled, Message.unapply)
  }

  lazy val messageTable = TableQuery[MessageTable]

//  lazy val ddl      = userTable.schema ++ messageTable.schema
}

case class Schema(val profile: JdbcProfile) extends Tables with Profile
