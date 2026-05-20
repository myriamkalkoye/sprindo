package ca.uqam.sprindo.modele;

/**
 * Représente un utilisateur dans l'application Sprindo. Un utilisateur est caractérisé par un nom
 * et un rôle (ex : Développeur, Scrum Master, Product Owner).
 */
public class Utilisateur {

  /**
   * Nom de l'utilisateur.
   */
  private String nom;

  /**
   * Rôle de l'utilisateur dans le projet.
   */
  private String role;

  /**
   * Constructeur par défaut. Crée une instance vide de l'utilisateur.
   */
  public Utilisateur() {
  }

  /**
   * Constructeur avec paramètres.
   *
   * @param nom  le nom de l'utilisateur
   * @param role le rôle de l'utilisateur
   */
  public Utilisateur(String nom, String role) {
    this.nom = nom;
    this.role = role;
  }

  /**
   * Retourne le nom de l'utilisateur.
   *
   * @return le nom de l'utilisateur
   */
  public String getNom() {
    return nom;
  }

  /**
   * Modifie le nom de l'utilisateur.
   *
   * @param nom le nouveau nom de l'utilisateur
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Retourne le rôle de l'utilisateur.
   *
   * @return le rôle de l'utilisateur
   */
  public String getRole() {
    return role;
  }

  /**
   * Modifie le rôle de l'utilisateur.
   *
   * @param role le nouveau rôle de l'utilisateur
   */
  public void setRole(String role) {
    this.role = role;
  }
}
