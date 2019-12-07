package models

import play.api.libs.json.{Json, Writes}

case class Status(message: String, count: Int) {
  def showMessage: String = this.message concat ". Call #" concat  count.toString
}

object Status {
  implicit val statusWrites = new Writes[Status] {
    def writes(status: Status) = Json.obj(
      "message" -> status.message,
      "count" -> status.count
    )
  }
}
