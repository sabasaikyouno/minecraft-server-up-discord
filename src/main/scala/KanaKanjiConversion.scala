import com.ibm.icu.text.Transliterator
import okhttp3.{OkHttpClient, Request}
import org.json4s.native.JsonMethods.parse

object KanaKanjiConversion {
  private val trans = Transliterator.getInstance("Latin-Hiragana")

  private val client = new OkHttpClient()

  def conversion(chat: String) = {
    val url = "http://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + trans.transliterate(chat)
    val request = new Request.Builder().url(url).build()
    val json = parse(client.newCall(request).execute().body().string())

    json.children.map(json => json.children.last.children.head.values.toString).mkString
  }
}
