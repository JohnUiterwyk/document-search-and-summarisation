mkdir -p bin
javac -d bin -sourcepath src src/inforet/search.java
java -cp bin inforet.search -BM25 -q testquery -n 8 -l lexicon -i invlists -m map -s stoplist book