# Documentation CI/CD - Sprint 3
## Gestionnaire de projets Sprindo

---

##  Informations du projet

**Nom du projet:** Sprindo - Gestionnaire de projets agile en Java

**Cours:** INF2050 – Outils et pratiques de développement logiciel

**Session:** Automne 2025

**Lien GitLab:** https://gitlab.info.uqam.ca/djeukam_tchouyomgou.celestine/sprindo

**Lien GitLab Pages (JavaDoc):** 
---

##  Équipe de développement

| Code permanent | Nom | Prénom | Contribution |
|----------------|-----|--------|--------------|
| KALM78350609 | Kalkoye Soumana Dit Fodo | Myriam | 33% |
| DJEC12338708 | Djeukam Tchouyomgou | Celestine | 34% |
| KODM83370504 | Koda Eyram | Manuella | 33% |

---

##  Qualité du code et tests

###  Métriques globales de qualité

| Métrique | Valeur | Statut | Objectif | Commentaires |
|----------|--------|--------|----------|----------|
| **Tests unitaires** | 267/267 |  100% | ≥ 100% | Evolution de 45% a 100% |
| **Couverture de lignes (JaCoCo)** | 94% (298/301) |  Excellent | ≥ 90% | Evolution de 61% a 94% |
| **Mutation Coverage (PITest)** | 82% (111/136) |  Très bon | ≥ 75% | Evolution de 0% a 82%. le plugin pitest etait mal configure |
| **Checkstyle** | 0 violation |  Parfait | 0 | A COMPLETER  |
| **PMD** | 4 violations (priorité 5) | Acceptable | < 10 | Evolution de 49 violations a 4 violations |
| **SpotBugs** | 0 violations | 100% | 0 bugs critiques | Evolution de 150 bugs a 9 bugs critiques ensuite a 0 bugs critiques.Plus que 4 bugs de priorites tres basses  |

### Détail des tests de mutation (PITest)

#### Résumé
```
Generated: 136 mutations
Killed:    111 mutations (82%)
Survived:  25 mutations (18%)
No Coverage: 0 mutations (0%)
```

#### Performance par type de mutation

| Type de mutation | Générées | Tuées | % | Évaluation |
|------------------|----------|-------|---|------------|
| **Primitive Returns** | 6 | 6 | 100% | Parfait |
| **Null Returns** | 6 | 6 | 100% |  Parfait |
| **Void Method Calls** | 2 | 2 | 100% | Parfait |
| **Math Operations** | 2 | 2 | 100% |  Parfait |
| **Remove Conditional (ORDER_ELSE)** | 1 | 1 | 100% |  Parfait |
| **Empty Object Returns** | 26 | 24 | 92% | Excellent |
| **Remove Conditionals (EQUAL_ELSE)** | 79 | 63 | 80% |  Très bon |
| **Boolean True Returns** | 11 | 6 | 55% |  Bon |
| **Boolean False Returns** | 2 | 1 | 50% | Acceptable |
| **Conditional Boundaries** | 1 | 0 | 0% | UN PEU BAS  |

**Score global : 82%**(Très bon)

### 📦Couverture par classe testée

| Classe | Fichier de test | Tests | Couverture |
|--------|-----------------|-------|------------|
| `ProjetImpl` | `ProjetImplTest.java` | 18 tests |  100% |
| `SprintImpl` | `SprintImplTest.java` | 18 tests |  100% |
| `TacheImpl` | `TacheImplTest.java` | 31 tests |  100% |
| `UtilisateurImpl` | `UtilisateurImplTest.java` | 16 tests | 100% |
| `Statistiques` | `StatistiquesTest.java` | 44 tests |  98% |
| `SprindoController` | `SprindoControllerTest.java` | 31 tests |  95% |
| `SprindoVue` | `SprindoVueTest.java` | 27 tests |  90% |
| **Modèles** | Tests unitaires | 82 tests |  100% |
| - `Projet` | `ProjetTest.java` | 23 tests | BON |
| - `Sprint` | `SprintTest.java` | 21 tests | BON |
| - `Tache` | `TacheTest.java` | 24 tests | BON |
| - `Utilisateur` | `UtilisateurTest.java` | 14 tests | BON |

**Total : 267 tests (100% de réussite)**

###  Analyses statiques détaillées

#### Checkstyle
```
Violations : 0
Style : Google Java Style Guide
Statut : PARFAIT
```
- Le code respecte à 100% les conventions de style Java
- Aucune violation détectée
- Configuration : `google_checks.xml`

####  PMD (Amélioration : 49 → 4 violations)

**Avant optimisation : 49 violations**
- 38 violations `SystemPrintln` (priorité 2)
- 5 violations `AvoidDuplicateLiterals` (priorité 3)
- 3 violations `UseCollectionIsEmpty` (priorité 3)
- 3 violations diverses

**Après optimisation : 4 violations**
```
Violations restantes : 4 (priorité 5 - la plus basse)
Type : DataflowAnomalyAnalysis
Localisation : 
  - Main.java (lignes 17, 57)
  - SprindoController.java (lignes 208, 241)
```

**Explication des violations restantes :**
- **Type** : Anomalies de flux de données (DU-anomaly)
- **Priorité** : 5 (la plus basse, non critique)
- **Nature** : Faux positifs générés par l'analyse statique
- **Impact** : Aucun - Le code est validé comme correct et fonctionnel
- **Justification** : Ces variables sont définies puis utilisées selon le flux normal du programme. PMD détecte un pattern "Define-Use" qu'il juge suspect, mais qui est intentionnel dans notre architecture MVC.

**Améliorations effectuées :**
1. Suppression de 38 `System.out.println` → Utilisation d'un logger
2.  Extraction des chaînes dupliquées dans des constantes
3.  Remplacement de `.size() == 0` par `.isEmpty()`
4.  Nettoyage des imports inutilisés
5. Réduction de la complexité des conditions

**Réduction : 92% des violations éliminées (49 → 4)**

###  Exécuter les tests et analyses

#### Tests unitaires standards
```bash
mvn clean test
```

#### Tests de mutation (PITest)
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```
Rapport généré dans : `target/pit-reports/[timestamp]/index.html`

#### Analyses statiques
```bash
# Checkstyle
mvn checkstyle:check

# PMD
mvn pmd:check

# Toutes les analyses
mvn clean test checkstyle:check pmd:check
```

#### Rapports de couverture
```bash
# Rapport JaCoCo
mvn jacoco:report
# Fichier : target/site/jacoco/index.html

# Rapport Surefire
mvn surefire-report:report
# Fichier : target/site/surefire-report.html
```


##  Configuration GitLab CI/CD

### Protection de la branche `main`

La branche principale `main` est protégée pour garantir la qualité du code et respecter les bonnes pratiques d'intégration continue:

#### Restrictions mises en place:

- **Push direct interdit**: Aucun développeur ne peut faire `git push` directement sur `main`
  - Configuration: `Allowed to push: No one`
  - Tout push direct sera rejeté par GitLab

- **Merge autorisé pour**: Maintainers uniquement
  - Configuration: `Allowed to merge: Maintainers`
  - Seuls les mainteneurs du projet peuvent fusionner les branches

- **Objectif**: Forcer la revue de code via Merge Requests et garantir que tout code fusionné a passé le pipeline CI/CD

#### Configuration dans GitLab:

Pour configurer la protection (déjà effectuée):
1. Settings → Repository → Protected branches
2. Sélectionner la branche `main`
3. Définir les règles de push et merge

### Déclenchement automatique des pipelines

Les pipelines CI/CD se lancent automatiquement dans les cas suivants:

#### 1. Push sur une branche (sauf `main`)

- **Déclencheur**: `git push origin <nom-branche>`
- **Objectif**: Tester le code avant de créer une Merge Request
- **Exemple**: 
  ```bash
  git checkout -b feature/nouvelle-fonctionnalite
  git add .
  git commit -m "Ajout fonctionnalité X"
  git push origin feature/nouvelle-fonctionnalite
  # → Pipeline se lance automatiquement
  ```

#### 2. Création d'une Merge Request vers `main`

- **Déclencheur**: Création ou mise à jour d'une MR vers `main`
- **Objectif**: Valider que le code est prêt à être fusionné
- **Processus**:
  1. Le pipeline se lance automatiquement
  2. Tous les tests et analyses doivent passer (jobs verts)
  3. Si le pipeline échoue, le merge est bloqué
  4. Une fois vert, un Maintainer peut approuver et fusionner

### Variables CI/CD

Les variables d'environnement suivantes sont configurées dans le pipeline:

| Variable | Valeur | Description |
|----------|--------|-------------|
| `MAVEN_OPTS` | `-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository` | Définit le répertoire local Maven pour optimiser le cache |
| `MAVEN_CLI_OPTS` | `--batch-mode --errors --fail-at-end --show-version` | Options Maven pour l'exécution en mode CI |

---

## 🔧 Pipeline `.gitlab-ci.yml`

### Vue d'ensemble

Notre pipeline comprend **5 stages** et **12 jobs** orchestrés pour garantir la qualité du code:

```
[analyse] → [build] → [test] → [reports] → [deploy]
```

### Architecture détaillée

#### Stage 1: `analyse` (Analyses statiques en parallèle)

Les trois jobs suivants s'exécutent **en parallèle** pour optimiser le temps d'exécution:

| Job | Description | Artefacts générés |
|-----|----------------|-------------|
| `analyse-statique-checkstyle`  | Vérifie les conventions de codage Java (Google Style) | `target/checkstyle-result.xml`<br>`target/site/checkstyle.html` | 
| `analyse-statique-pmd`| Détecte les problèmes de qualité (code mort, complexité) | `target/pmd.xml`<br>`target/site/pmd.html` |
| `analyse-statique-spotbugs` | Identifie les bugs potentiels (null pointers, ressources) | `target/spotbugsXml.xml`<br>`target/site/spotbugs.html` |

**Parallélisme**: Ces 3 jobs s'exécutent en même temps pour gagner du temps.

---

#### Stage 2: `build` (Compilation)

| Job | Commande Maven | Description | Artefacts générés | Dépendances |
|-----|----------------|-------------|-------------------|-------------|
| `compilation` | `mvn clean compile` | Compile le code source Java | `target/classes/` | Aucune |

**Objectif**: S'assurer que le code compile sans erreur avant d'exécuter les tests.

---

#### Stage 3: `test` (Exécution des tests)

| Job | Commande Maven | Description | Artefacts générés | Dépendances |
|-----|----------------|-------------|-------------------|-------------|
| `unit-test` | `mvn test` | Exécute les tests unitaires (`*Test.java`) | `target/surefire-reports/` | `compilation` |
| `integration-test` | `mvn verify` | Exécute les tests d'intégration (`*IT.java`) | `target/failsafe-reports/` | `compilation` |
| `mutation-test` | `mvn pitest:mutationCoverage` | Exécute les tests de mutation (PITest) | `target/pit-reports/` | `unit-test` |

---

#### Stage 4: `reports` (Génération des rapports en parallèle)

Les trois jobs suivants s'exécutent **en parallèle**:

| Job | Commande Maven | Description | Artefacts générés | 
|-----|----------------|-------------|-------------------|
| `rapports-tests` | `mvn surefire-report:report` | Génère le rapport HTML des tests unitaires | `target/site/surefire-report.html` |
| `rapports-couverture` | `mvn jacoco:report` | Génère le rapport de couverture JaCoCo | `target/site/jacoco/` |
| `documentation` | `mvn javadoc:javadoc` | Génère la JavaDoc complète | `target/site/apidocs/` | 

---

#### Stage 5: `deploy` (Construction et publication)

| Job | Commande Maven | Description | Artefacts générés | 
|-----|----------------|-------------|-------------------|
| `construction` | `mvn package -DskipTests` | Crée le JAR exécutable | `target/sprindo-1.0.jar` | 
| `pages` | `mvn javadoc:javadoc` + copie | Publie la JavaDoc sur GitLab Pages | `public/` (JavaDoc) |

**Note importante**: Le job `pages` ne s'exécute que sur la branche `main` après un merge réussi.

---

### Parallélisme et optimisation

#### Parallélisme configuré

1. **Stage `analyse`**: 3 jobs en parallèle
   - Gain de temps: ~60% (de 6 min à 2-3 min)

2. **Stage `reports`**: 3 jobs en parallèle
   - Gain de temps: ~50% (de 4 min à 2 min)

#### Dépendances entre jobs

```
analyse-statique-* (parallèle)
         ↓
    compilation
         ↓
    ┌────┴────┬──────────────
    ↓         ↓              ↓
unit-test  integration-test  
    ↓         
mutation-test
    ↓
rapports-* (parallèle)
    ↓
construction → pages (si main)
```

---

### Artefacts

Les artefacts sont des fichiers générés par les jobs et conservés pour consultation ou débogage:

#### Téléchargement des artefacts

1. Aller sur GitLab → CI/CD → Jobs
2. Cliquer sur le job souhaité
3. Bouton "Browse" (voir les fichiers) ou "Download" (télécharger le ZIP)

---

### Cache Maven

Un cache Maven est configuré pour accélérer les builds:

```yaml
cache:
  key: "$CI_COMMIT_REF_SLUG"
  paths:
    - .m2/repository
```

---

## 🛠️ Plugins Maven utilisés

### Tests et qualité

| Plugin | Version | Fonction |
|--------|---------|----------|
| `maven-surefire-plugin` | 3.2.5 | Exécution des tests unitaires (`*Test.java`) |
| `maven-failsafe-plugin` | 3.2.5 | Exécution des tests d'intégration (`*IT.java`) | 
| `pitest-maven` | 1.15.3 | Tests de mutation | 
| `jacoco-maven-plugin` | 0.8.11 | Mesure de la couverture de code | 

### Analyses statiques

| Plugin | Version | Fonction |
|--------|---------|----------|
| `maven-checkstyle-plugin` | 3.3.1 | Vérification des conventions de codage |
| `maven-pmd-plugin` | 3.21.2 | Détection de problèmes de qualité | 
| `spotbugs-maven-plugin` | 4.8.3.0 | Détection de bugs potentiels |

### Documentation et construction

| Plugin | Version | Fonction | 
|--------|---------|----------|
| `maven-javadoc-plugin` | 3.6.3 | Génération de la documentation | 
| `maven-shade-plugin` | 3.5.1 | Création d'un JAR exécutable |

### Utilitaires

| Plugin | Version | Fonction | 
|--------|---------|----------|
| `build-helper-maven-plugin` | 3.5.0 | Ajout du répertoire de tests d'intégration |

---


##  Exécution locale

### Prérequis

- **Java**: Version 21 
- **Maven**: Version 3.9.7
- **Git**: Pour cloner le dépôt

### Commandes Maven essentielles

#### Compilation et nettoyage

```bash
# Nettoyer et compiler
mvn clean compile

# Nettoyer complètement (supprime target/)
mvn clean
```

#### Tests

```bash
# Exécuter uniquement les tests unitaires
mvn test

# Exécuter tests unitaires + intégration
mvn verify

# Exécuter les tests de mutation (plus long)
mvn pitest:mutationCoverage
```

#### Analyses statiques

```bash
# Checkstyle
mvn checkstyle:check

# PMD
mvn pmd:check

# SpotBugs
mvn spotbugs:check

# Toutes les analyses en une commande
mvn checkstyle:check pmd:check spotbugs:check
```

#### Rapports

```bash
# Rapport de couverture JaCoCo
mvn jacoco:report
# → Ouvrir: target/site/jacoco/index.html

# Rapport des tests
mvn surefire-report:report
# → Ouvrir: target/site/surefire-report.html

# Rapport PITest
mvn pitest:mutationCoverage
# → Ouvrir: target/pit-reports/[timestamp]/index.html

# JavaDoc
mvn javadoc:javadoc
# → Ouvrir: target/site/apidocs/index.html
```

#### Construction finale

```bash
# Créer le JAR exécutable
mvn package

# Exécuter l'application
java -jar target/sprindo-1.0.jar
```

#### Pipeline complet local

```bash
# Exécuter toutes les étapes du pipeline
mvn clean verify \
    checkstyle:check \
    pmd:check \
    spotbugs:check \
    jacoco:report \
    pitest:mutationCoverage \
    javadoc:javadoc \
    package
```

---

## Captures d'écran du projet

### Board Jira
![Capture du board Jira](image/capture_board_jira.jpeg)

### Couverture de code (JaCoCo)
![JaCoCo avant](image/jacoco_avant.jpeg)
![Rapport JaCoCo](image/rapport_jacoco_ok.jpeg)

### Documentation (Javadoc)
![Javadoc Contrôleur](image/javadoc_sprindo_controlleur.jpeg)
![Javadoc Vue](image/javadoc_sprindo_vue.jpeg)
![Javadoc Modèle](image/javadoc_sprondo_modele.jpeg)

### Résultats des outils de qualité
![Checkstyle](image/resultats_checkstyle_build_success.jpeg)
![SpotBugs](image/resultats_spotbugs_build_success.jpeg)
![Tests de mutation](image/resultats_tests_mutation_build_success.jpeg)

### Structure du projet
![Arborescence](image/premiere_partie_tree.jpeg)

#### Arborescence du projet 

.
├── doc
│   ├── build.md
│   ├── Contributions-Sprint3.md
│   ├── DoD.md
│   ├── DoR.md
│   ├── Equipe.md
│   ├── qualite.md
│   ├── README-CI.md
│   ├── Style.md
│   ├── test.md
│   └── tests.md
├── out
│   └── production
│       └── sprindo-sprint1
│           └── com
│               └── sprindo
│                   ├── controller
│                   │   └── SprindoController.class
│                   ├── impl
│                   │   ├── ProjetImpl.class
│                   │   ├── SprintImpl.class
│                   │   ├── TacheImpl.class
│                   │   └── UtilisateurImpl.class
│                   ├── Main.class
│                   ├── model
│                   │   ├── Projet.class
│                   │   ├── Sprint.class
│                   │   ├── Tache.class
│                   │   ├── TestVue.class
│                   │   └── Utilisateur.class
│                   └── view
│                       └── SprindoVue.class
├── pom.xml
├── pom.xml.backup-avant-exclusions
├── pom.xml.backup-sprint2
├── README.md
├── README.pdf
├── README-temp.md
├── sprindo-sprint1.iml
├── src
│   ├── integration-test
│   │   └── java
│   │       └── ca
│   │           └── uqam
│   │               └── sprindo
│   │                   ├── ProjetIntegrationIT.java
│   │                   └── SprintIntegrationIT.java
│   ├── main
│   │   └── java
│   │       └── ca
│   │           └── uqam
│   │               └── sprindo
│   │                   ├── controleur
│   │                   │   ├── ProjetImpl.java
│   │                   │   ├── SprindoController.java
│   │                   │   ├── SprintImpl.java
│   │                   │   ├── Statistiques.java
│   │                   │   ├── TacheImpl.java
│   │                   │   └── UtilisateurImpl.java
│   │                   ├── Main.java
│   │                   ├── modele
│   │                   │   ├── Projet.java
│   │                   │   ├── Sprint.java
│   │                   │   ├── Tache.java
│   │                   │   └── Utilisateur.java
│   │                   └── vue
│   │                       └── SprindoVue.java
│   └── test
│       └── java
│           └── ca
│               └── uqam
│                   └── sprindo
│                       ├── controleur
│                       │   ├── ProjetImplTest.java
│                       │   ├── SprindoControllerTest.java
│                       │   ├── SprintImplTest.java
│                       │   ├── StatistiquesTest.java
│                       │   ├── TacheImplTest.java
│                       │   └── UtilisateurImplTest.java
│                       ├── modele
│                       │   ├── ProjetTest.java
│                       │   ├── SprintTest.java
│                       │   ├── TacheTest.java
│                       │   └── UtilisateurTest.java
│                       └── vue
│                           └── SprindoVueTest.java
└── target
    ├── checkstyle-cachefile
    ├── checkstyle-checker.xml
    ├── checkstyle-result.xml
    ├── classes
    │   └── ca
    │       └── uqam
    │           └── sprindo
    │               ├── controleur
    │               │   ├── ProjetImpl.class
    │               │   ├── SprindoController.class
    │               │   ├── SprintImpl.class
    │               │   ├── Statistiques.class
    │               │   ├── TacheImpl.class
    │               │   └── UtilisateurImpl.class
    │               ├── Main.class
    │               ├── modele
    │               │   ├── Projet.class
    │               │   ├── Sprint.class
    │               │   ├── Tache.class
    │               │   └── Utilisateur.class
    │               └── vue
    │                   └── SprindoVue.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    ├── jacoco.exec
    ├── javadoc-bundle-options
    │   └── javadoc-options-javadoc-resources.xml
    ├── maven-javadoc-plugin-stale-data.txt
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── site
    │   ├── apidocs
    │   │   ├── allclasses-index.html
    │   │   ├── allpackages-index.html
    │   │   ├── ca
    │   │   │   └── uqam
    │   │   │       └── sprindo
    │   │   │           ├── class-use
    │   │   │           │   └── Main.html
    │   │   │           ├── controleur
    │   │   │           │   ├── class-use
    │   │   │           │   │   ├── ProjetImpl.html
    │   │   │           │   │   ├── SprindoController.html
    │   │   │           │   │   ├── SprintImpl.html
    │   │   │           │   │   ├── Statistiques.html
    │   │   │           │   │   ├── TacheImpl.html
    │   │   │           │   │   └── UtilisateurImpl.html
    │   │   │           │   ├── package-summary.html
    │   │   │           │   ├── package-tree.html
    │   │   │           │   ├── package-use.html
    │   │   │           │   ├── ProjetImpl.html
    │   │   │           │   ├── SprindoController.html
    │   │   │           │   ├── SprintImpl.html
    │   │   │           │   ├── Statistiques.html
    │   │   │           │   ├── TacheImpl.html
    │   │   │           │   └── UtilisateurImpl.html
    │   │   │           ├── Main.html
    │   │   │           ├── modele
    │   │   │           │   ├── class-use
    │   │   │           │   │   ├── Projet.html
    │   │   │           │   │   ├── Sprint.html
    │   │   │           │   │   ├── Tache.html
    │   │   │           │   │   └── Utilisateur.html
    │   │   │           │   ├── package-summary.html
    │   │   │           │   ├── package-tree.html
    │   │   │           │   ├── package-use.html
    │   │   │           │   ├── Projet.html
    │   │   │           │   ├── Sprint.html
    │   │   │           │   ├── Tache.html
    │   │   │           │   └── Utilisateur.html
    │   │   │           ├── package-summary.html
    │   │   │           ├── package-tree.html
    │   │   │           ├── package-use.html
    │   │   │           └── vue
    │   │   │               ├── class-use
    │   │   │               │   └── SprindoVue.html
    │   │   │               ├── package-summary.html
    │   │   │               ├── package-tree.html
    │   │   │               ├── package-use.html
    │   │   │               └── SprindoVue.html
    │   │   ├── constant-values.html
    │   │   ├── element-list
    │   │   ├── index-all.html
    │   │   ├── index.html
    │   │   ├── jquery-ui.overrides.css
    │   │   ├── legal
    │   │   │   ├── ASSEMBLY_EXCEPTION
    │   │   │   ├── jquery.md
    │   │   │   └── jqueryUI.md
    │   │   ├── member-search-index.js
    │   │   ├── module-search-index.js
    │   │   ├── overview-summary.html
    │   │   ├── overview-tree.html
    │   │   ├── package-search-index.js
    │   │   ├── resources
    │   │   │   ├── glass.png
    │   │   │   └── x.png
    │   │   ├── script-dir
    │   │   │   ├── jquery-3.7.1.min.js
    │   │   │   ├── jquery-ui.min.css
    │   │   │   └── jquery-ui.min.js
    │   │   ├── script.js
    │   │   ├── search.js
    │   │   ├── stylesheet.css
    │   │   ├── tag-search-index.js
    │   │   └── type-search-index.js
    │   └── jacoco
    │       ├── ca.uqam.sprindo.controleur
    │       │   ├── index.html
    │       │   ├── index.source.html
    │       │   ├── ProjetImpl.html
    │       │   ├── ProjetImpl.java.html
    │       │   ├── SprindoController.html
    │       │   ├── SprindoController.java.html
    │       │   ├── SprintImpl.html
    │       │   ├── SprintImpl.java.html
    │       │   ├── Statistiques.html
    │       │   ├── Statistiques.java.html
    │       │   ├── TacheImpl.html
    │       │   ├── TacheImpl.java.html
    │       │   ├── UtilisateurImpl.html
    │       │   └── UtilisateurImpl.java.html
    │       ├── ca.uqam.sprindo.modele
    │       │   ├── index.html
    │       │   ├── index.source.html
    │       │   ├── Projet.html
    │       │   ├── Projet.java.html
    │       │   ├── Sprint.html
    │       │   ├── Sprint.java.html
    │       │   ├── Tache.html
    │       │   ├── Tache.java.html
    │       │   ├── Utilisateur.html
    │       │   └── Utilisateur.java.html
    │       ├── index.html
    │       ├── jacoco.csv
    │       ├── jacoco-resources
    │       │   ├── branchfc.gif
    │       │   ├── branchnc.gif
    │       │   ├── branchpc.gif
    │       │   ├── bundle.gif
    │       │   ├── class.gif
    │       │   ├── down.gif
    │       │   ├── greenbar.gif
    │       │   ├── group.gif
    │       │   ├── method.gif
    │       │   ├── package.gif
    │       │   ├── prettify.css
    │       │   ├── prettify.js
    │       │   ├── redbar.gif
    │       │   ├── report.css
    │       │   ├── report.gif
    │       │   ├── session.gif
    │       │   ├── sort.gif
    │       │   ├── sort.js
    │       │   ├── source.gif
    │       │   └── up.gif
    │       ├── jacoco-sessions.html
    │       └── jacoco.xml
    ├── surefire-reports
    │   ├── ca.uqam.sprindo.controleur.ProjetImplTest.txt
    │   ├── ca.uqam.sprindo.controleur.SprindoControllerTest.txt
    │   ├── ca.uqam.sprindo.controleur.SprintImplTest.txt
    │   ├── ca.uqam.sprindo.controleur.StatistiquesTest.txt
    │   ├── ca.uqam.sprindo.controleur.TacheImplTest.txt
    │   ├── ca.uqam.sprindo.controleur.UtilisateurImplTest.txt
    │   ├── ca.uqam.sprindo.modele.ProjetTest.txt
    │   ├── ca.uqam.sprindo.modele.SprintTest.txt
    │   ├── ca.uqam.sprindo.modele.TacheTest.txt
    │   ├── ca.uqam.sprindo.modele.UtilisateurTest.txt
    │   ├── ca.uqam.sprindo.vue.SprindoVueTest.txt
    │   ├── TEST-ca.uqam.sprindo.controleur.ProjetImplTest.xml
    │   ├── TEST-ca.uqam.sprindo.controleur.SprindoControllerTest.xml
    │   ├── TEST-ca.uqam.sprindo.controleur.SprintImplTest.xml
    │   ├── TEST-ca.uqam.sprindo.controleur.StatistiquesTest.xml
    │   ├── TEST-ca.uqam.sprindo.controleur.TacheImplTest.xml
    │   ├── TEST-ca.uqam.sprindo.controleur.UtilisateurImplTest.xml
    │   ├── TEST-ca.uqam.sprindo.modele.ProjetTest.xml
    │   ├── TEST-ca.uqam.sprindo.modele.SprintTest.xml
    │   ├── TEST-ca.uqam.sprindo.modele.TacheTest.xml
    │   ├── TEST-ca.uqam.sprindo.modele.UtilisateurTest.xml
    │   └── TEST-ca.uqam.sprindo.vue.SprindoVueTest.xml
    └── test-classes
        └── ca
            └── uqam
                └── sprindo
                    ├── controleur
                    │   ├── ProjetImplTest.class
                    │   ├── SprindoControllerTest.class
                    │   ├── SprintImplTest.class
                    │   ├── StatistiquesTest.class
                    │   ├── TacheImplTest.class
                    │   └── UtilisateurImplTest.class
                    ├── modele
                    │   ├── ProjetTest.class
                    │   ├── SprintTest.class
                    │   ├── TacheTest.class
                    │   └── UtilisateurTest.class
                    ├── ProjetIntegrationIT.class
                    ├── SprintIntegrationIT.class
                    └── vue
                        ├── SprindoVueTest$SprindoVueMock.class
                        └── SprindoVueTest.class

---


