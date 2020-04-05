name := "finch-example"
version := "0.1"
scalaVersion := "2.12.10"

lazy val finchVersion = "0.31.0"
lazy val circeVersion = "0.13.0"
lazy val twitterServerVersion = "20.3.0"
lazy val finagleVersion = "20.4.0"

lazy val scalaTestVersion = "3.0.1"
lazy val mockitoTestVersion = "1.10.19"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % finchVersion,
  "com.github.finagle" %% "finch-circe" % finchVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.twitter" %% "twitter-server" % twitterServerVersion,
  "com.twitter" %% "finagle-stats" % finagleVersion,

  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.mockito" % "mockito-all" % mockitoTestVersion % "test"
)
