import akka.actor.Actor
import MineChat.sendToDiscord
import ackcord.{CacheSnapshot, DiscordClient}

class MineChatActor(lines: Iterator[String])(implicit c: CacheSnapshot, client: DiscordClient) extends Actor with FileList {
  var lastModified = logJFile.lastModified()

  override def receive = {
    case "chat" if lastModified != logJFile.lastModified() =>
      lines.foreach(sendToDiscord)
      lastModified = logJFile.lastModified()
    case "init" => lines.length
  }
}
