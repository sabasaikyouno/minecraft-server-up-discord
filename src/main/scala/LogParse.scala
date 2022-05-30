import KanaKanjiConversion.conversion

object LogParse {

  def parse(log: String) = {
    val r = """: <.+> .*|: .+ (joined|left) the game""".r

    r.findFirstIn(log) match {
      case Some(s) => Some(s.drop(2))
      case _ => None
    }
  }

  def chatParse(log: String) = parse(log) match {
    case Some(chat) =>
      val rName = """<.+>""".r
      val rChat = """> .+""".r

      Some(MineMessage(
        rName.findFirstIn(chat).get.tail.dropRight(1),
        conversion(rChat.findFirstIn(chat).get.drop(2))
      ))
    case None => None
  }
}
