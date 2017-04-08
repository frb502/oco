#!/bin/bash

PROJECT_HOME="$(cd `dirname "${BASH_SOURCE-$0}"`/..; pwd)"
cd ${PROJECT_HOME}
LOG_DIR=${PROJECT_HOME}/logs
if [ ! -d ${LOG_DIR} ]; then
    mkdir ${LOG_DIR}
fi
DATE=`date +%F`
export LOG_FILE=${LOG_DIR}/boot_${DATE}.log
export PROJECT=oco-`whoami`
export PROJECT_JAR=oco.jar
export JAVA_OPTS="-Dname=$PROJECT"