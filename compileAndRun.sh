javac -d bin -sourcepath src src/inforet/index.java
javac -d bin -sourcepath src src/inforet/search.java
java -cp bin inforet.index data/latimes_med.txt
java -cp bin inforet.search lexicon invlists map governments