#!/bin/bash
sbt server/docker:stage
docker build -t divanvisagie/writ-api:latest target/docker/stage
docker push divanvisagie/writ-api:latest
