# Trello API Consumer

## Table of Contents
1. Description
2. Requirements for development
3. Setup
4. Endpoints
5. TODO

## Description

API to offer services that connect to Trello API.

## Requirements for development

- Java jdk 1.8 or higher;
- Maven 

## Setup

Fork this project, then update trello-api.properties, substituting myKey and myToken values.

The key can be generated by logging into Trello and then accessing [this link](https://trello.com/app-key).

The token can be generated by accessing https://trello.com/1/authorize?expiration=never&name=TrelloAPIConsumer&scope=read,write&response_type=token&key={api.key}

The whole process is better described [here](https://developers.trello.com/docs/api-introduction).

After changing trello-api.properties, if git marks this file as modified you can run the following command to prevent from commiting your private information:
```bash
cd PATH/TO/PROJECT/GIT
git update-index --skip-worktree src\main\resources\trello-api.properties
```

To run the api, you can use the provided mavenw executable or use your local installation.
Just run:
```bash
./mvnw spring-boot:run
```

This starts tomcat locally at port 8080 with the endpoints provided in the next section.

Front end access may come in future releases;


## Endpoints

### POST /sprintCalculator

**Description:** Gets cards from the "Backlog" list in each board and distributes them to fit the sprint.

#### Request

**Request Body:**

E.g.:
```json
{
	"sprintHours": 160,
	"boards": [
		{
			"shortUrl": "BOoXLhUs",
			"sprintPercent": 50
		},
		{
			"shortUrl": "BJDCfhvw",
			"sprintPercent": 50
		}
	]
}
```

#### Response

**Response Codes:** **200** (OK), **500** (Internal Server Error) - many possibilities. (TODO)

**Curl:**

E.g.:
```shell
curl -X POST "http://localhost:8080/sprintCalculator" -H "Content-Type: application/json" -d "{ \"sprintHours\": 160, \"boards\": [ { \"shortUrl\": \"BOoXLhUs\", \"sprintPercent\": 50 }, { \"shortUrl\": \"BJDCfhvw\", \"sprintPercent\": 50 } ] }"
```

**Request URL:**

```shell
localhost:8080/sprintCalculator
```
### POST /prepareBoardForScrum

**Description:** Sets up the board with the needed lists (Backlog, Sprint, Doing, Review, Done).

#### Request

**Request Body:**

E.g.:
```json
{
	"boards": [
		{
			"shortUrl": "BOoXLhUs"
		},
		{
			"shortUrl": "BJDCfhvw"
		}
	]
}
```

#### Response

**Response Codes:** **200** (OK), **500** (Internal Server Error) - many possibilities. (TODO)

**Curl:**

E.g.:
```shell
curl -X POST "http://localhost:8080/sprintCalculator" -H "Content-Type: application/json" -d "{ \"boards\": [ { \"shortUrl\": \"BOoXLhUs\", \"sprintPercent\": 50 }, { \"shortUrl\": \"BJDCfhvw\", \"sprintPercent\": 50 } ] }"
```

**Request URL:**

```shell
localhost:8080/prepareBoardForScrum
```

## TODO

- [ ] Front
- [ ] Handle Exceptions

New Services:
- [x] Prepare board for SCRUM;
- [ ] Calculate sprint progress;
- [ ] Calculate delays;
- [ ] Generate DOCS from card description;
- [ ] Impediments;
- [ ] Generate email report.