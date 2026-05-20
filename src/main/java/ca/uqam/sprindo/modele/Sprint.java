package ca.uqam.sprindo.modele;

import java.time.LocalDate;

/**
 * Représente un sprint dans l'application Sprindo. Un sprint correspond à une itération de
 * développement avec un objectif et une période de temps définie par une date de début et une date
 * de fin.
 */
public class Sprint {

  /**
   * Identifiant unique du sprint.
   */
  private String id;

  /**
   * Numéro du sprint (ex: 1, 2, 3...).
   */
  private int numero;

  /**
   * Objectif du sprint.
   */
  private String objectif;

  /**
   * Date de début du sprint.
   */
  private LocalDate debut;

  /**
   * Date de fin du sprint.
   */
  private LocalDate fin;

  /**
   * Constructeur permettant de créer un sprint avec toutes ses informations.
   *
   * @param id       l'identifiant unique du sprint
   * @param numero   le numéro du sprint
   * @param objectif l'objectif du sprint
   * @param debut    la date de début du sprint
   * @param fin      la date de fin du sprint
   */
  public Sprint(String id, int numero, String objectif, LocalDate debut, LocalDate fin) {
    this.id = id;
    this.numero = numero;
    this.objectif = objectif;
    this.debut = debut;
    this.fin = fin;
  }

  /**
   * Retourne l'identifiant du sprint.
   *
   * @return l'identifiant du sprint
   */
  public String getId() {
    return id;
  }

  /**
   * Modifie l'identifiant du sprint.
   *
   * @param id le nouvel identifiant du sprint
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Retourne le numéro du sprint.
   *
   * @return le numéro du sprint
   */
  public int getNumero() {
    return numero;
  }

  /**
   * Modifie le numéro du sprint.
   *
   * @param numero le nouveau numéro du sprint
   */
  public void setNumero(int numero) {
    this.numero = numero;
  }

  /**
   * Retourne l'objectif du sprint.
   *
   * @return l'objectif du sprint
   */
  public String getObjectif() {
    return objectif;
  }

  /**
   * Modifie l'objectif du sprint.
   *
   * @param objectif le nouvel objectif du sprint
   */
  public void setObjectif(String objectif) {
    this.objectif = objectif;
  }

  /**
   * Retourne la date de début du sprint.
   *
   * @return la date de début
   */
  public LocalDate getDebut() {
    return debut;
  }

  /**
   * Modifie la date de début du sprint.
   *
   * @param debut la nouvelle date de début
   */
  public void setDebut(LocalDate debut) {
    this.debut = debut;
  }

  /**
   * Retourne la date de fin du sprint.
   *
   * @return la date de fin
   */
  public LocalDate getFin() {
    return fin;
  }

  /**
   * Modifie la date de fin du sprint.
   *
   * @param fin la nouvelle date de fin
   */
  public void setFin(LocalDate fin) {
    this.fin = fin;
  }

  /**
   * Retourne une représentation textuelle du sprint.
   *
   * @return une chaîne décrivant le sprint
   */
  @Override
  public String toString() {
    return "Sprint{" + "id='" + id + '\'' + ", numero=" + numero + ", objectif='" + objectif + '\''
        + ", debut=" + debut + ", fin=" + fin + '}';
  }
}
