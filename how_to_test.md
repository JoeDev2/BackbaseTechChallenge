how to test

API Testing using Command Line

Determine if a movie won the best picture award

curl -X GET "http://localhost:8080/api/v1/movies/2/won-best-picture"
curl -X GET "http://localhost:8080/api/v1/movies/6/won-best-picture"

Give a movie a rating

curl -X POST "http://localhost:8080/api/v1/movies/1/rating" -H "Content-Type: application/json" -d "{\"rating\": 8.5}"
curl -X POST "http://localhost:8080/api/v1/movies/2/rating" -H "Content-Type: application/json" -d "{\"rating\": 9.2}"
curl -X POST "http://localhost:8080/api/v1/movies/3/rating" -H "Content-Type: application/json" -d "{\"rating\": 2.8}"

Find the top 10 rated movies ordered by box office value

curl -X GET "http://localhost:8080/api/v1/movies/top-10/box-office"