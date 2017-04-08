#!/bin/bash
if [[ $# -lt 1 ]]; then
    echo 'Usage: update.sh <oco_update.jar>'
    exit 1
fi

PROJECT_HOME="$(cd `dirname "${BASH_SOURCE-$0}"`/..; pwd)"
cd $PROJECT_HOME
if [ -f oco.jar ]; then
   rm oco.jar
fi
ln -s $1 oco.jar
echo 'update jar success, server will be restart!!!'
sh ${PROJECT_HOME}/bin/oco.sh restart