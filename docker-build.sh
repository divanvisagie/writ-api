#!/bin/bash

sbt docker:stage
docker build -t divanvisagie/writ-api target/docker/stage
docker push divanvisagie/writ-api
