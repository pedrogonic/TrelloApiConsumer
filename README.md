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

**Response Body:**

String with leftover hours in sprint.

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
curl -X POST "http://localhost:8080/sprintCalculator" -H "Content-Type: application/json" -d "{ \"boards\": [ { \"shortUrl\": \"BOoXLhUs\" }, { \"shortUrl\": \"BJDCfhvw\" } ] }"
```

**Request URL:**

```shell
localhost:8080/prepareBoardForScrum
```

### GET /sprintProgress

**Description:** Calculates the progress of the sprint by getting done tasks from cards in the Review list and in the Sprint and Doing lists with check items marked as complete. 

#### Request

**Request Body:**

E.g.:
```json
{
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
curl -X POST "http://localhost:8080/sprintProgress" -H "Content-Type: application/json" -d "{ \"boards\": [ { \"shortUrl\": \"BOoXLhUs\" }, { \"shortUrl\": \"BJDCfhvw\" } ] }"
```

**Request URL:**

```shell
localhost:8080/sprintProgress
```

**Response Body:**

E.g.:
```json
{
    "progress": 0.1437125748502994,
    "totalTasks": 167,
    "doneTasks": 24,
    "dateTime": "2020-02-04T18:38:26.008",
    "boards": [
        {
            "id": "5da9c9f9414da01a0f867927",
            "name": "Desenvolvimentos SGE",
            "shortUrl": "https://trello.com/b/BOoXLhUs",
            "progress": 0.27586206896551724,
            "totalTasks": 87,
            "doneTasks": 24
        },
        {
            "id": "5e30965f3c6a91872d595ba2",
            "name": "Desenvolvimentos POP",
            "shortUrl": "https://trello.com/b/BJDCfhvw",
            "progress": 0.0,
            "totalTasks": 80,
            "doneTasks": 0
        }
    ]
}
```

### GET /sprintPlanningResult

**Description:** Endpoint to generate a report after the sprint Planning. The generated text is included in an html file located in D:/output.html. The optional parameters prependText and appendText can be used to modify the report. Both params can also receive a %TOTAL_HOURS% placeholder that gets replaced by the sum of hours in the sprint.

#### Request

**Request Parameters:**
- boardUrl - required - short url of board to generate report
- multiplier - optional - double to multiply all tasks hours. default: 1
- prependText - optional - text to be prepended to generated report
- appendText - optional - text to be appended to generated report

#### Response

**Response Codes:** **200** (OK), **500** (Internal Server Error) - many possibilities. (TODO)

**Curl:**

E.g.:
```shell
curl -X POST "http://localhost:8080/sprintPlanningResult?boardUrl=BOoXLhUs&multiplier=2&prependText=Prepend Text&appendText=Append Text. Total hours: %TOTAL_HOURS%" -H "Content-Type: application/json"
```

**Request URL:**

```shell
http://localhost:8080/sprintPlanningResult?boardUrl=BOoXLhUs&multiplier=2&prependText=Prepend Text&appendText=Append Text. Total hours: %TOTAL_HOURS%
```

**Response Body:**

E.g.:
```json
{
    "text": "Prepend Text<br/><br/><ul><li>Development 1 - 4.0 horas</li><li>Development 2 - 6.0 horas</li></ul><br/><br/>Append Text. Total hours: 10",
    "totalTime": 10.0,
    "cards": [
        
        {
            "id": "5e2eefdcca25053bfca908a4",
            "name": "Development 1",
            "hours": 4.0,
            "closed": false,
            "checklistList": [
                {
                    "id": "5e2efa191bb7300d5b4feff5",
                    "name": "Tasks",
                    "trelloCheckItems": [
                        {
                            "id": "5e2efa2982a31f540fffef58",
                            "name": "Task 1 - 2h",
                            "hours": null,
                            "state": "incomplete"
                        }
                    ]
                }
            ]
        },
        {
            "id": "5e2eefdfff333146f416ad43",
            "name": "Development 2",
            "hours": 6.0,
            "closed": false,
            "checklistList": [
                {
                    "id": "5e2efbc94fc8433d35fc5f0e",
                    "name": "Tasks",
                    "trelloCheckItems": [
                        {
                            "id": "5e2efbd6755b4c25f8b77b51",
                            "name": "Task 1 - 2h",
                            "hours": null,
                            "state": "incomplete"
                        },
                        {
                            "id": "5e2efbd7c9cf39807cdd8aea",
                            "name": "Task 2 - 1h",
                            "hours": null,
                            "state": "incomplete"
                        }
                    ]
                }
            ]
        }
    ]
}
```

## TODO

- [ ] Refactor duplicate code
- [ ] Front
- [ ] Handle Exceptions

New Services:
- [x] Prepare board for SCRUM;
- [x] Calculate sprint progress;
- [x] Sprint Planning results;
- [ ] Billing;
- [ ] Calculate delays;
- [ ] Generate DOCS from card description;
- [ ] Impediments;
- [ ] Generate email report.