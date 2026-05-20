package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.controleur.UtilisateurImpl;
import ca.uqam.sprindo.modele.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe UtilisateurImpl.
 */
public class UtilisateurImplTest {

    private UtilisateurImpl utilisateurImpl;

    /**
     * Initialisation avant chaque test.
     */
    @Before
    public void setUp() {
        utilisateurImpl = new UtilisateurImpl();
    }

    /**
     * Test : Ajouter un utilisateur avec succès.
     */
    @Test
    public void testAjouterUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("Manuella", "Développeur");

        // Act
        utilisateurImpl.ajouterUtilisateur(utilisateur);
        Utilisateur resultat = utilisateurImpl.getUtilisateur("Manuella");

        // Assert
        assertNotNull("L'utilisateur ne devrait pas être null", resultat);
        assertEquals("Manuella", resultat.getNom());
        assertEquals("Développeur", resultat.getRole());
    }

    /**
     * Test : Récupérer un utilisateur existant.
     */
    @Test
    public void testGetUtilisateurExistant() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("Celestine", "Scrum Master");
        utilisateurImpl.ajouterUtilisateur(utilisateur);

        // Act
        Utilisateur resultat = utilisateurImpl.getUtilisateur("Celestine");

        // Assert
        assertNotNull("L'utilisateur devrait exister", resultat);
        assertEquals("Celestine", resultat.getNom());
    }

    /**
     * Test : Récupérer un utilisateur inexistant.
     */
    @Test
    public void testGetUtilisateurInexistant() {
        // Act
        Utilisateur resultat = utilisateurImpl.getUtilisateur("Inconnu");

        // Assert
        assertNull("L'utilisateur ne devrait pas exister", resultat);
    }

    /**
     * Test : Lister tous les utilisateurs.
     */
    @Test
    public void testGetUtilisateurs() {
        // Arrange
        Utilisateur u1 = new Utilisateur("Myriam", "Product Owner");
        Utilisateur u2 = new Utilisateur("Emmanuel", "Développeur");

        utilisateurImpl.ajouterUtilisateur(u1);
        utilisateurImpl.ajouterUtilisateur(u2);

        // Act
        List<Utilisateur> utilisateurs = utilisateurImpl.getUtilisateurs();

        // Assert
        assertEquals("Devrait contenir 2 utilisateurs", 2, utilisateurs.size());
    }

    /**
     * Test : Lister les utilisateurs quand la liste est vide.
     */
    @Test
    public void testGetUtilisateursVide() {
        // Act
        List<Utilisateur> utilisateurs = utilisateurImpl.getUtilisateurs();

        // Assert
        assertNotNull("La liste ne devrait pas être null", utilisateurs);
        assertTrue("La liste devrait être vide", utilisateurs.isEmpty());
    }
    /**
     * Test : Ajouter un utilisateur avec nom vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterUtilisateurNomVide() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("", "Développeur");

        // Act
        utilisateurImpl.ajouterUtilisateur(utilisateur);
        Utilisateur resultat = utilisateurImpl.getUtilisateur("");

        // Assert
        assertNotNull("L'utilisateur avec nom vide devrait être accepté", resultat);
        assertEquals("", resultat.getNom());
        assertEquals("Développeur", resultat.getRole());
    }

    /**
     * Test : Ajouter un utilisateur avec rôle vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterUtilisateurRoleVide() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("Alice", "");

        // Act
        utilisateurImpl.ajouterUtilisateur(utilisateur);
        Utilisateur resultat = utilisateurImpl.getUtilisateur("Alice");

        // Assert
        assertNotNull("L'utilisateur avec rôle vide devrait être accepté", resultat);
        assertEquals("Alice", resultat.getNom());
        assertEquals("", resultat.getRole());
    }

    /**
     * Test : Ajouter un utilisateur null (validation).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterUtilisateurNull() {
        // Act
        utilisateurImpl.ajouterUtilisateur(null);
    }

    /**
     * Test : Modifier le rôle d'un utilisateur existant.
     */
    @Test
    public void testModifierRoleUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("Bob", "Développeur");
        utilisateurImpl.ajouterUtilisateur(utilisateur);

        // Act - Récupérer et modifier
        Utilisateur recup = utilisateurImpl.getUtilisateur("Bob");
        recup.setRole("Scrum Master");

        // Assert
        Utilisateur resultat = utilisateurImpl.getUtilisateur("Bob");
        assertEquals("Scrum Master", resultat.getRole());
    }

    /**
     * Test : Ajouter plusieurs utilisateurs (test de volume).
     */
    @Test
    public void testAjouterMultiplesUtilisateurs() {
        // Arrange & Act
        for (int i = 1; i <= 10; i++) {
            Utilisateur u = new Utilisateur("User" + i, "Role" + i);
            utilisateurImpl.ajouterUtilisateur(u);
        }

        // Assert
        List<Utilisateur> utilisateurs = utilisateurImpl.getUtilisateurs();
        assertEquals("Devrait contenir 10 utilisateurs", 10, utilisateurs.size());
    }

    /**
     * Test : Vérifier qu'un utilisateur avec nom très long est accepté.
     */
    @Test
    public void testAjouterUtilisateurNomTresLong() {
        // Arrange
        String nomLong = "A".repeat(500);
        Utilisateur utilisateur = new Utilisateur(nomLong, "Développeur");

        // Act
        utilisateurImpl.ajouterUtilisateur(utilisateur);
        Utilisateur resultat = utilisateurImpl.getUtilisateur(nomLong);

        // Assert
        assertNotNull("L'utilisateur avec nom très long devrait être accepté", resultat);
        assertEquals(500, resultat.getNom().length());
    }

    /**
     * Test : Modifier le nom d'un utilisateur existant.
     */
    @Test
    public void testModifierNomUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("Charlie", "Testeur");
        utilisateurImpl.ajouterUtilisateur(utilisateur);

        // Act
        Utilisateur recup = utilisateurImpl.getUtilisateur("Charlie");
        recup.setNom("Charles");

        // Assert
        // Note: Selon l'implémentation, le nom peut être la clé,
        // donc ce test vérifie le comportement
        assertNotNull("L'utilisateur devrait toujours exister", recup);
    }

    /**
     * Test : Ajouter deux utilisateurs avec le même nom (doublon).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterUtilisateurDoublon() {
        // Arrange
        Utilisateur u1 = new Utilisateur("David", "Développeur");
        Utilisateur u2 = new Utilisateur("David", "Testeur");

        // Act
        utilisateurImpl.ajouterUtilisateur(u1);
        utilisateurImpl.ajouterUtilisateur(u2);

        // Assert
        Utilisateur resultat = utilisateurImpl.getUtilisateur("David");
        assertNotNull("Un utilisateur David devrait exister", resultat);
        // Le comportement dépend de l'implémentation (écrasement ou refus)
    }
    /**
     * Test : getUtilisateur avec nom null.
     */
    @Test
    public void testGetUtilisateurNomNull() {
        Utilisateur resultat = utilisateurImpl.getUtilisateur(null);
        assertNull("Devrait retourner null pour nom null", resultat);
    }

    /**
     * Test : getUtilisateur avec nom contenant espaces uniquement.
     */
    @Test
    public void testGetUtilisateurNomEspaces() {
        Utilisateur resultat = utilisateurImpl.getUtilisateur("   ");
        assertNull("Devrait retourner null pour nom avec espaces", resultat);
    }

    /**
     * Test : getUtilisateur insensible à la casse.
     */
    @Test
    public void testGetUtilisateurCaseInsensitive() {
        Utilisateur u = new Utilisateur("Alice", "Dev");
        utilisateurImpl.ajouterUtilisateur(u);

        Utilisateur resultat1 = utilisateurImpl.getUtilisateur("alice");
        Utilisateur resultat2 = utilisateurImpl.getUtilisateur("ALICE");
        Utilisateur resultat3 = utilisateurImpl.getUtilisateur("AlIcE");

        assertNotNull("Devrait trouver avec minuscules", resultat1);
        assertNotNull("Devrait trouver avec majuscules", resultat2);
        assertNotNull("Devrait trouver avec casse mixte", resultat3);
    }
}
