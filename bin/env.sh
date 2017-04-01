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
export PROJECT_JAR=adapter_2.11-1.0.jar
export MAIN_CLASS=com.haizhi.raptor.AdapterMain
export JAVA_OPTS="-Dname=$PROJECT"