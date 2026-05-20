# Definition of Done (DoD) — Projet Sprindo

Une *user story* est considérée comme **terminée** lorsque toutes les conditions suivantes sont remplies :

---

## 1. Code et qualité
- Le code est écrit, compile sans erreur et respecte le style convenu (**Style.md**).
- Les noms sont clairs, l’indentation uniforme et les commentaires utiles.
- La séparation **MVC** est respectée :
    - **Modèle** : `Projet`, `Sprint`, `Tache`, `Utilisateur`
    - **Implémentations** : `ProjetImpl`, `SprintImpl`, `TacheImpl`, `UtilisateurImpl`
    - **Contrôleur** : `SprindoController`
    - **Vue** : `SprindoVue`

---

## 2. Fonctionnalité
- Tous les critères d’acceptation de la user story sont satisfaits.
- Les cas d’erreurs sont gérés proprement.
- La fonctionnalité est intégrée dans le **menu interactif** (console).

---

## 3. Tests et validation
- Les **tests manuels** définis pour la fonctionnalité passent.
- Si applicable, des **tests unitaires** basiques sont créés et réussis.
- Les **cas limites** sont vérifiés.

---

## 4. Intégration
- Le code est intégré dans la **branche principale (main)** sans conflits.
- Les commits sont clairs, atomiques et disponibles sur **GitLab**.
- La **merge request** a été revue et approuvée par au moins un membre de l’équipe.

---

## 5. Documentation
- La documentation associée est mise à jour :  
  `Equipe.md`, `DoR.md`, `DoD.md`
- Les classes et méthodes sont commentées.
- Les **captures d’écran d’exécution** sont ajoutées au rapport PDF.

---

## 6. Fonctionnement global
- L’ajout, l’affichage, la modification, l’assignation et la suppression fonctionnent comme attendu.
- La suppression d’une entité met à jour immédiatement la **vue** et les **données**.
- Quitter l’application fonctionne correctement (**sortie propre du menu**).

