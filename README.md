# Task

## :small_blue_diamond: Project requirements

- [docker image](https://hub.docker.com/repository/docker/steshabolk/kameleoon/general)
- [docker compose](./docker-compose.yml)
- in-memory database : H2
- Java and Spring Boot

## :small_blue_diamond: Api capabilities

User service:

- registration

Quote service:

- adding a new quote to a user
- viewing specific quote or all user`s quotes
- getting a random quote
- modification and deletion a quote
- voting on quotes (either upvote or downvote)
- viewing of the top and worse 10 quotes
- getting scores history for a quote over time
- getting quotes voted for by the user
- information about the quote received in response: id, id and name of the user who posted it, quote text, current score

## :small_blue_diamond: Run api

Build and run api with [docker compose](./docker-compose.yml)

```
docker-compose up -d
```

- api is available at the link : http://localhost:8085
- [postman collection](./kameleoon.postman_collection.json)
