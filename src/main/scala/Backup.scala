import java.io.File
import java.util.Date

import akka.actor.{ActorSystem, Props}
import scala.concurrent.duration._
import org.apache.commons.io.FileUtils._
import java.nio.file.Files.delete

object Backup {

  private val worldFilePath = "C:\\Users\\kurotan\\AppData\\Roaming\\.mod3server1.12.2\\world"
  private val backupFilePath = "C:\\Users\\kurotan\\AppData\\Roaming\\.mod3server1.12.2\\backup"

  def backup() = {
    val worldFile = new File(worldFilePath)
    val folder = "\\world-" + new Date().toString.collect{ case ':' => '-'; case s => s}
    val backupFile = new File(backupFilePath + folder)

    if (canBackup()) {
      copyDirectory(worldFile, backupFile)
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
  def canBackup() = {
    val worldFile = new File(worldFilePath)
    val backupFiles = new File(backupFilePath)

    worldFile.lastModified() != backupFiles.lastModified()
  }

  // backupファイル数がfileMax以上になったら超えた数を消す
  def deleteBackup(fileMax: Int) = {
    val backupFiles = new File(backupFilePath).listFiles().sorted.reverse

    backupFiles.drop(fileMax).foreach(deleteDirectory)
  }
}
