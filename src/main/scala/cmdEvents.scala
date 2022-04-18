import ackcord.{CacheSnapshot, DiscordClient}
import ackcord.data.TextChannelId
import ackcord.requests.{CreateMessage, CreateMessageData}

import scala.sys.process.Process
import scala.concurrent.ExecutionContext.Implicits.global

object cmdEvents {

  def startServer() = {
    val batFile = "C:\\Users\\kurotan\\AppData\\Roaming\\.modserver1.12.2\\start.bat"

    Process(Seq("cmd.exe", "/c", "start", batFile)).run()
  }

  def sendMsg(channelId: TextChannelId,msg: String)(implicit c: CacheSnapshot, client: DiscordClient) =
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
