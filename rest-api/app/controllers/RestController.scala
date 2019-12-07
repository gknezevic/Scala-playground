package controllers

import javax.inject._
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.mvc._
import services.Counter

import scala.concurrent.ExecutionContext

@Singleton
class RestController @Inject()(cc: ControllerComponents, counter: Counter)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def status = Action {
    Ok(models.Status("Everything works fine", counter.nextCount()).showMessage)
  }

  def jsonStatus: Action[AnyContent] = Action {
    Ok(Json.toJson(models.Status("Everything works fine", counter.nextCount())))
  }

}
