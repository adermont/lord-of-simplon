package fr.simplon.lordofsimplon.impl.heroes;

import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.heroes.IHeroeAttack;
import fr.simplon.lordofsimplon.api.heroes.IHeroeMagic;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un héros du jeu, ou plutôt un représentant d'une guilde par exemple. Heroe est plutôt
 * une famille de personnages, avec ses caractéristiques (une liste d'attaques possibles ainsi
 * qu'une liste de sortilèges). On peut imaginer des évolutions où les héros auront des armes
 * favorites par exemple.
 */
public class Heroe implements IHeroe
{
    private String mName;
    private String mImageUrl;

    private List<IHeroeAttack> mAttacks;
    private List<IHeroeMagic>  mMagics;

    /**
     * Constructeur.
     *
     * @param pName     Nom de la famille du héros.
     * @param pImageUrl URL de l'image de l'avatar du héros.
     */
    public Heroe(String pName, String pImageUrl)
    {
        mName = pName;
        mImageUrl = pImageUrl;
        mAttacks = new ArrayList<>();
        mMagics = new ArrayList<>();
    }

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public String getImageUrl()
    {
        return mImageUrl;
    }

    @Override
    public List<IHeroeAttack> getAttacks()
    {
        return mAttacks;
    }

    @Override
    public List<IHeroeMagic> getMagics()
    {
        return mMagics;
    }
}
