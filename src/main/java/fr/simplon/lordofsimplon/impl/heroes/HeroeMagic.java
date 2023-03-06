package fr.simplon.lordofsimplon.impl.heroes;

import fr.simplon.lordofsimplon.api.heroes.IHeroeMagic;
import fr.simplon.lordofsimplon.api.heroes.MagicType;

/**
 * Un sortilège.
 */
public class HeroeMagic implements IHeroeMagic
{
    private String    mName;
    private MagicType mType;
    private int       mPoints;

    /**
     * Constructeur.
     * @param pName Nom de la magie.
     * @param pType Type de magie.
     * @param pPoints Nombre de points de base.
     */
    public HeroeMagic(String pName, MagicType pType, int pPoints)
    {
        mName = pName;
        mType = pType;
        mPoints = pPoints;
    }

    @Override
    public MagicType getType()
    {
        return mType;
    }

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public int getPoints()
    {
        return mPoints;
    }
}
