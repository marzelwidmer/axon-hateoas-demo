#!/usr/bin/env bash


source common.sh || source scripts/common.sh || echo "No common.sh script found..."

FOLDER=`pwd`

set -e

echo "---------------------------"
echo ""
echo ":  k3d                     "
echo ""
echo "---------------------------"

echo ""
echo ": Check Installed Sealed Secret"
echo ""
export K3D_DIR=${FOLDER}/k8s/k3d
kubectl get secrets/spring-encrypt -ojson | jq -r '.data."key"' | base64 --decode
kubectl get secrets/spring-encrypt -ojson | jq -r '.data."user"' | base64 --decode
