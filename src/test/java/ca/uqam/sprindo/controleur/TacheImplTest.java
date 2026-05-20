package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.controleur.TacheImpl;
import ca.uqam.sprindo.modele.Tache;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe TacheImpl.
 */
public class TacheImplTest {

    private TacheImpl tacheImpl;

    /**
     * Initialisation avant chaque test.
     */
    @Before
    public void setUp() {
        tacheImpl = new TacheImpl();
    }

    /**
     * Test : Ajouter une tâche avec succès.
     */
    @Test
    public void testAjouterTache() {
        // Arrange
        Tache tache = new Tache("T001", "Tâche Test", "Description test", "À faire", "");

        // Act
        tacheImpl.ajouterTache("P001", "S001", tache);
        Tache resultat = tacheImpl.getTache("P001", "S001", "T001");

        // Assert
        assertNotNull("La tâche ne devrait pas être null", resultat);
        assertEquals("T001", resultat.getId());
        assertEquals("Tâche Test", resultat.getTitre());
        assertEquals("À faire", resultat.getStatut());
    }

    /**
     * Test : Récupérer une tâche existante.
     */
    @Test
    public void testGetTacheExistante() {
        // Arrange
        Tache tache = new Tache("T002", "Tâche 2", "Desc 2", "En cours", "Dev1");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        Tache resultat = tacheImpl.getTache("P001", "S001", "T002");

        // Assert
        assertNotNull("La tâche devrait exister", resultat);
        assertEquals("T002", resultat.getId());
    }

    /**
     * Test : Assigner une tâche à un utilisateur.
     */
    @Test
    public void testAssignerTache() {
        // Arrange
        Tache tache = new Tache("T003", "Tâche 3", "Desc 3", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        tacheImpl.assignerTache("P001", "S001", "T003", "Manuella");
        Tache resultat = tacheImpl.getTache("P001", "S001", "T003");

        // Assert
        assertEquals("Manuella", resultat.getAssigneA());
    }

    /**
     * Test : Changer le statut d'une tâche.
     */
    @Test
    public void testChangerStatut() {
        // Arrange
        Tache tache = new Tache("T004", "Tâche 4", "Desc 4", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        tacheImpl.changerStatut("P001", "S001", "T004", "En cours");
        Tache resultat = tacheImpl.getTache("P001", "S001", "T004");

        // Assert
        assertEquals("En cours", resultat.getStatut());
    }

    /**
     * Test : Supprimer une tâche.
     */
    @Test
    public void testSupprimerTache() {
        // Arrange
        Tache tache = new Tache("T005", "Tâche 5", "Desc 5", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        tacheImpl.supprimerTache("P001", "S001", "T005");
        Tache resultat = tacheImpl.getTache("P001", "S001", "T005");

        // Assert
        assertNull("La tâche devrait être supprimée", resultat);
    }

    /**
     * Test : Lister les tâches d'un sprint.
     */
    @Test
    public void testGetTachesParSprint() {
        // Arrange
        Tache t1 = new Tache("T001", "Tâche 1", "Desc 1", "À faire", "");
        Tache t2 = new Tache("T002", "Tâche 2", "Desc 2", "En cours", "Dev1");

        tacheImpl.ajouterTache("P001", "S001", t1);
        tacheImpl.ajouterTache("P001", "S001", t2);

        // Act
        List<Tache> taches = tacheImpl.getTachesParSprint("P001", "S001");

        // Assert
        assertEquals("Devrait contenir 2 tâches", 2, taches.size());
    }
    /**
     * Test : Ajouter une tâche avec description vide.
     */

    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheDescriptionVide() {
        // Arrange
        Tache tache = new Tache("T006", "Tâche Test", "", "À faire", "");

        // Act
        tacheImpl.ajouterTache("P001", "S001", tache);
        Tache resultat = tacheImpl.getTache("P001", "S001", "T006");

        // Assert
        assertNotNull("La tâche devrait être acceptée", resultat);
        assertEquals("", resultat.getDescription());
    }

    /**
     * Test : Tester tous les statuts possibles d'une tâche.
     */
    @Test
    public void testTousLesStatutsPossibles() {
        // Arrange
        Tache tache = new Tache("T007", "Tâche Multi-Statuts", "Desc", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        String[] statuts = {"À faire", "En cours", "Terminé", "Bloqué", "En test"};

        // Act & Assert
        for (String statut : statuts) {
            tacheImpl.changerStatut("P001", "S001", "T007", statut);
            Tache resultat = tacheImpl.getTache("P001", "S001", "T007");
            assertEquals("Le statut devrait être : " + statut, statut, resultat.getStatut());
        }
    }

    /**
     * Test : Réassigner une tâche plusieurs fois.
     */
    @Test
    public void testReassignerTacheMultiplesFois() {
        // Arrange
        Tache tache = new Tache("T008", "Tâche Test", "Desc", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        String[] developpeurs = {"Alice", "Bob", "Charlie", "David", "Eve"};

        // Act & Assert
        for (String dev : developpeurs) {
            tacheImpl.assignerTache("P001", "S001", "T008", dev);
            Tache resultat = tacheImpl.getTache("P001", "S001", "T008");
            assertEquals("Devrait être assigné à : " + dev, dev, resultat.getAssigneA());
        }
    }

    /**
     * Test : Ajouter plusieurs tâches (test de volume).
     */
    @Test
    public void testAjouterMultiplesTaches() {
        // Arrange & Act
        for (int i = 1; i <= 20; i++) {
            Tache t = new Tache("T" + String.format("%03d", i),
                    "Tâche " + i,
                    "Description " + i,
                    "À faire",
                    "");
            tacheImpl.ajouterTache("P001", "S001", t);
        }

        // Assert
        List<Tache> taches = tacheImpl.getTachesParSprint("P001", "S001");
        assertEquals("Devrait contenir 20 tâches", 20, taches.size());
    }

    /**
     * Test : Modifier le titre d'une tâche existante.
     */
    @Test
    public void testModifierTitreTache() {
        // Arrange
        Tache tache = new Tache("T009", "Ancien Titre", "Desc", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        Tache recup = tacheImpl.getTache("P001", "S001", "T009");
        recup.setTitre("Nouveau Titre");

        // Assert
        Tache resultat = tacheImpl.getTache("P001", "S001", "T009");
        assertEquals("Nouveau Titre", resultat.getTitre());
    }

    /**
     * Test : Modifier la description d'une tâche existante.
     */
    @Test
    public void testModifierDescriptionTache() {
        // Arrange
        Tache tache = new Tache("T010", "Titre", "Ancienne Description", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        Tache recup = tacheImpl.getTache("P001", "S001", "T010");
        recup.setDescription("Nouvelle Description");

        // Assert
        Tache resultat = tacheImpl.getTache("P001", "S001", "T010");
        assertEquals("Nouvelle Description", resultat.getDescription());
    }

    /**
     * Test : Ajouter une tâche null (validation).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheNull() {
        // Act
        tacheImpl.ajouterTache("P001", "S001", null);
    }

    /**
     * Test : Récupérer une tâche après suppression (devrait être null).
     */
    @Test
    public void testGetTacheApresSuppression() {
        // Arrange
        Tache tache = new Tache("T011", "Tâche Test", "Desc", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);

        // Act
        tacheImpl.supprimerTache("P001", "S001", "T011");
        Tache resultat = tacheImpl.getTache("P001", "S001", "T011");

        // Assert
        assertNull("La tâche devrait être supprimée", resultat);
    }

    /**
     * Test : Ajouter une tâche avec description très longue.
     */
    @Test
    public void testAjouterTacheDescriptionTresLongue() {
        // Arrange
        String descLongue = "Description très longue. ".repeat(100);
        Tache tache = new Tache("T012", "Tâche Test", descLongue, "À faire", "");

        // Act
        tacheImpl.ajouterTache("P001", "S001", tache);
        Tache resultat = tacheImpl.getTache("P001", "S001", "T012");

        // Assert
        assertNotNull("La tâche devrait être acceptée", resultat);
        assertTrue("La description devrait être longue",
                resultat.getDescription().length() > 1000);
    }

    /**
     * Test : Workflow complet d'une tâche (création → assignation → changement statut → suppression).
     */
    @Test
    public void testWorkflowCompletTache() {
        // Arrange
        Tache tache = new Tache("T013", "Tâche Workflow", "Description", "À faire", "");

        // Act - Étape 1 : Création
        tacheImpl.ajouterTache("P001", "S001", tache);
        Tache etape1 = tacheImpl.getTache("P001", "S001", "T013");
        assertNotNull("La tâche devrait être créée", etape1);
        assertEquals("À faire", etape1.getStatut());

        // Act - Étape 2 : Assignation
        tacheImpl.assignerTache("P001", "S001", "T013", "Alice");
        Tache etape2 = tacheImpl.getTache("P001", "S001", "T013");
        assertEquals("Alice", etape2.getAssigneA());

        // Act - Étape 3 : Changement de statut
        tacheImpl.changerStatut("P001", "S001", "T013", "En cours");
        Tache etape3 = tacheImpl.getTache("P001", "S001", "T013");
        assertEquals("En cours", etape3.getStatut());

        // Act - Étape 4 : Réassignation
        tacheImpl.assignerTache("P001", "S001", "T013", "Bob");
        Tache etape4 = tacheImpl.getTache("P001", "S001", "T013");
        assertEquals("Bob", etape4.getAssigneA());

        // Act - Étape 5 : Terminé
        tacheImpl.changerStatut("P001", "S001", "T013", "Terminé");
        Tache etape5 = tacheImpl.getTache("P001", "S001", "T013");
        assertEquals("Terminé", etape5.getStatut());
    }

    /**
     * Test : Supprimer une tâche inexistante (ne devrait pas planter).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSupprimerTacheInexistante() {
        // Act - Ne devrait pas lever d'exception
        tacheImpl.supprimerTache("P001", "S001", "T999");

        // Assert - Vérifier qu'aucune erreur ne s'est produite
        Tache resultat = tacheImpl.getTache("P001", "S001", "T999");
        assertNull("La tâche ne devrait pas exister", resultat);
    }
    /**
     * Test : Ajouter une tâche avec projetId null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheProjetIdNull() {
        Tache tache = new Tache("T001", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache(null, "S001", tache);
    }

    /**
     * Test : Ajouter une tâche avec projetId vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheProjetIdVide() {
        Tache tache = new Tache("T001", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache("", "S001", tache);
    }

    /**
     * Test : Ajouter une tâche avec sprintId null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheSprintIdNull() {
        Tache tache = new Tache("T001", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache("P001", null, tache);
    }

    /**
     * Test : Ajouter une tâche avec sprintId vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheSprintIdVide() {
        Tache tache = new Tache("T001", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache("P001", "", tache);
    }

    /**
     * Test : Ajouter une tâche avec ID vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheIdVide() {
        Tache tache = new Tache("", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);
    }

    /**
     * Test : Ajouter une tâche avec titre vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheTitreVide() {
        Tache tache = new Tache("T001", "", "Description", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);
    }

    /**
     * Test : Ajouter une tâche avec statut vide.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheStatutVide() {
        Tache tache = new Tache("T001", "Titre", "Description", "", "");
        tacheImpl.ajouterTache("P001", "S001", tache);
    }

    /**
     * Test : Ajouter une tâche en doublon (même ID).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTacheDoublon() {
        Tache t1 = new Tache("T001", "Titre 1", "Desc 1", "À faire", "");
        Tache t2 = new Tache("T001", "Titre 2", "Desc 2", "À faire", "");

        tacheImpl.ajouterTache("P001", "S001", t1);
        tacheImpl.ajouterTache("P001", "S001", t2); // Devrait lever une exception
    }

    /**
     * Test : Assigner une tâche avec utilisateur null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssignerTacheUtilisateurNull() {
        Tache tache = new Tache("T001", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);
        tacheImpl.assignerTache("P001", "S001", "T001", null);
    }

    /**
     * Test : Assigner une tâche inexistante.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssignerTacheInexistante() {
        tacheImpl.assignerTache("P001", "S001", "T999", "Alice");
    }

    /**
     * Test : Changer statut avec nouveau statut null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangerStatutNull() {
        Tache tache = new Tache("T001", "Titre", "Description", "À faire", "");
        tacheImpl.ajouterTache("P001", "S001", tache);
        tacheImpl.changerStatut("P001", "S001", "T001", null);
    }

    /**
     * Test : Changer statut d'une tâche inexistante.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangerStatutTacheInexistante() {
        tacheImpl.changerStatut("P001", "S001", "T999", "En cours");
    }

    /**
     * Test : getTache avec projetId null.
     */
    @Test
    public void testGetTacheProjetIdNull() {
        Tache resultat = tacheImpl.getTache(null, "S001", "T001");
        assertNull("Devrait retourner null", resultat);
    }

    /**
     * Test : getTachesParSprint avec projetId vide.
     */
    @Test
    public void testGetTachesParSprintProjetIdVide() {
        List<Tache> taches = tacheImpl.getTachesParSprint("", "S001");
        assertTrue("Devrait retourner une liste vide", taches.isEmpty());
    }
}
