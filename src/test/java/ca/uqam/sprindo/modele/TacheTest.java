package ca.uqam.sprindo.modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TacheTest {

  private Tache tache;

  @Before
  public void setUp() {
    tache = new Tache("T001", "Tâche Test", "Description", "À faire", "Myriam");
  }

  @Test
  public void testConstructeur() {
    assertNotNull(tache);
    assertEquals("T001", tache.getId());
    assertEquals("Tâche Test", tache.getTitre());
    assertEquals("Description", tache.getDescription());
    assertEquals("À faire", tache.getStatut());
    assertEquals("Myriam", tache.getAssigneA());
  }

  @Test
  public void testGetId() {
    assertEquals("T001", tache.getId());
  }

  @Test
  public void testSetId() {
    tache.setId("T002");
    assertEquals("T002", tache.getId());
  }

  @Test
  public void testGetTitre() {
    assertEquals("Tâche Test", tache.getTitre());
  }

  @Test
  public void testSetTitre() {
    tache.setTitre("Nouveau Titre");
    assertEquals("Nouveau Titre", tache.getTitre());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Description", tache.getDescription());
  }

  @Test
  public void testSetDescription() {
    tache.setDescription("Nouvelle Description");
    assertEquals("Nouvelle Description", tache.getDescription());
  }

  @Test
  public void testGetStatut() {
    assertEquals("À faire", tache.getStatut());
  }

  @Test
  public void testSetStatut() {
    tache.setStatut("En cours");
    assertEquals("En cours", tache.getStatut());
  }

  @Test
  public void testGetAssigneA() {
    assertEquals("Myriam", tache.getAssigneA());
  }

  @Test
  public void testSetAssigneA() {
    tache.setAssigneA("Celestine");
    assertEquals("Celestine", tache.getAssigneA());
  }

  @Test
  public void testSetIdNull() {
    tache.setId(null);
    assertNull(tache.getId());
  }

  @Test
  public void testSetTitreNull() {
    tache.setTitre(null);
    assertNull(tache.getTitre());
  }

  @Test
  public void testSetDescriptionNull() {
    tache.setDescription(null);
    assertNull(tache.getDescription());
  }

  @Test
  public void testSetStatutNull() {
    tache.setStatut(null);
    assertNull(tache.getStatut());
  }

  @Test
  public void testSetAssigneANull() {
    tache.setAssigneA(null);
    assertNull(tache.getAssigneA());
  }

  @Test
  public void testSetTitreVide() {
    tache.setTitre("");
    assertEquals("", tache.getTitre());
  }

  @Test
  public void testSetDescriptionVide() {
    tache.setDescription("");
    assertEquals("", tache.getDescription());
  }

  @Test
  public void testSetAssigneAVide() {
    tache.setAssigneA("");
    assertEquals("", tache.getAssigneA());
  }

  @Test
  public void testToString() {
    String result = tache.toString();
    assertNotNull(result);
    assertTrue(result.contains("T001"));
    assertTrue(result.contains("Tâche Test"));
    assertTrue(result.contains("À faire"));
  }

  @Test
  public void testConstructeurAvecNull() {
    Tache t = new Tache(null, null, null, null, null);
    assertNull(t.getId());
    assertNull(t.getTitre());
    assertNull(t.getDescription());
    assertNull(t.getStatut());
    assertNull(t.getAssigneA());
  }

  @Test
  public void testConstructeurAvecVide() {
    Tache t = new Tache("", "", "", "", "");
    assertEquals("", t.getId());
    assertEquals("", t.getTitre());
    assertEquals("", t.getDescription());
    assertEquals("", t.getStatut());
    assertEquals("", t.getAssigneA());
  }

  @Test
  public void testChangementStatutComplet() {
    tache.setStatut("À faire");
    assertEquals("À faire", tache.getStatut());

    tache.setStatut("En cours");
    assertEquals("En cours", tache.getStatut());

    tache.setStatut("Terminé");
    assertEquals("Terminé", tache.getStatut());
  }

  @Test
  public void testReassignationMultiple() {
    tache.setAssigneA("Alice");
    assertEquals("Alice", tache.getAssigneA());

    tache.setAssigneA("Bob");
    assertEquals("Bob", tache.getAssigneA());

    tache.setAssigneA("Charlie");
    assertEquals("Charlie", tache.getAssigneA());
  }
}
