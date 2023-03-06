package fr.simplon.lordofsimplon.api.player;

import fr.simplon.lordofsimplon.api.fight.Hit;

import java.util.List;

/**
 * Permet de faire des interactions entre l'avatar dans le jeu et l'utilisateur physique :)
 */
public interface IPlayerInteractions
{
    /**
     * Demande à l'utilisateur le choix de son prochain coup.
     *
     * @param player        L'utilisateur en train de jouer.
     * @param pPossibleHits La liste des coups possibles.
     * @return Le coup choisi.
     */
    Hit chooseNextHit(Player player, List<Hit> pPossibleHits);
}
