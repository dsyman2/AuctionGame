package game

import models.Buyer

sealed trait ResultGame

object ResultGame {

  case class ResultBuyer(buyer: Buyer, resultPrice: Double) extends ResultGame {
    override def toString: String = s"${buyer.id} won the game with a price of $resultPrice"
  }

  case object InvalidGame extends ResultGame

}