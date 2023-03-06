package fr.simplon.lordofsimplon.api.persist;

import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.player.Player;

import java.util.List;

/**
 * Interface permettant de charger un ensemble de héros sans spécifier comment (fichier, bdd...).
 */
public interface ILordOfSimplonGameLoader
{
    /**
     * Charge un ensemble de héros à partir d'une source.
     *
     * @return La liste des héros trouvés.
     */
    List<IHeroe> loadHeroes();

    /**
     * Charge un ensemble de joueurs à partir d'une source.
     *
     * @return La liste des joueurs trouvés.
     */
    List<Player> loadPlayers();
}
