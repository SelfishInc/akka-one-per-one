resolvers ++= Seq(
"Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
"Typesafe Snaphot Repository" at "http://repo.typesafe.com/typesafe/snapshots/")

addSbtPlugin("com.typesafe.sbt" % "sbt-multi-jvm" % "0.3.11")
