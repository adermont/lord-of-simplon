package fr.simplon.lordofsimplon.api.fight;

import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.fight.Fight;

/**
 * Représente une étape d'un combat.
 * @param fight Le combat auquel appartient l'étape.
 * @param stepNumber Le numéro de l'étape.
 * @param attacking Le joueur qui attaque.
 * @param defending Le joueur qui défend.
 * @param hit Le coup porté.
 */
public record FightStep(Fight fight, int stepNumber, Player attacking, Player defending, Hit hit){

}

