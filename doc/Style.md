Guide de Style de Codage
#Projet Sprindo - Sprint 1
1. Langue

- Code : Français (classes, méthodes, variables)
- Commentaires : Français
- Justification : Projet francophone (UQAM), énoncé en français

2. Conventions de Nommage

. Classes

- Format : PascalCase
- Suffixes :`Impl` pour implémentations, `Vue` pour vues, `Controller` pour contrôleurs
- Exemples : `Projet`, `ProjetImpl`, `SprindoVue`, `SprindoController`

.  Méthodes

- Format : camelCase
- Début par un verbe : `ajouterProjet`, `getProjet`, `supprimerTache`
- Getters/Setters : `getNom()`, `setNom()`

. Variables et Attributs

- Format : camelCase
- Noms descriptifs : `nomUtilisateur`, `dateDebut`, `projets`
- Éviter : `nu`, `dd`, `p`

 . Constantes
- Format : MAJUSCULES_AVEC_UNDERSCORES
- Déclaration : `public static final`
- Exemples :`STATUT_A_FAIRE`, `STATUT_EN_COURS`, `STATUT_FAIT`



3. Formatage et Indentation

- Indentation : 4 espaces (pas de tabulations)
- Accolades : Ouvrante `{` sur la même ligne, fermante `}` sur nouvelle ligne
- Espacement : Un espace après les virgules et autour des opérateurs
- Ligne vide : Entre les méthodes



4. Organisation des Packages
Structure du projet :

src/model/
  Projet.java
  Sprint.java
  Tache.java
  Utilisateur.java

src/view/
  SprindoVue.java

src/controller/
  SprindoController.java

src/impl/
  ProjetImpl.java
  SprintImpl.java
  TacheImpl.java
  UtilisateurImpl.java

src/Main.java

Déclaration :Chaque fichier doit déclarer son package en première ligne



5. Commentaires

- Classes et méthodes publiques : Commentaires JavaDoc obligatoires
- Logique complexe : Commentaires inline pour clarifier
- Éviter : Commentaires évidents qui répètent le code


6. Gestion des Erreurs

### Validation des données
- Toujours valider les paramètres avant utilisation
- Vérifier null, vide, et valeurs invalides

### Messages
- Erreurs : Préfixe "Erreur : " + description claire
- Succès :  Message positif 


7. Structures de Données

- HashMap : Pour recherche rapide par clé (projets, sprints, tâches)
- ArrayList : Pour retourner des listes de résultats
- LocalDate : Pour les dates (pas String)


8. Git & Commits

- Un commit = une fonctionnalité ou correction
- Messages clairs et descriptifs
- Format :"Ajout de [fonctionnalité]" ou "Correction de [bug]"


9. Tests

- Tester toutes les méthodes critiques (manuellement ou unitaires)
- Cas normaux : Données valides
- Cas limites : Liste vide, ID inexistant, valeurs null
- Cas d'erreur : Doublons, données invalides




