# Quota API

### Techs used
- Java 17;
- Spring Framework 3.2.2;
- Junit;
- Mockito;
- Lombok;
- Flyway;
- MySql;
- Elasticsearch;

## Requirements

In order to run this project you have to install:

- [Java 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html);
- [Gradle](https://gradle.org/install/);
- [Docker](https://docs.docker.com/get-docker/);

## How to run with gradle

```bash  
gradle test
docker-compose up -d 
gradle :bootRun
```

## API Documentation

You can see the api docs accessing the http://localhost:8080/swagger-ui/index.html.

In order to help the tests there are also the Postman collection with the API calls. The Postman files are located into folder `postman`.
