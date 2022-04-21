import akka.actor.{Actor, ActorSystem, Props}
import scala.concurrent.duration._
import Backup._

object AkkaScheduler {
  import actorSystem.dispatcher

  val actorSystem = ActorSystem("BackupActor")
  val backupActor = actorSystem.actorOf(Props(new BackupActor))

  actorSystem.scheduler.schedule(0.milliseconds,4.hours, backupActor, "Backup")
}

class BackupActor extends Actor {

  override def receive: Receive = {
    case "Backup" => backup()
  }
}
