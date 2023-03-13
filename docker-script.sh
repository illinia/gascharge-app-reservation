#!/bin/bash

docker rmi gascharge-app-reservation
docker build -t gascharge-app-reservation -f Dockerfile /Users/gimtaemin/k8s/gascharge-app-reservation