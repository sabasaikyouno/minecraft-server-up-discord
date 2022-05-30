import akka.actor.{ActorSystem, Props}
import okhttp3.{MediaType, OkHttpClient, Request, RequestBody, Response}
import io.circe._
import io.circe.parser._

import scala.concurrent.duration._

object YTChat {
  import actorSystem.dispatcher

  private val actorSystem = ActorSystem("YTChatActor")
  private val ytChat = actorSystem.actorOf(Props(new YTChatActor()))
  private val channelId = "UCLMdeeejyoQOu8ITbNJx9mg"
  private val client = new OkHttpClient()

  def startYTChat() = {
    actorSystem.scheduler.scheduleOnce(0.millis, ytChat, "loop")
  }

  def getLiveJson(keyOpt: Option[String], conOpt: Option[String]) =  {
    (keyOpt, conOpt) match {
      case (Some(key), Some(con)) =>
        val url = s"https://www.youtube.com/youtubei/v1/live_chat/get_live_chat?key=$key"
        val requestBody = getRequestBody(con)
        val response = getResponse(url, requestBody)

        Option(parse(response).getOrElse(Json.Null))
      case _ => None
    }
  }

  def getKeyAndCon() = {
    val url = s"https://www.youtube.com/channel/$channelId/live"
    val response = getResponse(url)

    val rKey = """"innertubeApiKey":[^,]+""".r
    val rCon = """"continuation":[^,]+""".r
    val keyOpt = rKey.findFirstMatchIn(response)
    val conOpt = rCon.findFirstMatchIn(response)

    (keyOpt, conOpt) match {
      case (Some(key), Some(con)) =>
        Option(
          Option(key.toString().split(':').last.filter(_ != '"')),
          Option(con.toString().split(':').last.filter(_ != '"'))
        )
      case _ => None
    }
  }

  private def getRequestBody(continuation: String) = {
    val MIMEType = MediaType.get("application/json; charset=utf-8")

    RequestBody.create(
      s""" {"context" : { "client": { "clientName": "WEB", "clientVersion": "2.20220513.01.00"}},continuation: "$continuation"}""",
      MIMEType
    )
  }

  private def getResponse(url: String) = {
    val request = new Request.Builder().url(url).build()

    client
      .newCall(request)
      .execute()
      .body()
      .string()
  }

  private def getResponse(url: String, requestBody: RequestBody) = {
    val request = new Request.Builder().url(url).post(requestBody).build()

    client
      .newCall(request)
      .execute()
      .body()
      .string()
  }

  def getNextConOpt(liveJson: Json) = {
    liveJson
      .findAllByKey("continuation")
      .headOption
      .map(
        _.as[String].getOrElse("").filter(_ != '"')
      )
  }

}
