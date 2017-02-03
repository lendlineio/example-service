package io.lendline.example.service

import org.joda.time.{DateTime, LocalDate}
import org.joda.time.format.ISODateTimeFormat
import spray.json._


case class User(name: String,
                updated: DateTime = DateTime.now(),
                id : Long = 0)

case class Message(message: String,
                   userId: Long,
                   updated: DateTime = DateTime.now(),
                   id: Long = 0)

object JsonSerializeProtocol extends DefaultJsonProtocol {
  val dateTimeParser = ISODateTimeFormat.dateTimeParser()
  val localDateParser = ISODateTimeFormat.localDateParser()

  implicit object DateTimeFormat extends RootJsonFormat[DateTime] {
    def write(c: DateTime) = JsString(c.toString)

    def read(value: JsValue) = value match {
      case s: JsString => dateTimeParser.parseDateTime(s.value)
      case _ => deserializationError("Timestamp expected")
    }
  }

  implicit object LocalDateFormat extends RootJsonFormat[LocalDate] {
    def write(c: LocalDate) = JsString(c.toString)

    def read(value: JsValue) = value match {
      case s: JsString => localDateParser.parseLocalDate(s.value)
      case _ => deserializationError("Timestamp expected")
    }
  }

  implicit val exampleDataRowFormatter = jsonFormat3(User)
}
