import com.cm55.kanhira.{KakasiDictReader, Kanhira}
import com.ibm.icu.text.Transliterator

object KanaKanjiConversion {
  private val dict = KakasiDictReader.load("dict/kakasidict")
  private val kakasi = new Kanhira(dict)

  private val trans = Transliterator.getInstance("Latin-Hiragana")

  def conversion(chat: String) = {
    kakasi.convert(trans.transliterate(chat))
  }
}
