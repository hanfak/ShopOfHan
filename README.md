# Shop of Han

[![Build Status](https://travis-ci.org/hanfak/ShopOfHan.svg?branch=master)](https://travis-ci.org/hanfak/ShopOfHan)

[![codecov](https://codecov.io/gh/hanfak/ShopOfHan/branch/master/graph/badge.svg)](https://codecov.io/gh/hanfak/ShopOfHan)


[![BCH compliance](https://bettercodehub.com/edge/badge/hanfak/ShopOfHan?branch=master)](https://bettercodehub.com/)

https://bettercodehub.com/

A domain driven example of a shop to help learn the technologies used at work.

http://localhost:8081/productscheck?productName=Joy%20Of%20Java


## Running mySql via docker

From scratch
`docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=password -d mysql`

// docker run -it --link some-mysql:mysql --rm mysql sh -c 'exec  mysql -h"172.17.0.3" -P"3306" -uroot -p' 

If already pulled image
`docker start <container name>`

Check containers that are not running
`docker ps -a`

Check if working
`docker ps`

start bash in container
`docker exec -it   <container name>  /bin/bash `

Start mysql
`mysql -u root -p` 

Connect to mysql
`mysql -h"172.17.0.3" -P"3306" -uroot -p `

copy and paste sql file (/home/dev/Documents/HAN/ShopOfHan/ShopOfHanSQL/priming.sql)

// TODO: script to run via docker bash


## address in use

lsof -i tcp:8081

kill -9 <pid>