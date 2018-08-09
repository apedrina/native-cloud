#! /bin/bash


counter=1

while [ $counter -le 10 ]
do
  echo $counter
  ping -q -c5 rabbitmq > ./null

  if [ $? -eq 0 ]
  then
      echo "ok"
      break
  fi

  ((counter++))
  sleep 60
done
