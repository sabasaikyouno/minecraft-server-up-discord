import java.nio.charset.Charset

import ackcord.data.TextChannelId
import ackcord.requests.{CreateMessage, CreateMessageData}
import ackcord.{CacheSnapshot, DiscordClient}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.file.scaladsl.FileTailSource
import LogParse._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object MineChat extends FileList {
  private val textChannelId = TextChannelId(972072490903949332L)

  def startMineChat()(implicit c: CacheSnapshot, client: DiscordClient) = {
    implicit val system = ActorSystem("MineChatActor")
    implicit val materializer = ActorMaterializer()

    val lines = FileTailSource.lines(
      path = logFile.toPath,
      maxLineSize = 8192,
      pollingInterval = 250.millis,
      charset = Charset.forName("sjis")
    )

    lines.runForeach(sendToDiscord)
  }

  def sendToDiscord(line: String)(implicit c: CacheSnapshot, client: DiscordClient) = {
    parse(line) match {
      case Some(chat) =>
        client.requestsHelper.run(
          CreateMessage(
            textChannelId,
            CreateMessageData(chat)
          )
        ).map(_ => ())
      case None => ()
    }
  }
}
