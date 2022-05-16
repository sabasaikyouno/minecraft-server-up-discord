import akka.actor.{ActorSystem, Props}
import scala.concurrent.duration._
import org.apache.commons.io.FileUtils._
import FileList._

object Backup {

  def backup() = {
    if (canBackup()) {
      copyDirectory(worldFile, backupFolder)
      deleteBackup(10)
    }
  }

  def startAutoBackup() = {
    import actorSystem.dispatcher

    val actorSystem = ActorSystem("BackupActor")
    val backupActor = actorSystem.actorOf(Props(new BackupActor))

    actorSystem.scheduler.schedule(0.milliseconds,4.hours, backupActor, "Backup")
  }

  // 重複したbackupを取らないようにする
  def canBackup() =
    backupFiles.isEmpty || backupFiles.max.lastModified() != worldFile.lastModified()

  // backupファイル数がfileMax以上になったら超えた数を消す
  def deleteBackup(fileMax: Int) =
    sortedBackupFiles.drop(fileMax).foreach(deleteDirectory)

  private def sortedBackupFiles =
    backupFiles.sortBy(_.lastModified()).reverse
}
