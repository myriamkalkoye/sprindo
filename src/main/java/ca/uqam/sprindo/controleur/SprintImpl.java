package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Sprint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe de service permettant de gérer les sprints associés aux projets. Elle permet d'ajouter, de
 * récupérer et de lister les sprints pour un projet donné.
 */
public class SprintImpl {

  /**
   * Map contenant la liste des sprints pour chaque projet. La clé est l'identifiant du projet et la
   * valeur est la liste des sprints associés.
   */
  private Map<String, List<Sprint>> sprintsParProjet = new HashMap<>();

  /**
   * Ajoute un sprint à un projet. Si le projet n'existe pas encore dans la map, il sera créé
   * automatiquement.
   *
   * @param projetId identifiant du projet
   * @param s        sprint à ajouter
   */
  public void ajouterSprint(String projetId, Sprint s) {
    sprintsParProjet.computeIfAbsent(projetId, k -> new ArrayList<>()).add(s);
  }

  /**
   * Récupère un sprint précis à partir de son identifiant.
   *
   * @param projetId identifiant du projet
   * @param sprintId identifiant du sprint recherché
   * @return le sprint correspondant s'il est trouvé, sinon null
   */
  public Sprint getSprint(String projetId, String sprintId) {
    List<Sprint> sprints = sprintsParProjet.get(projetId);
    if (sprints != null) {
      for (Sprint s : sprints) {
        if (s.getId().equals(sprintId)) {
          return s;
        }
      }
    }
    return null; // si non trouvé
  }

  /**
   * Retourne la liste de tous les sprints associés à un projet.
   *
   * @param projetId identifiant du projet
   * @return une liste de sprints (vide si aucun sprint n'est associé)
   */
  public List<Sprint> getSprintsParProjet(String projetId) {
    return sprintsParProjet.getOrDefault(projetId, new ArrayList<>());
  }
}
