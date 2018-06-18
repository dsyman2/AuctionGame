package game

sealed trait Game

trait AuctionGame extends Game

trait SecondPriceAuction extends AuctionGame
