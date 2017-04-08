#!/bin/bash
if [ ! -f oco.jar ]; then
    echo 'please use update.sh <*.jar> for first running!!!'
    exit 1
fi
PROJECT_HOME="$(cd `dirname "${BASH_SOURCE-$0}"`/..; pwd)"
sh ${PROJECT_HOME}/bin/oco.sh restart
