#!/bin/bash
# Déploiement dans l'ordre
kubectl apply -f k8s/config-server/
kubectl apply -f k8s/mysql/
kubectl apply -f k8s/eureka-server/
sleep 30  # Attendre que Eureka soit opérationnel

kubectl apply -f k8s/employee-profile/
kubectl apply -f k8s/absence-management/
kubectl apply -f k8s/payroll/
kubectl apply -f k8s/gateway/
kubectl apply -f k8s/ingress.yaml