package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import ca.uqam.sprindo.modele.Utilisateur;

import ca.uqam.sprindo.vue.SprindoVue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Contrôleur principal de l'application Sprindo.
 * Gère la communication entre la vue et les implémentations métier.
 */
public class SprindoController {

    private ProjetImpl projetImpl;
    private SprintImpl sprintImpl;
    private TacheImpl tacheImpl;
    private UtilisateurImpl utilisateurImpl;
    private SprindoVue vue;

    /** Constructeur du contrôleur principal. */
    public SprindoController() {
        this.projetImpl = new ProjetImpl();
        this.sprintImpl = new SprintImpl();
        this.tacheImpl = new TacheImpl();
        this.utilisateurImpl = new UtilisateurImpl();
        this.vue = new SprindoVue();
    }

    /** Ajouter un projet. */
    public void ajouterProjet() {
        try {
            String id = vue.demanderIdProjet();
            String nom = vue.demanderNomProjet();
            String description = vue.demanderDescriptionProjet();

            Projet projet = new Projet(id, nom, description);
            projetImpl.ajouterProjet(projet);
            vue.afficherMessage("✓ Projet ajouté avec succès !");
        } catch (IllegalArgumentException e) {
            vue.afficherErreur(e.getMessage());
        } catch (Exception e) {
            vue.afficherErreur("Erreur inattendue : " + e.getMessage());
        }
    }

    /** Afficher tous les projets. */
    public void afficherProjets() {
        try {
            List<Projet> projets = projetImpl.getProjets();
            vue.afficherProjets(projets);
        } catch (Exception e) {
            vue.afficherErreur("Erreur lors de l'affichage des projets : " + e.getMessage());
        }
    }

    /** Ajouter un sprint à un projet. */
    public void ajouterSprint() {
        try {
            String projetId = vue.demanderIdProjet();

            if (projetImpl.getProjet(projetId) == null) {
                vue.afficherErreur("Projet introuvable : " + projetId);
                return;
            }

            String sprintId = vue.demanderIdSprint();
            int numero = vue.demanderNumeroSprint();
            String objectif = vue.demanderObjectifSprint();
            LocalDate debut = vue.demanderDateDebut();
            LocalDate fin = vue.demanderDateFin();

            Sprint sprint = new Sprint(sprintId, numero, objectif, debut, fin);
            sprintImpl.ajouterSprint(projetId, sprint);
            vue.afficherMessage("✓ Sprint ajouté avec succès !");
        } catch (IllegalArgumentException e) {
            vue.afficherErreur(e.getMessage());
        } catch (DateTimeParseException e) {
            vue.afficherErreur("Format de date invalide. Utilisez YYYY-MM-DD.");
        } catch (Exception e) {
            vue.afficherErreur("Erreur inattendue : " + e.getMessage());
        }
    }

    /** Afficher les sprints d'un projet. */
    public void afficherSprints() {
        try {
            String projetId = vue.demanderIdProjet();

            if (projetImpl.getProjet(projetId) == null) {
                vue.afficherErreur("Projet introuvable : " + projetId);
                return;
            }

            List<Sprint> sprints = sprintImpl.getSprintsParProjet(projetId);
            vue.afficherSprints(sprints, projetId);
        } catch (Exception e) {
            vue.afficherErreur("Erreur lors de l'affichage des sprints : " + e.getMessage());
        }
    }

    /** Ajouter une tâche à un sprint. */
    public void ajouterTache() {
        try {
            String projetId = vue.demanderIdProjet();
            String sprintId = vue.demanderIdSprint();

            if (projetImpl.getProjet(projetId) == null) {
                vue.afficherErreur("Projet introuvable : " + projetId);
                return;
            }

            if (sprintImpl.getSprint(projetId, sprintId) == null) {
                vue.afficherErreur("Sprint introuvable : " + sprintId);
                return;
            }

            String tacheId = vue.demanderIdTache();
            String titre = vue.demanderTitreTache();
            String description = vue.demanderDescriptionTache();
            String statut = vue.demanderStatutTache();

            Tache tache = new Tache(tacheId, titre, description, statut, null);
            tacheImpl.ajouterTache(projetId, sprintId, tache);
            vue.afficherMessage("✓ Tâche ajoutée avec succès !");
        } catch (IllegalArgumentException e) {
            vue.afficherErreur(e.getMessage());
        } catch (Exception e) {
            vue.afficherErreur("Erreur inattendue : " + e.getMessage());
        }
    }

    /** Afficher les tâches d'un sprint. */
    public void afficherTaches() {
        try {
            String projetId = vue.demanderIdProjet();
            String sprintId = vue.demanderIdSprint();

            if (projetImpl.getProjet(projetId) == null) {
                vue.afficherErreur("Projet introuvable : " + projetId);
                return;
            }

            if (sprintImpl.getSprint(projetId, sprintId) == null) {
                vue.afficherErreur("Sprint introuvable : " + sprintId);
                return;
            }

            List<Tache> taches = tacheImpl.getTachesParSprint(projetId, sprintId);
            vue.afficherTaches(taches, projetId, sprintId);
        } catch (Exception e) {
            vue.afficherErreur("Erreur lors de l'affichage des tâches : " + e.getMessage());
        }
    }

    /** Assigner une tâche à un utilisateur. */
    public void assignerTache() {
        try {
            String projetId = vue.demanderIdProjet();
            String sprintId = vue.demanderIdSprint();
            String tacheId = vue.demanderIdTache();
            String utilisateur = vue.demanderNomUtilisateur();

            tacheImpl.assignerTache(projetId, sprintId, tacheId, utilisateur);
            vue.afficherMessage("✓ Tâche assignée avec succès à " + utilisateur + " !");
        } catch (IllegalArgumentException e) {
            vue.afficherErreur(e.getMessage());
        } catch (Exception e) {
            vue.afficherErreur("Erreur inattendue : " + e.getMessage());
        }
    }

    /** Modifier le statut d'une tâche. */
    public void modifierStatutTache() {
        try {
            String projetId = vue.demanderIdProjet();
            String sprintId = vue.demanderIdSprint();
            String tacheId = vue.demanderIdTache();
            String nouveauStatut = vue.demanderStatutTache();

            tacheImpl.changerStatut(projetId, sprintId, tacheId, nouveauStatut);
            vue.afficherMessage("✓ Statut modifié avec succès !");
        } catch (IllegalArgumentException e) {
            vue.afficherErreur(e.getMessage());
        } catch (Exception e) {
            vue.afficherErreur("Erreur inattendue : " + e.getMessage());
        }
    }

    /** Supprimer une tâche. */
    public void supprimerTache() {
        try:
            String projetId = vue.demanderIdProjet();
            String sprintId = vue.demanderIdSprint();
            String tacheId = vue.demanderIdTache();

            tacheImpl.supprimerTache(projetId, sprintId, tacheId);
            vue.afficherMessage("✓ Tâche supprimée avec succès !");
        } catch (IllegalArgumentException e) {
            vue.afficherErreur(e.getMessage());
        } catch (Exception e) {
            vue.afficherErreur("Erreur inattendue : " + e.getMessage());
        }
    }
}
