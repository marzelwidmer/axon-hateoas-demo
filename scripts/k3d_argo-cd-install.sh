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
echo ": Installed argo-cd"
echo ""
helm repo add argo-cd https://argoproj.github.io/argo-helm
helm install --namespace=kube-system argo-cd argo-cd/argo-cd
