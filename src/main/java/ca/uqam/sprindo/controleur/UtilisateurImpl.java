package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Utilisateur;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Gestion des utilisateurs en mémoire. - Préserve l'ordre d'insertion avec LinkedHashMap. -
 * Garantit l'unicité du nom (insensible à la casse). - Valide les champs obligatoires.
 */
public class UtilisateurImpl {

  /**
   * Map des utilisateurs indexés par leur nom (en minuscules).
   * LinkedHashMap pour préserver l'ordre d'insertion.
   */
  private Map<String, Utilisateur> utilisateurs = new LinkedHashMap<>();

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
   * Ajouter un utilisateur. Règles : - nom, role : obligatoires et non vides - nom : unique
   * (insensible à la casse)
   *
   * @param u l'utilisateur à ajouter
   * @throws IllegalArgumentException si l'utilisateur est nul, si les champs obligatoires
   *                                  sont manquants, ou si le nom existe déjà
   */
  public void ajouterUtilisateur(Utilisateur u) {
    if (u == null) {
      throw new IllegalArgumentException("L'utilisateur ne peut pas être nul.");
    }
    if (isBlank(u.getNom()) || isBlank(u.getRole())) {
      throw new IllegalArgumentException("Champs obligatoires manquants : nom, role.");
    }

    String nomKey = u.getNom().trim().toLowerCase(Locale.ROOT);

    if (utilisateurs.containsKey(nomKey)) {
      throw new IllegalArgumentException("Un utilisateur avec ce nom existe déjà : " + u.getNom());
    }

    utilisateurs.put(nomKey, u);
  }

  /**
   * Récupérer un utilisateur par son nom.
   *
   * @param nom le nom de l'utilisateur (insensible à la casse)
   * @return l'Utilisateur, ou null s'il n'existe pas
   */
  public Utilisateur getUtilisateur(String nom) {
    if (isBlank(nom)) {
      return null;
    }

    String nomKey = nom.trim().toLowerCase(Locale.ROOT);
    return utilisateurs.get(nomKey);
  }

  /**
   * Lister tous les utilisateurs.
   *
   * @return liste (copie) des utilisateurs, ou liste vide si aucun
   */
  public List<Utilisateur> getUtilisateurs() {
    return new ArrayList<>(utilisateurs.values());
  }
}
