# Equipe Synergie4 du projet SPRINDO

## Membres
- **Myriam Kalkoye Soumana dit Fodo** - Rôle : Product Owner (PO)
- **Celestine Djeukam Tchouyomgou**   - Rôle : Scrum Master (SM)
- **Manuella Eyram Koda**             - Rôle : Développeur
- **Emmanuel Samassi Habib Karamoko** - Rôle : Développeur


## Charte d'équipe
- **Langage utilisé**    : Java  
- **Convention de code** : voir `Style.md`  
- **Communication**      : Teams 
- **Gestion de projet**  : JIRA (Scrum Board)  
- **Versionnement**      : GitLab (branche par fonctionnalité)


## Répartition générale des responsabilités
- **PO   Myriam Kalkoye**    : Gestion du backlog, rédaction des user stories.  
- **SM   Celestine Djeukam** : Organisation des réunions, suivi Scrum, merge requests.  
- **Dev1 Manuella Koda**     : Développement des classes liées aux Tâches (US-05 à US-09).  
- **Dev2 Emmanuel Samassi**  : Développement des classes liées aux Utilisateurs et de la Vue (US-10 + Main.java).


## Répartition détaillée des tâches

- **PO   Myriam Kalkoye**    : 
    - Créer le projet SPRINDO dans JIRA et inviter les membres de l'équipe à le rejoindre;
    - Créer le Board JIRA et gérer le Backlog JIRA; 
    - Rédiger les User Stories 1 à 3 dans JIRA (Ajouter un projet; Afficher la liste des projets; Ajouter un sprint à un projet);
    - Rédiger le document <DoD.md> ;
    - Créer la classe <Projet> et implémenter <ProjetImpl.java>;
    - Créer <SprindoController.java> (coordination entre Impl et Vue);
    - Créer des Merge Requests;
    - Faire des tests manuels de chaque fonctionnalité.

- **SM   Celestine Djeukam** : 
    - Organiser les réunions et les mélées, assurer le suivi Scrum ;
    - Créer le compte JIRA et rejoindre le projet SPRINDO;
    - Créer le dépôt Gitlab, initialiser la structure, inviter les membres et configurer la sécurité (en collaboration avec le Dev1);
    - Rédiger les User Stories 9 et 10 dans JIRA (Supprimer une tâche; Quitter l'application);
    - Rédiger le document <Equipe.md> ;
    - Créer la classe <Sprint> et implémenter <SprintImpl.java>;
    - Créer <Main.java> avec menu interactif (switch);
    - Créer des Merge Requests;
    - Faire des tests manuels de chaque fonctionnalité.
    
    
- **Dev1 Manuella Koda**     : 
    - Créer son compte JIRA et rejoindre le projet SPRINDO;
    - Créer le dépôt Gitlab, initialiser la structure, inviter les membres et configurer la sécurité (en collaboration avec le SM);
    - Rédiger les User Stories 4 à 6 dans JIRA (Afficher les sprints d'un projet; Ajouter une tâche à un sprint; Afficher les tâches d'un sprint);
    - Rédiger le document <DoR.md> ;
    - Créer la classe <Tache> et implémenter <TacheImpl.java>;
    - Créer <SprindoVue.java>;
    - Créer des Merge Requests;
    - Faire des tests manuels de chaque fonctionnalité.
    
- **Dev2 Emmanuel Samassi**  : 
    - Créer son compte JIRA et rejoindre le projet SPRINDO;
    - Rédiger les User Stories 7 et 8 dans JIRA (Assigner une tâche à un utilisateur; Modifier le statut d'une tâche);
    - Rédiger le document style.md;
    - Créer la classe <Utilisateur> et implémenter <UtilisateurImpl.java>;
    - Rédiger le rapport final;
    - Créer des Merge Requests;
    - Faire des tests manuels de chaque fonctionnalité.


## Engagements
- Chaque membre participe activement.
- Chaque membre doit effectuer au moins une des tâches de chaque rôle
- Les commits Git doivent être clairs et fréquents.  
- Les merge requests sont relues par au moins 1 autre membre.  
- Respect des deadlines fixées par le Sprint.  

#EQUIPE.MD DU SPRINT 2
 
# Equipe.md – Composition et contributions de l'équipe

**Équipe** : Synergie 4 
**Projet** : Sprindo 
**Sprint** : Sprint 2 – Tests, Build et Analyse Statique 
**Session** : Automne 2025 
**Cours** : INF2050 – Outils et pratiques de développement logiciel

---

##  Composition de l'équipe

### Celestine Djeukam Tchouyomgou - Product Owner (PO)
Responsable de la priorisation des users stories, de la validation des critères d'acceptation et de la communication avec les parties prenantes. Participation active au développement technique.

### Myriam Kalkoye Soumana - Scrum Master (SM) 
Responsable de l'animation des cérémonies Scrum, de la gestion du board JIRA, de la facilitation de la collaboration et de la suppression des obstacles. Participation active au développement technique.

### Manuella Koda Eyram - Développeuse
Responsable de l'implémentation des tests unitaires, de l'intégration des outils de qualité et du respect des bonnes pratiques de développement.

---

## Répartition des tâches – Sprint 2

### Myriam Kalkoye Soumana (Scrum Master)

**User Stories assignées :**
- Mise en place des fonctionnalités de statistiques
- Documentation du projet

**Tâches réalisées :**

**Gestion JIRA**
- Création du board Sprint 2
- Invitation des membres de l'équipe
- Création et gestion des users stories

**Module Statistiques**
- Développement de la classe `Statistiques.java`
- Calcul du nombre total de sprints et d'utilisateurs du projet
- Calcul du nombre de tâches par sprint
- Calcul du nombre de développeurs actifs par sprint (basé sur les affectations)
- Création de l'interface console pour consulter les statistiques
- Garantie de la mise à jour automatique des statistiques après modifications

**Menu interactif**
- Mise à jour de `Main.java` pour ajouter l'option "Afficher les statistiques"
- Intégration du point d'accès aux statistiques dans le menu principal

**Documentation**
- Rédaction complète de `build.md` (structure Maven, commandes, instructions d'exécution)
- Rédaction complète de `qualite.md` (Checkstyle, analyse statique, corrections)
- Rédaction de `Equipe.md` (présent document)


**Gestion Git**
- Création et gestion de certaines merge requests
- Intégration de certaines branches de fonctionnalités

---

### Celestine Djeukam Tchouyomgou (Product Owner)

**User Stories assignées :**
- Configuration Maven
- Intégration de Checkstyle
- Correction des violations Checkstyle

**Tâches réalisées :**

**Configuration Maven**
- Création et configuration complète du fichier `pom.xml`
- Structuration du projet selon les standards Maven (`src/main/java`, `src/test/java`)
- Intégration du plugin `maven-compiler-plugin` pour la compilation Java 
- Intégration du plugin `maven-surefire-plugin` pour l'exécution des tests
- Intégration du plugin `maven-jar-plugin` pour la création du JAR exécutable
- Intégration du plugin `exec-maven-plugin` pour l'exécution directe

**Intégration Checkstyle**
- Configuration du plugin `maven-checkstyle-plugin` version 3.3.0 avec le profil google
- Installation et configuration du plugin Checkstyle-IDEA dans IntelliJ
- Configuration du fichier `google_checks.xml` pour les règles de style
- Ajout du plugin Spotless  pour le formatage automatique du code

**Résolution des violations Checkstyle**
- Analyse initiale : détection de 712 violations
- Correction automatique via `mvn spotless:apply` (environ 60% des violations)
- Correction manuelle des violations restantes (environ 40%)
- Ajout de Javadoc sur toutes les classes et méthodes publiques
- Correction des problèmes d'indentation (passage de 4 à 2 espaces selon Google Style)
- Remplacement des imports avec astérisque par des imports explicites
- Découpage des lignes dépassant 100 caractères
- Ajout des accolades manquantes
- Résultat final : 0 violation

**Développement**
- Contributions aux classes du modèle et du contrôleur
- Respect des conventions de codage Google
- Création et gestion de certaines merge requests
- Intégration de certaines branches de fonctionnalités

---

### Manuella Koda Eyram (Développeuse)

**User Stories assignées :**
- Implémentation des tests unitaires
- Intégration JaCoCo

**Tâches réalisées :**

**Tests unitaires JUnit 4**
- Création de `ProjetImplTest.java` : tests complets des fonctionnalités de gestion de projets
- Création de `SprintImplTest.java` : tests complets des fonctionnalités de gestion de sprints
- Création de `TacheImplTest.java` : tests complets des fonctionnalités de gestion de tâches
- Création de `UtilisateurImplTest.java` : tests complets des fonctionnalités de gestion d'utilisateurs
- Création de tests pour les fonctionnalités de statistiques
- Application des bonnes pratiques : nommage clair des méthodes de test, assertions précises, isolation des tests
- Tests des cas nominaux et des cas d'erreur

**Intégration JaCoCo**
- Configuration complète du plugin `jacoco-maven-plugin` dans `pom.xml`
- Configuration de l'agent JaCoCo pour la collecte des données de couverture
- Configuration de la génération automatique des rapports après l'exécution des tests
- Configuration de la vérification du seuil minimum de couverture (70%)
- Génération des rapports HTML de couverture dans `target/site/jacoco/index.html`
- Vérification et documentation de l'atteinte des objectifs de couverture
- Documentation de l'emplacement des rapports XML et HTML

---

## Outils et technologies utilisés

L'équipe a utilisé les outils et technologies suivants pour le Sprint 2 :

**Langage et environnement**
- Java 17 comme langage principal
- Maven 3.6+ pour la gestion du build et des dépendances
- IntelliJ IDEA Ultimate comme environnement de développement

**Qualité et tests**
- JUnit pour les tests unitaires
- JaCoCo pour la mesure de couverture de code
- Checkstyle pour l'analyse statique du code
- Spotless pour le formatage automatique selon Google Java Style

**Gestion de projet**
- GitLab pour la gestion de versions et les merge requests
- JIRA pour la gestion agile des user stories et du sprint

---

## Rôles Scrum et participation active

### Product Owner : Celestine Djeukam Tchouyomgou

En tant que Product Owner, Celestine a assumé les responsabilités suivantes :
- Priorisation des user stories en fonction de la valeur métier
- Validation des critères d'acceptation pour chaque fonctionnalité
- Communication avec les parties prenantes du projet
- Définition de la vision du produit

En plus de ces responsabilités, conformément aux exigences du projet, Celestine a participé activement au développement en prenant en charge la configuration complète de Maven, l'intégration de Checkstyle et la correction exhaustive de toutes les violations de style détectées.

### Scrum Master : Myriam Kalkoye Soumana

En tant que Scrum Master, Myriam a assumé les responsabilités suivantes :
- Animation des cérémonies Scrum et coordination de l'équipe
- Gestion du board JIRA et suivi de l'avancement du sprint
- Facilitation de la collaboration entre les membres
- Identification et suppression des obstacles rencontrés
- Promotion des bonnes pratiques agiles

En plus de ces responsabilités, conformément aux exigences du projet, Myriam a participé activement au développement en prenant en charge l'intégralité du module de statistiques, la mise à jour du menu interactif, la gestion des merge requests Git et la rédaction complète de la documentation technique du projet.

### Développeuse : Manuella Koda Eyram

Manuella s'est concentrée sur l'implémentation des tests unitaires et l'intégration de la couverture de code. Elle a créé une suite complète de tests pour les quatre classes principales du projet, en respectant les bonnes pratiques de test. Elle a également configuré JaCoCo pour mesurer et suivre la couverture de code, en veillant à atteindre le seuil minimum de 70%.

---

## Conclusion

Tous les membres de l'équipe Synergie 4 ont participé activement au Sprint 2, en respectant leurs rôles respectifs tout en contribuant significativement au développement technique. Le Product Owner et le Scrum Master ont assumé des tâches de développement substantielles, conformément aux exigences du projet.

Les objectifs du Sprint 2 ont été atteints à 100% :
- Maven configuré et fonctionnel avec tous les plugins nécessaires
- Tests unitaires implémentés pour les 4 classes principales avec bonne couverture
- Checkstyle intégré avec succès et 712 violations corrigées (0 restante)
- JaCoCo configuré avec génération automatique des rapports de couverture
- Module de statistiques développé et intégré au menu interactif
- Documentation technique complète rédigée


Le travail d'équipe, la rigueur technique et l'engagement de chaque membre ont permis de livrer un sprint de qualité professionnelle.

---
