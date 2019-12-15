package services

import javax.inject.Inject
import javax.inject.Singleton
import pdi.jwt.{Jwt, JwtAlgorithm}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import reactivemongo.play.json._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthService @Inject()(reactiveMongo: ReactiveMongoApi) {

  val superSecretKey = "thisissupersecretkey"
  implicit def ec: ExecutionContext = this.ec

  def login(username: String, password: String): Future[Either[Error, String]] = {
    checkIfUserWithPassExist(username, password)
      .map(result => Either.cond(result, generateJWT(username), new Error("User or password invalid")))
  }

  private def generateJWT(username: String): String = {
    Jwt.encode(username, superSecretKey, JwtAlgorithm.HS256)
  }

  def checkIfUserWithPassExist(username: String, password: String): Future[Boolean] = {
    reactiveMongo.database.map(_.collection[JSONCollection]("auth"))
      .flatMap(_.find(BSONDocument("username" -> username, "password" -> password), Option.apply(BSONDocument("username" -> 1))).one[String]
        .map(a => a.isDefined))
  }

}

