import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "apimonitor"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.springframework"    %    "spring-context"    %    "3.0.7.RELEASE",
      "org.springframework"    %    "spring-core"       %    "3.0.7.RELEASE",
      "org.springframework"    %    "spring-beans"      %    "3.0.7.RELEASE",
      "net.databinder" 		   %    "dispatch-http_2.9.1" %  "0.8.8",
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
      "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
      "mysql" % "mysql-connector-java" % "5.1.18",
      "org.squeryl" %% "squeryl" % "0.9.5-2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here          
    )

}
