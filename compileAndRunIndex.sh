mkdir -p bin
javac -d bin -sourcepath src src/inforet/index.java
java -cp bin inforet.index -s data/stoplist data/latimes