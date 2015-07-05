# Princeton-Algorithms
Java

# Environment Setup
[java-algs4](http://algs4.cs.princeton.edu/mac/)

## CLI
Normal
```bash
javac <name>.java
java <name>
```
To include the classpath in algo4 standard libraries:
```bash
javac-algs4 <name>.java
java-algs4 <name>
```

`javac-algs4` will do the following:
```
#!/bin/bash
INSTALL=~/algs4
jars=(.:${INSTALL}/stdlib.jar:${INSTALL}/algs4.jar)
javac -cp "$jars" -g -encoding UTF-8 "$@"
```

## IntelliJ
1. Open the root director as the project root
1. Set language level to 6
1. Add `algs4.jar` and `stdlib.jar` to project's external libraries
1. In any assignment folder, mark `src` as `Sources Root`
1. Create target folder `tagert/classes`, `target/test-classes`, and add them to project target folders. 
1. To compile,
