### connect

> 펨키가 존재하는 위치에서 ssh 접근하기

```
ssh -i I4C101T.pem ubuntu@i4c101.p.ssafy.io
```

> 우리 프로젝트 클론

```
git clone https://lab.ssafy.com/s04-webmobile2-sub2/s04p13c101.git
```





### install java

```
sudo apt-get install openjdk-11-jdk
```



### install maven

```
sudo apt install maven
```



### install npm

```
sudo apt install npm

```



### vue config setting

```
// devServer options dont belong into `configureWebpack`
    devServer: {
    host: "0.0.0.0",
    hot: true,
    disableHostCheck: true
},
```



```
http://i4c101.p.ssafy.io:8080/
```

```
http://i4c101.p.ssafy.io:8000/swagger-ui.html
```



### backend nginx setting

```
### backend reverse proxy 설정 추가 ###
location /api {
    proxy_pass http://localhost:8080;
    proxy_http_version 1.1;
    proxy_set_header Connection "";
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Port $server_port;
}
### 여기까지 ###
```



```
java -jar target/*.jar --server.servlet.context-path=/api
```







----

# db connect

패키지 관리도구 최신화

```
sudo apt update && sudo apt-get -y upgrade

```



MariaDB설치

```
 sudo apt-get install -y mariadb-server

```



> 마리아디비 원격에서 접근할 수 있도록 허용하기 

```
sudo vim /etc/mysql/mariadb.conf.d/50-server.cnf
# bind-address를 0.0.0.0으로 바꿔준다
```



> 마리아디비 재시작

```
sudo systemctl restart mysqld

#모든 아이피 접근허용
# MariaDB 접속해서
grant all privileges on *.* to 'root'@'%' identified by 'root';
```





---



### deploy shell script

​	

```null
#!/bin/bash

REPOSITORY=/home/ubuntu/s04p13c101/
BACKEND=backend
FRONTEND=frontend
BACKEND_CHAT=backend_chat

cd $REPOSITORY

echo "> Git Pull"

sudo git pull https://khjeon5328:khjeon5328%40naver.com@lab.ssafy.com/s04-webmobile2-sub3/s04p13c101

echo "> CD frontend && npm install and run build && nginx restart"
cd $FRONTEND
npm i
sudo npm run build
sudo nginx -t
sudo nginx service restart

echo "> CD backend && build maven && run spring background"
kill -9 `pgrep java`
cd ..
cd $BACKEND
sudo mvn package
nohup java -jar target/*.jar --server.servlet.context-path=/api &

echo "> CD backend_chat && build maven && run spring background"
cd ..
cd $BACKEND_CHAT
sudo mvn package
nohup java -jar target/*.jar --server.servlet.context-path=/api &

```