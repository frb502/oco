#!/bin/bash

PROJECT_HOME="$(cd `dirname "${BASH_SOURCE-$0}"`/..; pwd)"
sh ${PROJECT_HOME}/bin/oco.sh stop
