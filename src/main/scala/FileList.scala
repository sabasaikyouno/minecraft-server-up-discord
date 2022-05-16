import java.io.File
import java.util.Date

import scala.io.Source

object FileList extends PathList {
  val worldFile = new File(worldFilePath)
  def backupFolder = {
    val folder = "world-" + new Date().toString.collect{ case ':' => '-'; case s => s}
    new File(backupFilePath + folder)
  }
  val backupFile = new File(backupFilePath)
  def backupFiles: Array[File] = backupFile.listFiles()
  val mineallFile = new File(mineallFilePath)
  val cutallFile = new File(cutallFilePath)
  def logFile = Source.fromFile(logFilePath, "Shift-JIS")
  val logJFile = new File(logFilePath)
}
