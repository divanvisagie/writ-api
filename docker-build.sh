#!/bin/bash
sbt server/docker:publish
docker build -t divanvisagie/writ-api:latest target/docker/stage
docker push divanvisagie/writ-api:latest
