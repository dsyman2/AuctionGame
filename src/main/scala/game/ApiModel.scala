package game

import game.ResultGame.ResultBuyer
import models.{Auction2nd, Bid, Buyer}

/**
  * Here is the interface that encapsulates the non-deterministic game concept
  * For example, if a ML algorithm detects cheating
  * @tparam G
  */
trait ApiModel[G <: Game] {
  def getWinner(game: G): ResultGame
}

object ApiModel{

  def apply[G <: Game](f: G => ResultGame): ApiModel[G] = new ApiModel[G] {
   override def getWinner(g: G) = f(g)
  }

  /**
    * Determine if there are cheating buyers
    * Here for example, if a buyer proposes a price which cost more than 1000x the object, we can say he's cheating with
    * a complice
    * @param validAuction
    * @return
    */
  def isCheat(validAuction: Auction2nd): Boolean = validAuction
    .validBuyers
    .exists(_._2.maxBy(_.price).price >= validAuction.auctionObject.reservePrice*1000)

  /**
    * Valid auction mean > 2 buyers and price > reservePrice
    * Could be non-deterministic
    * @param validAuction
    * @return
    */
  def predictWinner(validAuction: Auction2nd): ResultGame = {
    import implicits.BidImplicits.ImplicitBids
    import validAuction._

    // Determine max price
    val maxBuyer: (Buyer, Seq[Bid]) = buyers.reduce((b1, b2) => if(b1._2.maxOffer >= b2._2.maxOffer) b1 else b2)

    // Determine second max price
    val secondMaxPrice: Double = {
      val looseBuyers = buyers - maxBuyer._1
      val maxSecondPlayerPrice = looseBuyers.map(_._2.maxOffer).max
      math.max(maxSecondPlayerPrice, auctionObject.reservePrice)
    }

    if(isCheat(validAuction)) ResultGame.InvalidGame else ResultBuyer(maxBuyer._1, secondMaxPrice)
  }

}