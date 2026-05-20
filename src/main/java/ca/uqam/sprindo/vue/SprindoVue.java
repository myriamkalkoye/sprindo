package ca.uqam.sprindo.vue;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsable de l'affichage et de la saisie des données pour l'application Sprindo
 * (interface console). Elle gère les menus, les messages et les interactions avec l'utilisateur.
 */
public class SprindoVue {

  /**
   * Logger pour les messages de la vue.
   */
  private static final Logger LOGGER = Logger.getLogger(SprindoVue.class.getName());

  /**
   * Scanner utilisé pour lire les entrées saisies par l'utilisateur.
   */
  private final Scanner scanner;

  /**
   * Flux de sortie pour l'affichage (permet de remplacer System.out dans les tests).
   */
  private final PrintStream out;

  /**
   * Flux d'erreur pour l'affichage des erreurs.
   */
  private final PrintStream err;

  /**
   * Constructeur de la vue. Initialise le scanner pour la saisie au clavier.
   */
  public SprindoVue() {
    this(System.out, System.err);
  }

  /**
   * Constructeur avec injection des flux (pour les tests).
   *
   * @param out flux de sortie standard
   * @param err flux de sortie d'erreur
   */
  public SprindoVue(PrintStream out, PrintStream err) {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    this.out = out;
    this.err = err;
  }

  // ===== AFFICHAGE DES MENUS =====

  /**
   * Affiche le menu principal de l'application Sprindo.
   */
  public void afficherMenu() {
    out.println("\n====== SPRINDO - GESTION DE PROJETS AGILES ======");
    out.println("1. Ajouter un projet");
    out.println("2. Afficher la liste des projets");
    out.println("3. Ajouter un sprint à un projet");
    out.println("4. Afficher les sprints d'un projet");
    out.println("5. Ajouter une tâche à un sprint");
    out.println("6. Afficher les tâches d'un sprint");
    out.println("7. Assigner une tâche à un utilisateur");
    out.println("8. Modifier le statut d'une tâche");
    out.println("9. Supprimer une tâche");
    out.println("10. Afficher les statistiques");
    out.println("11. Quitter l'application");
    out.print("\nVotre choix : ");
  }

  // ===== LECTURE DES CHOIX UTILISATEUR =====

  /**
   * Lit le choix du menu saisi par l'utilisateur.
   *
   * @return le numéro du choix ou -1 si la saisie est invalide
   */
  public int lireChoixMenu() {
    try {
      int choix = scanner.nextInt();
      scanner.nextLine(); // Consommer le retour à la ligne
      return choix;
    } catch (Exception e) {
      scanner.nextLine(); // Nettoyer le buffer
      LOGGER.log(Level.WARNING, "Choix invalide saisi par l'utilisateur", e);
      return -1; // Choix invalide
    }
  }

  // ===== DEMANDES DE SAISIE - PROJET =====

  /**
   * Demande l'identifiant d'un projet.
   *
   * @return l'ID du projet
   */
  public String demanderIdProjet() {
    out.print("ID du projet : ");
    return scanner.nextLine();
  }

  /**
   * Demande le nom d'un projet.
   *
   * @return le nom du projet
   */
  public String demanderNomProjet() {
    out.print("Nom du projet : ");
    return scanner.nextLine();
  }

  /**
   * Demande la description d'un projet.
   *
   * @return la description du projet
   */
  public String demanderDescriptionProjet() {
    out.print("Description : ");
    return scanner.nextLine();
  }

  // ===== DEMANDES DE SAISIE - SPRINT =====

  /**
   * Demande l'identifiant d'un sprint.
   *
   * @return l'ID du sprint
   */
  public String demanderIdSprint() {
    out.print("ID du sprint : ");
    return scanner.nextLine();
  }

  /**
   * Demande le numéro d'un sprint.
   *
   * @return le numéro du sprint
   */
  public int demanderNumeroSprint() {
    out.print("Numéro du sprint : ");
    int numero = scanner.nextInt();
    scanner.nextLine();
    return numero;
  }

  /**
   * Demande l'objectif d'un sprint.
   *
   * @return l'objectif du sprint
   */
  public String demanderObjectifSprint() {
    out.print("Objectif : ");
    return scanner.nextLine();
  }

  /**
   * Demande la date de début du sprint.
   *
   * @return la date de début
   */
  public LocalDate demanderDateDebut() {
    out.print("Date de début (YYYY-MM-DD) : ");
    return LocalDate.parse(scanner.nextLine());
  }

  /**
   * Demande la date de fin du sprint.
   *
   * @return la date de fin
   */
  public LocalDate demanderDateFin() {
    out.print("Date de fin (YYYY-MM-DD) : ");
    return LocalDate.parse(scanner.nextLine());
  }

  // ===== DEMANDES DE SAISIE - TÂCHE =====

  /**
   * Demande l'identifiant d'une tâche.
   *
   * @return l'ID de la tâche
   */
  public String demanderIdTache() {
    out.print("ID de la tâche : ");
    return scanner.nextLine();
  }

  /**
   * Demande le titre d'une tâche.
   *
   * @return le titre de la tâche
   */
  public String demanderTitreTache() {
    out.print("Titre : ");
    return scanner.nextLine();
  }

  /**
   * Demande la description d'une tâche.
   *
   * @return la description de la tâche
   */
  public String demanderDescriptionTache() {
    out.print("Description : ");
    return scanner.nextLine();
  }

  /**
   * Demande le statut d'une tâche.
   *
   * @return le statut de la tâche
   */
  public String demanderStatutTache() {
    out.print("Statut (À faire / En cours / Terminé) : ");
    return scanner.nextLine();
  }

  /**
   * Demande le nom d'un utilisateur.
   *
   * @return le nom de l'utilisateur
   */
  public String demanderNomUtilisateur() {
    out.print("Nom de l'utilisateur : ");
    return scanner.nextLine();
  }

  // ===== AFFICHAGE DES DONNÉES =====

  /**
   * Affiche la liste des projets.
   *
   * @param projets liste des projets à afficher
   */
  public void afficherProjets(List<Projet> projets) {
    out.println("\n===== LISTE DES PROJETS =====");
    if (projets.isEmpty()) {
      out.println("Aucun projet disponible.");
    } else {
      for (Projet p : projets) {
        out.println(
            "ID: " + p.getId() + " | Nom: " + p.getNom() + " | Description: " + p.getDescription());
      }
    }
  }

  /**
   * Affiche les sprints d'un projet.
   *
   * @param sprints  liste des sprints
   * @param projetId identifiant du projet
   */
  public void afficherSprints(List<Sprint> sprints, String projetId) {
    out.println("\n===== SPRINTS DU PROJET " + projetId + " =====");
    if (sprints.isEmpty()) {
      out.println("Aucun sprint disponible pour ce projet.");
    } else {
      for (Sprint s : sprints) {
        out.println(
            "ID: "
                + s.getId()
                + " | Numéro: "
                + s.getNumero()
                + " | Objectif: "
                + s.getObjectif()
                + " | Début: "
                + s.getDebut()
                + " | Fin: "
                + s.getFin());
      }
    }
  }

  /**
   * Affiche les tâches d'un sprint.
   *
   * @param taches   liste des tâches
   * @param projetId identifiant du projet
   * @param sprintId identifiant du sprint
   */
  public void afficherTaches(List<Tache> taches, String projetId, String sprintId) {
    out.println("\n===== TÂCHES DU SPRINT " + sprintId + " (Projet " + projetId + ") =====");
    if (taches.isEmpty()) {
      out.println("Aucune tâche disponible pour ce sprint.");
    } else {
      for (Tache t : taches) {
        out.println(
            "ID: "
                + t.getId()
                + " | Titre: "
                + t.getTitre()
                + " | Description: "
                + t.getDescription()
                + " | Statut: "
                + t.getStatut()
                + " | Assigné à: "
                + (t.getAssigneA() != null ? t.getAssigneA() : "Non assigné"));
      }
    }
  }

  // ===== MESSAGES =====

  /**
   * Affiche un message informatif.
   *
   * @param message le message à afficher
   */
  public void afficherMessage(String message) {
    out.println(message);
    LOGGER.log(Level.INFO, message);
  }

  /**
   * Affiche un message d'erreur.
   *
   * @param erreur le message d'erreur
   */
  public void afficherErreur(String erreur) {
    err.println("ERREUR: " + erreur);
    LOGGER.log(Level.SEVERE, "ERREUR: {0}", erreur);
  }

  /**
   * Ferme le scanner proprement.
   */
  public void fermer() {
    scanner.close();
  }
}
