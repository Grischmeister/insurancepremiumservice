#!/bin/bash

set -e  # Skript abbrechen bei Fehler

echo "🔄 Bereinige vorherige Container..."
docker compose -f docker/docker-compose.yml down || true

echo "🐳 Starte Postgres Container..."
docker compose up --build postgres -d

echo "🔨 Baue alle Services..."
(cd premium-service && mvn clean package)
(cd insurance-application-service && mvn clean package)
(cd frontend-ui && mvn clean package)

echo "🐳 Starte alle Container..."
docker compose -f docker-compose.yml up -d --build

echo "🧪 Warte auf Dienste (Healthcheck Loop)..."
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

echo "🚦 Starte End-to-End Tests..."
(cd e2etests && mvn test)

echo "🧹 Fahre Container herunter..."
docker compose -f docker-compose.yml down

echo "✅ Alles erfolgreich abgeschlossen!"
