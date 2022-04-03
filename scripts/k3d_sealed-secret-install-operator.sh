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
echo ": Install Sealed Secret Operator"
echo ""
helm repo add sealed-secrets https://bitnami-labs.github.io/sealed-secrets
helm install --namespace=kube-system  sealed-secrets sealed-secrets/sealed-secrets


