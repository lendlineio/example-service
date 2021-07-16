package io.lendline.example.service

import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext

class UserDaoSpec extends FlatSpecWithDB {
  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  val db: JdbcBackend.DatabaseDef = Database.forURL("jdbc:h2:mem:UserDaoSpec;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")
  val userDao = new UserDao(db, slick.jdbc.H2Profile)

  override def createTables(): Unit = {
    userDao.create().futureValue
  }

  override def dropTables(): Unit = {
    userDao.drop().futureValue
  }

  "UserDao.add & get" should "add a new user" in {
    val orig = User("Anton")
    val userId = userDao.add(orig).futureValue

    val userOp = userDao.get(userId).futureValue

    assert(userOp.isDefined)

    val user = userOp.get

    user.name should be (orig.name)
  }

  "UserDao.delete" should "delete a user with userId" in {
    val orig = User("Anton")

    val userId = userDao.add(orig).futureValue

    val userOp = userDao.get(userId).futureValue

    assert(userOp.isDefined)

    val user = userOp.get

    user.name should be (orig.name)

    val numberOfDeletedRecords = userDao.delete(user.id).futureValue

    numberOfDeletedRecords should be (1)

    val userOp2 = userDao.get(userId).futureValue

    assert(userOp2.isEmpty)
  }

  "UserDao.update" should "update a user" in {
    val orig = User("Anton")
    val updated = User("Yulia")

    val userId = userDao.add(orig).futureValue
    userDao.update(updated.copy(id = userId)).futureValue

    val userOp = userDao.get(userId).futureValue

    assert(userOp.isDefined)

    val user = userOp.get

    user.name should be (updated.name)

    user.id should be (userId)
  }
}
