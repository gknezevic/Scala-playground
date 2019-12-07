package models

case class Status(message: String, count: Int) {
  def showMessage: String = this.message concat ". Call #" concat  count.toString
}
