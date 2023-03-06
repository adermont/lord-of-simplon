package fr.simplon.lordofsimplon.impl.persist;

import fr.simplon.lordofsimplon.impl.LordOfSimplonGameModel;
import fr.simplon.lordofsimplon.api.player.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Sauvegarde du statut du jeu sur le disque.
 */
public class LordOfSimplonPropertiesGameSaver
{
    public static final File FILE = new File(System.getProperty("user.home") + File.separator + ".lordofsimplon" + File.separator + "players.properties");

    public static final String P_PLAYERS="players";
    public static final String P_PLAYERS_COUNT="count";

    /**
     * Sauvegarde des scores des joueurs.
     *
     * @param game Le jeu à sauvegarder.
     */
    public void save(LordOfSimplonGameModel game) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        List<Player> players = game.getPlayers();
        sb.append(String.format("%s.%s=%d%n", P_PLAYERS, P_PLAYERS_COUNT, players.size()));
        int i = 1;
        for (Player player : players)
        {
            sb.append(String.format("%s.%d.name=%s%n", P_PLAYERS, i, player.getName()));
            sb.append(String.format("%s.%d.victoryCount=%s%n", P_PLAYERS, i, player.getVictoryCount()));
            sb.append(String.format("%s.%d.heroeName=%s%n", P_PLAYERS, i, player.getHeroe().getName()));
            i++;
        }
        FILE.getParentFile().mkdirs();
        Files.writeString(FILE.toPath(), sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }
}
