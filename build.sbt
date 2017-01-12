val slickV = "3.1.1"
val akkaV  = "10.0.1"

lazy val root = (project in file(".")).
  settings(
    name := "example-service",
    organizationName := "lendline.io",
    version := "1.0",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
    libraryDependencies ++= Seq(
        "com.typesafe.akka"     %% "akka-http"              % akkaV,
        "com.typesafe.akka"     %% "akka-http-spray-json"   % akkaV,
        "com.typesafe.akka"     %% "akka-slf4j"             % "2.4.16",

        "joda-time"             % "joda-time"               % "2.9.2",

        "com.typesafe.slick"    %% "slick"                  % slickV,
        "com.typesafe.slick"    %% "slick-codegen"          % slickV,

        "org.postgresql"        % "postgresql"              % "9.4.1207.jre7",
        "org.slf4j"             % "slf4j-simple"            % "1.7.22"
        //        "org.scalatest"       %%  "scalatest"          % "2.2.6" % "test",
      )
  )

assemblyJarName in assembly := s"${name.value}-${git.gitHeadCommit.value.get}.jar"