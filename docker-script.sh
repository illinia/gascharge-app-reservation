#

nohup docker rmi gascharge-app-reservation > nohup-rmi.out 2>&1 &
nohup docker build -t gascharge-app-reservation . > nohup.out 2>&1 &