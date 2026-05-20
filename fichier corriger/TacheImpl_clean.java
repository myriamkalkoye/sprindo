package ca.uqam.sprindo.controleur;

import ca.uqam.sprindo.modele.Tache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestion des tâches en mémoire.
 * - Structure : projetId -> sprintId -> liste de tâches
 * - Valide les champs obligatoires.
 * - Garantit l'unicité de l'id par sprint.
 */
public class TacheImpl {

    /** Structure de stockage : projetId -> sprintId -> liste de tâches. */
    private Map<String, Map<String, List<Tache>>> taches = new HashMap<>();

    /**
     * Ajouter une tâche à un sprint d'un projet.
     *
     * @param projetId id du projet
     * @param sprintId id du sprint
     * @param t tâche à ajouter
     */
    public void ajouterTache(String projetId, String sprintId, Tache t) {
        if (isBlank(projetId)) {
            throw new IllegalArgumentException("L'ID du projet est obligatoire.");
        }
        if (isBlank(sprintId)) {
            throw new IllegalArgumentException("L'ID du sprint est obligatoire.");
        }
        if (t == null) {
            throw new IllegalArgumentException("La tâche ne peut pas être nulle.");
        }
        if (isBlank(t.getId()) || isBlank(t.getTitre()) || isBlank(t.getDescription())) {
            throw new IllegalArgumentException("Champs obligatoires manquants : id, titre, description.");
        }
        if (isBlank(t.getStatut())) {
            throw new IllegalArgumentException("Le statut est obligatoire.");
        }

        taches.putIfAbsent(projetId, new HashMap<>());
        taches.get(projetId).putIfAbsent(sprintId, new ArrayList<>());

        for (Tache existing : taches.get(projetId).get(sprintId)) {
            if (existing.getId().equals(t.getId())) {
                throw new IllegalArgumentException("Une tâche avec cet ID existe déjà : " + t.getId());
            }
        }

        taches.get(projetId).get(sprintId).add(t);
    }

    /**
     * Récupérer une tâche par son ID.
     *
     * @param projetId id du projet
     * @param sprintId id du sprint
     * @param tacheId id de la tâche
     * @return la tâche ou null si absente
     */
    public Tache getTache(String projetId, String sprintId, String tacheId) {
        if (isBlank(projetId) || isBlank(sprintId) || isBlank(tacheId)) {
            return null;
        }

        if (taches.containsKey(projetId) && taches.get(projetId).containsKey(sprintId)) {
            for (Tache t : taches.get(projetId).get(sprintId)) {
                if (t.getId().equals(tacheId)) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * Lister toutes les tâches d'un sprint.
     *
     * @param projetId id du projet
     * @param sprintId id du sprint
     * @return liste de tâches
     */
    public List<Tache> getTachesParSprint(String projetId, String sprintId) {
        if (isBlank(projetId) || isBlank(sprintId)) {
            return new ArrayList<>();
        }

        if (taches.containsKey(projetId) && taches.get(projetId).containsKey(sprintId)) {
            return new ArrayList<>(taches.get(projetId).get(sprintId));
        }
        return new ArrayList<>();
    }

    /**
     * Assigner une tâche à un utilisateur.
     *
     * @param projetId id du projet
     * @param sprintId id du sprint
     * @param tacheId id de la tâche
     * @param utilisateur nom de l'utilisateur
     */
    public void assignerTache(String projetId, String sprintId, String tacheId, String utilisateur) {
        if (isBlank(utilisateur)) {
            throw new IllegalArgumentException("Le nom de l'utilisateur est obligatoire.");
        }

        Tache t = getTache(projetId, sprintId, tacheId);
        if (t == null) {
            throw new IllegalArgumentException("Tâche introuvable : " + tacheId);
        }

        t.setAssigneA(utilisateur);
    }

    /**
     * Changer le statut d'une tâche.
     *
     * @param projetId id du projet
     * @param sprintId id du sprint
     * @param tacheId id de la tâche
     * @param nouveauStatut nouveau statut
     */
    public void changerStatut(String projetId, String sprintId, String tacheId, String nouveauStatut) {
        if (isBlank(nouveauStatut)) {
            throw new IllegalArgumentException("Le nouveau statut est obligatoire.");
        }

        Tache t = getTache(projetId, sprintId, tacheId);
        if (t == null) {
            throw new IllegalArgumentException("Tâche introuvable : " + tacheId);
        }

        t.setStatut(nouveauStatut);
    }

    /**
     * Supprimer une tâche.
     *
     * @param projetId id du projet
     * @param sprintId id du sprint
     * @param tacheId id de la tâche
     */
    public void supprimerTache(String projetId, String sprintId, String tacheId) {
        if (isBlank(projetId) || isBlank(sprintId) || isBlank(tacheId)) {
            throw new IllegalArgumentException("Les IDs sont obligatoires.");
        }

        if (taches.containsKey(projetId) && taches.get(projetId).containsKey(sprintId)) {
            List<Tache> liste = taches.get(projetId).get(sprintId);
            boolean removed = liste.removeIf(t -> t.getId().equals(tacheId));
            if (!removed) {
                throw new IllegalArgumentException("Tâche introuvable : " + tacheId);
            }
        } else {
            throw new IllegalArgumentException("Projet ou sprint introuvable.");
        }
    }

    /**
     * Vérifie si une chaîne est vide.
     *
     * @param s chaîne
     * @return true si vide
     */
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
