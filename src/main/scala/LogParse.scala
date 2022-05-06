object LogParse {

  def parse(log: String) = {
    val r = """: <.+> .*|: .+ joined the game""".r

    r.findFirstIn(log) match {
      case Some(s) => Some(s.drop(2))
      case _ => None
    }
  }
}
