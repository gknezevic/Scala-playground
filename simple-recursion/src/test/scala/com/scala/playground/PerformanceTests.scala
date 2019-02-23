package com.scala.playground

import org.scalatest.FunSuite
import Recursion._

class PerformanceTests extends FunSuite{

  test("Performance of min function with 10 elements") {
    val (result, time) = executionTime(min(List(14, 155, -13, 0, 44, 55, 1, 22, 9, 11)))
    info("Execution time: " + time + " ns")
  }

  test("Performance of max function with 10 elements") {
    val (result, time) = executionTime(max(List(14, 155, -13, 0, 44, 55, 1, 22, 9, 11)))
    info("Execution time: " + time + " ns")
  }

  def executionTime[T](functionToExamine: => T) = {
    val startTime = System.nanoTime()
    val resultOfFunction = functionToExamine
    (resultOfFunction, System.nanoTime - startTime)
  }
}
