## 📘 README.md

# 🚗 Versicherungsprämien-Berechnung

Ein Java/Spring-basiertes Microservice-Projekt zur Berechnung von Kfz-Versicherungsprämien. Die Anwendung speichert Anträge in einer Datenbank, ermittelt Prämien auf Basis von Postleitzahl, Fahrzeugtyp und Kilometerleistung und bietet eine einfache Web-Oberfläche.

---

## 📌 Inhaltsverzeichnis

* [Projektüberblick](#projektüberblick)
* [Architektur](#architektur)
* [Technologien](#technologien)
* [Datenbankwahl](#datenbankwahl)
* [Services](#services)
* [Kommunikation zwischen Services](#kommunikation-zwischen-services)
* [Tests & Softwarequalität](#tests--softwarequalität)
* [Start & Stop](#start--stop)
* [Web-Oberfläche](#web-oberfläche)
* [Beispielablauf](#beispielablauf)

---

## 📖 Projektüberblick

Das System besteht aus zwei Services:

* **InsuranceApplicationService** – nimmt Versicherungsanträge entgegen, ruft den PremiumService zur Berechnung auf und speichert das Ergebnis in der Datenbank.
* **PremiumService** – berechnet die Versicherungsprämie auf Grundlage eines CSV-Mappings von Postleitzahlen zu Bundesländern sowie verschiedener Faktoren.

Ein optionales Frontend (`FrontendUI`) erlaubt die einfache Eingabe und Anzeige der Ergebnisse.

---

## 🏗️ Architektur

```
[Frontend UI] ---> [Insurance Application Service] ---> [Premium Service]
                                |
                         [PostgreSQL DB]
```

* Die Services sind lose gekoppelt über REST-Kommunikation.
* Datenbankzugriffe erfolgen ausschließlich über den InsuranceApplicationService.
* Die Premium-Berechnung ist vollständig ausgelagert.

---

## 🧰 Technologien

* Java 17
* Spring Boot
* Spring Web / WebFlux (WebClient)
* Spring Data JPA + Hibernate
* PostgreSQL
* Thymeleaf (Frontend)
* Docker & Docker Compose
* JUnit 5 + Mockito (Tests)

---

## 🗃️ Datenbankwahl

**PostgreSQL** wurde gewählt wegen:

* Weit verbreitet, stabil, Open Source
* Gute Integration mit Spring Data JPA
* Unterstützung komplexer Datentypen (z. B. JSON falls nötig)
* Leicht testbar mit Testcontainers

---

## 🧩 Services

### 1. InsuranceApplicationService

Verantwortlich für:

* Entgegennahme von Versicherungsanträgen
* Aufruf des PremiumService zur Prämienberechnung
* Speicherung der Anträge inkl. Ergebnis
* REST-Endpoint: `POST /application`

### 2. PremiumService

Verantwortlich für:

* Ermittlung des Bundeslands anhand der Postleitzahl
* Berechnung der Prämie auf Basis von Regeln (Fahrzeugtyp, Kilometerleistung, Region)
* REST-Endpoint: `POST /premium`

> 📁 CSV-Datei: `postcodes.csv` im `resources`-Verzeichnis

---

## 🔗 Kommunikation zwischen Services

Die Kommunikation erfolgt synchron via **HTTP REST**:

* `InsuranceApplicationService` ruft `PremiumService` per **`WebClient`** auf.
* Daten werden als JSON übertragen.
* DTOs werden in jedem Service separat gepflegt (Entkopplung).

---

## 🧪 Tests & Softwarequalität

### Teststrategie:

| Ebene       | Tool                         | Inhalt                                 |
| ----------- | ---------------------------- | -------------------------------------- |
| Unit        | JUnit 5, Mockito             | Logik-Tests, z. B. `PremiumCalculator` |
| Integration | SpringBootTest               | DB-Integration, API-Endpunkte          |
| End-to-End  | MockMvc, ggf. Testcontainers | Gesamtsystem über UI                   |

### Qualitätssicherung:

* Tests für alle Services
* Coverage-Ziel: >80 %
* Gültigkeit der CSV geprüft (PostConstruct)
* Fehlerhafte Eingaben werden abgefangen
* CI (optional) mit GitHub Actions o.ä.

---

## ▶️ Start & Stop

### 1. Klonen & Starten

```bash
git clone https://github.com/dein-benutzer/insurance-app.git
cd insurance-app
chmod +x start.sh
./start.sh
```

### 2. Stoppen

```bash
./stop.sh
```

### 3. Alternativ manuell (wenn Docker installiert ist)

```bash
mvn clean package
docker-compose up --build
```

---

## 🧑‍💻 Web-Oberfläche

### Verfügbar unter: [http://localhost:8082](http://localhost:8082)

* Einfache Eingabemaske mit:

  * Postleitzahl
  * Fahrzeugtyp
  * Kilometerleistung
* Rückgabe der berechneten Versicherungsprämie

---

## 🧪 Beispielablauf

1. Benutzer füllt das Formular aus und klickt auf "Berechnen".
2. UI sendet POST `/calculate` an `FrontendUI`.
3. Frontend ruft `/application` vom `InsuranceApplicationService`.
4. Service leitet an `PremiumService` weiter.
5. Ergebnis wird zurück an UI geleitet und angezeigt.

---
