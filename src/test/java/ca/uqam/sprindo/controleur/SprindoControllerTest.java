package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Projet;
import ca.uqam.sprindo.modele.Sprint;
import ca.uqam.sprindo.modele.Tache;
import ca.uqam.sprindo.vue.SprindoVue;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests complets pour SprindoController avec mocks injectés par reflection.
 */
public class SprindoControllerTest {

    private SprindoController controller;
    private ProjetImpl projetMock;
    private SprintImpl sprintMock;
    private TacheImpl tacheMock;
    private UtilisateurImpl utilisateurMock;
    private SprindoVue vueMock;
    private Statistiques statistiquesMock;

    @Before
    public void setUp() throws Exception {
        // Créer le controller
        controller = new SprindoController();

        // Créer les mocks
        projetMock = mock(ProjetImpl.class);
        sprintMock = mock(SprintImpl.class);
        tacheMock = mock(TacheImpl.class);
        utilisateurMock = mock(UtilisateurImpl.class);
        vueMock = mock(SprindoVue.class);
        statistiquesMock = mock(Statistiques.class);

        // Injection par reflection (accès aux champs privés)
        setPrivateField(controller, "projetImpl", projetMock);
        setPrivateField(controller, "sprintImpl", sprintMock);
        setPrivateField(controller, "tacheImpl", tacheMock);
        setPrivateField(controller, "utilisateurImpl", utilisateurMock);
        setPrivateField(controller, "vue", vueMock);
        setPrivateField(controller, "statistiques", statistiquesMock);
    }

    /**
     * Méthode utilitaire pour injecter des champs privés via reflection.
     */
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    // ================================================================
    // TESTS : ajouterProjet()
    // ================================================================

    @Test
    public void testAjouterProjet_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderNomProjet()).thenReturn("Projet Test");
        when(vueMock.demanderDescriptionProjet()).thenReturn("Description");

        controller.ajouterProjet();

        verify(projetMock).ajouterProjet(any(Projet.class));
        verify(vueMock).afficherMessage(contains("succès"));
    }

    @Test
    public void testAjouterProjet_ExceptionIllegalArgument() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderNomProjet()).thenReturn("Test");
        when(vueMock.demanderDescriptionProjet()).thenReturn("Desc");
        doThrow(new IllegalArgumentException("ID invalide"))
                .when(projetMock).ajouterProjet(any(Projet.class));

        controller.ajouterProjet();

        verify(vueMock).afficherErreur("ID invalide");
    }

    @Test
    public void testAjouterProjet_ExceptionGenerique() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderNomProjet()).thenReturn("Test");
        when(vueMock.demanderDescriptionProjet()).thenReturn("Desc");
        doThrow(new RuntimeException("Erreur système"))
                .when(projetMock).ajouterProjet(any(Projet.class));

        controller.ajouterProjet();

        verify(vueMock).afficherErreur(contains("Erreur inattendue"));
    }

    // ================================================================
    // TESTS : afficherProjets()
    // ================================================================

    @Test
    public void testAfficherProjets_Succes() {
        List<Projet> projets = new ArrayList<>();
        projets.add(new Projet("P1", "Projet 1", "Description 1"));
        when(projetMock.getProjets()).thenReturn(projets);

        controller.afficherProjets();

        verify(vueMock).afficherProjets(projets);
    }

    @Test
    public void testAfficherProjets_ListeVide() {
        when(projetMock.getProjets()).thenReturn(new ArrayList<>());

        controller.afficherProjets();

        verify(vueMock).afficherProjets(any());
    }

    @Test
    public void testAfficherProjets_Exception() {
        when(projetMock.getProjets()).thenThrow(new RuntimeException("Erreur BD"));

        controller.afficherProjets();

        verify(vueMock).afficherErreur(contains("Erreur"));
    }

    // ================================================================
    // TESTS : ajouterSprint()
    // ================================================================

    @Test
    public void testAjouterSprint_ProjetInexistant() {
        when(vueMock.demanderIdProjet()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("INEXISTANT")).thenReturn(null);

        controller.ajouterSprint();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAjouterSprint_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderNumeroSprint()).thenReturn(1);
        when(vueMock.demanderObjectifSprint()).thenReturn("Objectif");
        when(vueMock.demanderDateDebut()).thenReturn(LocalDate.of(2025, 12, 1));
        when(vueMock.demanderDateFin()).thenReturn(LocalDate.of(2025, 12, 15));

        controller.ajouterSprint();

        verify(sprintMock).ajouterSprint(eq("P1"), any(Sprint.class));
        verify(vueMock).afficherMessage(contains("succès"));
    }

    @Test
    public void testAjouterSprint_ExceptionIllegalArgument() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderNumeroSprint()).thenReturn(1);
        when(vueMock.demanderObjectifSprint()).thenReturn("Obj");
        when(vueMock.demanderDateDebut()).thenReturn(LocalDate.of(2025, 12, 1));
        when(vueMock.demanderDateFin()).thenReturn(LocalDate.of(2025, 12, 15));
        doThrow(new IllegalArgumentException("Dates invalides"))
                .when(sprintMock).ajouterSprint(eq("P1"), any(Sprint.class));

        controller.ajouterSprint();

        verify(vueMock).afficherErreur("Dates invalides");
    }

    // ================================================================
    // TESTS : afficherSprints()
    // ================================================================

    @Test
    public void testAfficherSprints_ProjetInexistant() {
        when(vueMock.demanderIdProjet()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("INEXISTANT")).thenReturn(null);

        controller.afficherSprints();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAfficherSprints_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        List<Sprint> sprints = new ArrayList<>();
        sprints.add(new Sprint("S1", 1, "Objectif", 
                LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 15)));
        when(sprintMock.getSprintsParProjet("P1")).thenReturn(sprints);

        controller.afficherSprints();

        verify(vueMock).afficherSprints(sprints, "P1");
    }

    // ================================================================
    // TESTS : ajouterTache()
    // ================================================================

    @Test
    public void testAjouterTache_ProjetInexistant() {
        when(vueMock.demanderIdProjet()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("INEXISTANT")).thenReturn(null);

        controller.ajouterTache();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAjouterTache_SprintInexistant() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        when(sprintMock.getSprint("P1", "INEXISTANT")).thenReturn(null);

        controller.ajouterTache();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAjouterTache_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        when(sprintMock.getSprint("P1", "S1")).thenReturn(
                new Sprint("S1", 1, "Obj", LocalDate.now(), LocalDate.now().plusDays(7)));
        when(vueMock.demanderIdTache()).thenReturn("T1");
        when(vueMock.demanderTitreTache()).thenReturn("Titre");
        when(vueMock.demanderDescriptionTache()).thenReturn("Description");
        when(vueMock.demanderStatutTache()).thenReturn("TODO");

        controller.ajouterTache();

        verify(tacheMock).ajouterTache(eq("P1"), eq("S1"), any(Tache.class));
        verify(vueMock).afficherMessage(contains("succès"));
    }

    // ================================================================
    // TESTS : afficherTaches()
    // ================================================================

    @Test
    public void testAfficherTaches_ProjetInexistant() {
        when(vueMock.demanderIdProjet()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("INEXISTANT")).thenReturn(null);

        controller.afficherTaches();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAfficherTaches_SprintInexistant() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        when(sprintMock.getSprint("P1", "INEXISTANT")).thenReturn(null);

        controller.afficherTaches();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAfficherTaches_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet", "Desc"));
        when(sprintMock.getSprint("P1", "S1")).thenReturn(
                new Sprint("S1", 1, "Obj", LocalDate.now(), LocalDate.now().plusDays(7)));
        List<Tache> taches = new ArrayList<>();
        taches.add(new Tache("T1", "Titre", "Description", "TODO", null));
        when(tacheMock.getTachesParSprint("P1", "S1")).thenReturn(taches);

        controller.afficherTaches();

        verify(vueMock).afficherTaches(taches, "P1", "S1");
    }

    // ================================================================
    // TESTS : assignerTache()
    // ================================================================

    @Test
    public void testAssignerTache_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderIdTache()).thenReturn("T1");
        when(vueMock.demanderNomUtilisateur()).thenReturn("Jean");

        controller.assignerTache();

        verify(tacheMock).assignerTache("P1", "S1", "T1", "Jean");
        verify(vueMock).afficherMessage(contains("Jean"));
    }

    @Test
    public void testAssignerTache_Exception() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderIdTache()).thenReturn("T1");
        when(vueMock.demanderNomUtilisateur()).thenReturn("Jean");
        doThrow(new IllegalArgumentException("Tâche introuvable"))
                .when(tacheMock).assignerTache("P1", "S1", "T1", "Jean");

        controller.assignerTache();

        verify(vueMock).afficherErreur("Tâche introuvable");
    }

    // ================================================================
    // TESTS : modifierStatutTache()
    // ================================================================

    @Test
    public void testModifierStatutTache_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderIdTache()).thenReturn("T1");
        when(vueMock.demanderStatutTache()).thenReturn("DONE");

        controller.modifierStatutTache();

        verify(tacheMock).changerStatut("P1", "S1", "T1", "DONE");
        verify(vueMock).afficherMessage(contains("modifié"));
    }

    @Test
    public void testModifierStatutTache_Exception() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderIdTache()).thenReturn("T1");
        when(vueMock.demanderStatutTache()).thenReturn("INVALID");
        doThrow(new IllegalArgumentException("Statut invalide"))
                .when(tacheMock).changerStatut("P1", "S1", "T1", "INVALID");

        controller.modifierStatutTache();

        verify(vueMock).afficherErreur("Statut invalide");
    }

    // ================================================================
    // TESTS : supprimerTache()
    // ================================================================

    @Test
    public void testSupprimerTache_Succes() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderIdTache()).thenReturn("T1");

        controller.supprimerTache();

        verify(tacheMock).supprimerTache("P1", "S1", "T1");
        verify(vueMock).afficherMessage(contains("supprimée"));
    }

    @Test
    public void testSupprimerTache_Exception() {
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(vueMock.demanderIdTache()).thenReturn("T1");
        doThrow(new IllegalArgumentException("Tâche introuvable"))
                .when(tacheMock).supprimerTache("P1", "S1", "T1");

        controller.supprimerTache();

        verify(vueMock).afficherErreur("Tâche introuvable");
    }

    // ================================================================
    // TESTS : afficherStatistiques()
    // ================================================================

    @Test
    public void testAfficherStatistiques_AucunProjet() {
        when(projetMock.getProjets()).thenReturn(new ArrayList<>());

        controller.afficherStatistiques();

        verify(vueMock).afficherErreur(contains("Aucun projet"));
    }

    @Test
    public void testAfficherStatistiques_ProjetsNull() {
        when(projetMock.getProjets()).thenReturn(null);

        controller.afficherStatistiques();

        verify(vueMock).afficherErreur(contains("Aucun projet"));
    }

    @Test
    public void testAfficherStatistiques_ProjetInexistant() {
        List<Projet> projets = new ArrayList<>();
        projets.add(new Projet("P1", "Projet 1", "Desc"));
        when(projetMock.getProjets()).thenReturn(projets);
        when(vueMock.demanderIdProjet()).thenReturn("INEXISTANT");
        when(projetMock.getProjet("INEXISTANT")).thenReturn(null);

        controller.afficherStatistiques();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAfficherStatistiques_SuccesSansSprintDetail() {
        List<Projet> projets = new ArrayList<>();
        projets.add(new Projet("P1", "Projet 1", "Desc"));
        when(projetMock.getProjets()).thenReturn(projets);
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet 1", "Desc"));
        when(statistiquesMock.genererRapportProjet("P1")).thenReturn("Rapport projet");
        when(sprintMock.getSprintsParProjet("P1")).thenReturn(new ArrayList<>());

        controller.afficherStatistiques();

        verify(statistiquesMock).genererRapportProjet("P1");
        verify(vueMock, atLeastOnce()).afficherMessage(anyString());
    }

    @Test
    public void testAfficherStatistiques_AvecSprints_ChoixNon() {
        List<Projet> projets = new ArrayList<>();
        projets.add(new Projet("P1", "Projet 1", "Desc"));
        List<Sprint> sprints = new ArrayList<>();
        sprints.add(new Sprint("S1", 1, "Obj", LocalDate.now(), LocalDate.now().plusDays(7)));
        
        when(projetMock.getProjets()).thenReturn(projets);
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet 1", "Desc"));
        when(statistiquesMock.genererRapportProjet("P1")).thenReturn("Rapport projet");
        when(sprintMock.getSprintsParProjet("P1")).thenReturn(sprints);
        when(vueMock.lireChoixMenu()).thenReturn(2); // Non

        controller.afficherStatistiques();

        verify(statistiquesMock).genererRapportProjet("P1");
        verify(statistiquesMock, never()).genererRapportSprint(anyString(), anyString());
    }

    @Test
    public void testAfficherStatistiques_AvecSprints_ChoixOui_SprintInexistant() {
        List<Projet> projets = new ArrayList<>();
        projets.add(new Projet("P1", "Projet 1", "Desc"));
        List<Sprint> sprints = new ArrayList<>();
        sprints.add(new Sprint("S1", 1, "Obj", LocalDate.now(), LocalDate.now().plusDays(7)));
        
        when(projetMock.getProjets()).thenReturn(projets);
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet 1", "Desc"));
        when(statistiquesMock.genererRapportProjet("P1")).thenReturn("Rapport projet");
        when(sprintMock.getSprintsParProjet("P1")).thenReturn(sprints);
        when(vueMock.lireChoixMenu()).thenReturn(1); // Oui
        when(vueMock.demanderIdSprint()).thenReturn("INEXISTANT");
        when(sprintMock.getSprint("P1", "INEXISTANT")).thenReturn(null);

        controller.afficherStatistiques();

        verify(vueMock).afficherErreur(contains("introuvable"));
    }

    @Test
    public void testAfficherStatistiques_AvecSprints_ChoixOui_Succes() {
        List<Projet> projets = new ArrayList<>();
        projets.add(new Projet("P1", "Projet 1", "Desc"));
        List<Sprint> sprints = new ArrayList<>();
        Sprint sprint = new Sprint("S1", 1, "Obj", LocalDate.now(), LocalDate.now().plusDays(7));
        sprints.add(sprint);
        
        when(projetMock.getProjets()).thenReturn(projets);
        when(vueMock.demanderIdProjet()).thenReturn("P1");
        when(projetMock.getProjet("P1")).thenReturn(new Projet("P1", "Projet 1", "Desc"));
        when(statistiquesMock.genererRapportProjet("P1")).thenReturn("Rapport projet");
        when(sprintMock.getSprintsParProjet("P1")).thenReturn(sprints);
        when(vueMock.lireChoixMenu()).thenReturn(1); // Oui
        when(vueMock.demanderIdSprint()).thenReturn("S1");
        when(sprintMock.getSprint("P1", "S1")).thenReturn(sprint);
        when(statistiquesMock.genererRapportSprint("P1", "S1")).thenReturn("Rapport sprint");

        controller.afficherStatistiques();

        verify(statistiquesMock).genererRapportProjet("P1");
        verify(statistiquesMock).genererRapportSprint("P1", "S1");
    }

    @Test
    public void testAfficherStatistiques_Exception() {
        when(projetMock.getProjets()).thenThrow(new RuntimeException("Erreur système"));

        controller.afficherStatistiques();

        verify(vueMock).afficherErreur(contains("Erreur"));
    }
}
