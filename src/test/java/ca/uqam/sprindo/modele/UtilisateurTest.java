package ca.uqam.sprindo.modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilisateurTest {

  private Utilisateur utilisateur;

  @Before
  public void setUp() {
    utilisateur = new Utilisateur("Myriam", "Developer");
  }

  @Test
  public void testConstructeurVide() {
    Utilisateur u = new Utilisateur();
    assertNotNull(u);
  }

  @Test
  public void testConstructeurComplet() {
    assertNotNull(utilisateur);
    assertEquals("Myriam", utilisateur.getNom());
    assertEquals("Developer", utilisateur.getRole());
  }

  @Test
  public void testGetNom() {
    assertEquals("Myriam", utilisateur.getNom());
  }

  @Test
  public void testSetNom() {
    utilisateur.setNom("Celestine");
    assertEquals("Celestine", utilisateur.getNom());
  }

  @Test
  public void testGetRole() {
    assertEquals("Developer", utilisateur.getRole());
  }

  @Test
  public void testSetRole() {
    utilisateur.setRole("Scrum Master");
    assertEquals("Scrum Master", utilisateur.getRole());
  }

  @Test
  public void testSetNomNull() {
    utilisateur.setNom(null);
    assertNull(utilisateur.getNom());
  }

  @Test
  public void testSetRoleNull() {
    utilisateur.setRole(null);
    assertNull(utilisateur.getRole());
  }

  @Test
  public void testSetNomVide() {
    utilisateur.setNom("");
    assertEquals("", utilisateur.getNom());
  }

  @Test
  public void testSetRoleVide() {
    utilisateur.setRole("");
    assertEquals("", utilisateur.getRole());
  }

  @Test
  public void testConstructeurAvecNull() {
    Utilisateur u = new Utilisateur(null, null);
    assertNull(u.getNom());
    assertNull(u.getRole());
  }

  @Test
  public void testConstructeurAvecVide() {
    Utilisateur u = new Utilisateur("", "");
    assertEquals("", u.getNom());
    assertEquals("", u.getRole());
  }

  @Test
  public void testModificationMultiple() {
    utilisateur.setNom("Alice");
    utilisateur.setRole("Product Owner");

    assertEquals("Alice", utilisateur.getNom());
    assertEquals("Product Owner", utilisateur.getRole());

    utilisateur.setNom("Bob");
    utilisateur.setRole("Tester");

    assertEquals("Bob", utilisateur.getNom());
    assertEquals("Tester", utilisateur.getRole());
  }

  @Test
  public void testChangementRoleComplet() {
    utilisateur.setRole("Developer");
    assertEquals("Developer", utilisateur.getRole());

    utilisateur.setRole("Scrum Master");
    assertEquals("Scrum Master", utilisateur.getRole());

    utilisateur.setRole("Product Owner");
    assertEquals("Product Owner", utilisateur.getRole());
  }
}
