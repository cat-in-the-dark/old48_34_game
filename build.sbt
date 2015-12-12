name := "old48_34_game"

version := "1.0"

scalaVersion := "2.11.7"

fork in Compile := true

unmanagedResourceDirectories in Compile += file("assets")

libraryDependencies ++= Seq(
  "com.badlogicgames.gdx" % "gdx" % "1.4.1",
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.4.1",
  "com.badlogicgames.gdx" % "gdx-platform" % "1.4.1" classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-freetype" % "1.4.1",
  "com.badlogicgames.gdx" % "gdx-freetype-platform" % "1.4.1" classifier "natives-desktop"
)