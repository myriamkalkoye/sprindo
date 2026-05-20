package ca.uqam.sprindo.modele;

/**
 * Représente une tâche dans l'application Sprindo. Une tâche contient un titre, une description, un
 * statut et peut être assignée à un utilisateur.
 */
public class Tache {

  /**
   * Identifiant unique de la tâche.
   */
  private String id;

  /**
   * Titre de la tâche.
   */
  private String titre;

  /**
   * Description détaillée de la tâche.
   */
  private String description;

  /**
   * Statut actuel de la tâche (ex : "À faire", "En cours", "Terminé").
   */
  private String statut;

  /**
   * Nom de l'utilisateur à qui la tâche est assignée (optionnel).
   */
  private String assigneA;

  /**
   * Constructeur permettant de créer une tâche avec toutes ses informations.
   *
   * @param id          l'identifiant unique de la tâche
   * @param titre       le titre de la tâche
   * @param description la description de la tâche
   * @param statut      le statut de la tâche
   * @param assigneA    le nom de l'utilisateur assigné (peut être null)
   */
  public Tache(String id, String titre, String description, String statut, String assigneA) {
    this.id = id;
    this.titre = titre;
    this.description = description;
    this.statut = statut;
    this.assigneA = assigneA;
  }

  /**
   * Retourne l'identifiant de la tâche.
   *
   * @return l'identifiant de la tâche
   */
  public String getId() {
    return id;
  }

  /**
   * Modifie l'identifiant de la tâche.
   *
   * @param id le nouvel identifiant
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Retourne le titre de la tâche.
   *
   * @return le titre de la tâche
   */
  public String getTitre() {
    return titre;
  }

  /**
   * Modifie le titre de la tâche.
   *
   * @param titre le nouveau titre
   */
  public void setTitre(String titre) {
    this.titre = titre;
  }

  /**
   * Retourne la description de la tâche.
   *
   * @return la description de la tâche
   */
  public String getDescription() {
    return description;
  }

  /**
   * Modifie la description de la tâche.
   *
   * @param description la nouvelle description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Retourne le statut de la tâche.
   *
   * @return le statut de la tâche
   */
  public String getStatut() {
    return statut;
  }

  /**
   * Modifie le statut de la tâche.
   *
   * @param statut le nouveau statut
   */
  public void setStatut(String statut) {
    this.statut = statut;
  }

  /**
   * Retourne le nom de l'utilisateur assigné à la tâche.
   *
   * @return le nom de l'utilisateur assigné ou null si aucun
   */
  public String getAssigneA() {
    return assigneA;
  }

  /**
   * Modifie le nom de l'utilisateur assigné à la tâche.
   *
   * @param assigneA le nom du nouvel utilisateur assigné
   */
  public void setAssigneA(String assigneA) {
    this.assigneA = assigneA;
  }

  /**
   * Retourne une représentation textuelle de la tâche, utile pour l'affichage en console.
   *
   * @return une chaîne descriptive de la tâche
   */
  @Override
  public String toString() {
    return "Tache {"
        + "id='"
        + id
        + '\''
        + ", titre='"
        + titre
        + '\''
        + ", description='"
        + description
        + '\''
        + ", statut='"
        + statut
        + '\''
        + ", assigné à='"
        + assigneA
        + '\''
        + '}';
  }
}
