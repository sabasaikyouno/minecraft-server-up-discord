import ackcord._
import cmdEvents.cmd
import Backup._
import MineChat._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  private val token = sys.env("DISCORD_TOKEN")
  private val clientSettings = ClientSettings(token)
  private implicit val client = Await.result(clientSettings.createClient(), Duration.Inf)

  // event
  client.onEventSideEffects { implicit c => {
    case APIMessage.Ready(_) => startMineChat()
    case APIMessage.MessageCreate(_, message, _) => cmd(message)
  }}

  client.login()

  // 自動バックアップスタート
  startAutoBackup()
}

