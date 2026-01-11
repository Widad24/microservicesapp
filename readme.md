# Projet MSA - Application de Gestion d'Emprunts

**Nom:** CHICHANE
**Prénom:** Widad
**Date:**  2026

---

## 📋 Description

Application de gestion d'emprunts basée sur une architecture microservices avec Spring Boot, MySQL et Kafka.

---

## 🏗️ Architecture

**Microservices principaux :**

Architecture
Microservices

Eureka Server (Port: 8761)

Service de découverte et d'enregistrement des microservices
Interface de monitoring accessible via http://localhost:8761


Gateway (Port: 9999)

Point d'entrée unique pour toutes les requêtes
Routage dynamique vers les microservices appropriés
Load balancing intégré


User Service (Port: 8082)

Gestion des utilisateurs
Base de données: db_user
Endpoints: /api/users


Book Service (Port: 8081)

Gestion du catalogue de livres
Base de données: db_book
Endpoints: /api/books


Emprunter Service (Port: 8085)

Gestion des emprunts de livres
Base de données: db_emprunter
Publication d'événements Kafka lors de la création d'emprunts
Endpoints: /api/emprunts


Notification Service (Port: 8087)

Consommation d'événements Kafka
Envoi de notifications (simulé via logs)
Fonctionne uniquement en mode asynchrone (pas d'endpoints REST)



## 🗄️ Base de Données

Architecture **Database per Service** avec MySQL :

| Service | Base de données |
|-------|----------------|
| User Service | db_user |
| Book Service | db_book |
| Emprunter Service | db_emprunter |

Chaque microservice possède sa **propre base de données**, garantissant :
- Indépendance
- Scalabilité
- Faible couplage

---

## Communication Asynchrone - Kafka

- **Topic :** `emprunt-created`
- **Producteur :** Emprunter Service
- **Consommateur :** Notification Service

### 📄 Format du Message

```json
{
  "empruntId": 1,
  "userId": 3,
  "bookId": 5,
  "eventType": "EMPRUNT_CREATED",
  "timestamp": "2025-01-01T14:00:00"
}

# Prérequis

- **Java 17+**

- **Maven 3.6+**

- **Docker & Docker Compose**

- **Git**

# Installation et Démarrage
- **Cloner le projet**
```
git clone https://gitlab.com/drissRiane/microservicesapp.git  
cd microservicesapp

- **Lancer l’infrastructure**
```
    docker-compose up -d

**Services démarrés :**

- MySQL

- Zookeeper

- Kafka

- Tous les microservices

**Accès aux Services**

- Eureka : http://localhost:8761

- Gateway : http://localhost:9999

- Users  : http://localhost:9999/api/users

- Books  : http://localhost:9999/api/books

- Emprunts  : http://localhost:9999/api/emprunts

# Tests Fonctionnels
**Ajout d'un user**
```
 curl -X POST http://localhost:9999/api/users \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe",
  "email": "john.doe@example.com"
}' 

**Ajout d'un livre**
```
curl -X POST http://localhost:9999/api/books \
-H "Content-Type: application/json" \
-d '{
  "titre": "Spring Boot en Action"
}'

**Création d'un emprunt**
```
curl -X POST http://localhost:8080/api/emprunts \
-H "Content-Type: application/json" \
-d '{
  "userId": 1,
  "bookId": 1,
  "dateEmprunt": "2025-01-10"
}'

Vérifier les notifications
```
docker-compose logs -f notification-service

# La structure du projet

microservicesapp/
├── book/
├── emprunter/
├── eurika
├── gateway/
├── notification_service/
├── user/
├── docker-compose.yml
└── README.md