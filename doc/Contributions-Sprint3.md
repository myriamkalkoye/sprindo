# Contributions - Sprint 3

## Déclaration des contributions

| Code permanent | Nom | Prénom | Contribution (%) |
|----------------|-----|--------|------------------|
| DJEC12338708 | Djeukam Tchouyomgou | Celestine | 34% |
| KALM78350609 | Kalkoye Soumana Dit Fodo | Myriam | 33% |
| KODM83370504 | Koda Eyram | Manuella | 33% |

**Total**: 100%

---

## Détail des tâches par membre

### Celestine (34%)

**Responsable CI/CD et Maven**

- **Phase 2**: Configuration Maven (pom.xml) avec tous les plugins nécessaires
  - Surefire, Failsafe, PITest, Checkstyle, PMD, SpotBugs, JaCoCo, JavaDoc, Shade, build-helper
  - Configuration des versions et propriétés Maven
  
- **Phase 6**: Tests de mutation avec PITest
  - Configuration du plugin PITest
  - Amélioration du score de mutation (≥ 75%)
  - Ajout de tests pour tuer les mutants survivants
  
- **Phase 7**: Création du pipeline GitLab CI/CD (.gitlab-ci.yml)
  - Définition des 5 stages (analyse, build, test, reports, deploy)
  - Configuration des 12 jobs du pipeline
  - Mise en place du parallélisme (analyses statiques et rapports)
  - Configuration du cache Maven
  
- **Phase 8**: Configuration GitLab
  - Protection de la branche main
  - Configuration des règles de merge (Maintainers only)
  - Création et test de Merge Request
  - Validation du pipeline sur MR

- Support technique et revue de code

### Myriam (33%)

**Responsable Tests unitaires et Documentation**

- **Phase 1**: Gestion JIRA Sprint 3
  - Création du Sprint 3 dans JIRA
  - Ajout des User Stories (US-20 à US-26)
  - Création des tâches techniques avec story points
  - Assignation des tâches aux membres de l'équipe
  
- **Phase 4**: Complétion des tests unitaires
  - Ajout de tests pour atteindre 90% de couverture JaCoCo
  - Tests des getters, setters et méthodes métier
  - Tests des cas limites (null, vide, négatifs)
  - Tests des exceptions (IllegalArgumentException, etc.)
  
- **Phase 9**: Documentation complète
  - Rédaction du README-CI.md
  - Documentation de la configuration GitLab CI/CD
  - Documentation du pipeline et des plugins Maven
  - Rédaction du fichier Contributions-Sprint3.md
  - Conversion README-CI.md en PDF
  
- **Phase 11**: JavaDoc et GitLab Pages (à venir)
  - Amélioration de la JavaDoc
  - Publication sur GitLab Pages
  - Mise à jour du lien Pages dans README-CI.md
  
- **Phase 12**: Accès enseignants (à venir)
  - Ajout des enseignants avec rôle Developer

### Manuella (33%)

**Responsable Tests d'intégration et Qualité**

- **Phase 3**: Tests d'intégration
  - Création de la structure src/integration-test/java/
  - Développement de ProjetIntegrationIT.java (minimum 5 tests)
  - Développement de SprintIntegrationIT.java (minimum 4 tests)
  - Ajout de méthodes métier manquantes (calculerDuree, calculerVelocity)
  - Validation avec mvn verify
  
- **Phase 5**: Analyses statiques
  - Correction des violations Checkstyle (indentation, lignes longues, JavaDoc)
  - Correction des violations PMD (variables non utilisées, complexité)
  - Correction des anomalies SpotBugs (null pointers, ressources, comparaisons)
  - Génération des rapports HTML
  
- **Phase 6**: Amélioration score mutation (avec Celestine)
  - Identification des mutants survivants
  - Ajout de tests boundary pour cas limites
  - Ajout de tests edge cases (null, vide, extrêmes)
  
- **Phase 10**: Documentation tests.md (à venir)
  - Stratégie de tests unitaires, intégration et mutation
  - Résultats détaillés avec métriques réelles
  - Documentation des analyses statiques

- Support qualité code et refactoring

---

## Validation

La somme des contributions est de **100%** et chaque membre a une contribution supérieure ou égale à la moyenne (33.33%).

Toutes les tâches ont été réparties équitablement selon les compétences de chaque membre:
- **Celestine**: Expert Maven et CI/CD
- **Myriam**: Spécialiste tests unitaires et documentation
- **Manuella**: Responsable tests d'intégration et qualité

---

**Date**: 29 novembre 2025

**Signatures**: Confirmé par tous les membres de l'équipe Sprindo
