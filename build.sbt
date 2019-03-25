import sbt.url

organization := "io.onema"

name := "beenpwned4s"

version := "0.1.0"

scalaVersion := "2.12.8"

libraryDependencies ++= {
  Seq(

    // dependencies
    "com.lihaoyi" % "requests_2.12"       % "0.1.7",
    "io.onema"   %% "json-extensions"     % "0.5.0",

    // Testing
    "org.scalatest"               % "scalatest_2.12"          % "3.0.5"   % Test,
    "org.scalamock"               %% "scalamock"              % "4.1.0"   % Test
  )
}

// Maven Central Repo boilerplate configuration
pomIncludeRepository := { _ => false }
licenses := Seq("MIT" -> url("https://github.com/onema/beenpwned4s/blob/master/LICENSE"))
homepage := Some(url("https://github.com/onema/beenpwned4s"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/onema/beenpwned4s"),
    "scm:git@github.com:onema/beenpwned4s.git"
  )
)
developers := List(
  Developer(
    id    = "onema",
    name  = "Juan Manuel Torres",
    email = "software@onema.io",
    url   = url("https://github.com/onema/")
  )
)
publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
publishArtifact in Test := false