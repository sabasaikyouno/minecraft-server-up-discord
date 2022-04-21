name := "minecraft-server-up-discord"

version := "0.1"

scalaVersion := "2.13.8"

resolvers += Resolver.JCenterRepository
libraryDependencies += "net.katsstuff" %% "ackcord" % "0.17.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.32"

assemblyMergeStrategy in assembly := {
  case PathList("module-info.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}