package models

import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import error.InvalidAuction
import game.SecondPriceAuction
import game.implicits.BidImplicits._

case class Auction2nd(auctionObject: Object,
                      buyers: Map[Buyer, Seq[Bid]]) extends SecondPriceAuction {
  /**
    * Valid buyers are those who haveat least one  bid > reserve price
    * @return
    */
  def validBuyers: Map[Buyer, Seq[Bid]] = buyers.filter(_._2.areValid(auctionObject))
}

object Auction2nd {

  /**
    * Invalid action if there are less than 2 buyers
    * @param buyers
    * @return
    */
  def validSize(buyers: Seq[Buyer]): Option[InvalidAuction] = if (buyers.distinct.size < 2) Some(InvalidAuction.LessTwoPlayers) else None

  /**
    * Invalid action if no buyers bid over reserve price
    * @param validBuyers
    * @param auctionObject
    * @return
    */
  def validPrice(validBuyers: Map[Buyer, Seq[Bid]], auctionObject: Object): Option[InvalidAuction] = {
    val offers = validBuyers.flatMap(_._2).toSeq
    if (offers.areValid(auctionObject)) None else Some(InvalidAuction.LessReservePrice)
  }

  /**
    * Return valid Auction or errors
    * @param auction
    * @return
    */
  def validate(auction: Auction2nd): Validated[Seq[InvalidAuction], Auction2nd] = {

    import auction._

    val optLessThanTwoBuyers: Option[InvalidAuction] = validSize(buyers.keys.toSeq)
    val optLessThanReservePrice: Option[InvalidAuction] = validPrice(validBuyers, auctionObject)

    val errors: Seq[InvalidAuction] = optLessThanReservePrice.toSeq ++ optLessThanTwoBuyers

    if (errors.nonEmpty) Invalid(errors) else Valid(auction)
  }

}