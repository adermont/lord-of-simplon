package fr.simplon.lordofsimplon.api.fight;

import fr.simplon.lordofsimplon.api.player.Player;

import java.util.List;
import java.util.concurrent.Callable;

public interface IFight extends Callable<Player>
{
    List<Player> getPlayers();

    boolean isStarted();

    boolean isOver();

    Player getWinner();

    void addListener(IFightListener pListener);

    void removeListener(IFightListener pListener);

    void fight();

    void nextStep();
}
