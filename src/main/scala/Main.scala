import ackcord._
import cmdEvents._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  private val token = sys.env("DISCORD_TOKEN")
  private val clientSettings = ClientSettings(token)
  private implicit val client = Await.result(clientSettings.createClient(), Duration.Inf)

  client.onEventSideEffects { implicit c => {
    case APIMessage.Ready(_) => println("Now ready")
    case APIMessage.MessageCreate(_, message, _) if message.content == "!start-server" => startServer()
    case APIMessage.MessageCreate(_, message, _) if message.content == "!address" => sendMsg(message.channelId, sys.env("Minecraft_Address"))
    case APIMessage.MessageCreate(_, message, _) if message.content == "!mod" => sendMsg(message.channelId, "https://www.dropbox.com/sh/34cwpmnf5q6al5g/AAC1MTx5TviqHUGWG9eXE5Cta?dl=0")
    case APIMessage.MessageCreate(_, message, _) if message.content == "!help" => sendMsg(message.channelId, createHelp)
  }}

  client.login()
}

