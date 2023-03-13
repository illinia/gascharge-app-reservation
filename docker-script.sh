#!/bin/bash

nohup docker build -t gascharge-app-reservation ~/Users/gimtaemin/k8s/gascharge-app-reservation/ > nohup.out 2>&1 &