package services

import javax.inject.{Inject, Singleton}
import pdi.jwt.{Jwt, JwtAlgorithm}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthService @Inject()(authRepo: AuthRepo) {

  val superSecretKey = "thisissupersecretkey"

  def login(username: String, password: String): Future[Either[Error, String]] = {
    authRepo.checkIfUserWithPassExist(username, password)
      .map(result => Either.cond(result, generateJWT(username), new Error("User or password invalid")))
  }

  private def generateJWT(username: String): String = {
    Jwt.encode(username, superSecretKey, JwtAlgorithm.HS256)
  }

}

