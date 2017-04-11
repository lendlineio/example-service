package io.lendline.example.service


import com.typesafe.scalalogging.StrictLogging
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}


abstract class FlatSpecWithDB extends FlatSpec with Matchers with StrictLogging with ScalaFutures
  with IntegrationPatience with BeforeAndAfter {

  def createTables(): Unit
  def dropTables(): Unit

  before {
    createTables()
  }

  after {
    dropTables()
  }
}