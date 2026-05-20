# build.md – Outil de build et structure du projet

## Informations générales

- **Projet** : Sprindo
- **Équipe** : Synergie4
- **Outil de build** : Apache Maven 
- **Version Java** : 17 et 21
- **Architecture** : MVC (Modèle-Vue-Contrôleur)

---

## Outil de build utilisé

Le projet **Sprindo** utilise **Maven** comme outil de build et de gestion des dépendances.

Maven permet d'automatiser les étapes suivantes :
- **Compilation** du code source (`mvn clean compile`)
- **Exécution** des tests unitaires (`mvn test`)
- **Analyse statique** avec Checkstyle (`mvn checkstyle:checkstyle`)
- **Génération** du rapport de couverture de code (`mvn jacoco:report`)
- **Création** du fichier exécutable `.jar` (`mvn package`)
- **Exécution** directe de l'application (`mvn exec:java`)

L'outil de build assure la cohérence du projet entre les membres de l'équipe et facilite l'intégration continue.

---

## Structure du projet Maven

```
sprindo-sprint1/
├── pom.xml                  # Configuration Maven
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ca/
│   │           └── uqam/
│   │               └── sprindo/
│   │                   ├── Main.java
│   │                   ├── controleur/
│   │                   │   ├── ProjetImpl.java
│   │                   │   ├── SprintImpl.java
│   │                   │   ├── TacheImpl.java
│   │                   │   ├── UtilisateurImpl.java
│   │                   │   ├── Statistiques.java
│   │                   │   └── SprindoController.java
│   │                   ├── modele/
│   │                   │   ├── Projet.java
│   │                   │   ├── Sprint.java
│   │                   │   ├── Tache.java
│   │                   │   └── Utilisateur.java
│   │                   └── vue/
│   │                       └── SprindoVue.java
│   └── test/
│       └── java/
│           └── ca/
│               └── uqam/
│                   └── sprindo/
│                       ├── ProjetImplTest.java
│                       ├── SprintImplTest.java
│                       ├── TacheImplTest.java
│                       ├── UtilisateurImplTest.java
│                       └── SprindoVueTest.java
├── target/                  # Fichiers générés par Maven
│   ├── classes/             # Classes compilées
│   ├── test-classes/        # Tests compilés
│   ├── jacoco.exec          # Données JaCoCo
│   ├── site/                # Site Maven
│   │   └── jacoco/          # Rapport JaCoCo HTML
│   └── sprindo-1.0-SNAPSHOT.jar  # JAR exécutable
└── doc/                     # Documentation
    ├── build.md
    ├── test.md
    ├── qualite.md
    └── Equipe.md
```

Cette structure respecte le **standard Maven** et le modèle d'architecture **MVC** (Modèle – Vue – Contrôleur).

---

##  Configuration du pom.xml

Le fichier `pom.xml` est le cœur de la configuration Maven. Il définit :

### Informations du projet
```xml
<groupId>ca.uqam.sprindo</groupId>
<artifactId>sprindo</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>jar</packaging>
```

### Dépendances principales
| Dépendance | Version | Usage 			 |
|------------|---------|-----------------|
| JUnit 	 | 4.13.2  | Tests unitaires |

### Plugins Maven utilisés

| Plugin 					| Rôle 										|
|---------------------------|-------------------------------------------|
| `maven-compiler-plugin` 	| Compilation du code Java 17 				|
| `jacoco-maven-plugin` 	| Couverture de code (seuil : 70%) 			|
| `maven-checkstyle-plugin` | Analyse statique (profil Google) 			|
| `maven-surefire-plugin` 	| Exécution des tests JUnit 				|
| `maven-jar-plugin` 		| Création du JAR avec manifest 			|
| `exec-maven-plugin` 		| Exécution directe de l'application 		|
| `spotless-maven-plugin` 	| Formatage automatique Google Java Style 	|
| `maven-site-plugin` 		| Génération du site de documentation 		|

---

## Commandes principales Maven

### Commandes de base

| Action 		| Commande 			| Description 																   |
|---------------|-------------------|------------------------------------------------------------------------------|
| **Nettoyer** 	| `mvn clean` 		| Supprime le dossier `target/` contenant les anciens fichiers compilés 	   |
| **Compiler** 	| `mvn compile` 	| Compile le code source dans `src/main/java` 								   |
| **Tester** 	| `mvn test` 		| Exécute tous les tests JUnit dans `src/test/java` 						   |
| **Packager** 	| `mvn package` 	| Crée le fichier `sprindo-1.0-SNAPSHOT.jar` dans `target/` 				   |

### Commandes d'analyse et qualité

| Action 					| Commande			 				| Description 															|
|---------------------------|-----------------------------------|-----------------------------------------------------------------------|
| **Checkstyle** 			| `mvn checkstyle:checkstyle` 		| Analyse le code selon le profil Google. 								|
|							|									| Rapport : `target/checkstyle-result.xml` 								|
| **Couverture** 			| `mvn jacoco:report` 				| Génère le rapport de couverture dans `target/site/jacoco/index.html`  |
| **Vérifier seuils** 		| `mvn jacoco:check` 				| Vérifie que la couverture atteint 70% minimum 						|
| **Site complet** 			| `mvn site` 						| Génère un site web avec tous les rapports dans `target/site/` 		|

---

## Exécution du programme


### Méthode 1 : Avec Maven 
```bash
mvn exec:java
```
Cette commande utilise le plugin `exec-maven-plugin` pour lancer directement la classe `Main`.

## Accès au module de statistiques

Le module de statistiques est accessible via le menu principal du programme, à partir de l'option 10 :

```
====== SPRINDO - GESTION DE PROJETS AGILES ======
1.  Ajouter un projet
2.  Afficher la liste des projets
3.  Ajouter un sprint à un projet
4.  Afficher les sprints d'un projet
5.  Ajouter une tâche à un sprint
6.  Afficher les tâches d'un sprint
7.  Assigner une tâche à un utilisateur
8.  Modifier le statut d'une tâche
9.  Supprimer une tâche
10. Afficher les statistiques    
11. Quitter l'application
```

En choisissant l'option **10**, l'utilisateur peut consulter :
- Le **nombre total de sprints** du projet
- Le **nombre total d'utilisateurs** enregistrés
- Pour un **sprint donné** :
  * Le nombre de tâches
  * Le nombre de développeurs actifs (ayant des tâches assignées)

Ces informations sont générées par la classe `Statistiques` et affichées directement dans la console.

---

## Emplacement des rapports générés

Après exécution des commandes Maven, les rapports sont générés dans le dossier `target/` :

| Rapports 				| Emplacements   				  			| Commandes 					|
|-----------------------|-------------------------------------------|-------------------------------|
| **Checkstyle** 		| `target/checkstyle-result.xml`  			| `mvn checkstyle:checkstyle`   |
| **Checkstyle (HTML)** | `target/site/checkstyle.html`   			| `mvn site` 					|
| **JaCoCo** 			| `target/site/jacoco-aggregate/index.html` | `mvn jacoco:report` 		    |
| **Tests Surefire** 	| `target/site/surefire-reports/`      		| `mvn test` 					|
| **Site complet** 		| `target/site/index.html`        			| `mvn site` 					|

---

## Workflow de développement recommandé

### 1. Avant de commencer à coder
```bash
mvn clean
```

### 2. Pendant le développement
```bash
# Compiler et tester fréquemment
mvn compile ou mvn clean compile
mvn test
```

### 3. Avant un commit Git
```bash
# Vérifier qualité + couverture
mvn clean test checkstyle:checkstyle jacoco:report

# Vérifier que le build complet passe
mvn clean package
```

### 4. Avant la livraison finale
```bash
# Build complet + génération de tous les rapports
mvn clean package site
```

---

## Résolution de problèmes

### Erreur : "JAVA_HOME not set"
```bash
# Vérifier Java
java -version

# Définir JAVA_HOME (Linux/Mac)
export JAVA_HOME=/path/to/java17

# Définir JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

### Erreur : Tests échouent
```bash
# Voir les détails des tests
mvn test -X

# Ignorer les tests temporairement (DÉCONSEILLÉ)
mvn package -DskipTests
```

---

## Vérification de l'installation

Pour vérifier que Maven est correctement configuré :

```bash
# Tester le build complet
mvn clean package

# Si tout fonctionne, vous devriez voir :
# BUILD SUCCESS
```

---

## Conclusion

Le build Maven de **Sprindo** garantit :
-  Une **structure claire et homogène** respectant les standards Java
-  Une **exécution automatisée** des analyses et tests
-  Une **intégration fluide** des outils de qualité (Checkstyle, JaCoCo)
-  Un **menu fonctionnel** avec accès au module de statistiques
-  Une **reproductibilité** du build sur tous les environnements de l'équipe

---
