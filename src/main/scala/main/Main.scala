package main

import cats.data.Validated
import error.InvalidAuction
import game.{ResultGame, SecondPriceAuction}
import game.implicits.BidImplicits._
import models.{Auction2nd, Buyer, Object}


object Main {

  val auctionObject: Object = Object(100)
  val aBuyer = Buyer("A")
  val bBuyer = Buyer("B")
  val cBuyer = Buyer("C")
  val dBuyer = Buyer("D")
  val eBuyer = Buyer("E")

  val validAuction: Auction2nd = Auction2nd(auctionObject,
    Map(
      aBuyer -> Seq(110d,130).toBids,
      bBuyer -> Seq.empty[Double].toBids,
      cBuyer -> Seq(125d).toBids,
      dBuyer -> Seq(105d, 115, 90).toBids,
      eBuyer -> Seq(132d,135, 140).toBids

    ))

  def main(args: Array[String]): Unit = {

    import game.implicits.GameImplicits._

    val validatedAuction: Validated[Seq[InvalidAuction], Auction2nd] = Auction2nd.validate(validAuction)

    val res: Validated[Seq[InvalidAuction], ResultGame] = validatedAuction.map(resultGame[SecondPriceAuction](_))

    println(res)

  }
}
