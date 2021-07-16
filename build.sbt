val slickV = "3.3.3"
val akkaV  = "10.2.4"

lazy val root = (project in file(".")).
  settings(
    name := "example-service",
    organizationName := "lendline.io",
    version := "1",
    scalaVersion := "2.13.5",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka"     %% "akka-http"              % akkaV,
      "com.typesafe.akka"     %% "akka-http-testkit"      % akkaV,
      "com.typesafe.akka"     %% "akka-testkit"           % "2.6.15" % Test,
      "com.typesafe.akka"     %% "akka-stream"            % "2.6.15",
      "com.typesafe.akka"     %% "akka-http-spray-json"   % akkaV,
      "com.typesafe.akka"     %% "akka-slf4j"             % "2.6.15",

      "com.typesafe.scala-logging" %% "scala-logging"     % "3.9.4",

      "joda-time"             % "joda-time"               % "2.10.10",
      "org.joda"              % "joda-convert"            % "2.2.1",

      "com.github.tototoshi"  %% "slick-joda-mapper"       % "2.5.0",
      "com.typesafe.slick"    %% "slick"                  % "3.3.3",

      "org.postgresql"        % "postgresql"              % "42.2.23",
      "com.h2database"        % "h2"                      % "1.4.200",
      "org.slf4j"             % "slf4j-api"               % "1.7.31",
      "org.slf4j"             % "slf4j-simple"            % "1.7.31",
      "org.scalatest"         %% "scalatest"              % "3.2.9" % Test
    )
  )

assemblyJarName in assembly := s"${name.value}-${git.gitHeadCommit.value.get}.jar"