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

  def createTemp = create("Pera", "pera@gmail.com")


  def create(name: String, email: String) = Action.async {
    collection.flatMap(_.insert.one(models.User(name, email))).map(status => Ok("Mongo insert result: %s".format(status)))
  }

}
