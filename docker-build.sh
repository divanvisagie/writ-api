#!/bin/bash
sbt server/docker:stage
docker build -t divanvisagie/writ-api target/docker/stage
docker push divanvisagie/writ-api
