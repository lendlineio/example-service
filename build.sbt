val slickV = "3.2.3"
val akkaV  = "10.0.9"

lazy val root = (project in file(".")).
  settings(
    name := "example-service",
    organizationName := "lendline.io",
    version := "1.0",
    scalaVersion := "2.12.4",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka"     %% "akka-http"              % akkaV,
      "com.typesafe.akka"     %  "akka-http-testkit_2.12" % akkaV % "test",
      "com.typesafe.akka"     %% "akka-http-spray-json"   % akkaV,
      "com.typesafe.akka"     %% "akka-slf4j"             % "2.4.16",


      "com.typesafe.scala-logging" %% "scala-logging"     % "3.9.0",

      "joda-time"             % "joda-time"               % "2.10",
      "org.joda"              % "joda-convert"            % "2.1",
      "com.github.tototoshi"  % "slick-joda-mapper_2.12"  % "2.3.0",
      "com.typesafe.slick"    % "slick_2.12"              % slickV,

      "org.postgresql"        % "postgresql"              % "9.4.1207.jre7",
      "com.h2database"        % "h2"                      % "1.4.196",
      "org.slf4j"             % "slf4j-api"               % "1.7.25",
      "org.slf4j"             % "slf4j-simple"            % "1.7.25",
      "org.scalatest"         % "scalatest_2.12"          % "3.0.3" % "test"
    )
  )

assemblyJarName in assembly := s"${name.value}-${git.gitHeadCommit.value.get}.jar"