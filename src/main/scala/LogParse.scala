object LogParse {

  def parse(log: String) = {
    val r = """: <.+> .*|: .+ (joined|left) the game""".r

    r.findFirstIn(log) match {
      case Some(s) => Some(s.drop(2))
      case _ => None
    }
  }
}
