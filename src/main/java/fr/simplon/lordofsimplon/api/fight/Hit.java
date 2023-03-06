package fr.simplon.lordofsimplon.api.fight;

import fr.simplon.lordofsimplon.api.heroes.IHeroeAttack;
import fr.simplon.lordofsimplon.api.heroes.IHeroeMagic;

/**
 * Représente un coup porté par un joueur.
 *
 * @param hitType Le type de coup (magie ou attaque).
 * @param attack  L'attaque portée, si <code>hitType == HitType.ATTACK</code>, sinon
 *                <code>null</code>.
 * @param magic   La magie utilisée, si <code>hitType == HitType.MAGIE</code>, sinon
 *                <code>null</code>.
 */
public record Hit(HitType hitType, IHeroeAttack attack, IHeroeMagic magic)
{
    /**
     * @return Le nom du coup porté.
     */
    public String getHitName()
    {
        return hitType() == HitType.MAGIC ? magic().getName() : attack().getName();
    }
}
