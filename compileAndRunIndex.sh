STOP_LIST=data/stoplist
DOC_COLLECTION=data/latimes_med.txt

mkdir -p bin
javac -d bin -sourcepath src src/inforet/index.java
#if [ -d /home/sl1 ]
#  then
#    DOC_COLLECTION=~/temp/latimes
#fi

java -cp bin inforet.index -s $STOP_LIST $DOC_COLLECTION