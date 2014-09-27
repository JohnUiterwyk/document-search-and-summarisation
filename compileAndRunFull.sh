STOP_LIST=data/stoplist
DOC_COLLECTION=data/latimes

mkdir -p bin
javac -d bin -sourcepath src src/inforet/index.java
java -cp bin inforet.index -s $STOP_LIST $DOC_COLLECTION

javac -d bin -sourcepath src src/inforet/search.java
java -cp bin inforet.search -BM25 -q testquery -n 8 -l lexicon -i invlists -m map -s $STOP_LIST book