val tapirVersion = "1.8.1"

lazy val rootProject = (project in file(".")).settings(
  Seq(
    name := "foo",
    version := "0.1.0-SNAPSHOT",
    organization := "com.example",
    scalaVersion := "2.13.10",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
      "ch.qos.logback" % "logback-classic" % "1.4.11",
      "dev.zio" %% "zio-logging" % "2.1.12",
      "dev.zio" %% "zio-logging-slf4j" % "2.0.0",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirVersion % Test,
      "dev.zio" %% "zio-test" % "2.0.13" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.13" % Test,
      "com.softwaremill.sttp.client3" %% "circe" % "3.9.1" % Test
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )
)
