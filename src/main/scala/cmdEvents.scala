import ackcord.{CacheSnapshot, DiscordClient}
import ackcord.data.{Message, TextChannelId}
import ackcord.requests.{CreateMessage, CreateMessageData, CreateMessageFile}
import Backup._

import scala.sys.process.Process
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.File

object cmdEvents extends FileList {

  def cmd(message: Message)(implicit c: CacheSnapshot, client: DiscordClient) = {
    implicit val channelId = message.channelId

    message.content match {
      case "!start-server" => startServer()
      case "!address"     => sendMsg(sys.env("Minecraft_Address"))
      case "!mod"         => sendMsg("https://1drv.ms/f/s!Andx-P21NVAYio8WCW0FzRLZziCRkg")
      case "!help"        => sendMsg(createHelp)
      case "!github"      => sendMsg("https://github.com/sabasaikyouno/minecraft-server-up-discord")
      case "!backup"      => backup()
      case msg if msg.take(7) == "!config" => cmdConfig(msg)
    }
  }

  def startServer() =
    Process(Seq("cmd.exe", "/c", "start", batFilePath)).run()

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
      ("!mod", "mod一覧ファイル"),
      ("!github", "discord-botのsource"),
      ("!backup", "マイクラのbackupを取る"),
      ("!config", "modのconfig一覧")
    ))

  def cmdConfig(msg: String)(implicit cacheSnapshot: CacheSnapshot, client: DiscordClient, channelId: TextChannelId) =
    msg.drop(8) match {
      case "mineall" => sendFile(mineallFile)
      case "cutall" => sendFile(cutallFile)
      case "" => sendMsg(createHelpConfig)
    }

  def sendFile(file: File)(implicit c: CacheSnapshot, client: DiscordClient, channelId: TextChannelId) =
    client.requestsHelper.run(
      CreateMessage(
        channelId,
        CreateMessageData(files = Seq(CreateMessageFile.FromPath(file.toPath)))
      )
    ).map(_ => ())

  def createHelpConfig =
    fmtHelp(Seq(
      ("使い方", "!config modの名前"),
      ("mineall", "mineallのconfig"),
      ("cutall", "ctuallのconfig")
    ))

  private def fmtHelp(seq: Seq[(String, String)]) = {
    val maxWidth = seq.maxBy(_._1.length)._1.length + 2

    seq.map { case (cmd, memo) =>
      cmd + " " * (maxWidth - cmd.length) + memo
    }.mkString("\n")
  }
}
