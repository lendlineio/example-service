package io.lendline.example.service

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

class Configuration() {
  val config: Config = {
    val configDefaults = ConfigFactory.load(this.getClass.getClassLoader, "application.conf")

    scala.sys.props.get("production.conf") match {
      case Some(filename) => ConfigFactory.parseFile(new File(filename)).withFallback(configDefaults)
      case None => configDefaults
    }
  }

  object ExampleRemoteServer {
    val url: String = config.getString("ExampleRemoteServer.url")
  }

}
