ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file(".")).settings(
    name := "SpringBootScalaWebSocket"
  )
coverageEnabled := true
coverageExcludedPackages := "*.model.*;*application.SpringBootApp*"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
updateSbtClassifiers / useCoursier := true
coverageExcludedPackages := "model/*"
libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.2" withSources() withJavadoc(),
  "org.apache.commons" % "commons-lang3" % "3.12.0" withSources() withJavadoc(),
  "org.playframework.anorm" %% "anorm" % "2.7.0" withSources() withJavadoc(),
  "joda-time" % "joda-time" % "2.12.5" withSources() withJavadoc(),
  "net.logstash.logback" % "logstash-logback-encoder" % "7.3" withSources() withJavadoc(),
  "org.typelevel" %% "cats-core" % "2.9.0" withSources() withJavadoc(),
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.15" % "test",
  "org.scalacheck" %% "scalacheck" % "1.17.0" % Test,
  "com.univocity" % "univocity-parsers" % "2.9.1" withSources() withJavadoc(),
  "org.springframework.boot" % "spring-boot-dependencies" % "3.1.5",
  "org.springframework.boot" % "spring-boot-starter-test" % "3.1.5",
  "org.springframework.boot" % "spring-boot-starter-web" % "3.1.5",
  "org.springframework.boot" % "spring-boot-starter-actuator" % "3.1.5",
  "org.springframework" % "spring-messaging" % "6.0.13",
  "org.springframework" % "spring-websocket" % "6.0.13",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "org.scala-lang" % "scala-library" % "2.13.12",
  "org.scala-lang" % "scala-reflect" % "2.13.12",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
  "org.scala-lang" % "scala-compiler" % "2.13.12",
  "org.scalatestplus" %% "scalatestplus-junit" % "junit-4-13_2.13" % "3.2.15.0",
  "org.scalatestplus" %% "mockito-4-5" % "3.2.12.0",
  "org.springframework" % "spring-test" % "6.0.6",
  "org.springframework.boot" % "spring-boot-test" % "3.0.4"
)
coverageHighlighting := true
coverageFailOnMinimum := false
coverageMinimumStmtTotal := 70
coverageMinimumBranchTotal := 70
coverageMinimumStmtPerPackage := 70
coverageMinimumBranchPerPackage := 70
coverageMinimumStmtPerFile := 70
coverageMinimumBranchPerFile := 70

Test / publishArtifact := false
Test / parallelExecution := false