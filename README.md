# Mancala Game

## Description
[Mancala](https://en.wikipedia.org/wiki/Mancala) is a two-player turn based game.
This is a Java implementation of it developed using Spring Boot.
Every game session and their states are stored in in-memory H2 database.

There two endpoints available on the backend:
 - Creating the game session: ```/api/v1/game/create```
 - Moving stones from a chosen pit: ```/api/v1/game/play```

## Installation
In order to run the backend application at least JDK 11 is required.
To start up the application on your local machine, select ```local``` maven profile and run ```mvn clean install```.
After build success click run on your IDE.  Alternatively, you can achieve the same by executing this maven goal ```mvn spring-boot:run -Plocal```. 

In local environments, backend is accessible only from the origin ```http://localhost:3000``` which is the default local address for React applications. 
If you want to change this default behaviour you need to modify the property ```jurengis.com.mancala.cors.allowed-origins``` in the [application-local.yaml](./src/main/resources/application-local.yaml) file.

---
### Docker Support
To start up the application, run ```mvn clean instal``` first and do following:
```
# set the environment variable beforehand
export SPRING_PROFILES_ACTIVE=local (Linux and Mac)
$env:SPRING_PROFILES_ACTIVE='local' (Windows)

# build and run the containers in the background
docker-compose up -d --build
```
---

## Usage
### Creating New Game Session ```POST /api/v1/game/create```
#### Request Body
```
{
  "firstPlayerId": "1", // required
  "secondPlayerId": "2", // required
  "initialStoneCount": "6", // optional
  "activePlayer": "0" // optional
}
```
#### Response Body
```
{
  "id": "3980ff01-ec08-4cc4-9a3e-712c03ab5151",
  "version": 0,
  "createdDate": "2021-12-18T22:16:07.191177+03:00",
  "lastModifiedDate": "2021-12-18T22:16:07.191177+03:00",
  "firstPlayerId": 1,
  "secondPlayerId": 2,
  "initialStoneCount": 6,
  "activePlayer": 0,
  "winner": -1,
  "finished": false,
  "board": {
    "id": "0c3d1ab1-8da4-4764-a220-8ec74a7a08a5",
    "state": [
      {
        "pits": [
          {
            "type": 0, // little pit
            "stoneCount": 6
          },
          ...
          {
            "type": 1, // big pit
            "stoneCount": 0
          }
        ]
      },
      {
        "pits": [
          {
            "type": 0, // little pit
            "stoneCount": 6
          },
          ...
          {
            "type": 1, // big pit
            "stoneCount": 0
          }
        ]
      }
    ]
  }
}
```
### Moving Stones ```GET /api/v1/game/play/{gameId}?pit={index}```
#### Response Body
```
{
  "id": "3980ff01-ec08-4cc4-9a3e-712c03ab5151",
  "version": 0,
  "createdDate": "2021-12-18T22:16:07.191177+03:00",
  "lastModifiedDate": "2021-12-18T22:16:07.191177+03:00",
  "firstPlayerId": 1,
  "secondPlayerId": 2,
  "initialStoneCount": 6,
  "activePlayer": 0,
  "winner": -1,
  "finished": false,
  "board": {
    "id": "0c3d1ab1-8da4-4764-a220-8ec74a7a08a5",
    "state": [
      {
        "pits": [
          {
            "type": 0, // little pit
            "stoneCount": 0
          },
          ...
          {
            "type": 1, // big pit
            "stoneCount": 1
          }
        ]
      },
      {
        "pits": [
          {
            "type": 0, // little pit
            "stoneCount": 6
          },
          ...
          {
            "type": 1, // big pit
            "stoneCount": 0
          }
        ]
      }
    ]
  }
}
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
