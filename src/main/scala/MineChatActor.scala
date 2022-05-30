import akka.actor.Actor
import MineChat.{sendToDiscord, sendToMineChat}
import FileList._

case class MineMessage(name: String, chat: String)

class MineChatActor() extends Actor {
  var lastModified = logJFile.lastModified()
  var lines = logFile.getLines()
  lines.length

  override def receive = {
    case chatImp: ChatImp if lastModified != logJFile.lastModified() =>
      import chatImp._
      if (lines.hasNext) {
        val chatLines = lines.toList
        chatLines.foreach(sendToDiscord)
        chatLines.foreach(sendToMineChat)
        lastModified = logJFile.lastModified()
      } else {
        lines = logFile.getLines()
      }
  }
}
