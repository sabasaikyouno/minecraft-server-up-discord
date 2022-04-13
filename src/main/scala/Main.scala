import ackcord._
import ackcord.data._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.sys.process._

object Main extends App {

  val token = sys.env("DISCORD_TOKEN")
  val clientSettings = ClientSettings(token)
  val client = Await.result(clientSettings.createClient(), Duration.Inf)

  client.onEventSideEffectsIgnore {
    case APIMessage.Ready(_) => println("Now ready")
    case APIMessage.MessageCreate(_, message, _) if message.content == "!start server" => startServer()
  }

  client.login()

  def startServer() = {
    val batFile = "C:\\Users\\kurotan\\AppData\\Roaming\\.modserver1.12.2\\start.bat"

    Process(Seq("cmd.exe", "/c", "start", batFile)).run()
  }
}

