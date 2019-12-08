package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class UserController @Inject() (cc: ControllerComponents, reactiveMongo: ReactiveMongoApi) extends AbstractController(cc)
  with MongoController with ReactiveMongoComponents {

  override def reactiveMongoApi: ReactiveMongoApi = reactiveMongo

  implicit def ec: ExecutionContext = cc.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("users"))

  def validate(params: Map[String, Seq[String]]): Try[Tuple2[String, String]] = {
    Try((getParamIfValid(params, "name"), getParamIfValid(params, "email")))
  }

  def getParamIfValid(params: Map[String, Seq[String]], paramName: String): String = {
    params.get(paramName) match {
      case Some(value) => value match {
        case Nil => throw new IllegalArgumentException(String.format("Parameter %s is missing", paramName))
        case a :: Nil => a
        case a :: b => throw new IllegalArgumentException(String.format("Parameter %s has multiple values", paramName))
      }
      case _ => throw new IllegalArgumentException(String.format("Parameter %s is missing", paramName))
    }
  }

  def store(params: Tuple2[String, String]): Future[Result] =
    collection.flatMap(_.insert.one(models.User(params._1, params._2)))
      .map(status => Ok("Mongo insert result: %s".format(status)))

  def create = Action.async { request =>
    val params = request.body.asFormUrlEncoded.getOrElse(Map.empty[String, scala.Seq[String]])
    validate(params) match {
      case Success(name_email) => store(name_email)
      case Failure(error) => Future.apply(BadRequest(error.getMessage))
    }
  }

}
