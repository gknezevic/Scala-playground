package com.scala.playground

import com.scala.playground.Recursion._
import org.scalatest.FunSuite

class RecursionTests extends FunSuite {

  test("largest integer in the list should be 7") {
    assert(max(List(1, 3, -9, 7)) === 7)
  }
  
}
