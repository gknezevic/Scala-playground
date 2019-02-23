package com.scala.playground

object Recursion {

  /***
    * Search for largest element in the list
    *
    * @param inputList Input list for which max element is returned
    * @return largest element (Int) or throw exception if list is Nil
    */
  def max(inputList: List[Int]): Int = {
    inputList match {
      case Nil => throw new IllegalArgumentException
      case x::Nil => x
      case _ => {
        val maxElement = max(inputList.tail)
        if (inputList.head > maxElement) inputList.head else maxElement
      }
    }
  }

  def min(inputList: List[Int]): Int = ???

  def sum(inputList: List[Int]): Int = ???
}
