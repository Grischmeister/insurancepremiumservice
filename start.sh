#!/bin/bash

echo "🔧 Baue alle Services..."

# Baue die Java-Anwendungen (Application- und Premium-Service)
echo "📦 Baue premium-service..."
cd premium-service
./mvnw clean package -DskipTests || { echo "❌ Fehler beim Bauen von premium-service"; exit 1; }
cd ..

echo "📦 Baue application-service..."
cd application-service
./mvnw clean package -DskipTests || { echo "❌ Fehler beim Bauen von application-service"; exit 1; }
cd ..

echo "📦 Baue frontend-ui..."
cd frontend-ui
./mvnw clean package -DskipTests || { echo "❌ Fehler beim Bauen von frontend-ui"; exit 1; }
cd ..

echo "🐳 Starte Docker-Container..."
docker compose down --remove-orphans
docker compose up --build --detach

echo "✅ Anwendung läuft!"
echo "🌐 Öffne http://localhost:8082 im Browser"