#!/usr/bin/env bash

set -e

ROOT_FOLDER=`pwd`
echo "Current folder is $ROOT_FOLDER"

if [[ ! -e "${ROOT_FOLDER}/.git" ]]; then
    cd ..
    ROOT_FOLDER=`pwd`
fi

echo "Root folder is $ROOT_FOLDER"
export ROOT_FOLDER
