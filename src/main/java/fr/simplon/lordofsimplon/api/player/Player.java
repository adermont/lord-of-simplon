package fr.simplon.lordofsimplon.api.player;

import fr.simplon.lordofsimplon.api.fight.Hit;
import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.impl.fight.Fight;

import java.util.List;

/**
 * Un joueur.
 */
public class Player
{
    public static final int MAX_ENERGY = 50;

    private String mName;
    private IHeroe mHeroe; // type de héro
    private int    mEnergy; // évolue pendant les combats
    private int    mNbVictories;

    private IPlayerInteractions mPlayerInteractions;

    public Player(IHeroe pHeroe, String pName, int pNbVictories)
    {
        mHeroe = pHeroe;
        mName = pName;
        mNbVictories = pNbVictories;
        mEnergy = MAX_ENERGY;
    }

    public void setInteractor(IPlayerInteractions pInteractor)
    {
        mPlayerInteractions = pInteractor;
    }

    public String getName()
    {
        return mName;
    }

    public IHeroe getHeroe()
    {
        return mHeroe;
    }

    public int getEnergy()
    {
        return mEnergy;
    }

    public int getVictoryCount()
    {
        return mNbVictories;
    }

    public void damage(int nbPoints)
    {
        mEnergy = Math.max(0, mEnergy - nbPoints);
    }

    public void regenerate(int nbPoints)
    {
        mEnergy = Math.min(MAX_ENERGY, mEnergy + nbPoints);
    }

    public void win(Fight f)
    {
        if (f.getWinner() != this)
        {
            throw new IllegalArgumentException("Tentative de triche ! le gagnant du combat n'est pas ce joueur");
        }
        mNbVictories++;
    }

    /**
     * Demande au joueur de choisir son prochain coup.
     * @param pPossibleHits
     * @return Le prochain coup porté à l'adversaire.
     */
    public Hit chooseNextHit(List<Hit> pPossibleHits)
    {
        if (mPlayerInteractions == null)
        {
            throw new RuntimeException("Utilisez d'abord setInteractor(IPlayerInteractions) pour configurer un interacteur.");
        }
        return mPlayerInteractions.chooseNextHit(this, pPossibleHits);
    }
}
