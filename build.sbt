name := "old48_34_game"

version := "1.2"

scalaVersion := "2.11.7"

fork in Compile := true

unmanagedResourceDirectories in Compile += file("assets")

val libgdxVersion = "1.7.2"

libraryDependencies ++= Seq(
  "com.badlogicgames.gdx" % "gdx" % libgdxVersion,
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % libgdxVersion,
  "com.badlogicgames.gdx" % "gdx-platform" % libgdxVersion classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-freetype" % libgdxVersion,
  "com.badlogicgames.gdx" % "gdx-freetype-platform" % libgdxVersion classifier "natives-desktop",
  "org.zeromq" % "jeromq" % "0.3.5"
)