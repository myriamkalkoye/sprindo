## 📋 Vue d'ensemble

Ce document décrit la stratégie de tests mise en place pour le projet Sprindo et présente les résultats obtenus lors du Sprint 3.

---

## 🎯 Objectifs de tests

- Garantir la qualité du code
- Atteindre une couverture de tests ≥ 90%
- Valider les interactions entre les composants
- Détecter les bugs potentiels (mutation testing)
- Assurer la conformité aux standards de qualité

---

# Stratégie de tests - Sprint 3

Les tests ont été écrits pour valider principalement les classes métier du package `ca.uqam.sprindo.controleur` et la vue console `SprindoVue`.

Approche adoptée :

- Tests unitaires isolés par classe Impl (Projet, Sprint, Tâche, Utilisateur).
- Tests du contrôleur `SprindoController` avec mocks (Mockito) et injection par réflexion.
- Tests de la classe de service `Statistiques` basés sur des scénarios projet/sprint/tâches/utilisateurs réalistes.
- Tests de la vue `SprindoVue` avec redirection de `System.in` / `System.out`.

Principes appliqués :

- Pattern AAA : Arrange, Act, Assert systématiquement.
- Validation des entrées : tests avec valeurs nulles, vides, espaces, doublons.
- Tests de volume : ajout de 5 à 20 objets selon la classe.
- Tests d’intégration légère : modification d’objets retournés par les Impl.


##  Types de tests

### 1. Tests unitaires (Sprint 2 + Sprint 3)

**Responsable :** Myriam

**Objectif :** Tester chaque classe individuellement en isolation.

**Couverture globale :** 

- Instructions : 43 %
- Branches : 58 %

**Classes testées :**
| Package | Instructions | Branches |
|---------|-------------:|---------:|
| `ca.uqam.sprindo.controleur` | 53% | 77% |
| `ca.uqam.sprindo.modele` | 57% | 0% |
| `ca.uqam.sprindo.vue` | 4% | 0% |
| `Main` | 0% | 0% |

La couverture réelle est concentrée exclusivement sur :

- Les Impl métier (`ProjetImpl`, `SprintImpl`, `TacheImpl`, `UtilisateurImpl`)
- La classe de service `Statistiques`
- Le contrôleur `SprindoController` et la vue `SprindoVue`


**Outil utilisé :** JUnit 4

## 2. Documentation par Implémentation

### 2.1. ProjetImpl — Gestion des projets  
Référence : `ProjetImplTest.java`.

**Rôle général :** gérer le cycle de vie des projets : ajout, récupération et liste.

| Méthode | Description | Validation/Exceptions |
|--------|-------------|----------------------|
| `void ajouterProjet(Projet p)` | Ajoute un projet unique. | `IllegalArgumentException` si `null`, nom vide, description vide, doublon d’ID. |
| `Projet getProjet(String id)` | Récupère un projet par ID. | Retourne `null` si inexistant. |
| `List<Projet> getProjets()` | Liste tous les projets. | Liste non nulle, vide si aucun projet. |

**Observations testées :**
- Ajout correct : `testAjouterProjet`
- Null interdit : `testAjouterProjetNull`
- Nom/description vides interdits
- Doublon interdit
- ID long accepté

---

### 2.2. SprintImpl — Gestion des sprints  
Référence : `SprintImplTest.java`.

**Rôle général :** gérer les sprints par projet.

| Méthode | Description | Validation/Exceptions |
|--------|-------------|----------------------|
| `void ajouterSprint(String idProjet, Sprint sprint)` | Ajoute un sprint à un projet. | Accepte dates nulles, objectif vide. Comportement permissif pour projet inexistant. |
| `Sprint getSprint(String idProjet, String idSprint)` | Récupère un sprint. | `null` si inexistant. |
| `List<Sprint> getSprintsParProjet(String idProjet)` | Liste les sprints d’un projet. | Liste toujours non nulle. |

**Observations testées :**
- Dates nulles acceptées
- Objectif vide accepté
- Volume (10+) accepté
- Modification persistée via l’objet retourné

---

### 2.3. TacheImpl — Gestion des tâches  
Référence : `TacheImplTest.java`.

**Rôle général :** gérer les tâches par sprint.

| Méthode | Description | Validation/Exceptions |
|--------|-------------|----------------------|
| `void ajouterTache(String idProjet, String idSprint, Tache t)` | Ajout d’une tâche. | `IllegalArgumentException` si `null` ou description vide. |
| `Tache getTache(String idProjet, String idSprint, String idTache)` | Récupère une tâche. | `null` si inexistante ou supprimée. |
| `List<Tache> getTachesParSprint(...)` | Liste les tâches. | Liste non nulle. |
| `void assignerTache(...)` | Affecte une personne. | Accepté même plusieurs fois. |
| `void changerStatut(...)` | Modifie le statut. | Tous les statuts testés. |
| `void supprimerTache(...)` | Supprime une tâche. | `getTache` renvoie `null` après. |

---

### 2.4. UtilisateurImpl — Gestion des utilisateurs  
Référence : `UtilisateurImplTest.java`.

| Méthode | Description | Validation |
|--------|-------------|-----------|
| `void ajouterUtilisateur(Utilisateur u)` | Ajoute un utilisateur. | Null interdit, nom vide interdit, rôle vide interdit, doublon interdit. |
| `Utilisateur getUtilisateur(String nom)` | Récupère par nom. | `null` si inexistant. |
| `List<Utilisateur> getUtilisateurs()` | Liste des utilisateurs. | Liste non nulle, vide si aucun. |

**Observation notable :** accepte un nom très long (test explicitement validé).

---

### 2.5. Statistiques — Calcul des métriques  
Référence : `StatistiquesTest.java`.

| Méthode | Description |
|--------|-------------|
| `getNombreTotalSprints(idProjet)` | Retourne le nombre de sprints d’un projet. |
| `getNombreTotalUtilisateurs()` | Compte les utilisateurs. |
| `getNombreTachesDansSprint(idP,idS)` | Retourne le nombre de tâches d’un sprint. |
| `getNombreDeveloppeursActifs(...)` | Développeurs distincts avec au moins une tâche assignée. |
| `getPourcentageTachesTerminees(...)` | Pourcentage de tâches terminées. |

**Observations :**
- Retourne systématiquement 0 pour null, vide, espaces.
- Normalisation de la casse des développeurs.
- Ignore les tâches non assignées.

---

### 2.6. SprindoController — Contrôleur MVC  
Référence : `SprindoControllerTest.java`.

**Rôle :** oriente les appels entre la vue et les Impl + gère exceptions.

**Méthodes publiques observées :**

| Méthode | Rôle |
|--------|------|
| `ajouterProjet()` | Collecte la saisie et délègue à `ProjetImpl`. |
| `afficherProjets()` | Demande à la vue d’afficher la liste. |
| `ajouterSprint()` | Vérifie projet + saisie, délègue. |
| `afficherSprints()` | Idem pour les sprints. |
| `ajouterTache()` | Vérifie projet+sprint+tâche. |
| `afficherTaches()` | Affiche les tâches d’un sprint. |
| `assignerTache()` | Délègue et affiche succès/erreur. |
| `modifierStatutTache()` | Change le statut via Impl. |

Testé avec **Mockito + réflexion** pour injecter des dépendances privées.

---

### 2.7. SprindoVue — Saisie/affichage console  
Référence : `SprindoVueTest.java`.

| Catégorie | Méthodes |
|-----------|----------|
| Affichage | `afficherMessage`, `afficherErreur`, `afficherMenu`, `afficherProjets`, `afficherSprints`, `afficherTaches` |
| Saisie | `lireChoixMenu`, `demanderIdProjet`, `demanderNomProjet`, `demanderDescriptionProjet`, `demanderIdSprint`, `demanderNumeroSprint`, `demanderObjectifSprint`, `demanderDateDebut`, `demanderDateFin`, `demanderIdTache`, `demanderTitreTache`, `demanderDescriptionTache`, `demanderStatutTache`, `demanderNomUtilisateur` |

Simulation par `System.in` dans les tests.

---

**Commande Maven :**
```bash
mvn test
```

**Résultats :**
- Tous les tests unitaires passent
- Couverture JaCoCo : **94** %
- Rapports générés dans `target/surefire-reports/`

---

### 3. Tests d'intégration (Sprint 3)

**Responsable :** Manuella

**Objectif :** Valider les interactions entre plusieurs composants du système.

**Emplacement :** `src/integration-test/java/ca/uqam/sprindo/`

**Classes de tests créées :**

#### **ProjetIntegrationIT.java** (5 tests)
1. `testAjouterSprintsAuProjet` - Vérifier l'ajout de sprints à un projet
2. `testAjouterTachesAuSprintDansProjet` - Tester l'ajout de tâches via le projet
3. `testAssignerUtilisateursAuxTaches` - Valider l'assignation d'utilisateurs
4. `testSupprimerSprintDuProjet` - Vérifier la suppression de sprints
5. `testWorkflowCompletProjetAgile` - Scénario complet (Projet → Sprint → Tâche → Utilisateur)

#### **SprintIntegrationIT.java** (4 tests)
1. `testAjouterTachesEtCalculerStatistiques` - Calcul de métriques sur les tâches
2. `testAssignerPlusieursUtilisateurs` - Assignation multiple de développeurs
3. `testSupprimerTacheDuSprint` - Suppression de tâches
4. `testWorkflowCompletSprint` - Workflow complet avec calcul de progression

**Outil utilisé :** JUnit 4 + Maven Failsafe Plugin

**Commande Maven :**
```bash
mvn verify
```

**Résultats :**
-  **9/9 tests d'intégration passent**
-  Temps d'exécution : ~0.1 seconde
-  Rapports générés dans `target/failsafe-reports/`

**Scénarios testés :**
- Interaction Projet ↔ Sprint
- Interaction Sprint ↔ Tâche
- Interaction Tâche ↔ Utilisateur
- Cascade de suppression
- Calcul de statistiques (nombre de tâches, développeurs actifs, progression)

---

### 4. Tests de mutation (Sprint 3)

**Responsable :** Celestine + Manuella (amélioration du score)

**Objectif :** Vérifier la robustesse des tests en introduisant des mutations (bugs volontaires) dans le code.

**Outil utilisé :** PITest (Mutation Testing)

**Commande Maven :**
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

**Résultats :**
- **Score de mutation :** **82** %
- **Mutants générés :** 136 
- **Mutants tués :** 111
- **Mutants survivants :** 25
-  Rapports générés dans `target/pit-reports/`

**Cible :** Score de mutation ≥ 75%

---

## Analyses statiques

### Checkstyle (Google Java Style)

**Responsable :** Manuella

**Commande :**
```bash
mvn checkstyle:check
```

**Résultats :**
-  **0 violations** (455 violations corrigées)
- Code conforme au Google Java Style Guide
- Rapport : `target/site/checkstyle.html`

---

### PMD (Bug Detection)

**Responsable :** Manuella

**Commande :**
```bash
mvn pmd:check
```

**Résultats :**
-  **4 violations détectées** (toutes acceptables)
  - 4 violations `DataflowAnomaly` (faux positifs)
-  Toutes les violations critiques corrigées
-  Rapport : `target/pmd.xml`

---

### SpotBugs (Security & Bug Detection)

**Responsable :** Manuella

**Commande :**
```bash
mvn spotbugs:check
```

**Résultats :**
-  **0 bugs détectés** (19 bugs détectés)
-  Aucun NullPointerException potentiel
-  Aucune fuite de ressources
-  Code sécurisé

---

##  Couverture de code (JaCoCo)

**Responsable :** Myriam

**Outil :** JaCoCo Maven Plugin

**Commande :**
```bash
mvn jacoco:report
```

**Objectif :** ≥ 90% de couverture

**Résultats :**
- **Couverture globale :** **94** %
- **Package `modele` :** 100 %
- **Package `controleur` :** 95 %
- **Package `vue` :** 98 %
- Rapport : `target/site/jacoco/index.html`

**Métriques JaCoCo :**
- Instructions couvertes
- Branches couvertes
- Lignes couvertes
- Méthodes couvertes
- Classes couvertes

---

##  Configuration Maven

### Plugins utilisés

#### maven-surefire-plugin (Tests unitaires)
```xml
<version>3.2.5</version>
```
- Exécute les tests `*Test.java`
- Génère les rapports dans `target/surefire-reports/`

#### maven-failsafe-plugin (Tests d'intégration)
```xml
<version>3.2.5</version>
```
- Exécute les tests `*IT.java`
- Génère les rapports dans `target/failsafe-reports/`

#### pitest-maven (Tests de mutation)
```xml
<version>1.15.3</version>
```
- Génère des mutations du code
- Vérifie si les tests détectent les bugs introduits

#### jacoco-maven-plugin (Couverture)
```xml
<version>0.8.10</version>
```
- Mesure la couverture de code
- Génère des rapports HTML/XML

---

## Exécution des tests

### Commandes principales
```bash
# Tous les tests (unitaires + intégration)
mvn verify

# Tests unitaires uniquement
mvn test

# Tests d'intégration uniquement
mvn failsafe:integration-test failsafe:verify

# Couverture de code
mvn jacoco:report

# Tests de mutation
mvn org.pitest:pitest-maven:mutationCoverage

# Toutes les analyses
mvn clean verify jacoco:report
```

---


##  Résultats obtenus

### Tests unitaires (Surefire)

| Métrique | Résultat |
|----------|----------|
| **Tests exécutés** | 267 |
| **Tests réussis** | 267 |
| **Tests échoués** | 0 |
| **Taux de réussite** | 100% |


**Rapport** : `target/surefire-reports/`

---

### Tests d'intégration (Failsafe)

| Métrique | Résultat |
|----------|----------|
| **Tests exécutés** | 9 |
| **Tests réussis** | 9 |
| **Tests échoués** | 0 |
| **Taux de réussite** | 100% |

**Rapport** : `target/failsafe-reports/`

---

### Couverture de code (JaCoCo)


**Détails par package** :

| Package | Couverture |
|---------|-----------|
| `ca.uqam.sprindo.modele` | 100% |
| `ca.uqam.sprindo.controleur` | 95% |
| `ca.uqam.sprindo.vue` | 98% |

**Rapport** : `target/site/jacoco/index.html`

---

### Tests de mutation (PITest)

| Métrique | Résultat | Objectif |
|----------|----------|----------|
| **Mutations générées** | 138 | - |
| **Mutations tuées** | 111 | - |
| **Mutations survived** | 25 | Minimiser |
| **Test strength** | 82% | ≥ 75% |

**Rapport** : `target/pit-reports/[timestamp]/index.html`

---

### Analyses statiques

#### Checkstyle

| Métrique | Résultat |
|----------|----------|
| **Erreurs** | 0 |


**Principales règles vérifiées** :
- Indentation (2 espaces)
- Longueur des lignes (≤ 100 caractères)
- Javadoc sur classes/méthodes publiques
- Ordre des imports

---

#### PMD

| Métrique | Résultat (avant) | Résultat (aprés) |
|----------|----------|----------|
| **Violations** | 49 |   4       |
| **Priorité 1 (haute)** | 39 |  0   |
| **Priorité 2 (moyenne)** | 6 |   0 |
| **Priorité 3 (basse)** | 4 |    4  |

**Les violations traitées

**Règles appliquées** :
- Détection de code mort
- Complexité cyclomatique
- Variables non utilisées

---

#### SpotBugs

| Métrique | Résultat |
|----------|----------|
| **Bugs trouvés** | 19 |
| **Haute priorité** | 0 |
| **Moyenne priorité** | 12 |
| **Basse priorité** | 7 |

**Types de bugs corrigés** :
- Null pointer dereference
- Ressources non fermées
- Comparaisons incorrectes


---

##  Conclusion

La stratégie de tests mise en place garantit :
- La qualité du code (analyses statiques)
- La fiabilité des fonctionnalités (tests unitaires)
- La cohérence des interactions (tests d'intégration)
- La robustesse des tests (tests de mutation)
-  Une couverture de code élevée (JaCoCo)

**Qualité globale du code : EXCELLENTE** 

---

## Équipe

- **Manuella** : Tests d'intégration, Checkstyle, PMD, SpotBugs
- **Celestine** : Tests de mutation (PITest)
- **Myriam** : Tests unitaires, Couverture JaCoCo

---

*Document généré par Manuella Koda 
*Sprint 3 - Projet Sprindo - INF2050*
