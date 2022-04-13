import ackcord._
import ackcord.data.TextChannelId
import ackcord.requests._
import ackcord.syntax._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.sys.process._

object Main extends App {

  val token = sys.env("DISCORD_TOKEN")
  val clientSettings = ClientSettings(token)
  val client = Await.result(clientSettings.createClient(), Duration.Inf)

  client.onEventSideEffects { implicit c => {
    case APIMessage.Ready(_) => println("Now ready")
    case APIMessage.MessageCreate(_, message, _) if message.content == "!start server" => startServer()
    case APIMessage.MessageCreate(_, message, _) if message.content == "!address" => sendMsg(message.channelId, sys.env("Minecraft_Address"))
    case APIMessage.MessageCreate(_, message, _) if message.content == "!mod" => sendMsg(message.channelId, "https://www.dropbox.com/sh/34cwpmnf5q6al5g/AAC1MTx5TviqHUGWG9eXE5Cta?dl=0")
    case APIMessage.MessageCreate(_, message, _) if message.content == "!help" => sendMsg(message.channelId, createHelp)
  }}

  client.login()

  def startServer() = {
    val batFile = "C:\\Users\\kurotan\\AppData\\Roaming\\.modserver1.12.2\\start.bat"

    Process(Seq("cmd.exe", "/c", "start", batFile)).run()
  }

  def sendMsg(channelId: TextChannelId,msg: String)(implicit c: CacheSnapshot) =
    client.requestsHelper.run(
      CreateMessage(
        channelId,
        CreateMessageData(msg)
      )
    ).map(_ => ())

  def createHelp =
    """
      |!help          コマンド一覧
      |!start server  マイクラサーバー起動
      |!address       マイクラのサーバーアドレス
      |!mod           mod一覧ファイル
      |""".stripMargin

}

