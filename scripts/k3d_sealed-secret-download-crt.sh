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
echo ": Download Sealed Secret CRT"
echo ""
export K3D_DIR=${FOLDER}/k8s/k3d
kubectl get secret -n kube-system | grep 'sealed-secrets-key' | awk '{print$1}' | xargs -I {} kubectl get secret/{} -n kube-system  -o yaml | grep tls.crt | awk -F: 'NR==1{print $2}' | base64 --decode >> $K3D_DIR/sealed/tls.crt
