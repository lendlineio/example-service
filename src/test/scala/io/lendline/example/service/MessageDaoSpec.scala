package io.lendline.example.service

import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class MessageDaoSpec extends FlatSpecWithDB {
  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  val db: JdbcBackend.DatabaseDef =
    Database.forURL("jdbc:h2:mem:MessageDaoSpec;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")

  val messageDao: MessageDao = new MessageDao(db, slick.jdbc.H2Profile)
  val userDao: UserDao = new UserDao(db, slick.jdbc.H2Profile)

  override def createTables() : Unit = {
    userDao.create().futureValue
    messageDao.create().futureValue
  }

  override def dropTables() : Unit = {
    messageDao.drop().futureValue
    userDao.drop().futureValue
  }

  "MessageDao.add & get" should "add a new user" in {
    val user = User("Anton")

    val userId = userDao.add(user).futureValue

    val orig = Message("Hello World", userId)

    val messageId = messageDao.add(orig).futureValue
    val messageOp = messageDao.get(messageId).futureValue

    assert(messageOp.isDefined)

    val m = messageOp.get

    m.message should be (orig.message)
  }

  "messageDao.delete" should "delete a user with userId" in {
    val user = User("Anton")

    val userId = userDao.add(user).futureValue

    val orig = Message("Hello World", userId)

    val messageId = messageDao.add(orig).futureValue
    val messageOp = messageDao.get(messageId).futureValue

    assert(messageOp.isDefined)

    val m = messageOp.get

    m.message should be (orig.message)

    val numberOfDeletedRecords = messageDao.delete(m.id).futureValue

    numberOfDeletedRecords should be (1)

    val messageOp2 = messageDao.get(messageId).futureValue

    assert(messageOp2.isEmpty)
  }

  "messageDao.update" should "update a user" in {
    val user = User("Anton")

    val userId = userDao.add(user).futureValue

    val orig = Message("Hello World", userId)
    val updated = Message("Goodbuy", userId)
    val messageId = messageDao.add(orig).futureValue

    messageDao.update(updated.copy(id = messageId)).futureValue

    val messageOp = messageDao.get(messageId).futureValue

    assert(messageOp.isDefined)

    val message = messageOp.get

    message.message should be (updated.message)

    message.id should be (messageId)
  }
}
