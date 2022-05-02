import java.io.File
import java.util.Date

trait FileList extends PathList {
  val worldFile = new File(worldFilePath)
  def backupFolder = {
    val folder = "world-" + new Date().toString.collect{ case ':' => '-'; case s => s}
    new File(backupFilePath + folder)
  }
  val backupFiles: Array[File] = new File(backupFilePath).listFiles()
  val mineallFile = new File(mineallFilePath)
  val cutallFile = new File(cutallFilePath)
}
