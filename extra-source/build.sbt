name := "extra-source"

organization := "extra-source"

version := "0.1-SNAPSHOT"

mappings in (Compile, packageSrc) <+= baseDirectory map (bd => (bd / "extra.txt") -> "extra.txt")