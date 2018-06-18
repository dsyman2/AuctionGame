package game.implicits

import models.{Bid, Object}

object BidImplicits {
  implicit class ImplicitBids(bids: Seq[Bid]){
    def maxOffer: Double = if(bids.nonEmpty) bids.map(_.price).max else 0d
    def areValid(auctionObject: Object): Boolean = bids.exists(_.price >= auctionObject.reservePrice)
  }

  implicit class ImplicitConverter(prices: Seq[Double]){
    def toBids: Seq[Bid] = prices.map(Bid)
  }
}
