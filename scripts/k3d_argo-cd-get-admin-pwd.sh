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
echo ": Get Admin Password for  argo-cd"
echo ""
kubectl -n kube-system get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d && echo
