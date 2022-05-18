import akka.actor.Actor
import YTChat._
import MineRcon.sendMineChat

class YTChatActor extends Actor {
  var latestTime = 0L

  def receive = {
    case "loop" =>
      getKeyAndCon() match {
        case Some((keyOpt: Some[String], conOpt: Some[String])) =>
          self ! (keyOpt, conOpt)
        case None =>
          Thread.sleep(10000)
          self ! "loop"
      }
    case (keyOpt: Option[String], conOpt: Option[String]) =>
      Thread.sleep(3000)
      getLiveJson(keyOpt, conOpt) match {
        case Some(liveJson) =>
          for {
            json <- liveJson.findAllByKey("liveChatTextMessageRenderer")
            name = json.findAllByKey("simpleText").head.as[String].getOrElse("")
            chat = json.findAllByKey("text").head.as[String].getOrElse("")
            time = json.findAllByKey("timestampUsec").head.as[Long].getOrElse(0L)
            if time > latestTime
          } {
            latestTime = time
            sendMineChat(name, chat)
          }

          getNextConOpt(liveJson) match {
            case nextConOpt: Some[String] => self ! (keyOpt, nextConOpt)
            case None => self ! "loop"
          }
        case None => self ! "loop"
      }
  }
}
