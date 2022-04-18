import ackcord.{CacheSnapshot, DiscordClient}
import ackcord.data.{Message, TextChannelId}
import ackcord.requests.{CreateMessage, CreateMessageData}

import scala.sys.process.Process
import scala.concurrent.ExecutionContext.Implicits.global

object cmdEvents {

  def cmd(message: Message)(implicit c: CacheSnapshot, client: DiscordClient) = {
    implicit val channelId = message.channelId

    message.content match {
      case "!start-server" => startServer()
      case "!address" => sendMsg(sys.env("Minecraft_Address"))
      case "!mod" => sendMsg("https://www.dropbox.com/sh/34cwpmnf5q6al5g/AAC1MTx5TviqHUGWG9eXE5Cta?dl=0")
      case "!help" => sendMsg(createHelp)
    }
  }

  def startServer() = {
    val batFile = "C:\\Users\\kurotan\\AppData\\Roaming\\.modserver1.12.2\\start.bat"

    Process(Seq("cmd.exe", "/c", "start", batFile)).run()
  }

  def sendMsg(msg: String)(implicit c: CacheSnapshot, client: DiscordClient, channelId: TextChannelId) =
    client.requestsHelper.run(
      CreateMessage(
        channelId,
        CreateMessageData(msg)
      )
    ).map(_ => ())

  def createHelp =
    fmtHelp(Seq(
      ("!help", "コマンド一覧"),
      ("!start-server", "マイクラサーバー起動"),
      ("!address", "マイクラのサーバーアドレス"),
      ("!mod", "mod一覧ファイル")
    ))

  private def fmtHelp(seq: Seq[(String, String)]) = {
    val maxWidth = seq.maxBy(_._1)._1.length + 2

    seq.map { case (cmd, memo) =>
      cmd + " " * (maxWidth - cmd.length) + memo
    }.mkString("\n")
  }
}
