package fr.simplon.lordofsimplon.api.fight;

import fr.simplon.lordofsimplon.api.player.Player;

import java.util.List;

/**
 * Règles de déroulement d'un combat. Avant chaque coup on s'assure qu'il est possible avec la
 * méthode {@link #isPossibleHit(Player, Hit)}.
 * <p>
 * Pour connaître en une seule fois tous les coups possibles on peut appeler
 * {@link #getPossibleHits(Player)}.
 * <p>
 * Enfin pour appliquer les règles de comptage de points quand un joueur porte un coup à son
 * adversaire il faut appeler la méthode {@link #applyHit(FightStep)}.
 */
public interface IFightingRules
{
    /**
     * Retourne tous les coups que peut faire un joueur.
     *
     * @param attacking Le joueur attaquant.
     * @return La liste des coups possibles par ce joueur.
     */
    List<Hit> getPossibleHits(Player attacking);

    /**
     * Retourne <code>true</code> si un coup est possible, en fonction des règles du jeu.
     *
     * @param attacking   Le joueur attaquant.
     * @param expectedHit Le coup qui est prévu.
     * @return <code>true</code> si un coup est possible, en fonction des règles du jeu.
     */
    boolean isPossibleHit(Player attacking, Hit expectedHit);

    /**
     * Applique les règles de comptage des points lorsqu'un coup est porté.
     *
     * @param step L'étape de jeu qui contient les joueurs et le coup porté.
     */
    void applyHit(FightStep step);
}
