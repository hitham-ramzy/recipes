# Recipes Application

A task of searching for Recipes and its Ingredients

### The used Entities

- The Recipe Entity : A model for the Recipe:

    - name
    - type
    - instructions
    - numberOfServings
    - ingredients


- The Recipe Type Entity : A model for the Recipe Type:

    - name (like VEGETARIAN, NON_VEGETARIAN, or VEGAN)


- The Ingredients Entity : A model for the Ingredients:

    - unit : the unit used for that ingredient (like MILLIGRAM, LITER, TEASPOON, GALLON, etc..)


- The Recipe Ingredients Entity : the entity links between the Recipe and its Ingredients as the relationship between
  the Recipe to Ingredients is many-to-many relationship

    - recipe
    - amount

### This application is using these technologies/frameworks:

- Spring Boot
- Swagger
- Mysql Database
- Docker
- Liquibase
- Junit

### How to start the app

All you need is:

1- packaging the application (just for the first time, No need to do it again if you are restarting the appication)

`./mvnw package`

2- run docker-compose command

`docker-compose up`

then open the Swagger documentation page

localhost:8080/

You can track the application logs by appending the detached flag (`-d`) to the command

`docker-compose up -d`

To stop the container all you need it running

`docker-compose down`

### Notes

- It could take some time on running the application for the first time, as the docker will need to download the java
  image
- The application use docker volume, so don't care about losing your data on stopping the containers

