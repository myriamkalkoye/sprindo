package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.controleur.ProjetImpl;
import ca.uqam.sprindo.modele.Projet;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe ProjetImpl.
 */
public class ProjetImplTest {

    private ProjetImpl projetImpl;

    /**
     * Initialisation avant chaque test.
     */
    @Before
    public void setUp() {
        projetImpl = new ProjetImpl();
    }

    /**
     * Test : Ajouter un projet avec succès.
     */
    @Test
    public void testAjouterProjet() {
        // Arrange
        Projet projet = new Projet("P001", "Projet Test", "Description test");

        // Act
        projetImpl.ajouterProjet(projet);
        Projet resultat = projetImpl.getProjet("P001");

        // Assert
        assertNotNull("Le projet ne devrait pas être null", resultat);
        assertEquals("P001", resultat.getId());
        assertEquals("Projet Test", resultat.getNom());
        assertEquals("Description test", resultat.getDescription());
    }

    /**
     * Test : Récupérer un projet existant.
     */
    @Test
    public void testGetProjetExistant() {
        // Arrange
        Projet projet = new Projet("P002", "Projet 2", "Description 2");
        projetImpl.ajouterProjet(projet);

        // Act
        Projet resultat = projetImpl.getProjet("P002");

        // Assert
        assertNotNull("Le projet devrait exister", resultat);
        assertEquals("P002", resultat.getId());
    }

    /**
     * Test : Récupérer un projet inexistant.
     */
    @Test
    public void testGetProjetInexistant() {
        // Act
        Projet resultat = projetImpl.getProjet("P999");

        // Assert
        assertNull("Le projet ne devrait pas exister", resultat);
    }

    /**
     * Test : Lister tous les projets.
     */
    @Test
    public void testGetProjets() {
        // Arrange
        Projet p1 = new Projet("P001", "Projet 1", "Desc 1");
        Projet p2 = new Projet("P002", "Projet 2", "Desc 2");
        projetImpl.ajouterProjet(p1);
        projetImpl.ajouterProjet(p2);

        // Act
        List<Projet> projets = projetImpl.getProjets();

        // Assert
        assertEquals("Devrait contenir 2 projets", 2, projets.size());
        assertTrue("Devrait contenir P001",
                projets.stream().anyMatch(p -> p.getId().equals("P001")));
        assertTrue("Devrait contenir P002",
                projets.stream().anyMatch(p -> p.getId().equals("P002")));
    }

    /**
     * Test : Lister les projets quand la liste est vide.
     */
    @Test
    public void testGetProjetsVide() {
        // Act
        List<Projet> projets = projetImpl.getProjets();

        // Assert
        assertNotNull("La liste ne devrait pas être null", projets);
        assertTrue("La liste devrait être vide", projets.isEmpty());
    }

    /**
     * Test : Ajouter un projet null (comportement attendu).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterProjetNull() {
        // Act
        projetImpl.ajouterProjet(null);


    }
    /**
     * Test : Ajouter un projet avec description vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterProjetDescriptionVide() {
        // Arrange
        Projet projet = new Projet("P003", "Projet Sans Description", "");

        // Act
        projetImpl.ajouterProjet(projet);
        Projet resultat = projetImpl.getProjet("P003");

        // Assert
        assertNotNull("Le projet devrait être accepté", resultat);
        assertEquals("", resultat.getDescription());
    }

    /**
     * Test : Ajouter un projet avec nom vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterProjetNomVide() {
        // Arrange
        Projet projet = new Projet("P004", "", "Description");

        // Act
        projetImpl.ajouterProjet(projet);
        Projet resultat = projetImpl.getProjet("P004");

        // Assert
        assertNotNull("Le projet devrait être accepté", resultat);
        assertEquals("", resultat.getNom());
    }

    /**
     * Test : Ajouter plusieurs projets (test de volume).
     */
    @Test
    public void testAjouterMultiplesProjets() {
        // Arrange & Act
        for (int i = 1; i <= 5; i++) {
            Projet p = new Projet("P" + String.format("%03d", i),
                    "Projet " + i,
                    "Description " + i);
            projetImpl.ajouterProjet(p);
        }

        // Assert
        List<Projet> projets = projetImpl.getProjets();
        assertEquals("Devrait contenir 5 projets", 5, projets.size());
    }

    /**
     * Test : Modifier le nom d'un projet existant.
     */
    @Test
    public void testModifierNomProjet() {
        // Arrange
        Projet projet = new Projet("P005", "Ancien Nom", "Description");
        projetImpl.ajouterProjet(projet);

        // Act
        Projet recup = projetImpl.getProjet("P005");
        recup.setNom("Nouveau Nom");

        // Assert
        Projet resultat = projetImpl.getProjet("P005");
        assertEquals("Nouveau Nom", resultat.getNom());
    }

    /**
     * Test : Modifier la description d'un projet existant.
     */
    @Test
    public void testModifierDescriptionProjet() {
        // Arrange
        Projet projet = new Projet("P006", "Projet Test", "Ancienne Description");
        projetImpl.ajouterProjet(projet);

        // Act
        Projet recup = projetImpl.getProjet("P006");
        recup.setDescription("Nouvelle Description");

        // Assert
        Projet resultat = projetImpl.getProjet("P006");
        assertEquals("Nouvelle Description", resultat.getDescription());
    }

    /**
     * Test : Ajouter un projet avec ID très long.
     */
    @Test
    public void testAjouterProjetIdTresLong() {
        // Arrange
        String idLong = "P" + "0".repeat(100);
        Projet projet = new Projet(idLong, "Projet Test", "Description");

        // Act
        projetImpl.ajouterProjet(projet);
        Projet resultat = projetImpl.getProjet(idLong);

        // Assert
        assertNotNull("Le projet avec ID long devrait être accepté", resultat);
        assertEquals(idLong, resultat.getId());
    }

    /**
     * Test : Ajouter un projet en double (même ID).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterProjetDoublon() {
        // Arrange
        Projet p1 = new Projet("P007", "Premier", "Description 1");
        Projet p2 = new Projet("P007", "Deuxième", "Description 2");

        // Act
        projetImpl.ajouterProjet(p1);
        projetImpl.ajouterProjet(p2);

        // Assert
        Projet resultat = projetImpl.getProjet("P007");
        assertNotNull("Un projet P007 devrait exister", resultat);
        // Le comportement dépend de l'implémentation (écrasement ou refus)
    }

    /**
     * Test : Récupération après ajouts et modifications multiples.
     */
    @Test
    public void testIntegrationProjetComplet() {
        // Arrange
        Projet p1 = new Projet("P008", "Projet 1", "Desc 1");
        Projet p2 = new Projet("P009", "Projet 2", "Desc 2");

        // Act
        projetImpl.ajouterProjet(p1);
        projetImpl.ajouterProjet(p2);

        Projet recup1 = projetImpl.getProjet("P008");
        recup1.setNom("Projet 1 Modifié");

        // Assert
        List<Projet> projets = projetImpl.getProjets();
        assertEquals("Devrait contenir 2 projets", 2, projets.size());

        Projet resultat = projetImpl.getProjet("P008");
        assertEquals("Projet 1 Modifié", resultat.getNom());
    }

    /**
     * Test : Ajouter un projet avec description très longue.
     */
    @Test
    public void testAjouterProjetDescriptionTresLongue() {
        // Arrange
        String descLongue = "Description très longue. ".repeat(100);
        Projet projet = new Projet("P010", "Projet Test", descLongue);

        // Act
        projetImpl.ajouterProjet(projet);
        Projet resultat = projetImpl.getProjet("P010");

        // Assert
        assertNotNull("Le projet devrait être accepté", resultat);
        assertTrue("La description devrait être longue",
                resultat.getDescription().length() > 1000);
    }
    /**
     * Test : getProjet avec ID null.
     */
    @Test
    public void testGetProjetIdNull() {
        Projet resultat = projetImpl.getProjet(null);
        assertNull("Devrait retourner null pour ID null", resultat);
    }

    /**
     * Test : getProjet avec ID vide.
     */
    @Test
    public void testGetProjetIdVide() {
        Projet resultat = projetImpl.getProjet("");
        assertNull("Devrait retourner null pour ID vide", resultat);
    }

    /**
     * Test : getProjet avec ID contenant espaces uniquement.
     */
    @Test
    public void testGetProjetIdEspaces() {
        Projet resultat = projetImpl.getProjet("   ");
        assertNull("Devrait retourner null pour ID avec espaces", resultat);
    }
}
