#!/bin/bash
# 
# Automatically pulls the current Git Repository.
# Checks if there are any changes since last pull.
# Run and output to public_html run information.
# This assumes you've setup SSH_Key based access to
# your GIT repository.
# Written by Daniel Ting (www.hellodanielting.com)
# Created on 20 Sept 2014
###################################################

### Configuration
USR_HOME="/home/sh6/S3340096"
WORKSPACE_PATH="$USR_HOME/ir-a2"
SRC_PATH="$WORKSPACE_PATH/src"
BIN_PATH="$WORKSPACE_PATH/bin"

JAVA_MAIN=""

COMPILE="javac"
COMPILE_ARGS="-d bin -sourcepath src $JAVA_MAIN"

RUN_PATH="$BIN_PATH"
RUN_CMD="java -Xms5G"
RUN_FILE=""
RUN_ARGS=

RUN_BATCH="run.cfg"

GIT_URI="git@github.com:JohnUiterwyk/rmit-ir-a1.git"
GIT_PULL_ARG=""
GIT_FOLDER="$SOURCE_PATH"

## Public Status File

OUTPUT_LOC="$USR_HOME/public_html"
OUTPUT_FILE="$OUTPUT_LOC/index.html"
TMP_OUT="$USR_HOME/out.tmp"
###################################################


cd $WORKSPACE_PATH
git pull "$GIT_PULLARG" "$GIT_URI" &> gitOutput.tmp


# Check if there were any diff to the file on the server
# versus that which is from the upstream repository.
# Exit if no changes.
if [ "`tail -1 gitOutput.tmp`" != "Already up-to date." ]
	then
		exit 0
fi

## If you're here, it means that a change has been detcted.
## Lets recompile the source

# -- Header for the output file
touch "$OUTPUT_FILE"
echo "<br /><br />" > "$TMP_OUT"
echo "#############################################################" >> $TMP_OUT
echo -n "Last Updated : " >> "$TMP_OUT"
date >> $TMP_OUT

### RUN THE PROGRAM ############
# We just need to insert the run stuff here

"$COMPILE $COMPILE_ARGS"
time "$RUN_CMD $RUN_FILE $RUN_ARGS" &> "$TMP_OUT"
if [ -e $RUN_BATCH ] then
	time bash < $RUN_BATCH >> $TMP_OUT
fi

### END of RUN THE PROGRAM #####
# Prepare the OUTPUT
sed -i 's/$/<br>/' "$TMP_OUT"
cat $TMP_OUT >> "$OUTPUT_FILE"

# Cleanup
rm -f gitOutput.tmp "$TMP_OUT"


