package ca.uqam.sprindo.modele;

import java.util.Objects;

/**
 * Représente un projet dans l'application Sprindo. Un projet est défini par un identifiant unique,
 * un nom et une description. Cette classe permet de créer, modifier et comparer des projets.
 */
public class Projet {

  /**
   * Identifiant unique du projet.
   */
  private String id;

  /**
   * Nom du projet.
   */
  private String nom;

  /**
   * Description du projet.
   */
  private String description;

  /**
   * Constructeur par défaut. Crée une instance vide de Projet.
   */
  public Projet() {
  }

  /**
   * Constructeur avec paramètres.
   *
   * @param id          l'identifiant du projet
   * @param nom         le nom du projet
   * @param description la description du projet
   */
  public Projet(String id, String nom, String description) {
    this.id = id;
    this.nom = nom;
    this.description = description;
  }

  /**
   * Retourne l'identifiant du projet.
   *
   * @return l'identifiant du projet
   */
  public String getId() {
    return id;
  }

  /**
   * Modifie l'identifiant du projet.
   *
   * @param id le nouvel identifiant du projet
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Retourne le nom du projet.
   *
   * @return le nom du projet
   */
  public String getNom() {
    return nom;
  }

  /**
   * Modifie le nom du projet.
   *
   * @param nom le nouveau nom du projet
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Retourne la description du projet.
   *
   * @return la description du projet
   */
  public String getDescription() {
    return description;
  }

  /**
   * Modifie la description du projet.
   *
   * @param description la nouvelle description du projet
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Retourne une représentation textuelle du projet.
   *
   * @return une chaîne décrivant l'objet Projet
   */
  @Override
  public String toString() {
    return "projet{" + "id='" + id + '\'' + ", nom='" + nom + '\'' + ", description='" + description
        + '\'' + '}';
  }

  /**
   * Compare ce projet avec un autre objet.
   *
   * @param o l'objet à comparer
   * @return true si les deux projets sont égaux, false sinon
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Projet projet = (Projet) o;
    return Objects.equals(id, projet.id) && Objects.equals(nom, projet.nom) && Objects.equals(
        description, projet.description);
  }

  /**
   * Calcule le code de hachage du projet.
   *
   * @return le hashCode basé sur id, nom et description
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, nom, description);
  }
}
