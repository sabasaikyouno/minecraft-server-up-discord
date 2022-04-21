import java.io.File
import java.util.Date

import org.apache.commons.io.FileUtils._

object Backup {

  def backup() = {
    val worldFilePath = new File("C:\\Users\\kurotan\\AppData\\Roaming\\.mod3server1.12.2\\world")
    val folder = "world-" + new Date().toString.collect{ case ':' => '-'; case s => s}
    val backupFilePath = new File("C:\\Users\\kurotan\\AppData\\Roaming\\.mod3server1.12.2\\backup\\" + folder)

    copyDirectory(worldFilePath, backupFilePath)
  }

  def startAutoBackup() = {
    AkkaScheduler
  }
}
