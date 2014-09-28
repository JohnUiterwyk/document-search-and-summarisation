STOP_LIST=data/stoplist
DOC_COLLECTION=data/latimes

mkdir -p bin
javac -d bin -sourcepath src src/inforet/index.java
java -cp bin inforet.index -s $STOP_LIST $DOC_COLLECTION