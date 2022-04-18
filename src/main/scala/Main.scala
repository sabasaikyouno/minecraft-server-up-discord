import ackcord._
import cmdEvents._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  private val token = sys.env("DISCORD_TOKEN")
  private val clientSettings = ClientSettings(token)
  private implicit val client = Await.result(clientSettings.createClient(), Duration.Inf)

  client.onEventSideEffects { implicit c => {
    case APIMessage.Ready(_) => println("Now ready")
    case APIMessage.MessageCreate(_, message, _) => cmd(message)
  }}

  client.login()
}

