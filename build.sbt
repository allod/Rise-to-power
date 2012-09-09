name:= "Rise to power"

version := "0.1"

scalaVersion := "2.9.2"

libraryDependencies += "xpp3" % "xpp3" % "1.1.4c"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.3"

seq(lwjglSettings: _*)

lwjgl.version := "2.8.4"