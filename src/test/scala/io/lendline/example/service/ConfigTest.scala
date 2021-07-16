package io.lendline.example.service

import org.scalatest.flatspec.AnyFlatSpec

class ConfigTest extends AnyFlatSpec {

  "Config Test" should "pass" in {
    val config = new Configuration()

    assert(config.ExampleRemoteServer.url != null)
    assert(config.ExampleRemoteServer.url != "")
  }
}
