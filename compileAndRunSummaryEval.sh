mkdir -p bin
javac -d bin -sourcepath src src/inforet/summaryeval.java
java -cp bin inforet.summaryeval -BM25  -n 10 -l lexicon -i invlists -m map -s data/stoplist -d data/latimes