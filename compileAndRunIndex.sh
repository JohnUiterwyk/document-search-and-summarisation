STOP_LIST=data/stoplist
DOC_COLLECTION=data/latimes_med.txt

mkdir -p bin
javac -d bin -sourcepath src src/inforet/index.java
if [ -d /home/sl1/S3406031 ]
  then
    DOC_COLLECTION=/KDrive/SEH/SCSIT/Students/Courses/ISYS1078/2014/a2/latimes
fi

java -cp bin inforet.index -s $STOP_LIST $DOC_COLLECTION