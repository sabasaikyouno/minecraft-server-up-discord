import akka.actor.Actor
import MineChat.sendToDiscord
import FileList._

class MineChatActor() extends Actor {
  var lastModified = logJFile.lastModified()
  var lines = logFile.getLines()
  lines.length

  override def receive = {
    case chat: Chat if lastModified != logJFile.lastModified() =>
      import chat._
      if (lines.hasNext) {
        lines.foreach(sendToDiscord)
        lastModified = logJFile.lastModified()
      } else {
        lines = logFile.getLines()
      }
  }
}
