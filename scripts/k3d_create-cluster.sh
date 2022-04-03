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
echo ": Create k3d Cluster"
echo ""
export K3D_CONFIG=${FOLDER}/k8s/k3d/k3d.yaml
k3d cluster create --config $K3D_CONFIG
