#Match Filter

## Technologies Used:

* Java11
* Spring Boot Web
* H2 in memory (Database)
* H2gis extension (For Spatial functions)
* Junit5 (Testing)
* MockMvc (Testing)
* Swagger (API DOC)
* Gradle (Build tool)

##Summary:

In this project we have used Java11 language,Spring Boot Web is used to exposed API’s
H2 in memory used for Sql database to store data, H2gis extension for spatial queries,
Junit5 used for testing.
This project uses Query dsl to run filter queries against the database.

##How to start the application:
To run the application simply run the command  *./gradlew bootRun*

The application by default runs on *PORT 8090*.
If you you want load the initial data, there are 2 properties provided in application.properties file:
* spark.initialData.fileName and
* spark.initialDataLoad

Use spark.initialData.fileName to provide a json file_name for the initial set of matches. The default file name is matches.json incase you miss this property.
Property * *spark.initialDataLoad, when set as true, will enable the loading of initial data during the start of application.
To setup the data provided in matches for Json please use properties

Once the application is up, http://localhost:8090/swagger-ui.html will take to the swagger ui.

Here I have exposed a *GET* endpoint **_/users/{userId}/matches_**
In the *matches.json*, all the matches are for the user with is **31eed42a-fdd1-4751-bcf3-00a4c8e40d7e**

There are in total 7 query params supported by the end point: 
* ageRange.from (integer)
* ageRange.to (integer)
* compatibilityRang.from (decimal)
* compatibilityRang.to (decimal)
* heightRange.from (integer)
* heightRange.to (integer)
* hasPhoto (true / false)
* inContact (true / false)
* isFavorite (true / false)

It takes latitude and longitude of the logged in user in the headers. (could have been in the cookies)
* latitude (decimal)
* longitude (decimal)

Feel free to refer swagger for more info.
##Approach
For the query filters, I have used QueryDSL (http://www.querydsl.com/) along with Hibernate spatial (https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#spatial).
The QueryDsl provides proxy classes for the hibernate entities.
The reason for using spatial is to fulfill the requirement of searching matches within a certain distance using database.
Used H2 with extension H2-gis (http://www.h2gis.org/). It gives standard spatial functions in H2.

This was done keeping in mind that the filtering if done at the api server, might take a lot of memory and would not be that scalable.

#Areas of Improvement
* The location can be stored in cookie and might be useful for other features instead of passing in headers.
* We can extract a class from MatchService which can be responsible for interacting with PredicateBuilder and can be easily mocked while testing MatchService. This would enable us to skip adding tests in MatchService while adding new filters. (First priority)
* Currently PredicateBuilder knows if the Filter / Predicate should be applied, would be great if the Filter classes by themselves are aware of it. This way may be we can get rid of the builder and use the interface Filter to find all implementations and build the predicate.


#Known Bugs
* The distance filter currently measures the distance in degrees instead of metres/kms. This is due to the fact that the Hibernate spatial doesnt support the ST_distanceSphere method of GIS. Came to know a lot later in the implementation.
 
 One of the ways to fix this, would be to write the queries directly as the DB still supports. Given more time this can be done. The other way to fix would be to use Lucene which has a really good support for spatial queries, also we can use mongo may be but not sure about the support.



