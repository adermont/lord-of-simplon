package fr.simplon.lordofsimplon.impl.heroes;

import fr.simplon.lordofsimplon.api.heroes.IHeroeAttack;

/**
 * Une attaque standard.
 */
public class HeroeAttack implements IHeroeAttack
{
    private String mName;
    private int    mPoints;

    /**
     * Constructeur.
     * @param pName Nom de l'attaque.
     * @param pPoints Nombre de points de base.
     */
    public HeroeAttack(String pName, int pPoints)
    {
        mName = pName;
        mPoints = pPoints;
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
