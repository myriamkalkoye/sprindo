package ca.uqam.sprindo;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import ca.uqam.sprindo.modele.Utilisateur;
import ca.uqam.sprindo.controleur.ProjetImpl;
import ca.uqam.sprindo.controleur.SprintImpl;
import ca.uqam.sprindo.controleur.TacheImpl;
import ca.uqam.sprindo.controleur.UtilisateurImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests d'intégration pour le système Sprindo. Teste les interactions entre ProjetImpl, SprintImpl,
 * TacheImpl et UtilisateurImpl.
 */
public class ProjetIntegrationIT {

  private ProjetImpl projetImpl;
  private SprintImpl sprintImpl;
  private TacheImpl tacheImpl;
  private UtilisateurImpl utilisateurImpl;

  @Before
  public void setUp() {
    projetImpl = new ProjetImpl();
    sprintImpl = new SprintImpl();
    tacheImpl = new TacheImpl();
    utilisateurImpl = new UtilisateurImpl();
  }

  /**
   * Test 1 : Créer un projet et y ajouter des sprints.
   */
  @Test
  public void testAjouterSprintsAuProjet() {
    // Créer un projet
    Projet projet = new Projet("P1", "Sprindo", "Application de gestion de projets agile");
    projetImpl.ajouterProjet(projet);

    // Ajouter des sprints au projet
    Sprint sprint1 = new Sprint("S1", 1, "Fondations du système",
        LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 15));
    Sprint sprint2 = new Sprint("S2", 2, "Qualité logicielle",
        LocalDate.of(2025, 11, 16), LocalDate.of(2025, 11, 30));

    sprintImpl.ajouterSprint("P1", sprint1);
    sprintImpl.ajouterSprint("P1", sprint2);

    // Vérifications
    Projet projetRecupere = projetImpl.getProjet("P1");
    assertNotNull("Le projet devrait exister", projetRecupere);
    assertEquals("P1", projetRecupere.getId());

    List<Sprint> sprints = sprintImpl.getSprintsParProjet("P1");
    assertEquals("Le projet devrait avoir 2 sprints", 2, sprints.size());
    assertEquals("S1", sprints.get(0).getId());
    assertEquals("S2", sprints.get(1).getId());
  }

  /**
   * Test 2 : Créer un projet, ajouter un sprint, puis ajouter des tâches au sprint.
   */
  @Test
  public void testAjouterTachesAuSprint() {
    // Créer le projet
    Projet projet = new Projet("P2", "E-commerce", "Site de vente en ligne");
    projetImpl.ajouterProjet(projet);

    // Ajouter un sprint
    Sprint sprint = new Sprint("S1", 1, "MVP",
        LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 15));
    sprintImpl.ajouterSprint("P2", sprint);

    // Ajouter des tâches au sprint
    Tache tache1 = new Tache("T1", "Créer base de données", "MySQL", "À faire", null);
    Tache tache2 = new Tache("T2", "Implémenter panier", "Fonctionnalité", "À faire", null);

    tacheImpl.ajouterTache("P2", "S1", tache1);
    tacheImpl.ajouterTache("P2", "S1", tache2);

    // Vérifications
    List<Tache> taches = tacheImpl.getTachesParSprint("P2", "S1");
    assertEquals("Le sprint devrait avoir 2 tâches", 2, taches.size());
    assertEquals("T1", taches.get(0).getId());
    assertEquals("T2", taches.get(1).getId());
  }

  /**
   * Test 3 : Créer un utilisateur et lui assigner une tâche.
   */
  @Test
  public void testAssignerTacheAUtilisateur() {
    // Créer le projet et le sprint
    Projet projet = new Projet("P3", "Sprindo", "Gestion agile");
    projetImpl.ajouterProjet(projet);

    Sprint sprint = new Sprint("S1", 1, "Sprint 1",
        LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 15));
    sprintImpl.ajouterSprint("P3", sprint);

    // Créer une tâche
    Tache tache = new Tache("T1", "Implémenter ProjetImpl", "Classe de gestion", "À faire", null);
    tacheImpl.ajouterTache("P3", "S1", tache);

    // Créer un utilisateur
    Utilisateur dev = new Utilisateur("Alice", "Développeuse");
    utilisateurImpl.ajouterUtilisateur(dev);

    // Assigner la tâche à l'utilisateur
    tacheImpl.assignerTache("P3", "S1", "T1", "Alice");

    // Vérifications
    Tache tacheRecuperee = tacheImpl.getTache("P3", "S1", "T1");
    assertNotNull("La tâche devrait exister", tacheRecuperee);
    assertEquals("Alice", tacheRecuperee.getAssigneA());

    Utilisateur utilisateurRecupere = utilisateurImpl.getUtilisateur("Alice");
    assertNotNull("L'utilisateur devrait exister", utilisateurRecupere);
    assertEquals("Alice", utilisateurRecupere.getNom());
  }

  /**
   * Test 4 : Changer le statut d'une tâche.
   */
  @Test
  public void testChangerStatutTache() {
    // Créer le projet et le sprint
    Projet projet = new Projet("P4", "Test Statuts", "Tester les statuts");
    projetImpl.ajouterProjet(projet);

    Sprint sprint = new Sprint("S1", 1, "Sprint Test",
        LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 15));
    sprintImpl.ajouterSprint("P4", sprint);

    // Créer une tâche
    Tache tache = new Tache("T1", "Tâche test", "Description", "À faire", null);
    tacheImpl.ajouterTache("P4", "S1", tache);

    // Changer le statut
    tacheImpl.changerStatut("P4", "S1", "T1", "En cours");

    // Vérification
    Tache tacheRecuperee = tacheImpl.getTache("P4", "S1", "T1");
    assertEquals("En cours", tacheRecuperee.getStatut());

    // Changer à nouveau le statut
    tacheImpl.changerStatut("P4", "S1", "T1", "Terminée");
    tacheRecuperee = tacheImpl.getTache("P4", "S1", "T1");
    assertEquals("Terminée", tacheRecuperee.getStatut());
  }

  /**
   * Test 5 : Workflow complet - Projet → Sprint → Tâches → Utilisateurs → Changements de statut.
   */
  @Test
  public void testWorkflowComplet() {
    // 1. Créer un projet
    Projet projet = new Projet("P5", "Workflow Test", "Test complet du workflow");
    projetImpl.ajouterProjet(projet);

    // 2. Ajouter un sprint
    Sprint sprint = new Sprint("S1", 1, "Sprint Complet",
        LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 30));
    sprintImpl.ajouterSprint("P5", sprint);

    // 3. Ajouter des tâches
    Tache tache1 = new Tache("T1", "Setup projet", "Initialisation", "À faire", null);
    Tache tache2 = new Tache("T2", "Créer modèle", "Classes POJOs", "À faire", null);
    Tache tache3 = new Tache("T3", "Créer vue", "Interface console", "À faire", null);

    tacheImpl.ajouterTache("P5", "S1", tache1);
    tacheImpl.ajouterTache("P5", "S1", tache2);
    tacheImpl.ajouterTache("P5", "S1", tache3);

    // 4. Créer des utilisateurs
    Utilisateur dev1 = new Utilisateur("Bob", "Développeur Backend");
    Utilisateur dev2 = new Utilisateur("Charlie", "Développeur Frontend");
    utilisateurImpl.ajouterUtilisateur(dev1);
    utilisateurImpl.ajouterUtilisateur(dev2);

    // 5. Assigner les tâches
    tacheImpl.assignerTache("P5", "S1", "T1", "Bob");
    tacheImpl.assignerTache("P5", "S1", "T2", "Bob");
    tacheImpl.assignerTache("P5", "S1", "T3", "Charlie");

    // 6. Changer les statuts
    tacheImpl.changerStatut("P5", "S1", "T1", "Terminée");
    tacheImpl.changerStatut("P5", "S1", "T2", "En cours");
    tacheImpl.changerStatut("P5", "S1", "T3", "En cours");

    // 7. Vérifications globales
    Projet projetFinal = projetImpl.getProjet("P5");
    assertNotNull("Le projet devrait exister", projetFinal);
    assertEquals("Workflow Test", projetFinal.getNom());

    List<Sprint> sprints = sprintImpl.getSprintsParProjet("P5");
    assertEquals("Le projet devrait avoir 1 sprint", 1, sprints.size());

    List<Tache> taches = tacheImpl.getTachesParSprint("P5", "S1");
    assertEquals("Le sprint devrait avoir 3 tâches", 3, taches.size());

    // Vérifier les assignations
    Tache t1 = tacheImpl.getTache("P5", "S1", "T1");
    assertEquals("Bob", t1.getAssigneA());
    assertEquals("Terminée", t1.getStatut());

    Tache t2 = tacheImpl.getTache("P5", "S1", "T2");
    assertEquals("Bob", t2.getAssigneA());
    assertEquals("En cours", t2.getStatut());

    Tache t3 = tacheImpl.getTache("P5", "S1", "T3");
    assertEquals("Charlie", t3.getAssigneA());
    assertEquals("En cours", t3.getStatut());

    // Vérifier les utilisateurs
    List<Utilisateur> utilisateurs = utilisateurImpl.getUtilisateurs();
    assertEquals("Il devrait y avoir 2 utilisateurs", 2, utilisateurs.size());
  }
}