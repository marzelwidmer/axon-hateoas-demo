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
echo ": Generate Sealed Secret"
echo ""
export K3D_DIR=${FOLDER}/k8s/k3d
#kubeseal --namespace default --cert $K3D_DIR/sealed/tls.crt --format=yaml < $K3D_DIR/sealed/secrets.yaml > $K3D_DIR/sealed/sealed-secrets.yaml
kubeseal --cert $K3D_DIR/sealed/tls.crt --format=yaml < $K3D_DIR/sealed/secrets.yaml > $K3D_DIR/sealed/sealed-secrets.yaml
