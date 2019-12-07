package controllers

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext}

@Singleton
class RestController @Inject()(cc: ControllerComponents)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def status = Action {
    Ok("Everything works fine")
  }

}
