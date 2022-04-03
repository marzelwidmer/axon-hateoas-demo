#!/usr/bin/env bash

source common.sh || source scripts/common.sh || echo "No common.sh script found..."

FOLDER=`pwd`

set -e

./mvnw clean package -Pcontracts,integration,documentation -U
