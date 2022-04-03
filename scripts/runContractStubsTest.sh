#!/usr/bin/env bash

source common.sh || source scripts/common.sh || echo "No common.sh script found..."

FOLDER=`pwd`

set -e

./mvnw clean -Pcontracts spring-cloud-contract:convert spring-cloud-contract:generateTests spring-cloud-contract:generateStubs spring-cloud-contract:run -U
