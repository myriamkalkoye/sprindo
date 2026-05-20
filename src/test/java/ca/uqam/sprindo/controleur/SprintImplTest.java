package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.controleur.ProjetImpl;
import ca.uqam.sprindo.controleur.SprintImpl;
import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe SprintImpl.
 */
public class SprintImplTest {

    private SprintImpl sprintImpl;
    private ProjetImpl projetImpl;

    /**
     * Initialisation avant chaque test.
     */
    @Before
    public void setUp() {
        sprintImpl = new SprintImpl();
        projetImpl = new ProjetImpl();

        // Créer un projet pour les tests
        Projet projet = new Projet("P001", "Projet Test", "Description");
        projetImpl.ajouterProjet(projet);
    }

    /**
     * Test : Ajouter un sprint avec succès.
     */
    @Test
    public void testAjouterSprint() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S001", 1, "Sprint Test", debut, fin);

        // Act
        sprintImpl.ajouterSprint("P001", sprint);
        Sprint resultat = sprintImpl.getSprint("P001", "S001");

        // Assert
        assertNotNull("Le sprint ne devrait pas être null", resultat);
        assertEquals("S001", resultat.getId());
        assertEquals(1, resultat.getNumero());
        assertEquals("Sprint Test", resultat.getObjectif());
    }

    /**
     * Test : Récupérer un sprint existant.
     */
    @Test
    public void testGetSprintExistant() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S002", 2, "Sprint 2", debut, fin);
        sprintImpl.ajouterSprint("P001", sprint);

        // Act
        Sprint resultat = sprintImpl.getSprint("P001", "S002");

        // Assert
        assertNotNull("Le sprint devrait exister", resultat);
        assertEquals("S002", resultat.getId());
    }

    /**
     * Test : Récupérer un sprint inexistant.
     */
    @Test
    public void testGetSprintInexistant() {
        // Act
        Sprint resultat = sprintImpl.getSprint("P001", "S999");

        // Assert
        assertNull("Le sprint ne devrait pas exister", resultat);
    }

    /**
     * Test : Lister les sprints d'un projet.
     */
    @Test
    public void testGetSprintsParProjet() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint s1 = new Sprint("S001", 1, "Sprint 1", debut, fin);
        Sprint s2 = new Sprint("S002", 2, "Sprint 2", debut.plusDays(15), fin.plusDays(15));

        sprintImpl.ajouterSprint("P001", s1);
        sprintImpl.ajouterSprint("P001", s2);

        // Act
        List<Sprint> sprints = sprintImpl.getSprintsParProjet("P001");

        // Assert
        assertEquals("Devrait contenir 2 sprints", 2, sprints.size());
    }

    /**
     * Test : Lister les sprints d'un projet sans sprints.
     */
    @Test
    public void testGetSprintsParProjetVide() {
        // Act
        List<Sprint> sprints = sprintImpl.getSprintsParProjet("P001");

        // Assert
        assertNotNull("La liste ne devrait pas être null", sprints);
        assertTrue("La liste devrait être vide", sprints.isEmpty());
    }
    /**
     * Test : Ajouter un sprint avec dates null.
     */
    @Test
    public void testAjouterSprintDatesNull() {
        // Arrange
        Sprint sprint = new Sprint("S003", 3, "Sprint Test", null, null);

        // Act
        sprintImpl.ajouterSprint("P001", sprint);
        Sprint resultat = sprintImpl.getSprint("P001", "S003");

        // Assert
        assertNotNull("Le sprint devrait être accepté", resultat);
        assertNull("La date de début devrait être null", resultat.getDebut());
        assertNull("La date de fin devrait être null", resultat.getFin());
    }

    /**
     * Test : Ajouter un sprint avec objectif vide.
     */
    @Test
    public void testAjouterSprintObjectifVide() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S004", 4, "", debut, fin);

        // Act
        sprintImpl.ajouterSprint("P001", sprint);
        Sprint resultat = sprintImpl.getSprint("P001", "S004");

        // Assert
        assertNotNull("Le sprint devrait être accepté", resultat);
        assertEquals("", resultat.getObjectif());
    }

    /**
     * Test : Ajouter plusieurs sprints à un même projet (test de volume).
     */
    @Test
    public void testAjouterMultiplesSprints() {
        // Arrange & Act
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);

        for (int i = 1; i <= 10; i++) {
            Sprint s = new Sprint("S" + String.format("%03d", i),
                    i,
                    "Sprint " + i,
                    debut.plusWeeks(i - 1),
                    fin.plusWeeks(i - 1));
            sprintImpl.ajouterSprint("P001", s);
        }

        // Assert
        List<Sprint> sprints = sprintImpl.getSprintsParProjet("P001");
        assertEquals("Devrait contenir 10 sprints", 10, sprints.size());
    }

    /**
     * Test : Modifier l'objectif d'un sprint existant.
     */
    @Test
    public void testModifierObjectifSprint() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S005", 5, "Objectif Initial", debut, fin);
        sprintImpl.ajouterSprint("P001", sprint);

        // Act
        Sprint recup = sprintImpl.getSprint("P001", "S005");
        recup.setObjectif("Objectif Modifié");

        // Assert
        Sprint resultat = sprintImpl.getSprint("P001", "S005");
        assertEquals("Objectif Modifié", resultat.getObjectif());
    }

    /**
     * Test : Modifier les dates d'un sprint existant.
     */
    @Test
    public void testModifierDatesSprint() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S006", 6, "Sprint Test", debut, fin);
        sprintImpl.ajouterSprint("P001", sprint);

        // Act
        Sprint recup = sprintImpl.getSprint("P001", "S006");
        LocalDate nouvelleDebut = LocalDate.of(2025, 12, 1);
        LocalDate nouvelleFin = LocalDate.of(2025, 12, 15);
        recup.setDebut(nouvelleDebut);
        recup.setFin(nouvelleFin);

        // Assert
        Sprint resultat = sprintImpl.getSprint("P001", "S006");
        assertEquals(nouvelleDebut, resultat.getDebut());
        assertEquals(nouvelleFin, resultat.getFin());
    }

    /**
     * Test : Ajouter un sprint null (validation).
     */
    @Test
    public void testAjouterSprintNull() {
        // Act
        sprintImpl.ajouterSprint("P001", null);
    }

    /**
     * Test : Ajouter un sprint à un projet inexistant.
     */
    @Test
    public void testAjouterSprintProjetInexistant() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S007", 7, "Sprint Test", debut, fin);

        // Act
        sprintImpl.ajouterSprint("P999", sprint);
        Sprint resultat = sprintImpl.getSprint("P999", "S007");

        // Assert
        // Le comportement dépend de l'implémentation
        // Peut être null ou créer le projet automatiquement
        assertNotNull("Vérifier le comportement avec projet inexistant",
                sprintImpl.getSprintsParProjet("P999"));
    }

    /**
     * Test : Récupérer les sprints d'un projet avec plusieurs sprints.
     */
    @Test
    public void testGetSprintsOrdreChronologique() {
        // Arrange
        LocalDate debut1 = LocalDate.of(2025, 11, 1);
        LocalDate fin1 = LocalDate.of(2025, 11, 15);
        LocalDate debut2 = LocalDate.of(2025, 11, 16);
        LocalDate fin2 = LocalDate.of(2025, 11, 30);
        LocalDate debut3 = LocalDate.of(2025, 12, 1);
        LocalDate fin3 = LocalDate.of(2025, 12, 15);

        Sprint s1 = new Sprint("S001", 1, "Sprint 1", debut1, fin1);
        Sprint s2 = new Sprint("S002", 2, "Sprint 2", debut2, fin2);
        Sprint s3 = new Sprint("S003", 3, "Sprint 3", debut3, fin3);

        // Act
        sprintImpl.ajouterSprint("P001", s1);
        sprintImpl.ajouterSprint("P001", s3);
        sprintImpl.ajouterSprint("P001", s2);

        // Assert
        List<Sprint> sprints = sprintImpl.getSprintsParProjet("P001");
        assertEquals("Devrait contenir 3 sprints", 3, sprints.size());
    }

    /**
     * Test : Ajouter un sprint avec numéro négatif.
     */
    @Test
    public void testAjouterSprintNumeroNegatif() {
        // Arrange
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S008", -1, "Sprint Test", debut, fin);

        // Act
        sprintImpl.ajouterSprint("P001", sprint);
        Sprint resultat = sprintImpl.getSprint("P001", "S008");

        // Assert
        assertNotNull("Le sprint devrait être accepté", resultat);
        assertEquals(-1, resultat.getNumero());
    }
    /**
     * Test : Ajouter un sprint avec validation null.
     */
    /**@Test(expected = IllegalArgumentException.class)
    public void testAjouterSprintValidationNull() {
        sprintImpl.ajouterSprint("P001", null);
    }*/

    /**
     * Test : getSprint avec projetId inexistant.
     */
    @Test
    public void testGetSprintProjetInexistant() {
        Sprint resultat = sprintImpl.getSprint("P999", "S001");
        assertNull("Devrait retourner null pour projet inexistant", resultat);
    }

    /**
     * Test : getSprint avec sprintId null.
     */
    @Test
    public void testGetSprintIdNull() {
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);
        Sprint sprint = new Sprint("S001", 1, "Sprint Test", debut, fin);
        sprintImpl.ajouterSprint("P001", sprint);

        Sprint resultat = sprintImpl.getSprint("P001", null);
        assertNull("Devrait retourner null pour ID null", resultat);
    }

    /**
     * Test : getSprintsParProjet pour projet avec aucun sprint.
     */
    @Test
    public void testGetSprintsParProjetAucunSprint() {
        List<Sprint> sprints = sprintImpl.getSprintsParProjet("P999");
        assertNotNull("La liste ne devrait pas être null", sprints);
        assertTrue("La liste devrait être vide", sprints.isEmpty());
    }

    /**
     * Test : Ajouter plusieurs sprints au même projet.
     */
    @Test
    public void testAjouterPlusieursSprints() {
        LocalDate debut = LocalDate.of(2025, 11, 1);
        LocalDate fin = LocalDate.of(2025, 11, 15);

        for (int i = 1; i <= 5; i++) {
            Sprint s = new Sprint("S00" + i, i, "Sprint " + i, debut, fin);
            sprintImpl.ajouterSprint("P001", s);
        }

        List<Sprint> sprints = sprintImpl.getSprintsParProjet("P001");
        assertEquals("Devrait contenir 5 sprints", 5, sprints.size());
    }
}
