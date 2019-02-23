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

  test("Min integer in the list should be -9") {
    assert(min(List(1, 3, -9, 7)) === -9)
  }

  test("Smallest positive integer is 1") {
    assert(min(List(1, 3, 9, 7)) === 1)
  }

  test("Sum of empty list is 0") {
    assert(sum(List()) === 0)
  }

  test("Sum of elements [3, 5, 7] is 15") {
    assert(sum(List(3, 5, 7)) === 15)
  }

  test("Sum of elements [3, 5, -7, -2] is -1") {
    assert(sum(List(3, 5, -7, -2)) === -1)
  }

}
