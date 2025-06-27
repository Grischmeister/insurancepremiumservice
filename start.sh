#!/bin/bash

echo "🔧 Baue alle Services..."

# Baue die Java-Anwendungen (Application- und Premium-Service)
echo "📦 Baue premium-service..."
cd premium-service
./mvnw clean package -DskipTests || { echo "❌ Fehler beim Bauen von premium-service"; exit 1; }
cd ..

echo "📦 Baue application-service..."
cd insurance-application-service
./mvnw clean package -DskipTests || { echo "❌ Fehler beim Bauen von application-service"; exit 1; }
cd ..

echo "📦 Baue frontend-ui..."
cd frontend-ui
./mvnw clean package -DskipTests || { echo "❌ Fehler beim Bauen von frontend-ui"; exit 1; }
cd ..

echo "🐳 Starte Docker-Container..."
docker compose down --remove-orphans
docker compose up --build --detach

echo "⏳ Warte auf das UI unter http://localhost:8082 ..."

# Health-Check-Loop (max 60 Sekunden)
max_attempts=30
attempt=1
until curl -s http://localhost:8082 > /dev/null; do
  if [ $attempt -ge $max_attempts ]; then
    echo "❌ UI-Service ist nach $((attempt*2)) Sekunden nicht erreichbar."
    exit 1
  fi
  echo "🔄 Versuch $attempt: UI noch nicht erreichbar..."
  attempt=$((attempt+1))
  sleep 2
done

echo "✅ Anwendung läuft!"
echo "🌐 Öffne http://localhost:8082 im Browser"