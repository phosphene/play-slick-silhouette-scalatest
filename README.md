Play 2.4 Slick Silhouette ScalaTest Demo
==============================



Build requirements:

* Oracle [Java SE](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8
* [Scala](http://scala-lang.org/files/archive/scala-2.11.7.tgz) 2.11.7
* [sbt](http://www.scala-sbt.org) 0.13.9


We have suspended using the artima plugin for [scalatest](http://www.scalatest.org/install)

we do not yet need to add the following line to ~/.sbt/0.13/global.sbt:

<!-- language: lang-none -->
    resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"



# Test

    $ sbt test


Our focus here will be on driving development through acceptance testing

so our real tests are in test/acceptance

and you probably only want to run those.

# Test only syntax for sbt using a wildcard

    $ sbt "test-only *FeatureSpec"
