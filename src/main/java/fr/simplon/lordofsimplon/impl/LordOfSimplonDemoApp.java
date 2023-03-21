package fr.simplon.lordofsimplon.impl;

import fr.simplon.lordofsimplon.api.fight.IFight;
import fr.simplon.lordofsimplon.api.fight.IFightListener;
import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.persist.ILordOfSimplonGameLoader;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.persist.LordOfSimplonDatabaseGameLoader;
import fr.simplon.lordofsimplon.impl.persist.LordOfSimplonPropertiesGameLoader;
import fr.simplon.lordofsimplon.impl.persist.LordOfSimplonPropertiesGameSaver;
import fr.simplon.lordofsimplon.impl.player.AutomaticPlayerInteractions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Application de démonstration permettant de lancer le jeu en mode automatique.
 */
public class LordOfSimplonDemoApp
{
    private ILordOfSimplonGameLoader mLoader;
    private LordOfSimplonGameModel   mGame;

    /**
     * Constructeur par défaut.
     */
    public LordOfSimplonDemoApp(ILordOfSimplonGameLoader loader)
    {
        mLoader = loader;
        mGame = new LordOfSimplonGameModel(Optional.ofNullable(mLoader).orElse(new LordOfSimplonPropertiesGameLoader()));
    }

    public LordOfSimplonGameModel getGame()
    {
        return mGame;
    }

    public List<IHeroe> getHeroes()
    {
        return getGame().getHeroes();
    }

    public List<Player> getPlayers()
    {
        return getGame().getPlayers();
    }

    public int getNbPlayers()
    {
        return getPlayers().size();
    }

    /**
     * Exemple de chargement d'un jeu à partir d'une sauvegarde sur disque puis déroulement d'un
     * combat en mode automatique avec sauvegarde du résultat en fin de combat.
     * <p>
     * Pour effacer les sauvegardes et réinitialiser les compteurs de victoires, supprimez le
     * dossier "$home_directory/.lordofsimplon".
     *
     * @param args Aucun argument requis.
     * @throws IOException En cas de problème d'enregistrement du fichier de sauvegarde.
     */
    public static void main(String[] args) throws IOException
    {
        // Chargement du jeu
        LordOfSimplonDemoApp app = new LordOfSimplonDemoApp(new LordOfSimplonDatabaseGameLoader("lordofsimplon"));

        // Sélection de 2 joueurs au pif pour faire un combat
        if (app.getPlayers().size() >= 2)
        {
            Player p1 = app.getPlayers().get(new Random().nextInt(app.getNbPlayers()));
            Player p2 = app.getPlayers().get(new Random().nextInt(app.getNbPlayers()));
            while (p2 == p1)
            {
                p2 = app.getPlayers().get(new Random().nextInt(app.getNbPlayers()));
            }

            p1.setInteractor(new AutomaticPlayerInteractions());
            p2.setInteractor(new AutomaticPlayerInteractions());

            // Création d'un combat
            IFight fight = app.getGame().createNewFight(p1, p2);
            fight.addListener(new IFightListener() {});

            // Lancement du combat en mode automatique
            FutureTask<Player> task = new FutureTask<>(fight);

            // Attente de la fin du combat
            try (ExecutorService executorService = Executors.newFixedThreadPool(1))
            {
                executorService.submit(task);
                task.get();

                LordOfSimplonPropertiesGameSaver gameSaver = new LordOfSimplonPropertiesGameSaver();
                gameSaver.save(app.getGame());
            }
            catch (InterruptedException | ExecutionException pE)
            {
                throw new RuntimeException(pE);
            }
        }
        else
        {
            System.out.println("Nombre de joueurs insuffisant pour lancer un combat : " + app.getPlayers().size());
        }
    }
}