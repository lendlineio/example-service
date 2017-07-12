package io.lendline.example.service

import slick.jdbc.JdbcProfile
import org.joda.time._
import com.github.tototoshi.slick.GenericJodaSupport
import slick.lifted.{ForeignKeyQuery, ProvenShape}

trait Profile {
  val profile: JdbcProfile

  object PortableJodaSupport extends GenericJodaSupport(profile)
}

trait Tables {
  this: Profile =>

  import profile.api._

  import PortableJodaSupport._

  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name: Rep[String] = column[String]("name")
    def updated: Rep[DateTime] = column[DateTime]("updated")

    def * : ProvenShape[User] = (name, updated, id) <>(User.tupled, User.unapply)
  }

  lazy val userTable: TableQuery[UserTable] = TableQuery[UserTable]

  class MessageTable(tag: Tag) extends Table[Message](tag, "messages") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def message: Rep[String] = column[String]("message")
    def userId: Rep[Long] = column[Long]("user_id")
    def updated: Rep[DateTime] = column[DateTime]("updated")

    def user: ForeignKeyQuery[UserTable, User] = foreignKey("user_fk", userId, userTable)(_.id)
    def * : ProvenShape[Message] = (message, userId, updated, id) <>(Message.tupled, Message.unapply)
  }

  lazy val messageTable: TableQuery[MessageTable] = TableQuery[MessageTable]
}

//case class Schema(val profile: JdbcProfile) extends Tables with Profile
