object ToKana {
  private val romajiTable = Map(
    "a" -> "あ", "i" -> "い", "u" -> "う", "e" -> "え", "o" -> "お",
    "ka" -> "か", "ki" -> "き", "ku" -> "く", "ke" -> "け", "ko" -> "こ",
    "sa" -> "さ", "si" -> "し", "su" -> "す", "se" -> "せ", "so" -> "そ",
    "ta" -> "た", "ti" -> "ち", "tu" -> "つ", "te" -> "て", "to" -> "と",
    "na" -> "な", "ni" -> "に", "nu" -> "ぬ", "ne" -> "ね", "no" -> "の",
    "ha" -> "は", "hi" -> "ひ", "hu" -> "ふ", "he" -> "へ", "ho" -> "ほ",
    "ma" -> "ま", "mi" -> "み", "mu" -> "む", "me" -> "め", "mo" -> "も",
    "ya" -> "や", "yu" -> "ゆ", "yo" -> "よ",
    "ra" -> "ら", "ri" -> "り", "ru" -> "る", "re" -> "れ", "ro" -> "ろ",
    "wa" -> "わ", "wo" -> "を", "nn" -> "ん", "n" -> "ん",
    "ga" -> "が", "gi" -> "ぎ", "gu" -> "ぐ", "ge" -> "げ", "go" -> "ご",
    "za" -> "ざ", "zi" -> "じ", "zu" -> "ず", "ze" -> "ぜ", "zo" -> "ぞ",
    "da" -> "だ", "di" -> "ぢ", "du" -> "づ", "de" -> "で", "do" -> "ど",
    "ba" -> "ば", "bi" -> "び", "bu" -> "ぶ", "be" -> "べ", "bo" -> "ぼ",
    "pa" -> "ぱ", "pi" -> "ぴ", "pu" -> "ぷ", "pe" -> "ぺ", "po" -> "ぽ",
    "fa" -> "ふぁ", "fi" -> "ふぃ", "fu" -> "ふ", "fe" -> "ふぇ", "fo" -> "ふぉ",
    "kya" -> "きゃ", "kyu" -> "きゅ", "kyo" -> "きょ",
    "sha" -> "しゃ", "shu" -> "しゅ", "sho" -> "しょ",
    "cha" -> "ちゃ", "chu" -> "ちゅ", "cho" -> "ちょ",
    "nya" -> "にゃ", "nyu" -> "にゅ", "nyo" -> "にょ",
    "hya" -> "ひゃ", "hyu" -> "ひゅ", "hyo" -> "ひょ",
    "mya" -> "みゃ", "myu" -> "みゅ", "myo" -> "みょ",
    "rya" -> "りゃ", "ryu" -> "りゅ", "ryo" -> "りょ",
    "gya" -> "ぎゃ", "gyu" -> "ぎゅ", "gyo" -> "ぎょ",
    "ja" -> "じゃ", "ji" -> "じ", "ju" -> "じゅ", "je" -> "じぇ", "jo" -> "じょ",
    "dya" -> "ぢゃ", "dyu" -> "ぢゅ", "dyo" -> "ぢょ",
    "bya" -> "びゃ", "byu" -> "びゅ", "byo" -> "びょ",
    "pya" -> "ぴゃ", "pyu" -> "ぴゅ", "pyo" -> "ぴょ",
    "tya" -> "ちゃ", "tyi" -> "ちぃ", "tyu" -> "ちゅ", "tye" -> "ちぇ", "tyo" -> "ちょ"
  )

  private val maxRomaji = romajiTable.maxBy(_._1.length)._1.length + 1

  def toKana: String => String = {
    case "" => ""
    case s =>
      val headRomaji = getHeadRomaji(s)
      oneRomajiToKana(headRomaji) + toKana(s.drop(headRomaji.length))
  }

  private def getHeadRomaji(S: String): String =
    S.take(S.length min maxRomaji).inits.filter(s =>
      romajiTable.contains(s) ||
      (s.length >= 3 && !"aaiiuueeoonn".contains(s.take(2)) && s(0) == s(1) &&
        romajiTable.contains(s.tail))
    ).nextOption.getOrElse(S.head.toString)

  private def oneRomajiToKana(s: String): String =
    romajiTable.getOrElse(
      s,
      if (s.length >= 3)
        s"っ" + oneRomajiToKana(s.tail)
      else
        s
    )
}
