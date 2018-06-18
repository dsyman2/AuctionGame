package models

import cats.data.Validated.{Invalid, Valid}
import error.InvalidAuction.{LessReservePrice, LessTwoPlayers}
import game.ApiModel
import game.ResultGame.ResultBuyer
import game.implicits.BidImplicits.ImplicitConverter
import org.scalatest.FunSuite

class Auction2ndTest extends FunSuite {

  import main.Main._

  test("test result game"){

    val validAuction2 = Auction2nd(auctionObject, Map(aBuyer -> Seq(90d).toBids, aBuyer.copy(id = "B")->Seq(20,140d).toBids))

    val invalidAuction1 = Auction2nd(auctionObject, Map(aBuyer -> Seq(40d).toBids))
    val invalidAuction2 = Auction2nd(auctionObject, Map(aBuyer -> Seq(120d).toBids))
    val invalidAuction3 = Auction2nd(auctionObject, Map(aBuyer -> Seq(90d).toBids, bBuyer -> Seq(95d).toBids))

    val Seq(validRes1, validRes2) = Seq(validAuction, validAuction2).map(Auction2nd.validate(_).map(ApiModel.predictWinner))

    val Seq(inv1, inv2, inv3) = Seq(invalidAuction1, invalidAuction2, invalidAuction3).map{ ia =>
      Auction2nd.validate(ia).map(ApiModel.predictWinner)
    }


    assert(inv1 == Invalid(Seq(LessReservePrice, LessTwoPlayers)))
    assert(inv2 == Invalid(Seq(LessTwoPlayers)))
    assert(inv3 == Invalid(Seq(LessReservePrice)))

    assert(validRes1 == Valid(ResultBuyer(eBuyer, 130d)))
    assert(validRes2 == Valid(ResultBuyer(bBuyer, 100d)))

  }
}
