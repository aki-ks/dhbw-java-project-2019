name := "filmprojekt"
version := "0.1"
scalaVersion := "2.12.8"

crossPaths := false // do not append scala version to artifact names
autoScalaLibrary := false // do not use scala runtime dependency

val javaVersion = "1.8"
javacOptions ++= "-source" :: javaVersion :: Nil
javacOptions ++= "-target" :: javaVersion :: Nil

libraryDependencies += "org.junit.jupiter" % "junit-jupiter-api" % "5.4.2" % Test
