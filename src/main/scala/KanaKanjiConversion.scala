import okhttp3.{OkHttpClient, Request}
import org.json4s.native.JsonMethods.parse
import ToKana.toKana

object KanaKanjiConversion {
  private val client = new OkHttpClient()

  def conversion(chat: String) = {
    val url = "http://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + toKana(chat)
    val request = new Request.Builder().url(url).build()
    val json = parse(client.newCall(request).execute().body().string())

    json.children.map(json => json.children.last.children.head.values.toString).mkString
  }

}
