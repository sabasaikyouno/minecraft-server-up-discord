import akka.actor.Actor
import Backup._

class BackupActor extends Actor {

  override def receive: Receive = {
    case "Backup" => backup()
  }
}
