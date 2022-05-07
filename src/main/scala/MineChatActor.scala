import akka.actor.Actor
import MineChat.sendToDiscord
import ackcord.{CacheSnapshot, DiscordClient}

class MineChatActor(lines: Iterator[String])(implicit c: CacheSnapshot, client: DiscordClient) extends Actor with FileList {

  override def receive = {
    case "chat" => lines.foreach(sendToDiscord)
    case "init" => lines.length
  }
}
