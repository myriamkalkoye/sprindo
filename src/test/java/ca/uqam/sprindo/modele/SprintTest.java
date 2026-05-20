package ca.uqam.sprindo.modele;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class SprintTest {

  private Sprint sprint;
  private LocalDate debut;
  private LocalDate fin;

  @Before
  public void setUp() {
    debut = LocalDate.of(2025, 11, 1);
    fin = LocalDate.of(2025, 11, 15);
    sprint = new Sprint("S001", 1, "Sprint Test", debut, fin);
  }

  @Test
  public void testConstructeur() {
    assertNotNull(sprint);
    assertEquals("S001", sprint.getId());
    assertEquals(1, sprint.getNumero());
    assertEquals("Sprint Test", sprint.getObjectif());
    assertEquals(debut, sprint.getDebut());
    assertEquals(fin, sprint.getFin());
  }

  @Test
  public void testGetId() {
    assertEquals("S001", sprint.getId());
  }

  @Test
  public void testSetId() {
    sprint.setId("S002");
    assertEquals("S002", sprint.getId());
  }

  @Test
  public void testGetNumero() {
    assertEquals(1, sprint.getNumero());
  }

  @Test
  public void testSetNumero() {
    sprint.setNumero(2);
    assertEquals(2, sprint.getNumero());
  }

  @Test
  public void testSetNumeroNegatif() {
    sprint.setNumero(-1);
    assertEquals(-1, sprint.getNumero());
  }

  @Test
  public void testSetNumeroZero() {
    sprint.setNumero(0);
    assertEquals(0, sprint.getNumero());
  }

  @Test
  public void testGetObjectif() {
    assertEquals("Sprint Test", sprint.getObjectif());
  }

  @Test
  public void testSetObjectif() {
    sprint.setObjectif("Nouvel Objectif");
    assertEquals("Nouvel Objectif", sprint.getObjectif());
  }

  @Test
  public void testSetObjectifNull() {
    sprint.setObjectif(null);
    assertNull(sprint.getObjectif());
  }

  @Test
  public void testSetObjectifVide() {
    sprint.setObjectif("");
    assertEquals("", sprint.getObjectif());
  }

  @Test
  public void testGetDebut() {
    assertEquals(debut, sprint.getDebut());
  }

  @Test
  public void testSetDebut() {
    LocalDate nouveauDebut = LocalDate.of(2025, 12, 1);
    sprint.setDebut(nouveauDebut);
    assertEquals(nouveauDebut, sprint.getDebut());
  }

  @Test
  public void testSetDebutNull() {
    sprint.setDebut(null);
    assertNull(sprint.getDebut());
  }

  @Test
  public void testGetFin() {
    assertEquals(fin, sprint.getFin());
  }

  @Test
  public void testSetFin() {
    LocalDate nouvelleFin = LocalDate.of(2025, 12, 15);
    sprint.setFin(nouvelleFin);
    assertEquals(nouvelleFin, sprint.getFin());
  }

  @Test
  public void testSetFinNull() {
    sprint.setFin(null);
    assertNull(sprint.getFin());
  }

  @Test
  public void testToString() {
    String result = sprint.toString();
    assertNotNull(result);
    assertTrue(result.contains("S001"));
    assertTrue(result.contains("1"));
    assertTrue(result.contains("Sprint Test"));
  }

  @Test
  public void testConstructeurAvecNull() {
    Sprint s = new Sprint(null, 0, null, null, null);
    assertNull(s.getId());
    assertEquals(0, s.getNumero());
    assertNull(s.getObjectif());
    assertNull(s.getDebut());
    assertNull(s.getFin());
  }

  @Test
  public void testConstructeurAvecVide() {
    Sprint s = new Sprint("", 0, "", debut, fin);
    assertEquals("", s.getId());
    assertEquals("", s.getObjectif());
  }

  @Test
  public void testModificationMultiple() {
    sprint.setId("S999");
    sprint.setNumero(99);
    sprint.setObjectif("Objectif Modifié");

    assertEquals("S999", sprint.getId());
    assertEquals(99, sprint.getNumero());
    assertEquals("Objectif Modifié", sprint.getObjectif());
  }
}
