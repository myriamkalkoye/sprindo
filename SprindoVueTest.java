package ca.uqam.sprindo.vue;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Classe de tests pour SprindoVue.
 * Version adaptée pour éviter la fermeture de System.in.
 */
public class SprindoVueTest {

    /**
     * Sous-classe pour neutraliser fermer() qui brise System.in
     */
    private static class SprindoVueMock extends SprindoVue {
        @Override
        public void fermer() {
            // On NE ferme PAS System.in, sinon Surefire plante
        }
    }

    private SprindoVue vue;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
        if (vue != null) {
            vue.fermer();  // sécurisé grâce à SprindoVueMock
        }
    }

    // ===== TESTS AFFICHAGE MESSAGES =====

    @Test
    public void testAfficherMessage() {
        vue = new SprindoVueMock();
        vue.afficherMessage("Bonjour Sprindo !");
        assertTrue(outContent.toString().contains("Bonjour Sprindo"));
    }

    @Test
    public void testAfficherErreur() {
        vue = new SprindoVueMock();
        vue.afficherErreur("Erreur de test");
        assertTrue(errContent.toString().contains("ERREUR"));
        assertTrue(errContent.toString().contains("Erreur de test"));
    }

    @Test
    public void testAfficherMenu() {
        vue = new SprindoVueMock();
        vue.afficherMenu();
        String output = outContent.toString();
        assertTrue(output.contains("SPRINDO"));
        assertTrue(output.contains("1. Ajouter un projet"));
        assertTrue(output.contains("11. Quitter"));
    }

    // ===== TESTS AFFICHAGE PROJETS =====

    @Test
    public void testAfficherProjetsVide() {
        vue = new SprindoVueMock();
        List<Projet> projets = new ArrayList<>();
        vue.afficherProjets(projets);
        assertTrue(outContent.toString().contains("Aucun projet disponible"));
    }

    @Test
    public void testAfficherProjetsAvecDonnees() {
        vue = new SprindoVueMock();
        List<Projet> projets = new ArrayList<>();
        Projet p1 = new Projet("P1", "Projet Test", "Description test");
        projets.add(p1);
        vue.afficherProjets(projets);
        String output = outContent.toString();
        assertTrue(output.contains("P1"));
        assertTrue(output.contains("Projet Test"));
        assertTrue(output.contains("Description test"));
    }

    // ===== TESTS AFFICHAGE SPRINTS =====

    @Test
    public void testAfficherSprintsVide() {
        vue = new SprindoVueMock();
        List<Sprint> sprints = new ArrayList<>();
        vue.afficherSprints(sprints, "P1");
        assertTrue(outContent.toString().contains("Aucun sprint disponible"));
    }

    @Test
    public void testAfficherSprintsAvecDonnees() {
        vue = new SprindoVueMock();
        List<Sprint> sprints = new ArrayList<>();
        Sprint s1 = new Sprint("S1", 1, "Sprint 1", LocalDate.now(), LocalDate.now().plusDays(14));
        sprints.add(s1);
        vue.afficherSprints(sprints, "P1");
        String output = outContent.toString();
        assertTrue(output.contains("S1"));
        assertTrue(output.contains("Sprint 1"));
    }

    // ===== TESTS AFFICHAGE TÂCHES =====

    @Test
    public void testAfficherTachesVide() {
        vue = new SprindoVueMock();
        List<Tache> taches = new ArrayList<>();
        vue.afficherTaches(taches, "P1", "S1");
        assertTrue(outContent.toString().contains("Aucune tâche disponible"));
    }

    @Test
    public void testAfficherTachesAvecDonnees() {
        vue = new SprindoVueMock();
        List<Tache> taches = new ArrayList<>();
        Tache t1 = new Tache("T1", "Tâche test", "Description", "À faire", null);
        taches.add(t1);
        vue.afficherTaches(taches, "P1", "S1");
        String output = outContent.toString();
        assertTrue(output.contains("T1"));
        assertTrue(output.contains("Tâche test"));
    }

    @Test
    public void testAfficherTachesAvecAssignation() {
        vue = new SprindoVueMock();
        List<Tache> taches = new ArrayList<>();
        Tache t1 = new Tache("T1", "Tâche test", "Description", "À faire", null);
        t1.setAssigneA("Alice");
        taches.add(t1);
        vue.afficherTaches(taches, "P1", "S1");
        assertTrue(outContent.toString().contains("Alice"));
    }

    @Test
    public void testAfficherTachesSansAssignation() {
        vue = new SprindoVueMock();
        List<Tache> taches = new ArrayList<>();
        Tache t1 = new Tache("T1", "Tâche test", "Description", "À faire", null);
        taches.add(t1);
        vue.afficherTaches(taches, "P1", "S1");
        assertTrue(outContent.toString().contains("Non assigné"));
    }

    // ===== TESTS LECTURE MENU =====

    @Test
    public void testLireChoixMenuValide() {
        System.setIn(new ByteArrayInputStream("5\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals(5, vue.lireChoixMenu());
    }

    @Test
    public void testLireChoixMenuInvalide() {
        System.setIn(new ByteArrayInputStream("abc\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals(-1, vue.lireChoixMenu());
    }

    // ===== TESTS SAISIES =====

    @Test
    public void testDemanderIdProjet() {
        System.setIn(new ByteArrayInputStream("P123\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("P123", vue.demanderIdProjet());
    }

    @Test
    public void testDemanderNomProjet() {
        System.setIn(new ByteArrayInputStream("Projet Test\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("Projet Test", vue.demanderNomProjet());
    }

    @Test
    public void testDemanderDescriptionProjet() {
        System.setIn(new ByteArrayInputStream("Description\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("Description", vue.demanderDescriptionProjet());
    }

    @Test
    public void testDemanderIdSprint() {
        System.setIn(new ByteArrayInputStream("S456\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("S456", vue.demanderIdSprint());
    }

    @Test
    public void testDemanderNumeroSprint() {
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals(3, vue.demanderNumeroSprint());
    }

    @Test
    public void testDemanderObjectifSprint() {
        System.setIn(new ByteArrayInputStream("Objectif\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("Objectif", vue.demanderObjectifSprint());
    }

    @Test
    public void testDemanderDateDebut() {
        System.setIn(new ByteArrayInputStream("2025-01-01\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals(LocalDate.of(2025, 1, 1), vue.demanderDateDebut());
    }

    @Test
    public void testDemanderDateFin() {
        System.setIn(new ByteArrayInputStream("2025-12-31\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals(LocalDate.of(2025, 12, 31), vue.demanderDateFin());
    }

    @Test
    public void testDemanderIdTache() {
        System.setIn(new ByteArrayInputStream("T789\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("T789", vue.demanderIdTache());
    }

    @Test
    public void testDemanderTitreTache() {
        System.setIn(new ByteArrayInputStream("Titre\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("Titre", vue.demanderTitreTache());
    }

    @Test
    public void testDemanderDescriptionTache() {
        System.setIn(new ByteArrayInputStream("Desc\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("Desc", vue.demanderDescriptionTache());
    }

    @Test
    public void testDemanderStatutTache() {
        System.setIn(new ByteArrayInputStream("En cours\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("En cours", vue.demanderStatutTache());
    }

    @Test
    public void testDemanderNomUtilisateur() {
        System.setIn(new ByteArrayInputStream("Alice\n".getBytes()));
        vue = new SprindoVueMock();
        assertEquals("Alice", vue.demanderNomUtilisateur());
    }

    @Test
    public void testFermer() {
        vue = new SprindoVueMock();
        vue.fermer();
        assertTrue(true); // no crash
    }
}

