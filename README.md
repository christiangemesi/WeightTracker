# Weight Tracker - a `webeng` project

## Development
This project is written for `docker compose`.
Start the application:
```bash
docker compose up
```
Stop the application:
```bash
docker compose down
```
Drop the database:
```bash
docker compose down && docker volume rm $(basename $(pwd))_database
```
For Windows cmd:
```cmd
docker compose down
for %I in (.) do docker volume rm %~nxI_database
```

# Testing

> Tests can  run locally, without requiring a docker container. Except the E2ETests, they require a docker container to be running
