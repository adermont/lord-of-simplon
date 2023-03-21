package fr.simplon.lordofsimplon.impl.player;

import fr.simplon.lordofsimplon.api.fight.Hit;
import fr.simplon.lordofsimplon.api.fight.HitType;
import fr.simplon.lordofsimplon.api.heroes.IHeroeAttack;
import fr.simplon.lordofsimplon.api.heroes.IHeroeMagic;
import fr.simplon.lordofsimplon.api.heroes.MagicType;
import fr.simplon.lordofsimplon.api.player.IPlayerInteractions;
import fr.simplon.lordofsimplon.api.player.Player;

import java.util.List;
import java.util.Random;

/**
 * Permet de faire des interactions entre l'avatar dans le jeu et l'utilisateur physique :)
 */
public class AutomaticPlayerInteractions implements IPlayerInteractions
{
    public Hit chooseNextHit(Player mPlayer, List<Hit> pPossibleHits)
    {
        Hit hit = null;
        HitType type = null;
        IHeroeMagic magic = null;
        IHeroeAttack attack = null;

        List<Hit> magics = pPossibleHits.stream().filter(h -> h.hitType() == HitType.MAGIC && h.magic().getType() == MagicType.REGENERATE).toList();
        if (mPlayer.getEnergy() < 20 && magics.size() > 0)
        {
            type = HitType.MAGIC;
            magic = magics.get(new Random(System.currentTimeMillis()).nextInt(magics.size())).magic();
            hit = new Hit(type, attack, magic);
        }
        else
        {
            hit = pPossibleHits.get(new Random(System.currentTimeMillis()).nextInt(pPossibleHits.size()));
        }
        return hit;
    }
}
