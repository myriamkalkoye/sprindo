package ca.uqam.sprindo;

import ca.uqam.sprindo.controleur.SprindoController;
import ca.uqam.sprindo.vue.SprindoVue;

/**
 * Point d'entrée de l'application Sprindo.
 */
public class Main {

  /**
   * Méthode principale - lance le menu principal.
   *
   * @param args arguments de la ligne de commande
   */
  public static void main(String[] args) {
    SprindoController controller = new SprindoController();
    SprindoVue vue = new SprindoVue();
    boolean continuer = true;

    while (continuer) {
      vue.afficherMenu();
      int choix = vue.lireChoixMenu();

      switch (choix) {
        case 1:
          controller.ajouterProjet();
          break;
        case 2:
          controller.afficherProjets();
          break;
        case 3:
          controller.ajouterSprint();
          break;
        case 4:
          controller.afficherSprints();
          break;
        case 5:
          controller.ajouterTache();
          break;
        case 6:
          controller.afficherTaches();
          break;
        case 7:
          controller.assignerTache();
          break;
        case 8:
          controller.modifierStatutTache();
          break;
        case 9:
          controller.supprimerTache();
          break;
        case 10:
          controller.afficherStatistiques();
          break;
        case 11:
          continuer = false;
          vue.afficherMessage("Au revoir !");
          break;
        default:
          vue.afficherErreur("Choix invalide. Veuillez choisir entre 1 et 11.");
          break;
      }
    }

    vue.fermer();
  }
}
