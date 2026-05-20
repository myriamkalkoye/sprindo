package ca.uqam.sprindo.modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjetTest {

  private Projet projet;

  @Before
  public void setUp() {
    projet = new Projet("P001", "Sprindo", "Gestionnaire agile");
  }

  @Test
  public void testConstructeurVide() {
    Projet p = new Projet();
    assertNotNull(p);
  }

  @Test
  public void testConstructeurComplet() {
    assertNotNull(projet);
    assertEquals("P001", projet.getId());
    assertEquals("Sprindo", projet.getNom());
    assertEquals("Gestionnaire agile", projet.getDescription());
  }

  @Test
  public void testGetId() {
    assertEquals("P001", projet.getId());
  }

  @Test
  public void testSetId() {
    projet.setId("P002");
    assertEquals("P002", projet.getId());
  }

  @Test
  public void testGetNom() {
    assertEquals("Sprindo", projet.getNom());
  }

  @Test
  public void testSetNom() {
    projet.setNom("Nouveau Nom");
    assertEquals("Nouveau Nom", projet.getNom());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Gestionnaire agile", projet.getDescription());
  }

  @Test
  public void testSetDescription() {
    projet.setDescription("Nouvelle Description");
    assertEquals("Nouvelle Description", projet.getDescription());
  }

  @Test
  public void testSetIdNull() {
    projet.setId(null);
    assertNull(projet.getId());
  }

  @Test
  public void testSetNomNull() {
    projet.setNom(null);
    assertNull(projet.getNom());
  }

  @Test
  public void testSetDescriptionNull() {
    projet.setDescription(null);
    assertNull(projet.getDescription());
  }

  @Test
  public void testSetNomVide() {
    projet.setNom("");
    assertEquals("", projet.getNom());
  }

  @Test
  public void testToString() {
    String result = projet.toString();
    assertNotNull(result);
    assertTrue(result.contains("P001"));
    assertTrue(result.contains("Sprindo"));
    assertTrue(result.contains("Gestionnaire agile"));
  }

  @Test
  public void testEqualsMemeObjet() {
    assertEquals(projet, projet);
  }

  @Test
  public void testEqualsObjetIdentique() {
    Projet autre = new Projet("P001", "Sprindo", "Gestionnaire agile");
    assertEquals(projet, autre);
  }

  @Test
  public void testNotEqualsIdDifferent() {
    Projet autre = new Projet("P002", "Sprindo", "Gestionnaire agile");
    assertNotEquals(projet, autre);
  }

  @Test
  public void testNotEqualsNomDifferent() {
    Projet autre = new Projet("P001", "Autre", "Gestionnaire agile");
    assertNotEquals(projet, autre);
  }

  @Test
  public void testNotEqualsNull() {
    assertNotEquals(projet, null);
  }

  @Test
  public void testNotEqualsAutreClasse() {
    assertNotEquals(projet, "P001");
  }

  @Test
  public void testHashCodeIdentique() {
    Projet autre = new Projet("P001", "Sprindo", "Gestionnaire agile");
    assertEquals(projet.hashCode(), autre.hashCode());
  }

  @Test
  public void testHashCodeDifferent() {
    Projet autre = new Projet("P002", "Autre", "Autre desc");
    assertNotEquals(projet.hashCode(), autre.hashCode());
  }

  @Test
  public void testConstructeurAvecNull() {
    Projet p = new Projet(null, null, null);
    assertNull(p.getId());
    assertNull(p.getNom());
    assertNull(p.getDescription());
  }

  @Test
  public void testConstructeurAvecVide() {
    Projet p = new Projet("", "", "");
    assertEquals("", p.getId());
    assertEquals("", p.getNom());
    assertEquals("", p.getDescription());
  }
}
