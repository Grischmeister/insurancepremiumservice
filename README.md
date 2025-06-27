## 📘 README.md

# 🚗 Versicherungsprämien-Berechnung

Ein Java/Spring-basiertes Microservice-Projekt zur Berechnung von Kfz-Versicherungsprämien. Die Anwendung speichert Anträge in einer Datenbank, ermittelt Prämien basierend auf Postleitzahl, Fahrzeugtyp und Kilometerleistung und bietet eine einfache Web-Oberfläche.

---

## 📌 Inhaltsverzeichnis

- [Projektüberblick](#projektüberblick)
- [Architektur](#architektur)
- [Technologien](#technologien)
- [Datenbankwahl](#datenbankwahl)
- [Services](#services)
- [Kommunikation zwischen Services](#kommunikation-zwischen-services)
- [Tests & Softwarequalität](#tests--softwarequalität)
- [Start & Stop](#start--stop)
- [Web-Oberfläche](#web-oberfläche)
- [Beispielablauf](#beispielablauf)

---

## 📖 Projektüberblick

Das System besteht aus mindestens zwei Microservices:

- **InsuranceApplicationService** – Entgegennahme und Speicherung von Versicherungsanträgen sowie Koordination der Prämienberechnung.
- **PremiumService** – Berechnung der Versicherungsprämie basierend auf einem CSV-Mapping von Postleitzahlen zu Bundesländern und weiteren Faktoren.

Ein optionales Frontend (`FrontendUI`) ermöglicht die einfache Eingabe und Anzeige der Ergebnisse.

---

## 🏗️ Architektur

```
[Frontend UI] ---> [Insurance Application Service] ---> [Premium Service]
                                |
                         [PostgreSQL DB]
```

- Services kommunizieren lose gekoppelt über REST.
- Datenbankzugriffe erfolgen ausschließlich über den InsuranceApplicationService.
- Prämienberechnung ist ausgelagert im PremiumService.

---

## 🧰 Technologien

- Java 17
- Spring Boot (Web, WebFlux/WebClient)
- Spring Data JPA + Hibernate
- PostgreSQL
- Thymeleaf (Frontend)
- Docker & Docker Compose
- JUnit 5, Mockito, RestAssured (Tests)

---

## 🗃️ Datenbankwahl

**PostgreSQL** wurde gewählt aufgrund:

- Stabilität und weit verbreiteter Nutzung
- Hervorragender Integration in Spring Data JPA
- Unterstützung komplexer Datentypen (z.B. JSON)
- Gute Testbarkeit via Testcontainers
- Zukunftssichere Skalierbarkeit

---

## 🧩 Services

### 1. InsuranceApplicationService

- REST-Endpoint: `POST /application`
- Aufgaben:
  - Annahme von Versicherungsanträgen
  - Validierung und Persistenz der Anträge
  - Aufruf des PremiumService zur Prämienberechnung
  - Rückgabe des Ergebnisses an den Client

### 2. PremiumService

- REST-Endpoint: `POST /premium`
- Aufgaben:
  - Ermittlung des Bundeslands anhand der Postleitzahl (CSV-Mapping)
  - Berechnung der Prämie nach Fahrzeugtyp, Kilometerleistung und Region

---

## 🔗 Kommunikation zwischen Services

- Synchronous HTTP REST APIs
- JSON als Datenformat
- InsuranceApplicationService verwendet Spring WebClient zum Aufruf des PremiumService
- DTOs sind in jedem Service separat definiert (Entkopplung)

---

## 🧪 Tests & Softwarequalität

### Testframeworks & Tools

| Ebene         | Zweck                         | Tools                |
| ------------- | ----------------------------- | -------------------- |
| Unit-Tests    | Geschäftslogik                | JUnit 5, Mockito     |
| Integration   | Zusammenspiel mit DB          | Spring Boot Test, Testcontainers |
| End-to-End    | REST API Gesamtfunktionalität | RestAssured          |

### Qualitätssicherung

- Vollständige Unit-Test-Abdeckung für Kernlogik
- Abfangen und Testen fehlerhafter Eingaben
- Automatisierte Start- und Testskripte (`start-and-test.sh`)
- Ausführliche Dokumentation & Code Reviews

---

## ▶️ Start & Stop

### Vorraussetzungen

- JDK17
- Docker und Docker Compose
- Maven

### 1. Klonen & Starten

```bash
git clone https://github.com/Grischmeister/insurancepremiumservice.git
cd insurancepremiumservice
chmod +x start-and-test.sh
./start-and-test.sh
````

Danach regulär starten:

```bash
chmod +x start.sh
./start.sh
```

### 2. Stoppen

```bash
./stop.sh
```

---

## 🧑‍💻 Web-Oberfläche

* Verfügbar unter: [http://localhost:8082](http://localhost:8082)
* Einfache Eingabemaske für:

  * Postleitzahl
  * Fahrzeugtyp
  * Kilometerleistung
* Anzeige der berechneten Prämie

---

## 🧪 Beispielablauf

1. Benutzer füllt Formular in Web-UI aus und klickt "Berechnen".
2. FrontendUI sendet POST `/calculate`.
3. FrontendUI ruft `/application` des InsuranceApplicationService auf.
4. InsuranceApplicationService delegiert an PremiumService (`POST /premium`).
5. PremiumService berechnet Prämie und sendet Ergebnis zurück.
6. Ergebnis wird an FrontendUI weitergeleitet und angezeigt.
7. Antrag wird in PostgreSQL gespeichert.

---
