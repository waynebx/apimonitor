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
      "net.databinder" % "dispatch-http_2.9.1" % "0.8.8",
      "com.google.code" % "morphia" % "0.91"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
       
    )

}
