package fr.simplon.lordofsimplon.impl.fight;

import fr.simplon.lordofsimplon.api.fight.FightStep;
import fr.simplon.lordofsimplon.api.fight.Hit;
import fr.simplon.lordofsimplon.api.fight.HitType;
import fr.simplon.lordofsimplon.api.fight.IFightingRules;
import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.heroes.IHeroeAttack;
import fr.simplon.lordofsimplon.api.heroes.IHeroeMagic;
import fr.simplon.lordofsimplon.api.heroes.MagicType;
import fr.simplon.lordofsimplon.api.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Règles de déroulement d'un combat.
 */
public class FightingRules implements IFightingRules
{
    private Map<Player, IHeroeMagic> mActiveMagicsByPlayer;
    private Map<Player, Hit>        mLastHitByPlayer;

    /**
     * Constructeur.
     */
    public FightingRules()
    {
        mActiveMagicsByPlayer = new HashMap<>();
        mLastHitByPlayer = new HashMap<>();
    }

    /**
     * Détermine les coups qu'un joueur peut porter à son adversaire en fonction des coups
     * précédents.
     *
     * @param attacking Le joueur qui attaque.
     * @return La liste des coups possibles.
     */
    public List<Hit> getPossibleHits(Player attacking)
    {
        IHeroe heroe = attacking.getHeroe();
        List<IHeroeMagic> magics = heroe.getMagics();
        List<IHeroeAttack> attacks = heroe.getAttacks();
        List<Hit> result = new ArrayList<>();
        for (IHeroeMagic magic : magics)
        {
            Hit hit = new Hit(HitType.MAGIC, null, magic);
            if (isPossibleHit(attacking, hit))
            {
                result.add(hit);
            }
        }
        for (IHeroeAttack attack : attacks)
        {
            Hit hit = new Hit(HitType.ATTACK, attack, null);
            if (isPossibleHit(attacking, hit))
            {
                result.add(hit);
            }
        }
        return result;
    }

    /**
     * Retourne <code>true</code> si un coup est possible pour le tour courant.
     *
     * @param attacking   Le joueur attaquant.
     * @param expectedHit Le coup qui va être porté à son adversaire.
     * @return <code>true</code> si un coup est possible pour le tour courant.
     */
    public boolean isPossibleHit(Player attacking, Hit expectedHit)
    {
        boolean result = true;

        Hit lastHit = mLastHitByPlayer.get(attacking);

        // Interdit de faire 2 fois de suite de la magie
        if (lastHit != null && lastHit.hitType() == HitType.MAGIC && expectedHit.hitType() == HitType.MAGIC)
        {
            result = false;
        }
        // Interdit de faire 2 fois de suite la même attaque
        if (lastHit != null && expectedHit.equals(lastHit))
        {
            result = false;
        }
        return result;
    }

    /**
     * Applique les règles de comptage de points lorsqu'on coup est porté.
     *
     * @param step Un nouveau tour de jeu qui contient les joueurs et le coup porté.
     */
    public void applyHit(FightStep step)
    {
        Player attacking = step.attacking();
        Player defensing = step.defending();
        Hit hit = step.hit();
        HitType hitType = hit.hitType();
        if (hitType == HitType.ATTACK)
        {
            IHeroeAttack attack = hit.attack();
            IHeroeMagic magicToApply = mActiveMagicsByPlayer.get(attacking);
            int damages = attack.getPoints();
            if ( magicToApply != null){
                damages += magicToApply.getPoints();
            }
            defensing.damage(damages);
        }
        else if (hitType == HitType.MAGIC)
        {
            IHeroeMagic magic = hit.magic();
            if (magic.getType() == MagicType.REGENERATE)
            {
                attacking.regenerate(magic.getPoints());
            }
            else if (magic.getType() == MagicType.BOOST_DAMAGES)
            {
                mActiveMagicsByPlayer.put(attacking, magic);
            }
            else
            {
                // Type de magie inconnu
                throw new IllegalArgumentException("Le type de magie appliqué (" + magic.getType() + ") n'est pas reconnu par le jeu pour l'instant.");
            }
        }
        else
        {
            // Type de coup non géré pour le moment
            throw new IllegalArgumentException("Le coup réalisé n'est pas reconnu par le jeu pour l'instant.");
        }
        mLastHitByPlayer.put(attacking, hit);
    }
}
