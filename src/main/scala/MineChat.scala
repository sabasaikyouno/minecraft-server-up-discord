import ackcord.data.TextChannelId
import ackcord.requests.{CreateMessage, CreateMessageData}
import ackcord.{CacheSnapshot, DiscordClient}
import akka.actor.{ActorSystem, Props}
import LogParse._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object MineChat extends FileList with DiscoList {

  def startMineChat()(implicit c: CacheSnapshot, client: DiscordClient) = {
    import actorSystem.dispatcher

    val actorSystem = ActorSystem("MineChatActor")
    val mineChatActor = actorSystem.actorOf(Props(new MineChatActor(logFile.getLines())))

    actorSystem.scheduler.scheduleOnce(0.millis, mineChatActor, "init")
    actorSystem.scheduler.schedule(500.millis, 500.millis, mineChatActor, "chat")
  }

  def sendToDiscord(line: String)(implicit c: CacheSnapshot, client: DiscordClient): Unit = {
    parse(line) match {
      case Some(chat) =>
        client.requestsHelper.run(
          CreateMessage(
            mineChatChannel,
            CreateMessageData(chat)
          )
        ).map(_ => ())
      case None => ()
    }
  }
}
