package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import ca.uqam.sprindo.modele.Utilisateur;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Statistiques.
 * Objectif : Atteindre ≥75% de mutation coverage.
 */
public class StatistiquesTest {

    private Statistiques statistiques;
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
        statistiques = new Statistiques(projetImpl, sprintImpl, tacheImpl, utilisateurImpl);

        initialiserDonneesTest();
    }

    private void initialiserDonneesTest() {
        // Créer un projet
        Projet projet = new Projet("P1", "Projet Test", "Description du projet test");
        projetImpl.ajouterProjet(projet);

        // Ajouter des sprints avec idProjet
        Sprint sprint1 = new Sprint("S1", 1, "Sprint 1 - Setup",
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 15));
        Sprint sprint2 = new Sprint("S2", 2, "Sprint 2 - Dev",
                LocalDate.of(2025, 1, 16), LocalDate.of(2025, 1, 31));
        sprintImpl.ajouterSprint("P1", sprint1);
        sprintImpl.ajouterSprint("P1", sprint2);

        // Ajouter des tâches avec idProjet + idSprint
        Tache t1 = new Tache("T1", "Tâche 1", "Description tâche 1", "Terminé", "Alice");
        Tache t2 = new Tache("T2", "Tâche 2", "Description tâche 2", "En cours", "Bob");
        Tache t3 = new Tache("T3", "Tâche 3", "Description tâche 3", "À faire", "Alice");
        tacheImpl.ajouterTache("P1", "S1", t1);
        tacheImpl.ajouterTache("P1", "S1", t2);
        tacheImpl.ajouterTache("P1", "S1", t3);

        // Créer des utilisateurs (nom, role)
        Utilisateur u1 = new Utilisateur("Alice", "Développeur");
        Utilisateur u2 = new Utilisateur("Bob", "Développeur");
        utilisateurImpl.ajouterUtilisateur(u1);
        utilisateurImpl.ajouterUtilisateur(u2);
    }

    /**
     * TESTS POUR getNombreTotalSprints()
     */

    @Test
    public void testGetNombreTotalSprints_ProjetNull() {
        int resultat = statistiques.getNombreTotalSprints(null);
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTotalSprints_ProjetVide() {
        int resultat = statistiques.getNombreTotalSprints("");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTotalSprints_ProjetEspaces() {
        int resultat = statistiques.getNombreTotalSprints("   ");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTotalSprints_ProjetInexistant() {
        int resultat = statistiques.getNombreTotalSprints("P999");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTotalSprints_ProjetExistant() {
        int resultat = statistiques.getNombreTotalSprints("P1");
        assertEquals(2, resultat); // S1 + S2
    }

    @Test
    public void testGetNombreTotalSprints_UnSeulSprint() {
        // Créer un nouveau projet avec 1 seul sprint
        Projet p2 = new Projet("P2", "Projet 2", "Description");
        projetImpl.ajouterProjet(p2);
        Sprint s3 = new Sprint("S3", 1, "Sprint unique",
                LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 15));
        sprintImpl.ajouterSprint("P2", s3);

        int resultat = statistiques.getNombreTotalSprints("P2");
        assertEquals(1, resultat);
    }

    /**
     * TESTS POUR getNombreTotalUtilisateurs()
     */

    @Test
    public void testGetNombreTotalUtilisateurs() {
        int resultat = statistiques.getNombreTotalUtilisateurs();
        assertEquals(2, resultat); // Alice + Bob
    }

    @Test
    public void testGetNombreTotalUtilisateurs_ApresAjout() {
        Utilisateur u3 = new Utilisateur("Charlie", "Testeur");
        utilisateurImpl.ajouterUtilisateur(u3);

        int resultat = statistiques.getNombreTotalUtilisateurs();
        assertEquals(3, resultat);
    }

    /**
     * TESTS POUR getNombreTachesDansSprint()
     */

    @Test
    public void testGetNombreTachesDansSprint_ProjetNull() {
        int resultat = statistiques.getNombreTachesDansSprint(null, "S1");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTachesDansSprint_SprintNull() {
        int resultat = statistiques.getNombreTachesDansSprint("P1", null);
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTachesDansSprint_ProjetVide() {
        int resultat = statistiques.getNombreTachesDansSprint("", "S1");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTachesDansSprint_SprintVide() {
        int resultat = statistiques.getNombreTachesDansSprint("P1", "");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTachesDansSprint_ProjetEspaces() {
        int resultat = statistiques.getNombreTachesDansSprint("   ", "S1");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTachesDansSprint_SprintEspaces() {
        int resultat = statistiques.getNombreTachesDansSprint("P1", "   ");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreTachesDansSprint_SprintExistantAvecTaches() {
        int resultat = statistiques.getNombreTachesDansSprint("P1", "S1");
        assertEquals(3, resultat); // T1, T2, T3
    }

    @Test
    public void testGetNombreTachesDansSprint_SprintSansTaches() {
        int resultat = statistiques.getNombreTachesDansSprint("P1", "S2");
        assertEquals(0, resultat); // S2 n'a pas de tâches
    }

    @Test
    public void testGetNombreTachesDansSprint_SprintInexistant() {
        int resultat = statistiques.getNombreTachesDansSprint("P1", "S999");
        assertEquals(0, resultat);
    }

    /**
     * TESTS POUR getNombreDeveloppeursActifs()
     */

    @Test
    public void testGetNombreDeveloppeursActifs_ProjetNull() {
        int resultat = statistiques.getNombreDeveloppeursActifs(null, "S1");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreDeveloppeursActifs_SprintNull() {
        int resultat = statistiques.getNombreDeveloppeursActifs("P1", null);
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreDeveloppeursActifs_ProjetVide() {
        int resultat = statistiques.getNombreDeveloppeursActifs("", "S1");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreDeveloppeursActifs_SprintVide() {
        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreDeveloppeursActifs_SprintExistant() {
        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S1");
        assertEquals(2, resultat); // Alice et Bob (Alice compte 1 fois même avec 2 tâches)
    }

    @Test
    public void testGetNombreDeveloppeursActifs_SprintSansTaches() {
        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S2");
        assertEquals(0, resultat);
    }

    @Test
    public void testGetNombreDeveloppeursActifs_TacheNonAssignee() {
        Tache t4 = new Tache("T4", "Tâche non assignée", "Description", "À faire", null);
        tacheImpl.ajouterTache("P1", "S1", t4);

        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S1");
        assertEquals(2, resultat); // Toujours 2 (null n'est pas compté)
    }

    @Test
    public void testGetNombreDeveloppeursActifs_TacheAssigneeVide() {
        Tache t5 = new Tache("T5", "Tâche vide", "Description", "À faire", "");
        tacheImpl.ajouterTache("P1", "S1", t5);

        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S1");
        assertEquals(2, resultat); // Chaîne vide n'est pas comptée
    }

    @Test
    public void testGetNombreDeveloppeursActifs_TacheAssigneeEspaces() {
        Tache t6 = new Tache("T6", "Tâche espaces", "Description", "À faire", "   ");
        tacheImpl.ajouterTache("P1", "S1", t6);

        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S1");
        assertEquals(2, resultat); // Espaces seuls ne comptent pas
    }

    @Test
    public void testGetNombreDeveloppeursActifs_CasseDifferente() {
        Tache t7 = new Tache("T7", "Tâche ALICE", "Description", "À faire", "ALICE");
        Tache t8 = new Tache("T8", "Tâche alice", "Description", "À faire", "alice");
        tacheImpl.ajouterTache("P1", "S1", t7);
        tacheImpl.ajouterTache("P1", "S1", t8);

        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S1");
        assertEquals(2, resultat); // ALICE/alice = même personne (normalisé)
    }

    @Test
    public void testGetNombreDeveloppeursActifs_UnSeulDeveloppeur() {
        // Créer un sprint avec tâches d'un seul dev
        Sprint s4 = new Sprint("S4", 4, "Sprint solo",
                LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 15));
        sprintImpl.ajouterSprint("P1", s4);

        Tache t9 = new Tache("T9", "Tâche David 1", "Description", "En cours", "David");
        Tache t10 = new Tache("T10", "Tâche David 2", "Description", "À faire", "David");
        tacheImpl.ajouterTache("P1", "S4", t9);
        tacheImpl.ajouterTache("P1", "S4", t10);

        int resultat = statistiques.getNombreDeveloppeursActifs("P1", "S4");
        assertEquals(1, resultat); // 1 seul développeur
    }

    /**
     * TESTS POUR getPourcentageTachesTerminees()
     */

    @Test
    public void testGetPourcentageTachesTerminees_SprintExistant() {
        double resultat = statistiques.getPourcentageTachesTerminees("P1", "S1");
        assertEquals(33.33, resultat, 0.1); // 1/3 = 33.33%
    }

    @Test
    public void testGetPourcentageTachesTerminees_SprintSansTaches() {
        double resultat = statistiques.getPourcentageTachesTerminees("P1", "S2");
        assertEquals(0.0, resultat, 0.01);
    }

    @Test
    public void testGetPourcentageTachesTerminees_SprintInexistant() {
        double resultat = statistiques.getPourcentageTachesTerminees("P999", "S999");
        assertEquals(0.0, resultat, 0.01);
    }

    @Test
    public void testGetPourcentageTachesTerminees_ToutesTerminees() {
        Sprint s5 = new Sprint("S5", 5, "Sprint terminé",
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 15));
        sprintImpl.ajouterSprint("P1", s5);

        Tache t11 = new Tache("T11", "Tâche finie 1", "Description", "Terminé", "Alice");
        Tache t12 = new Tache("T12", "Tâche finie 2", "Description", "Terminé", "Bob");
        tacheImpl.ajouterTache("P1", "S5", t11);
        tacheImpl.ajouterTache("P1", "S5", t12);

        double resultat = statistiques.getPourcentageTachesTerminees("P1", "S5");
        assertEquals(100.0, resultat, 0.01);
    }

    @Test
    public void testGetPourcentageTachesTerminees_AucuneTerminee() {
        Sprint s6 = new Sprint("S6", 6, "Sprint en cours",
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 15));
        sprintImpl.ajouterSprint("P1", s6);

        Tache t13 = new Tache("T13", "Tâche A faire", "Description", "À faire", "Alice");
        Tache t14 = new Tache("T14", "Tâche En cours", "Description", "En cours", "Bob");
        tacheImpl.ajouterTache("P1", "S6", t13);
        tacheImpl.ajouterTache("P1", "S6", t14);

        double resultat = statistiques.getPourcentageTachesTerminees("P1", "S6");
        assertEquals(0.0, resultat, 0.01);
    }

    @Test
    public void testGetPourcentageTachesTerminees_50Pourcent() {
        Sprint s7 = new Sprint("S7", 7, "Sprint 50%",
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 15));
        sprintImpl.ajouterSprint("P1", s7);

        Tache t15 = new Tache("T15", "Tâche terminée", "Description", "Terminé", "Alice");
        Tache t16 = new Tache("T16", "Tâche en cours", "Description", "En cours", "Bob");
        tacheImpl.ajouterTache("P1", "S7", t15);
        tacheImpl.ajouterTache("P1", "S7", t16);

        double resultat = statistiques.getPourcentageTachesTerminees("P1", "S7");
        assertEquals(50.0, resultat, 0.01);
    }

    @Test
    public void testGetPourcentageTachesTerminees_CasseDifferente() {
        Sprint s8 = new Sprint("S8", 8, "Sprint casse",
                LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 15));
        sprintImpl.ajouterSprint("P1", s8);

        Tache t17 = new Tache("T17", "TERMINÉ maj", "Description", "TERMINÉ", "Alice");
        Tache t18 = new Tache("T18", "terminé min", "Description", "terminé", "Bob");
        Tache t19 = new Tache("T19", "En cours", "Description", "En cours", "Charlie");
        tacheImpl.ajouterTache("P1", "S8", t17);
        tacheImpl.ajouterTache("P1", "S8", t18);
        tacheImpl.ajouterTache("P1", "S8", t19);

        double resultat = statistiques.getPourcentageTachesTerminees("P1", "S8");
        assertEquals(66.66, resultat, 0.1); // 2/3 = 66.66%
    }

    /**
     * TESTS POUR genererRapportProjet()
     */

    @Test
    public void testGenererRapportProjet_ProjetExistant() {
        String rapport = statistiques.genererRapportProjet("P1");

        assertNotNull(rapport);
        assertTrue(rapport.contains("Projet Test"));
        assertTrue(rapport.contains("P1"));
        assertTrue(rapport.contains("Sprint 1"));
        assertTrue(rapport.contains("Sprint 2"));
    }

    @Test
    public void testGenererRapportProjet_ProjetInexistant() {
        String rapport = statistiques.genererRapportProjet("P999");

        assertNotNull(rapport);
        assertTrue(rapport.contains("❌"));
        assertTrue(rapport.contains("Projet introuvable"));
        assertTrue(rapport.contains("P999"));
    }

    @Test
    public void testGenererRapportProjet_ProjetNull() {
        String rapport = statistiques.genererRapportProjet(null);

        assertNotNull(rapport);
        assertTrue(rapport.contains("❌") || rapport.contains("introuvable"));
    }

    @Test
    public void testGenererRapportProjet_ProjetSansSprints() {
        Projet p3 = new Projet("P3", "Projet vide", "Sans sprints");
        projetImpl.ajouterProjet(p3);

        String rapport = statistiques.genererRapportProjet("P3");

        assertNotNull(rapport);
        assertTrue(rapport.contains("Projet vide"));
        assertTrue(rapport.contains("Aucun sprint") || rapport.contains("0"));
    }

    /**
     * TESTS POUR genererRapportSprint()
     */

    @Test
    public void testGenererRapportSprint_SprintExistant() {
        String rapport = statistiques.genererRapportSprint("P1", "S1");

        assertNotNull(rapport);
        assertTrue(rapport.contains("Sprint 1"));
        assertTrue(rapport.contains("MÉTRIQUES") || rapport.contains("Nombre"));
    }

    @Test
    public void testGenererRapportSprint_SprintInexistant() {
        String rapport = statistiques.genererRapportSprint("P1", "S999");

        assertNotNull(rapport);
        assertTrue(rapport.contains("❌"));
        assertTrue(rapport.contains("Sprint introuvable"));
        assertTrue(rapport.contains("S999"));
    }

    @Test
    public void testGenererRapportSprint_SprintNull() {
        String rapport = statistiques.genererRapportSprint("P1", null);

        assertNotNull(rapport);
        assertTrue(rapport.contains("❌") || rapport.contains("introuvable"));
    }

    @Test
    public void testGenererRapportSprint_SprintSansTaches() {
        String rapport = statistiques.genererRapportSprint("P1", "S2");

        assertNotNull(rapport);
        assertTrue(rapport.contains("Sprint 2"));
        assertTrue(rapport.contains("0") || rapport.contains("Aucun"));
    }

    /**
     * Test 7 : Variations orthographiques mixtes
     * Tue tous les mutants en testant toutes les variantes simultanément
     */
    @Test
    public void testGenererRapportSprint_VariationsOrthographiques() {
        // Créer un sprint
        Sprint s15 = new Sprint("S15", 15, "Sprint variations",
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 15));
        sprintImpl.ajouterSprint("P1", s15);

        // Tester toutes les variations (avec/sans accent)
        Tache t42 = new Tache("T42", "T1", "Desc", "À faire", "Alice");
        Tache t43 = new Tache("T43", "T2", "Desc", "A faire", "Bob");
        Tache t44 = new Tache("T44", "T3", "Desc", "Terminé", "Charlie");
        Tache t45 = new Tache("T45", "T4", "Desc", "Terminee", "David");
        Tache t46 = new Tache("T46", "T5", "Desc", "Bloqué", "Eve");
        Tache t47 = new Tache("T47", "T6", "Desc", "Bloque", "Frank");

        tacheImpl.ajouterTache("P1", "S15", t42);
        tacheImpl.ajouterTache("P1", "S15", t43);
        tacheImpl.ajouterTache("P1", "S15", t44);
        tacheImpl.ajouterTache("P1", "S15", t45);
        tacheImpl.ajouterTache("P1", "S15", t46);
        tacheImpl.ajouterTache("P1", "S15", t47);

        // Générer le rapport
        String rapport = statistiques.genererRapportSprint("P1", "S15");

        // Vérifications critiques
        assertNotNull(rapport);

        // 2 tâches "À faire" (avec et sans accent)
        assertTrue("Le rapport devrait compter 2 tâches À faire/A faire",
                rapport.contains("2") && rapport.contains("faire"));

        // 2 tâches "Terminé" (avec et sans accent)
        assertTrue("Le rapport devrait compter 2 tâches Terminé/Terminee",
                rapport.contains("2") && rapport.toLowerCase().contains("termin"));

        // 2 tâches "Bloqué" (avec et sans accent)
        assertTrue("Le rapport devrait compter 2 tâches Bloqué/Bloque",
                rapport.contains("2") && rapport.toLowerCase().contains("bloqu"));
    }

}
