package io.lendline.example.service

import org.h2.jdbc.JdbcSQLException
import org.scalatest.{BeforeAndAfter, FlatSpec}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


//class DataAccessSpec extends FlatSpec with BeforeAndAfter {
//  val dc: DatabaseConfig[JdbcProfile]=DatabaseConfig.forConfig[JdbcProfile]("h2_dc")
//  import scala.concurrent.ExecutionContext.Implicits.global
//
//  val da: DataAccess = new DataAccess(dc)
//  val db = dc.db
//
//  import dc.driver.api._
//
//  val anton = User("Anton")
//  val ddl = da.userTable.schema ++ da.messageTable.schema
//
//
//  "DataAccess.User.get" should "return Future[None] on empty table" in {
//    val res = execFuture(da.User.get(1L))
//    assert(res.isEmpty)
//  }
//
//  "DataAccess.insert" should "insert User into table" in {
//    val res = execFuture(da.User.insert(anton))
//    val saved = execFuture(da.User.get(res))
//    assert(saved.isDefined)
//    val user = saved.get
//    assert( user.name === anton.name)
//  }
//
//  "DataAccess.Message.get" should "return Future[None] on empty table" in {
//    val res = execFuture(da.Message.get(1L))
//    assert(res.isEmpty)
//  }
//
//  "DataAccess.Message.insert" should "fail because of 'user_fk' foreign key violation" in {
//    val hello = Message("Hello World!", 1L)
//
//    intercept[JdbcSQLException](execFuture(da.Message.insert(hello)))
//  }
//
//  "DataAccess.Message.get" should "return a record from 'message' table" in {
//    val userId = execFuture(da.User.insert(anton))
//    val hello = Message("Hello World!", userId)
//
//    val messageId = execFuture(da.Message.insert(hello))
//    val mOp = execFuture(da.Message.get(messageId))
//
//    assert(mOp.isDefined)
//
//    val m = mOp.get
//    assert(m.message === hello.message)
//    assert(m.userId === hello.userId)
//  }
//}
