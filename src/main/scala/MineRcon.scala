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

  def sendMineChat(msg: Message) = {
    if (rcon.authenticate(minePassword))
      rcon.sendCommand(fmtDiscordChat(msg.authorUsername, msg.content))
  }

  def sendMineChat(name: String, chat: String) = {
    if (rcon.authenticate(minePassword))
      rcon.sendCommand(fmtYoutubeChat(name, chat))
  }

  private def fmtDiscordChat(name: String, chat: String) = {
    s"""tellraw @a [{"text":"[Discord]", "color":"aqua"}, {"text":" $name >> $chat", "color":"white"}]"""
  }

  private def fmtYoutubeChat(name: String, chat: String) = {
    s"""tellraw kuro0117 [{"text":"[YouTube]", "color":"red"}, {"text":" $name >> $chat", "color":"white"}]"""
  }
}
