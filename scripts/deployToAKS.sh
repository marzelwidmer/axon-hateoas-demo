#!/usr/bin/env bash


source common.sh || source scripts/common.sh || echo "No common.sh script found..."

FOLDER=`pwd`

set -e

echo "---------------------------"
echo ""
echo ": Deploy to AKS           "
echo ""
echo "---------------------------"

read -p "Enter Resource Group Name [AKS]: " AKS_RESOURCE_GROUP
export AKS_RESOURCE_GROUP=${AKS_RESOURCE_GROUP:-AKS}

registry=`az acr list --resource-group $AKS_RESOURCE_GROUP | jq '.[].loginServer' -r`
echo ""
echo ": Login to Container Registry $registry"
echo ""
az acr login --name $registry

echo ""
echo ": Deploy with skaffold"
echo ""
skaffold run -p aks
