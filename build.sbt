import sbt.Keys._

parallelExecution in ThisBuild := false

lazy val versions = new {
  val finatra = "2.1.5"
  val guice = "4.0"
  val logback = "1.0.13"
  val mockito = "1.9.5"
  val scalatest = "2.2.3"
  val specs2 = "2.3.12"
}

lazy val baseSettings = Seq(
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  ivyScala := ivyScala.value.map(_.copy(overrideScalaVersion = true)),
  libraryDependencies ++= Seq(
    "org.mockito" % "mockito-core" % versions.mockito % "test",
    "org.scalatest" %% "scalatest" % versions.scalatest % "test",
    "org.specs2" %% "specs2" % versions.specs2 % "test"
  ),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    "Twitter Maven" at "https://maven.twttr.com"
  ),
  fork in run := true
)

lazy val root = (project in file(".")).
  settings(
    name := """finatra-thrift-seed""",
    organization := "com.example",
    moduleName := "activator-thrift-seed"
  ).
  aggregate(
    idl,
    server
  )

lazy val idl = (project in file("swiss-guard-idl")).
  settings(baseSettings).
  settings(
    name := "thrift-idl",
    moduleName := "thrift-idl",
    scroogeThriftDependencies in Compile := Seq(
      "finatra-thrift_2.11"
    ),
    libraryDependencies ++= Seq(
      "com.twitter.finatra" %% "finatra-thrift" % versions.finatra
    )
  )

lazy val server = (project in file("server")).
    enablePlugins(JavaServerAppPackaging).
    settings(baseSettings).
    settings(
      name := "writ-api",
      moduleName := "writ-api",
      libraryDependencies ++= Seq(

        "org.scala-lang" % "scala-compiler" % scalaVersion.value,
        //brought in from thrift client
        "com.twitter" %% "finagle-core" % "6.34.0",
//        "com.twitter" %% "finagle-stats" % "6.34.0",
        "com.twitter" % "finagle-thrift_2.11" % "6.34.0",
        "org.apache.thrift" % "libthrift" % "0.9.0" % "compile",
        //end of that

        "com.twitter.finatra" %% "finatra-http" % versions.finatra,
        "com.twitter.finatra" %% "finatra-slf4j" % versions.finatra,
        "ch.qos.logback" % "logback-classic" % versions.logback,
        "ch.qos.logback" % "logback-classic" % versions.logback % "test",

        "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test",
        "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % "test",
        "com.twitter.inject" %% "inject-server" % versions.finatra % "test",
        "com.twitter.inject" %% "inject-app" % versions.finatra % "test",
        "com.twitter.inject" %% "inject-core" % versions.finatra % "test",
        "com.twitter.inject" %% "inject-modules" % versions.finatra % "test",
        "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

        "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test" classifier "tests",
        "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % "test" classifier "tests",
        "com.twitter.inject" %% "inject-server" % versions.finatra % "test" classifier "tests",
        "com.twitter.inject" %% "inject-app" % versions.finatra % "test" classifier "tests",
        "com.twitter.inject" %% "inject-core" % versions.finatra % "test" classifier "tests",
        "com.twitter.inject" %% "inject-modules" % versions.finatra % "test" classifier "tests",

        "org.mockito" % "mockito-core" % versions.mockito % "test",
        "org.scalatest" %% "scalatest" % versions.scalatest % "test",
        "org.specs2" %% "specs2" % versions.specs2 % "test"
      )
    ).
    dependsOn(idl)

fork in run := true
