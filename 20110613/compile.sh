#!/bin/bash

scalac29 -d target unapplyseq.scala
scala29 -classpath target Main
scala210 -version
scala210 -classpath target Main

~/code/scala/build/pack/bin/scala -classpath target Main
