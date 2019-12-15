name := "restApi"
 
version := "1.0" 
      
lazy val `restapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies ++= Seq( "org.reactivemongo" %% "play2-reactivemongo" % "0.19.3-play27" )
libraryDependencies ++= Seq("com.pauldijou" %% "jwt-core" % "4.2.0")
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

routesGenerator := InjectedRoutesGenerator