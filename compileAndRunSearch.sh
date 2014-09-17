mkdir -p bin
javac -d bin -sourcepath src src/inforet/search.java
java -cp bin inforet.search lexicon invlists map microsoft