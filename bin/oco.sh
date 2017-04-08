#!/usr/bin/env bash
PROJECT_HOME="$(cd `dirname "${BASH_SOURCE-$0}"`/..; pwd)"
source $PROJECT_HOME/bin/env.sh
cd $PROJECT_HOME

pid=`ps -ef | grep name=$PROJECT | grep -v grep | awk '{print $2}'`
start() {
    if [[ $pid ]]; then
        echo "${PROJECT} already started, stop it first!"
        exit 100
    fi

     nohup java $JAVA_OPTS -jar $PROJECT_JAR >oco.out &

    if [[ $? -eq 0 ]]; then
        echo "${PROJECT} started, pid is $!"
    else
        echo "${PROJECT} failed to start: $?"
    fi
}

stop() {
    if [[ ! $pid ]]; then
        echo "${PROJECT} is not running"
    else
        kill -TERM ${pid}
        # 杀进程是耗时间的，等等吧⊙﹏⊙
        retry=1
        sleep 1
        while [[ true ]]
        do
            pid=`ps -ef | grep name=$PROJECT | grep -v grep | awk '{print $2}'`
            if [[ ! $pid ]]; then
                echo "${PROJECT} stopped"
                break
            else
                sleep 10
                echo "waiting for ${PROJECT} to exit, $retry"
                retry=`expr $retry + 1`
            fi
            # 10s还没关闭，就强制杀掉
            if [[ $retry -eq 30 ]]; then
                kill -9 ${pid}
                echo "force kill $PROJECT after $retry"
            fi
        done
    fi
}


restart() {
    stop
    start
}

case "$1" in
    start|stop|restart)
        $1
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart}"
        exit 2
esac
