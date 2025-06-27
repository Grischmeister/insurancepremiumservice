#!/bin/bash

echo "🛑 Stoppe alle Container der Anwendung..."

docker compose down --remove-orphans

echo "✅ Alle Services wurden gestoppt und entfernt."