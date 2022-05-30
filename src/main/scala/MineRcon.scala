import java.net.InetSocketAddress
import java.nio.channels.SocketChannel
import java.nio.charset.Charset

import ackcord.data.Message
import nl.vv32.rcon.Rcon

object MineRcon {
  private val minePassword = sys.env("Minecraft_RCON_Password")
  private val rcon = Rcon.newBuilder()
    .withChannel(SocketChannel.open(
      new InetSocketAddress("localhost", 65285)))
    .withCharset(Charset.forName("utf-8"))
    .build()

  def sendMineChat(msg: Any) = {
    val cmd = msg match {
      case msg: Message => fmtDiscordChat(msg)
      case msg: YTMessage => fmtYoutubeChat(msg)
      case msg: MineMessage => fmtMineChat(msg)
    }

    if (rcon.authenticate(minePassword))
      rcon.sendCommand(cmd)
  }

  private def fmtDiscordChat(msg: Message) = {
    fmtChat(msg.authorUsername, msg.content, from = "[Discord]", fromColor = "aqua")
  }

  private def fmtYoutubeChat(msg: YTMessage) = {
    fmtChat(msg.name, msg.chat, target = "kuro0117", from = "[YouTube]", fromColor = "red")
  }

  private def fmtMineChat(msg: MineMessage) = {
    fmtChat(s"[${msg.name}]", msg.chat, target = "kuro0117")
  }

  private def fmtChat(
    name: String,
    chat: String,
    target: String = "@a",
    chatColor: String = "white",
    from: String = "",
    fromColor: String = "white"
  ): String = {
    s"""tellraw $target [{"text":"$from", "color":"$fromColor"}, {"text":" $name >> $chat", "color":"$chatColor"}]"""
  }
}
