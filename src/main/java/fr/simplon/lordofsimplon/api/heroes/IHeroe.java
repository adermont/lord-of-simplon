package fr.simplon.lordofsimplon.api.heroes;

import fr.simplon.lordofsimplon.impl.heroes.HeroeAttack;
import fr.simplon.lordofsimplon.impl.heroes.HeroeMagic;

import java.util.List;

public interface IHeroe
{
    String getName();

    String getImageUrl();

    List<IHeroeAttack> getAttacks();

    List<IHeroeMagic> getMagics();
}
