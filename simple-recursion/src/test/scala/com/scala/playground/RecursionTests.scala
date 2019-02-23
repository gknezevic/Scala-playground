package com.scala.playground

import com.scala.playground.Recursion._
import org.scalatest.FunSuite

class RecursionTests extends FunSuite {

  test("Largest integer in the list should be 7") {
    assert(max(List(1, 3, -9, 7)) === 7)
  }

  test("Largest negative integer is -1") {
    assert(max(List(-1, -3, -9, -7)) === -1)
  }

  test("Throw exception if list is empty") {
    intercept[IllegalArgumentException](max(List()))
  }

}
