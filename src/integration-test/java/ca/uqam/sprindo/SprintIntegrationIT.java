package ca.uqam.sprindo;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import ca.uqam.sprindo.controleur.ProjetImpl;
import ca.uqam.sprindo.controleur.SprintImpl;
import ca.uqam.sprindo.controleur.TacheImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests d'intégration pour la gestion des sprints et tâches.
 */
public class SprintIntegrationIT {

  private ProjetImpl projetImpl;
  private SprintImpl sprintImpl;
  private TacheImpl tacheImpl;

  @Before
  public void setUp() {
    projetImpl = new ProjetImpl();
    sprintImpl = new SprintImpl();
    tacheImpl = new TacheImpl();
  }

  /**
   * Test 1 : Ajouter plusieurs tâches à un sprint et calculer des statistiques.
   */
  @Test
  public void testAjouterTachesEtCalculerStatistiques() {
    // Créer le projet et le sprint
    Projet projet = new Projet("P1", "Projet Stats", "Test statistiques");
    projetImpl.ajouterProjet(projet);

    Sprint sprint = new Sprint("S1", 1, "Sprint Statistiques", LocalDate.of(2025, 11, 1),
        LocalDate.of(2025, 11, 15));
    sprintImpl.ajouterSprint("P1", sprint);

    // Ajouter des tâches avec différents statuts
    Tache t1 = new Tache("T1", "Tâche 1", "Description 1", "À faire", null);
    Tache t2 = new Tache("T2", "Tâche 2", "Description 2", "En cours", null);
    Tache t3 = new Tache("T3", "Tâche 3", "Description 3", "En cours", null);
    Tache t4 = new Tache("T4", "Tâche 4", "Description 4", "Terminée", null);

    tacheImpl.ajouterTache("P1", "S1", t1);
    tacheImpl.ajouterTache("P1", "S1", t2);
    tacheImpl.ajouterTache("P1", "S1", t3);
    tacheImpl.ajouterTache("P1", "S1", t4);

    // Vérifications
    List<Tache> taches = tacheImpl.getTachesParSprint("P1", "S1");
    assertEquals("Le sprint devrait avoir 4 tâches", 4, taches.size());

    // Calculer les statistiques
    long tachesAFaire = taches.stream().filter(t -> "À faire".equals(t.getStatut())).count();
    long tachesEnCours = taches.stream().filter(t -> "En cours".equals(t.getStatut())).count();
    long tachesTerminees = taches.stream().filter(t -> "Terminée".equals(t.getStatut())).count();

    assertEquals("Il devrait y avoir 1 tâche à faire", 1, tachesAFaire);
    assertEquals("Il devrait y avoir 2 tâches en cours", 2, tachesEnCours);
    assertEquals("Il devrait y avoir 1 tâche terminée", 1, tachesTerminees);
  }

  /**
   * Test 2 : Assigner plusieurs développeurs et compter les tâches par développeur.
   */
  @Test
  public void testAssignerPlusieursUtilisateurs() {
    // Créer le projet et le sprint
    Projet projet = new Projet("P2", "Projet Assignations", "Test assignations");
    projetImpl.ajouterProjet(projet);

    Sprint sprint = new Sprint("S1", 1, "Sprint Assignations", LocalDate.of(2025, 11, 1),
        LocalDate.of(2025, 11, 15));
    sprintImpl.ajouterSprint("P2", sprint);

    // Créer des tâches
    Tache t1 = new Tache("T1", "Créer API REST", "Backend", "À faire", null);
    Tache t2 = new Tache("T2", "Créer interface", "Frontend", "À faire", null);
    Tache t3 = new Tache("T3", "Documentation", "Docs", "À faire", null);

    tacheImpl.ajouterTache("P2", "S1", t1);
    tacheImpl.ajouterTache("P2", "S1", t2);
    tacheImpl.ajouterTache("P2", "S1", t3);

    // Assigner les tâches
    tacheImpl.assignerTache("P2", "S1", "T1", "Alice");
    tacheImpl.assignerTache("P2", "S1", "T2", "Bob");
    tacheImpl.assignerTache("P2", "S1", "T3", "Alice");

    // Vérifications
    List<Tache> taches = tacheImpl.getTachesParSprint("P2", "S1");

    long tachesAlice = taches.stream().filter(t -> "Alice".equals(t.getAssigneA())).count();
    long tachesBob = taches.stream().filter(t -> "Bob".equals(t.getAssigneA())).count();

    assertEquals("Alice devrait avoir 2 tâches", 2, tachesAlice);
    assertEquals("Bob devrait avoir 1 tâche", 1, tachesBob);
  }

  /**
   * Test 3 : Supprimer une tâche d'un sprint.
   */
  @Test
  public void testSupprimerTache() {
    // Créer le projet et le sprint
    Projet projet = new Projet("P3", "Projet Suppression", "Test suppression");
    projetImpl.ajouterProjet(projet);

    Sprint sprint = new Sprint("S1", 1, "Sprint Suppression", LocalDate.of(2025, 11, 1),
        LocalDate.of(2025, 11, 15));
    sprintImpl.ajouterSprint("P3", sprint);

    // Ajouter des tâches
    Tache t1 = new Tache("T1", "Tâche à garder 1", "Description 1", "À faire", null);
    Tache t2 = new Tache("T2", "Tâche à supprimer", "Description 2", "À faire", null);
    Tache t3 = new Tache("T3", "Tâche à garder 2", "Description 3", "À faire", null);

    tacheImpl.ajouterTache("P3", "S1", t1);
    tacheImpl.ajouterTache("P3", "S1", t2);
    tacheImpl.ajouterTache("P3", "S1", t3);

    // Vérifier qu'on a 3 tâches
    assertEquals("Le sprint devrait avoir 3 tâches", 3,
        tacheImpl.getTachesParSprint("P3", "S1").size());

    // Supprimer T2
    tacheImpl.supprimerTache("P3", "S1", "T2");

    // Vérifications
    List<Tache> tachesRestantes = tacheImpl.getTachesParSprint("P3", "S1");
    assertEquals("Le sprint ne devrait plus avoir que 2 tâches", 2, tachesRestantes.size());

    assertNotNull("T1 devrait toujours exister", tacheImpl.getTache("P3", "S1", "T1"));
    assertNull("T2 ne devrait plus exister", tacheImpl.getTache("P3", "S1", "T2"));
    assertNotNull("T3 devrait toujours exister", tacheImpl.getTache("P3", "S1", "T3"));
  }

  /**
   * Test 4 : Workflow complet d'un sprint avec calcul de progression.
   */
  @Test
  public void testWorkflowCompletSprint() {
    // Créer le projet
    Projet projet = new Projet("P4", "Workflow Sprint", "Test workflow complet");
    projetImpl.ajouterProjet(projet);

    // Créer un sprint
    Sprint sprint = new Sprint("S1", 1, "Sprint MVP", LocalDate.of(2025, 12, 1),
        LocalDate.of(2025, 12, 15));
    sprintImpl.ajouterSprint("P4", sprint);

    // Ajouter 5 tâches
    for (int i = 1; i <= 5; i++) {
      Tache t = new Tache("T" + i, "Tâche " + i, "Description " + i, "À faire", null);
      tacheImpl.ajouterTache("P4", "S1", t);
    }

    // Assigner les tâches
    tacheImpl.assignerTache("P4", "S1", "T1", "Dev1");
    tacheImpl.assignerTache("P4", "S1", "T2", "Dev1");
    tacheImpl.assignerTache("P4", "S1", "T3", "Dev2");
    tacheImpl.assignerTache("P4", "S1", "T4", "Dev2");
    tacheImpl.assignerTache("P4", "S1", "T5", "Dev1");

    // Changer les statuts pour simuler l'avancement
    tacheImpl.changerStatut("P4", "S1", "T1", "Terminée");
    tacheImpl.changerStatut("P4", "S1", "T2", "En cours");
    tacheImpl.changerStatut("P4", "S1", "T3", "En cours");
    // T4 et T5 restent "À faire"

    // Vérifications
    List<Tache> taches = tacheImpl.getTachesParSprint("P4", "S1");
    assertEquals("Le sprint devrait avoir 5 tâches", 5, taches.size());

    // Calculer la progression
    long tachesTerminees = taches.stream().filter(t -> "Terminée".equals(t.getStatut())).count();
    long tachesEnCours = taches.stream().filter(t -> "En cours".equals(t.getStatut())).count();
    long tachesAFaire = taches.stream().filter(t -> "À faire".equals(t.getStatut())).count();

    assertEquals("1 tâche devrait être terminée", 1, tachesTerminees);
    assertEquals("2 tâches devraient être en cours", 2, tachesEnCours);
    assertEquals("2 tâches devraient être à faire", 2, tachesAFaire);

    // Calculer le pourcentage de progression
    double progression = (tachesTerminees * 100.0) / taches.size();
    assertEquals("La progression devrait être de 20%", 20.0, progression, 0.1);
  }
}