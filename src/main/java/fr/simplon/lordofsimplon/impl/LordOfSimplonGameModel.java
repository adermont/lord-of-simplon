package fr.simplon.lordofsimplon.impl;

import fr.simplon.lordofsimplon.api.fight.IFight;
import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.persist.ILordOfSimplonGameLoader;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.fight.Fight;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Modèle où sont stockées les infos du jeu : joueurs et héros.
 */
public class LordOfSimplonGameModel
{
    private List<Player> mPlayers;
    private List<IHeroe>  mHeroes;

    /**
     * Constructeur.
     *
     * @param loader Le chargeur du jeu.
     */
    public LordOfSimplonGameModel(ILordOfSimplonGameLoader loader)
    {
        mHeroes = loader.loadHeroes();
        mPlayers = loader.loadPlayers();
    }

    /**
     * Ajoute un nouveau joueur.
     *
     * @param pPlayer Le joueur à ajouter.
     */
    public void addPlayer(Player pPlayer)
    {
        if (mPlayers.contains(pPlayer))
        {
            throw new IllegalArgumentException("Joueur déjà inscrit");
        }
        mPlayers.add(pPlayer);
    }

    /**
     * @return La liste des joueurs.
     */
    public List<Player> getPlayers()
    {
        return mPlayers;
    }

    /**
     * @return Liste des héros disponibles pour créer un avatar.
     */
    public List<IHeroe> getHeroes()
    {
        return mHeroes;
    }

    /**
     * Crée un nouveau combat et le retourne sous forme d'un résultat futur. Pour le lancer il faut
     * utiliser la méthode {@link java.util.concurrent.ExecutorService#submit(Callable)} et attendre
     * le résultat avec {@link FutureTask#get()} qui est une méthode bloquante.
     *
     * @param p1 Le joueur 1.
     * @param p2 Le joueur 2.
     * @return Le résultat futur du combat.
     */
    public IFight createNewFight(Player p1, Player p2)
    {
        return new Fight(p1, p2);
    }

}
