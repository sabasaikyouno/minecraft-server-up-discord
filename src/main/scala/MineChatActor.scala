import akka.actor.Actor

class MineChatActor extends Actor {

  override def receive: Receive = {
    case "chat" => println("hi")
  }
}
