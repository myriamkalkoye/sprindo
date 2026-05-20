package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Projet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestion des projets en mémoire. - Préserve l'ordre d'insertion (affichage cohérent). - Garantit
 * l'unicité de l'id. - Valide les champs obligatoires : id, nom, description.
 */
public class ProjetImpl {

  /**
   * Map des projets indexés par leur identifiant.
   * LinkedHashMap pour garder l'ordre d'insertion lors de la liste.
   */
  private final Map<String, Projet> projets = new LinkedHashMap<>();

  /**
   * Vérifie si une chaîne est nulle ou vide (après trim).
   *
   * @param s la chaîne à vérifier
   * @return true si la chaîne est nulle ou vide, false sinon
   */
  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  /**
   * Ajouter un projet. Règles (d'après les user stories) : - id, nom, description :
   * obligatoires et non vides - id : unique -> refus si déjà présent
   *
   * @param p le projet à ajouter
   * @throws IllegalArgumentException si le projet est nul, si les champs obligatoires
   *                                  sont manquants, ou si l'id existe déjà
   */
  public void ajouterProjet(Projet p) {
    if (p == null) {
      throw new IllegalArgumentException("Projet nul interdit.");
    }
    if (isBlank(p.getId()) || isBlank(p.getNom()) || isBlank(p.getDescription())) {
      throw new IllegalArgumentException("Champs obligatoires manquants : id, nom, description.");
    }
    if (projets.containsKey(p.getId())) {
      throw new IllegalArgumentException("Un projet avec cet id existe déjà : " + p.getId());
    }
    projets.put(p.getId(), p);
    // Le message de confirmation s'affichera côté Vue/console.
  }

  /**
   * Récupérer un projet par son identifiant.
   *
   * @param id identifiant du projet
   * @return le Projet, ou null s'il n'existe pas
   */
  public Projet getProjet(String id) {
    if (isBlank(id)) {
      return null;
    }
    return projets.get(id);
  }

  /**
   * Lister tous les projets dans l'ordre d'insertion.
   *
   * @return liste (copie) des projets
   */
  public List<Projet> getProjets() {
    return new ArrayList<>(projets.values());
  }
}
