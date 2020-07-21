# WebServiceRHB
RHB Webservice - Spring Boot application for CRUD operation for a person

- Sample springboot app with JPA and mysql
- docker-compose.yml and Dockerfile provided

Docker Command:

- Make changes to application.yml, application.properties and Dockerfile accordingly
- Create network: docker network create springboot-mysql-docker
- Create db container: docker container run --name mysqldb --network springboot-mysql-docker -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=rhb_db -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:8
- Build springboot app image: docker build . -t rhb-service-app-api
- Run as container with network, app and db: docker container run --network springboot-mysql-docker --name rhb-service-app-container -p 8080:8080 --link mysqldb:mysql -d rhb-service-app-api

- list of container: docker container ls
- list of network: docker network ls
- list of images: docker images
- view log: docker logs *container*
- accessing db: docker exec -it mysqldb bash
- start container: docker container start *container*
- stop container: docker container stop *container*
- check docker-compose format: docker-compose config
- run docker-compose: docker-compose up