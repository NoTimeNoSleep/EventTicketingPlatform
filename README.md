# Event Ticketing Platform

---

## Project Overview
This project is a web-based event ticketing platform that allows organizers to create events and sell tickets directly to users without intermediaries.

Users can browse events, purchase tickets, receive QR codes and use them for event entry.

---

## Main Features
- Event catalog with descriptions and dates  
- Ticket purchase system  
- QR code ticket generation  
- Ticket validation (QR scanning)  
- Basic user and event management  

---

## Data Model

### Class Diagram
- **File:** `class_diagram.txt`
- **Format:** PlantUML (`@startuml ... @enduml`)
- **Covers:** core domain entities, enums, relationships, multiplicities, and composition markers.

### SQL Schema
- **Base schema file:** `create_ER.sql`
	- Relational model with foreign keys and `CHECK` constraints for integrity.
- **Enum-based schema file:** `create_ER_with_enums.sql`
	- PostgreSQL version using native `ENUM` types for domain statuses and categories.

### Quick Usage
- Render class diagram (if PlantUML is installed):

```powershell
plantuml class_diagram.txt
```

---

## Documentation
Further documentation (analysis, personas, story map, etc.):

**Overleaf link:**  
[https://www.overleaf.com/project/69c68aeb3d2247fd435574cd]

--- 

## Team
- Ramunė Riaubaitė 
- Emilija Dailydžionytė
- Ignė Balvočiūtė
- Aleksej Krasavcev
- Martyna Valančiūtė