package game.implicits

import game.{ApiModel, Game, ResultGame, SecondPriceAuction}
import models.Auction2nd


object GameImplicits{
  def resultGame[G <: Game](g: G)(implicit api: ApiModel[G]): ResultGame = api.getWinner(g)

  implicit val getSecondPriceGameAuction: ApiModel[SecondPriceAuction] = ApiModel{ game: SecondPriceAuction =>
    game match {
      case g: Auction2nd => ApiModel.predictWinner(g)
      case _ => ResultGame.InvalidGame
    }
  }
}
