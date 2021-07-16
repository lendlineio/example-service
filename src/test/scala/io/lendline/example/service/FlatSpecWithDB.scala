package io.lendline.example.service


import com.typesafe.scalalogging.StrictLogging
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.flatspec.AnyFlatSpec


abstract class FlatSpecWithDB extends AnyFlatSpec with StrictLogging with ScalaFutures
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