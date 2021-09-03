#!/bin/sh

apt-get update -y

yes|apt-get install curl

curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)

while [[ ! curlResult == "200" ]]; do
  >&2 echo "config server is not up yet"
  sleep 2
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)
done

./cnb/lifecycle/launcher
