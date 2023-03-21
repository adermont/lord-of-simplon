package fr.simplon.lordofsimplon.impl.fight;

import fr.simplon.lordofsimplon.api.fight.FightStep;
import fr.simplon.lordofsimplon.api.fight.Hit;
import fr.simplon.lordofsimplon.api.fight.IFight;
import fr.simplon.lordofsimplon.api.fight.IFightListener;
import fr.simplon.lordofsimplon.api.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implémentation par défaut d'un combat.
 */
public class Fight implements IFight
{
    private List<IFightListener> mListeners;
    private List<Player>         mPlayers;
    private boolean              isStarted;
    private Player               mWinner;
    private int                  mStep;
    private FightingRules        mRules;

    public Fight(Player h1, Player h2)
    {
        mPlayers = Arrays.asList(h1, h2);
        mListeners = new CopyOnWriteArrayList<>();
        mRules = new FightingRules();
    }

    @Override
    public List<Player> getPlayers()
    {
        return mPlayers;
    }

    @Override
    public boolean isStarted()
    {
        return isStarted;
    }

    @Override
    public boolean isOver()
    {
        return !isStarted;
    }

    @Override
    public Player getWinner()
    {
        return mWinner;
    }

    @Override
    public void addListener(IFightListener pListener)
    {
        mListeners.add(pListener);
    }

    @Override
    public void removeListener(IFightListener pListener)
    {
        mListeners.remove(pListener);
    }

    @Override
    public Player call() throws Exception
    {
        fight();
        return mWinner;
    }

    @Override
    public void fight()
    {
        if (isStarted())
        {
            throw new IllegalStateException("Le combat est déjà en cours");
        }
        if (getWinner() != null)
        {
            throw new IllegalStateException("Le combat a déjà eu lieu");
        }
        isStarted = true;
        mStep = 1;

        mListeners.forEach(l -> l.onFightStarted(this));

        while (isStarted)
        {
            nextStep();
        }

        mListeners.forEach(l -> l.onFightFinished(this));
    }

    @Override
    public void nextStep()
    {
        Player attackingPlayer = mPlayers.get(mStep % 2);
        Player defensivePlayer = mPlayers.get((mStep + 1) % 2);

        // On calcule la liste des coups possibles pour le joueur qui attaque
        List<Hit> possibleHits = mRules.getPossibleHits(attackingPlayer);
        Hit choosenHit = null;
        do
        {
            // On demande au joueur qui attaque de choisir un coup
            choosenHit = attackingPlayer.chooseNextHit(possibleHits);
        } while (!mRules.isPossibleHit(attackingPlayer, choosenHit));

        // On crée une nouvelle étape du combat
        FightStep step = new FightStep(this, mStep, attackingPlayer, defensivePlayer, choosenHit);

        // On avertit les listeners qu'on est prêt à lancer une nouvelle étape du combat
        mListeners.forEach(l -> l.onFightStep(step));

        // On applique les règles de combat
        mRules.applyHit(step);
        mListeners.forEach(l -> l.onHitApplied(step));

        // Si le défenseur perd toute son énergie, le combat s'arrête et on déclare l'attaquant vainqueur
        if (isStarted && defensivePlayer.getEnergy() <= 0)
        {
            mWinner = attackingPlayer;
            mWinner.win(this);
            isStarted = false;
        }
        mStep++;
    }
}
