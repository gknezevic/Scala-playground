package models

import play.api.libs.json.{Json, OWrites, Writes}

case class User(name: String, email: String)

object User {
  implicit val userWrites = new Writes[User] {
    def writes(user: User) = Json.obj(
      "name" -> user.name,
      "email" -> user.email
    )
  }

  implicit val userMongoWrites = new OWrites[User] {
    def writes(user: User) = Json.obj(
      "name" -> user.name,
      "email" -> user.email
    )
  }

}
