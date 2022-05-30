import ackcord.requests.{CreateMessage, CreateMessageData}
import ackcord.{CacheSnapshot, DiscordClient}
import akka.actor.{ActorSystem, Props}
import LogParse._
import DiscoList._
import MineRcon.sendMineChat

import scala.concurrent.duration._

case class ChatImp()(implicit val c: CacheSnapshot, implicit val client: DiscordClient)

object MineChat {
  import actorSystem.dispatcher

  private val actorSystem = ActorSystem("MineChatActor")
  private val mineChatActor = actorSystem.actorOf(Props(new MineChatActor()))

  def startMineChat()(implicit c: CacheSnapshot, client: DiscordClient) =
    actorSystem.scheduler.schedule(3.seconds, 500.millis, mineChatActor, ChatImp())

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

  def sendToMineChat(line: String): Unit = {
    chatParse(line) match {
      case Some(mineMessage) =>
        sendMineChat(mineMessage)
      case None => ()
    }
  }
}
