# TP 1 : Gestion de stock de pharmacie

Module : Spring Boot - MicroServices  
Filiere : Ingenierie Informatique - ENSA Fes  

---

## 1. Description du projet

Ce projet consiste a developper une application Spring Boot permettant la gestion du stock de medicaments d'une pharmacie. L'application met en oeuvre les concepts fondamentaux de Spring Boot, Spring Data JPA, Thymeleaf ainsi qu'une API REST pour la gestion et la consultation des produits.

L'application repond aux exigences suivantes :
- Gestion complete des produits pharmaceutiques.
- Persistance relationnelle avec MySQL et Spring Data JPA.
- Mise en oeuvre des quatre types de relations JPA : OneToOne, ManyToOne, OneToMany et ManyToMany.
- Interface web de visualisation et d'administration sous Thymeleaf.
- Exposition de services web REST conformes aux standards HTTP.

---

## 2. Modele de donnees et relations JPA

Le modele relationnel est compose de cinq entites principales :

1. **Produit** : Represente un medicament avec son nom, description, prix, code-barre et date d'expiration.
   - Relation `@ManyToOne` vers **Categorie** (`categorie_id`).
   - Relation `@ManyToOne` vers **Fournisseur** (`fournisseur_id`).
   - Relation `@OneToOne` avec cascade vers **DetailProduit** (`detail_id`).
   - Relation `@ManyToMany` vers **Tag** via la table de jointure `produit_tag`.

2. **Categorie** : Categorie therapeutique du medicament (Antibiotiques, Antalgiques, etc.).
   - Relation `@OneToMany(mappedBy = "categorie")` vers Produit.

3. **Fournisseur** : Laboratoire ou distributeur fournissant les produits (nom, contact, adresse).
   - Relation `@OneToMany(mappedBy = "fournisseur")` vers Produit.

4. **DetailProduit** : Informations techniques et pharmaceutiques (fabricant, pays d'origine, composition, dosage, indications).
   - Relation `@OneToOne(mappedBy = "detailProduit")` vers Produit.

5. **Tag** : Etiquettes descriptives (Sans ordonnance, Promotion, etc.).
   - Relation `@ManyToMany(mappedBy = "tags")` vers Produit.

---

## 3. Structure du code

Le projet suit une architecture multicouche standard :

```
src/main/java/ma/ensa/pharmaciestock/
├── config/
│   └── DataInitializer.java              # Initialisation automatique du jeu de donnees
├── controllers/
│   ├── rest/
│   │   ├── ProduitRestController.java    # API REST des produits
│   │   ├── CategorieRestController.java  # API REST des categories
│   │   ├── FournisseurRestController.java# API REST des fournisseurs
│   │   ├── TagRestController.java        # API REST des tags
│   │   └── DetailProduitRestController.java
│   └── web/
│       └── ProduitWebController.java     # Controleur web Thymeleaf
├── entities/
│   ├── Produit.java
│   ├── Categorie.java
│   ├── Fournisseur.java
│   ├── DetailProduit.java
│   └── Tag.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── repositories/
│   ├── ProduitRepository.java
│   ├── CategorieRepository.java
│   ├── FournisseurRepository.java
│   ├── DetailProduitRepository.java
│   └── TagRepository.java
├── services/
│   ├── ProduitService.java
│   ├── CategorieService.java
│   ├── FournisseurService.java
│   └── TagService.java
└── PharmacieStockApplication.java
```

---

## 4. Configuration de la base de donnees

Dans le fichier `src/main/resources/application.properties` :

```properties
spring.application.name=pharmacie-stock
server.port=8085

# Connexion MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/pharmacie_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=true
```

Le script SQL complet est disponible dans le fichier `schema_et_donnees.sql`.

---

## 5. Lancement de l'application

Pour compiler et executer le projet :

```bash
mvn spring-boot:run
```

Ou en utilisant le wrapper Maven sous Windows :

```powershell
.\mvn.cmd spring-boot:run
```

Lors du premier demarrage, la classe `DataInitializer` peuple automatiquement la base de donnees avec le jeu d'essai du TP.

---

## 6. Interface Web (Thymeleaf)

L'interface web est accessible sur le navigateur a l'adresse suivante :

```
http://localhost:8085/produits
```

Fonctionnalites disponibles :
- Consultation de la liste des produits avec filtres par categorie et recherche textuelle.
- Ajout d'un nouveau medicament avec association de la categorie, du fournisseur, des tags et des details techniques.
- Modification et mise a jour des informations d'un medicament existant.
- Suppression d'un produit.
- Consultation de la fiche technique detaillee d'un produit (`/produits/{id}`).

---

## 7. Documentation de l'API REST

Les points d'entree REST respectent les conventions HTTP :

### Produits
- `GET /api/produits` : Liste de tous les produits (Code 200)
- `GET /api/produits/{id}` : Consultation d'un produit par identifiant (Code 200)
- `POST /api/produits` : Creation d'un produit avec relations (Code 201)
- `PUT /api/produits/{id}` : Mise a jour d'un produit existant (Code 200)
- `DELETE /api/produits/{id}` : Suppression d'un produit (Code 204)

### Donnees annexes
- `GET /api/categories` : Liste des categories (Code 200)
- `POST /api/categories` : Ajout d'une categorie (Code 201)
- `PUT /api/categories/{id}` : Modification d'une categorie (Code 200)
- `DELETE /api/categories/{id}` : Suppression d'une categorie (Code 204)
- `GET /api/fournisseurs` : Liste des fournisseurs (Code 200)
- `POST /api/fournisseurs` : Ajout d'un fournisseur (Code 201)
- `PUT /api/fournisseurs/{id}` : Modification d'un fournisseur (Code 200)
- `DELETE /api/fournisseurs/{id}` : Suppression d'un fournisseur (Code 204)
- `GET /api/tags` : Liste des tags (Code 200)
- `POST /api/tags` : Ajout d'un tag (Code 201)
