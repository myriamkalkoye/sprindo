package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import ca.uqam.sprindo.vue.SprindoVue;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Contrôleur principal de l'application Sprindo. Gère la communication entre la vue et les
 * implémentations métier.
 */
public class SprindoController {

  // ===== CONSTANTES =====
  
  /** Message d'erreur pour un projet introuvable. */
  private static final String PROJET_INTROUVABLE = "Projet introuvable : ";
  
  /** Message d'erreur générique. */
  private static final String ERREUR_INATTENDUE = "Erreur inattendue : ";
  
  /** Choix menu : Oui. */
  private static final int CHOIX_OUI = 1;

  // ===== ATTRIBUTS =====
  
  /** Gestionnaire des projets. */
  private ProjetImpl projetImpl;
  
  /** Gestionnaire des sprints. */
  private SprintImpl sprintImpl;
  
  /** Gestionnaire des tâches. */
  private TacheImpl tacheImpl;
  
  /** Gestionnaire des utilisateurs. */
  private UtilisateurImpl utilisateurImpl;
  
  /** Interface de la vue utilisateur. */
  private SprindoVue vue;
  
  /** Service de génération de statistiques. */
  private Statistiques statistiques;

  /**
   * Constructeur du contrôleur principal.
   */
  public SprindoController() {
    this.projetImpl = new ProjetImpl();
    this.sprintImpl = new SprintImpl();
    this.tacheImpl = new TacheImpl();
    this.utilisateurImpl = new UtilisateurImpl();
    this.vue = new SprindoVue();

    // Initialisation du service de statistiques
    this.statistiques = new Statistiques(projetImpl, sprintImpl, tacheImpl, utilisateurImpl);
  }

  /**
   * Afficher les statistiques du projet ou d'un sprint.
   */
  @SuppressFBWarnings(value = "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE", 
                    justification = "La vérification null est nécessaire pour les tests unitaires")
  public void afficherStatistiques() {
    try {
      // Vérifier s'il y a des projets
      List<Projet> projets = projetImpl.getProjets();
      if (projets == null || projets.isEmpty()) {
        vue.afficherErreur("Aucun projet disponible. Veuillez d'abord créer un projet.");
        return;
      }

      // Demander quel projet
      vue.afficherMessage("\n📊 === MODULE STATISTIQUES ===");
      vue.afficherProjets(projets);
      String projetId = vue.demanderIdProjet();

      // Vérifier que le projet existe
      Projet projet = projetImpl.getProjet(projetId);
      if (projet == null) {
        vue.afficherErreur(PROJET_INTROUVABLE + projetId);
        return;
      }

      // Afficher le rapport du projet
      String rapportProjet = statistiques.genererRapportProjet(projetId);
      vue.afficherMessage(rapportProjet);

      // Demander si l'utilisateur veut voir un sprint spécifique
      List<Sprint> sprints = sprintImpl.getSprintsParProjet(projetId);
      if (!sprints.isEmpty()) {
        vue.afficherMessage("\nVoulez-vous voir les détails d'un sprint spécifique ?");
        vue.afficherMessage("1. Oui");
        vue.afficherMessage("2. Non");
        int choix = vue.lireChoixMenu();

        if (choix == CHOIX_OUI) {
          vue.afficherSprints(sprints, projetId);
          String sprintId = vue.demanderIdSprint();

          // Vérifier que le sprint existe
          if (sprintImpl.getSprint(projetId, sprintId) == null) {
            vue.afficherErreur("Sprint introuvable : " + sprintId);
            return;
          }

          // Afficher le rapport du sprint
          String rapportSprint = statistiques.genererRapportSprint(projetId, sprintId);
          vue.afficherMessage(rapportSprint);
        }
      }

    } catch (Exception e) {
      vue.afficherErreur("Erreur lors de l'affichage des statistiques : " + e.getMessage());
    }
  }

  /**
   * Ajouter un projet.
   */
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
      vue.afficherErreur(ERREUR_INATTENDUE + e.getMessage());
    }
  }

  /**
   * Afficher tous les projets.
   */
  public void afficherProjets() {
    try {
      List<Projet> projets = projetImpl.getProjets();
      vue.afficherProjets(projets);
    } catch (Exception e) {
      vue.afficherErreur("Erreur lors de l'affichage des projets : " + e.getMessage());
    }
  }

  /**
   * Ajouter un sprint à un projet.
   */
  public void ajouterSprint() {
    try {
      String projetId = vue.demanderIdProjet();

      if (projetImpl.getProjet(projetId) == null) {
        vue.afficherErreur(PROJET_INTROUVABLE + projetId);
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
      vue.afficherErreur(ERREUR_INATTENDUE + e.getMessage());
    }
  }

  /**
   * Afficher les sprints d'un projet.
   */
  public void afficherSprints() {
    try {
      String projetId = vue.demanderIdProjet();

      if (projetImpl.getProjet(projetId) == null) {
        vue.afficherErreur(PROJET_INTROUVABLE + projetId);
        return;
      }

      List<Sprint> sprints = sprintImpl.getSprintsParProjet(projetId);
      vue.afficherSprints(sprints, projetId);
    } catch (Exception e) {
      vue.afficherErreur("Erreur lors de l'affichage des sprints : " + e.getMessage());
    }
  }

  /**
   * Ajouter une tâche à un sprint.
   */
  public void ajouterTache() {
    try {
      String projetId = vue.demanderIdProjet();
      String sprintId = vue.demanderIdSprint();

      if (projetImpl.getProjet(projetId) == null) {
        vue.afficherErreur(PROJET_INTROUVABLE + projetId);
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
      vue.afficherErreur(ERREUR_INATTENDUE + e.getMessage());
    }
  }

  /**
   * Afficher les tâches d'un sprint.
   */
  public void afficherTaches() {
    try {
      String projetId = vue.demanderIdProjet();
      String sprintId = vue.demanderIdSprint();

      if (projetImpl.getProjet(projetId) == null) {
        vue.afficherErreur(PROJET_INTROUVABLE + projetId);
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

  /**
   * Assigner une tâche à un utilisateur.
   */
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
      vue.afficherErreur(ERREUR_INATTENDUE + e.getMessage());
    }
  }

  /**
   * Modifier le statut d'une tâche.
   */
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
      vue.afficherErreur(ERREUR_INATTENDUE + e.getMessage());
    }
  }

  /**
   * Supprimer une tâche.
   */
  public void supprimerTache() {
    try {
      String projetId = vue.demanderIdProjet();
      String sprintId = vue.demanderIdSprint();
      String tacheId = vue.demanderIdTache();

      tacheImpl.supprimerTache(projetId, sprintId, tacheId);
      vue.afficherMessage("✓ Tâche supprimée avec succès !");
    } catch (IllegalArgumentException e) {
      vue.afficherErreur(e.getMessage());
    } catch (Exception e) {
      vue.afficherErreur(ERREUR_INATTENDUE + e.getMessage());
    }
  }
}
