trait PathList {
  val serverFilePath: String = "C:\\Users\\kurotan\\AppData\\Roaming\\.modserver1.12.2\\"
  val batFilePath: String = serverFilePath + "start.bat"
  val worldFilePath: String = serverFilePath + "world\\"
  val backupFilePath: String = serverFilePath + "backup\\"
  val configFilePath: String = serverFilePath + "config\\"
  val mineallFilePath: String = configFilePath + "net.minecraft.scalar.mineall.mod_mineallsmp.cfg"
  val cutallFilePath: String = configFilePath + "net.minecraft.scalar.cutall.mod_cutallsmp.cfg"
}
