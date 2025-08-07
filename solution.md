Technical Challenge

REST API built for the challenge that supports initialising the database with the rows from the CSV nominee oscar data and embellishing that data with an api call
to the movies api. Once the database is initialised, the server allows the user to call the api to give a movie a new rating, 
check if a movie has won the best picture award, and to get the top 10 highest rated based on the single user rating ordered by box office value.

Uses Spring Boot + MySQL + JPA + Lombok

Model-view controller monolith architecture. Has distinct service, controller, and repository layers.
