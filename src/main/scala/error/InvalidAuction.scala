package error


trait InvalidAuction

object InvalidAuction {

  case object LessTwoPlayers extends InvalidAuction
  case object LessReservePrice extends InvalidAuction

}