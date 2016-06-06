#!/bin/bash

sbt docker:publishLocal
# docker build -t divanvisagie/writ-api target/docker/stage
docker push divanvisagie/writ-api
