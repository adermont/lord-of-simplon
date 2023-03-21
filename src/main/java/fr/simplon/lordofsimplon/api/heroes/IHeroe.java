package fr.simplon.lordofsimplon.api.heroes;

import java.util.List;

public interface IHeroe
{
    String getName();

    String getImageUrl();

    List<IHeroeAttack> getAttacks();

    List<IHeroeMagic> getMagics();
}
