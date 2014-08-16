javac -d bin -sourcepath src src/inforet/index.java
java -cp bin inforet.index -s data/stoplist data/latimes_huge.txt