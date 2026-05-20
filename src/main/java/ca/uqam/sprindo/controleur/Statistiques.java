package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Gestion des statistiques du projet. Calcule les métriques sur les sprints, tâches et
 * développeurs.
 */
public class Statistiques {

  // ===== CONSTANTES =====
  
  /** Ligne de séparation pour les rapports. */
  private static final String SEPARATOR_LINE = 
      "┌───────────────────────────────────────────────────────┐%n";
  
  /** Ligne de séparation de fin pour les rapports. */
  private static final String SEPARATOR_END = 
      "═════════════════════════════════════════════════════%n";

  // ===== ATTRIBUTS =====
  
  /** Gestionnaire des projets. */
  private ProjetImpl projetImpl;
  
  /** Gestionnaire des sprints. */
  private SprintImpl sprintImpl;
  
  /** Gestionnaire des tâches. */
  private TacheImpl tacheImpl;
  
  /** Gestionnaire des utilisateurs. */
  private UtilisateurImpl utilisateurImpl;

  /**
   * Constructeur.
   *
   * @param projetImpl      Gestionnaire des projets
   * @param sprintImpl      Gestionnaire des sprints
   * @param tacheImpl       Gestionnaire des tâches
   * @param utilisateurImpl Gestionnaire des utilisateurs
   */
  public Statistiques(ProjetImpl projetImpl, SprintImpl sprintImpl, TacheImpl tacheImpl,
      UtilisateurImpl utilisateurImpl) {
    this.projetImpl = projetImpl;
    this.sprintImpl = sprintImpl;
    this.tacheImpl = tacheImpl;
    this.utilisateurImpl = utilisateurImpl;
  }

  /**
   * Calcule le nombre total de sprints d'un projet.
   *
   * @param projetId L'identifiant du projet
   * @return Le nombre de sprints
   */
  public int getNombreTotalSprints(String projetId) {
    if (projetId == null || projetId.trim().isEmpty()) {
      return 0;
    }

    List<Sprint> sprints = sprintImpl.getSprintsParProjet(projetId);
    return sprints != null ? sprints.size() : 0;
  }

  /**
   * Calcule le nombre total d'utilisateurs dans le système.
   *
   * @return Le nombre total d'utilisateurs
   */
  public int getNombreTotalUtilisateurs() {
    return utilisateurImpl.getUtilisateurs().size();
  }

  /**
   * Calcule le nombre de tâches dans un sprint donné.
   *
   * @param projetId L'identifiant du projet
   * @param sprintId L'identifiant du sprint
   * @return Le nombre de tâches
   */
  public int getNombreTachesDansSprint(String projetId, String sprintId) {
    if (projetId == null || projetId.trim().isEmpty() || sprintId == null || sprintId.trim()
        .isEmpty()) {
      return 0;
    }

    List<Tache> taches = tacheImpl.getTachesParSprint(projetId, sprintId);
    return taches.size();
  }

  /**
   * Calcule le nombre de développeurs actifs dans un sprint. Un développeur est actif s'il a au
   * moins une tâche assignée.
   *
   * @param projetId L'identifiant du projet
   * @param sprintId L'identifiant du sprint
   * @return Le nombre de développeurs actifs (uniques)
   */
  public int getNombreDeveloppeursActifs(String projetId, String sprintId) {
    if (projetId == null || projetId.trim().isEmpty() || sprintId == null || sprintId.trim()
        .isEmpty()) {
      return 0;
    }

    List<Tache> taches = tacheImpl.getTachesParSprint(projetId, sprintId);
    if (taches.isEmpty()) {
      return 0;
    }

    // Utiliser un Set pour éviter les doublons
    Set<String> developpeursActifs = new HashSet<>();

    for (Tache tache : taches) {
      String assigneA = tache.getAssigneA();
      // Ne compter que les tâches assignées (non null et non vide)
      if (assigneA != null && !assigneA.trim().isEmpty()) {
        developpeursActifs.add(assigneA.trim().toLowerCase(Locale.ROOT));
      }
    }

    return developpeursActifs.size();
  }

  /**
   * Calcule le pourcentage de tâches terminées dans un sprint.
   *
   * @param projetId L'identifiant du projet
   * @param sprintId L'identifiant du sprint
   * @return Le pourcentage (0-100)
   */
  public double getPourcentageTachesTerminees(String projetId, String sprintId) {
    List<Tache> taches = tacheImpl.getTachesParSprint(projetId, sprintId);
    if (taches.isEmpty()) {
      return 0.0;
    }

    long tachesTerminees = taches.stream().filter(
        t -> "Terminé".equalsIgnoreCase(t.getStatut()) || "Terminee".equalsIgnoreCase(
            t.getStatut())).count();

    return (tachesTerminees * 100.0) / taches.size();
  }

  /**
   * Génère un rapport formaté des statistiques d'un projet.
   *
   * @param projetId L'identifiant du projet
   * @return Chaîne contenant le rapport formaté
   */
  public String genererRapportProjet(String projetId) {
    Projet projet = projetImpl.getProjet(projetId);
    if (projet == null) {
      return "❌ Projet introuvable : " + projetId;
    }

    StringBuilder rapport = new StringBuilder();
    rapport.append("\n╔═══════════════════════════════════════════════════════╗%n");
    rapport.append("  ║         STATISTIQUES DU PROJET                             ║%n");
    rapport.append("  ╚═══════════════════════════════════════════════════════╝%n%n");

    rapport.append("Projet : ").append(projet.getNom()).append("\n");
    rapport.append("ID : ").append(projet.getId()).append("\n");
    rapport.append("Description : ").append(projet.getDescription()).append("%n%n");

    rapport.append(SEPARATOR_LINE);
    rapport.append("│ STATISTIQUES GLOBALES                                   │%n");
    rapport.append("└───────────────────────────────────────────────────────┘%n");

    int nbSprints = getNombreTotalSprints(projetId);
    int nbUtilisateurs = getNombreTotalUtilisateurs();

    rapport.append(String.format("  • Nombre total de sprints      : %d%n", nbSprints));
    rapport.append(String.format("  • Nombre total d'utilisateurs  : %d%n%n", nbUtilisateurs));

    // Détails par sprint
    List<Sprint> sprints = sprintImpl.getSprintsParProjet(projetId);
    if (sprints != null && !sprints.isEmpty()) {
      rapport.append(SEPARATOR_LINE);
      rapport.append("│ DÉTAILS PAR SPRINT                                      │%n");
      rapport.append("└───────────────────────────────────────────────────────┘%n%n");

      for (Sprint sprint : sprints) {
        rapport.append("  ▶ Sprint ").append(sprint.getNumero()).append(" : ")
            .append(sprint.getObjectif()).append("%n");
        rapport.append("    ├─ ID                    : ").append(sprint.getId()).append("%n");
        rapport.append("    ├─ Période               : ").append(sprint.getDebut()).append(" → ")
            .append(sprint.getFin()).append("\n");

        int nbTaches = getNombreTachesDansSprint(projetId, sprint.getId());
        rapport.append(String.format("    ├─ Nombre de tâches      : %d%n", nbTaches));

        int nbDevActifs = getNombreDeveloppeursActifs(projetId, sprint.getId());
        rapport.append(String.format("    ├─ Développeurs actifs   : %d%n", nbDevActifs));

        double pourcentageTermine = getPourcentageTachesTerminees(projetId, sprint.getId());
        rapport.append(
            String.format("    └─ Progression           : %.1f%% terminé%n%n", pourcentageTermine));
      }
    } else {
      rapport.append("  ℹ️  Aucun sprint dans ce projet.%n%n");
    }

    rapport.append(SEPARATOR_END);

    return rapport.toString();
  }

  /**
   * Génère un rapport détaillé pour un sprint spécifique.
   *
   * @param projetId L'identifiant du projet
   * @param sprintId L'identifiant du sprint
   * @return Chaîne contenant le rapport formaté
   */
  public String genererRapportSprint(String projetId, String sprintId) {
    Sprint sprint = sprintImpl.getSprint(projetId, sprintId);
    if (sprint == null) {
      return "❌ Sprint introuvable : " + sprintId;
    }

    StringBuilder rapport = new StringBuilder();
    rapport.append("\n╔═══════════════════════════════════════════════════════╗%n");
    rapport.append("  ║         STATISTIQUES DU SPRINT                             ║%n");
    rapport.append("  ╚═══════════════════════════════════════════════════════╝%n%n");

    rapport.append("Sprint : ").append(sprint.getObjectif()).append("\n");
    rapport.append("Numéro : ").append(sprint.getNumero()).append("\n");
    rapport.append("Période : ").append(sprint.getDebut()).append(" → ").append(sprint.getFin())
        .append("\n\n");

    int nbTaches = getNombreTachesDansSprint(projetId, sprintId);
    int nbDevActifs = getNombreDeveloppeursActifs(projetId, sprintId);
    double pourcentageTermine = getPourcentageTachesTerminees(projetId, sprintId);

    rapport.append(SEPARATOR_LINE);
    rapport.append("│ MÉTRIQUES                                               │%n");
    rapport.append("└───────────────────────────────────────────────────────┘%n");
    rapport.append(String.format("  • Nombre total de tâches       : %d%n", nbTaches));
    rapport.append(String.format("  • Développeurs actifs          : %d%n", nbDevActifs));
    rapport.append(
        String.format("  • Progression                  : %.1f%% terminé%n%n", pourcentageTermine));

    // Détails des tâches par statut
    List<Tache> taches = tacheImpl.getTachesParSprint(projetId, sprintId);
    if (!taches.isEmpty()) {
      rapport.append(SEPARATOR_LINE);
      rapport.append("│ RÉPARTITION PAR STATUT                                  │%n");
      rapport.append("└───────────────────────────────────────────────────────┘%n");

      // Compter par statut
      long afaire = taches.stream().filter(
          t -> "À faire".equalsIgnoreCase(t.getStatut()) || "A faire".equalsIgnoreCase(
              t.getStatut())).count();
      long enCours = taches.stream().filter(t -> "En cours".equalsIgnoreCase(t.getStatut()))
          .count();
      long termine = taches.stream().filter(
          t -> "Terminé".equalsIgnoreCase(t.getStatut()) || "Terminee".equalsIgnoreCase(
              t.getStatut())).count();
      long bloque = taches.stream()
          .filter(t -> "Bloqué".equalsIgnoreCase(t.getStatut())
              || "Bloque".equalsIgnoreCase(t.getStatut()))
          .count();

      rapport.append(String.format("  • À faire                      : %d%n", afaire));
      rapport.append(String.format("  • En cours                     : %d%n", enCours));
      rapport.append(String.format("  • Terminé                      : %d%n", termine));
      rapport.append(String.format("  • Bloqué                       : %d%n%n", bloque));
    }

    rapport.append(SEPARATOR_END);

    return rapport.toString();
  }
}
