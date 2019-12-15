package services

import javax.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import ExecutionContext.Implicits.global

class AuthRepo @Inject()(reactiveMongo: ReactiveMongoApi) {

  def checkIfUserWithPassExist(username: String, password: String): Future[Boolean] = {
    reactiveMongo.database.map(_.collection[JSONCollection]("auth"))
      .flatMap(_.find(BSONDocument("username" -> username, "password" -> password), Option.apply(BSONDocument("username" -> 1))).one[String]
        .map(a => a.isDefined))
  }

}
