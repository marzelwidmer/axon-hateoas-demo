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
echo ": Install Sealed Secret"
echo ""
export K3D_DIR=${FOLDER}/k8s/k3d
kubectl apply -f $K3D_DIR/sealed/sealed-secrets.yaml
