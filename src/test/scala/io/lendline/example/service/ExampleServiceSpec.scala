package io.lendline.example.service

import org.scalatest.FlatSpec
import spray.http.StatusCodes._
import spray.http._
import spray.testkit.ScalatestRouteTest

class ExampleServiceSpec extends FlatSpec with ScalatestRouteTest with ExampleService {
  def actorRefFactory = system

  "return echo for GET Echo requests" should "pass" in {
    Get("/Echo/Hello") ~> route ~> check {
      assert(responseAs[String] contains "Hello")
    }
  }

  "return a MethodNotAllowed error for PUT requests to the root path" should "pass" in {
    Put("/Echo/Hello") ~> sealRoute(route) ~> check {
      assert(status === MethodNotAllowed)
      assert(responseAs[String] === "HTTP method not allowed, supported methods: GET")
    }
  }

  "leave GET requests to other paths unhandled" should "pass" in {
    Get("/kermit") ~> route ~> check {
      assert(!handled)
    }
  }
}
