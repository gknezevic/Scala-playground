package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject() (cc: ControllerComponents, reactiveMongo: ReactiveMongoApi) extends AbstractController(cc)
  with MongoController with ReactiveMongoComponents {

  override def reactiveMongoApi: ReactiveMongoApi = reactiveMongo

  implicit def ec: ExecutionContext = cc.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("users"))

  def create = Action.async { request =>
    val params = request.body.asFormUrlEncoded.getOrElse(Map.empty[String, scala.Seq[String]])
    val name = params.get("name").get match {
      case Nil => ""
      case a :: _ => a
    }
    val email = params.get("email") match {
      case Some(value) => value match {
        case Nil => ""
        case a :: _ => a
      }
      case _ => ""
    }

    collection.flatMap(_.insert.one(models.User(name, email))).map(status => Ok("Mongo insert result: %s".format(status)))
  }

}
