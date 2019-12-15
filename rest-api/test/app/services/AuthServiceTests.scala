package app.services

import java.util.concurrent.TimeUnit

import org.scalatest.FunSuite
import pdi.jwt.{Jwt, JwtAlgorithm}
import services.{AuthRepo, AuthService}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class AuthServiceTests extends FunSuite {

  val successAuthMock: SuccessAuthMock = new SuccessAuthMock
  val failedAuthMock: FailedAuthMock = new FailedAuthMock

  test("generating JWT for valid username and password") {
    val authService = new AuthService(successAuthMock)
    val jwtEither = Await.result(authService.login("testUser", "testPassword"), Duration(500, TimeUnit.MILLISECONDS))
    assert(jwtEither.isRight)
  }

  test("generating JWT will fail if user and password are not found") {
    val authService = new AuthService(failedAuthMock)
    val jwtEither = Await.result(authService.login("testUser", "testPassword"), Duration(500, TimeUnit.MILLISECONDS))
    assert(jwtEither.isLeft)
  }

  test("jwt can be decoded") {
    val authService = new AuthService(successAuthMock)
    val jwtEither = Await.result(authService.login("testUser", "testPassword"), Duration(500, TimeUnit.MILLISECONDS))
    assert(Jwt.decode(jwtEither.right.get, authService.superSecretKey, Seq(JwtAlgorithm.HS256)).isSuccess)
  }

  test("username can be read from generated JWT") {
    val authService = new AuthService(successAuthMock)
    val jwtEither = Await.result(authService.login("testUser", "testPassword"), Duration(500, TimeUnit.MILLISECONDS))

    val decoded = Jwt.decode(jwtEither.right.get, authService.superSecretKey, Seq(JwtAlgorithm.HS256))
    assert(decoded.get.content == "testUser")
  }

}

class SuccessAuthMock extends AuthRepo(null) {
  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global
  override def checkIfUserWithPassExist(username: String, password: String): Future[Boolean] = Future.apply(true)
}

class FailedAuthMock extends AuthRepo(null) {
  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global
  override def checkIfUserWithPassExist(username: String, password: String): Future[Boolean] = Future.apply(false)
}