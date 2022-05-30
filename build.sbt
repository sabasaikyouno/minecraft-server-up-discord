name := "minecraft-server-up-discord"

version := "0.1"

scalaVersion := "2.13.8"

resolvers += Resolver.JCenterRepository
libraryDependencies += "net.katsstuff" %% "ackcord" % "0.17.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.14"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-file" % "3.0.4"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.14"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.6.14"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-typed" % "2.6.14"
libraryDependencies += "nl.vv32.rcon" % "rcon" % "1.2.0"
libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "4.9.3"
libraryDependencies += "joda-time" % "joda-time" % "2.10.14"
libraryDependencies += "com.ibm.icu" % "icu4j" % "71.1"
libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.5"

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)


assemblyMergeStrategy in assembly := {
  case PathList("module-info.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}