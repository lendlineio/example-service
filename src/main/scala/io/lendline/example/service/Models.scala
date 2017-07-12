package io.lendline.example.service

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.joda.time.{DateTime, LocalDate}
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import spray.json._


case class User(name: String,
                updated: DateTime = DateTime.now(),
                id : Long = 0)

case class Message(message: String,
                   userId: Long,
                   updated: DateTime = DateTime.now(),
                   id: Long = 0)

trait JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  val dateTimeParser: DateTimeFormatter = ISODateTimeFormat.dateTimeParser()
  val localDateParser: DateTimeFormatter = ISODateTimeFormat.localDateParser()

  implicit object DateTimeFormat extends RootJsonFormat[DateTime] {
    def write(c: DateTime): JsString = JsString(c.toString)

    def read(value: JsValue): DateTime = value match {
      case s: JsString => dateTimeParser.parseDateTime(s.value)
      case _ => deserializationError("Timestamp expected")
    }
  }

  implicit object LocalDateFormat extends RootJsonFormat[LocalDate] {
    def write(c: LocalDate): JsString = JsString(c.toString)

    def read(value: JsValue): LocalDate = value match {
      case s: JsString => localDateParser.parseLocalDate(s.value)
      case _ => deserializationError("Timestamp expected")
    }
  }

  implicit val userFormatter: RootJsonFormat[User] = jsonFormat3(User)
  implicit val messageFormatter: RootJsonFormat[Message] = jsonFormat4(Message)
}
