# Lunch Microservice

The service provides an endpoint that will determine, from a set of recipes, what I can have for lunch at a given date, based on my fridge ingredient's expiry date, so that I can quickly decide what I’ll be having to eat, and the ingredients required to prepare the meal.

## Prerequisites

* [Java 11 Runtime](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Docker](https://docs.docker.com/get-docker/) & [Docker-Compose](https://docs.docker.com/compose/install/)

*Note: Docker is used for the local MySQL database instance, feel free to use your own instance or any other SQL database and insert data from lunch-data.sql script* 


### Run

1. Start database:

    ```
    docker-compose up -d
    ```
   
2. Add test data from  `sql/lunch-data.sql` to the database. Here's a helper script if you prefer:


    ```
    CONTAINER_ID=$(docker inspect --format="{{.Id}}" lunch-db)
    ```
    
    ```
    docker cp sql/lunch-data.sql $CONTAINER_ID:/lunch-data.sql
    ```
    
    ```
    docker exec $CONTAINER_ID /bin/sh -c 'mysql -u root -prezdytechtask lunch </lunch-data.sql'
    ```
    
3. Run Springboot LunchApplication

## Changes by San Seo
1. Added repository class so that we can take the logic for persistent layer from service layer
2. Updated package structure so that we can find each item more easily
3. Controller mapping. It should be a get mapping and moved end point mapping at class level as the controller should handle the requests for lunch only
4. Removed all field dependency injections
5. Fixed join mapping and fixed predicate

#### Thoughts
* Currently, it's filtering the recipe by the useByDate of ingredient in each recipe at database level.
The last acceptance criteria is bit ambiguous as we need to put the recipe which has any ingredient past it's bestBefore. 
I think we can simply the logic in service by get the earliest bestBefore date from each recipe and sort them by the date.
