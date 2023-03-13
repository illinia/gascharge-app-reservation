#!/bin/bash

cd ~/Users/gimtaemin/k8s/gascharge-app-reservation/
nohup docker build -t gascharge-app-reservation . > nohup.out 2>&1 &