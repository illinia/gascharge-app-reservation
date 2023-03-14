#!/bin/sh

nohup /usr/local/bin/docker stop gascharge-app-reservation > nohup/nohup-app-reservation-stop.out 2>&1 ;
nohup /usr/local/bin/docker rm gascharge-app-reservation > nohup/nohup-app-reservation-rm.out 2>&1 ;
nohup /usr/local/bin/docker rmi gascharge-app-reservation > nohup/nohup-app-reservation-rmi.out 2>&1 ;
nohup /usr/local/bin/docker build -t gascharge-app-reservation k8s/gascharge-app-reservation/ > nohup/nohup-app-reservation-build.out 2>&1 ;
nohup /usr/local/bin/docker run --name gascharge-app-reservation -it -d -p 8400:8400 --privileged --cgroupns=host -v /sys/fs/cgroup:/sys/fs/cgroup:rw gascharge-app-reservation /usr/sbin/init > nohup/nohup-app-reservation-run.out 2>&1 ;