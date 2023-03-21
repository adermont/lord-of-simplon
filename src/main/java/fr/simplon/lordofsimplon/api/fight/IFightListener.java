package fr.simplon.lordofsimplon.api.fight;

import fr.simplon.lordofsimplon.api.heroes.MagicType;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.fight.Fight;

/**
 * Evènements diffusés pendant un combat.
 */
public interface IFightListener
{
    /**
     * Evènement déclenché au début du combat.
     *
     * @param pFight Le combat qui vient d'être lancé.
     */
    default void onFightStarted(IFight pFight)
    {
        System.out.printf("==================== NOUVEAU COMBAT ========================%n");
        Player player1 = pFight.getPlayers().get(0);
        Player player2 = pFight.getPlayers().get(1);
        System.out.printf("'%s' (%s) vs '%s' (%s)%n", player1.getName(), player1.getHeroe().getName(), player2.getName(), player2.getHeroe().getName());
    }

    /**
     * Evènement diffusé avant chaque tour du combat.
     *
     * @param pStep La prochaine étape du combat.
     */
    default void onFightStep(FightStep pStep)
    {
        HitType hitType = pStep.hit().hitType();
        if (hitType == HitType.MAGIC)
        {
            if (pStep.hit().magic().getType() == MagicType.BOOST_DAMAGES)
            {
                System.out.printf("Tour n°%d : +++++BOOST '%s'! '%s' augmente ses dommages de +%d points de base%n",
                                  pStep.stepNumber(),
                                  pStep.hit().magic().getName(),
                                  pStep.attacking().getName(),
                                  pStep.hit().magic().getPoints());
            }
            else
            {
                System.out.printf("Tour n°%d : '%s' lance un sort '%s' de type '%s' (%d points de base) %n",
                                  pStep.stepNumber(),
                                  pStep.attacking().getName(),
                                  pStep.hit().magic().getName(),
                                  pStep.hit().magic().getType().toString(),
                                  pStep.hit().magic().getPoints());
            }
        }
        else if (hitType == HitType.ATTACK)
        {
            System.out.printf("Tour n°%d : '%s' lance une attaque '%s' sur '%s' (%d points de base) %n",
                              pStep.stepNumber(),
                              pStep.attacking().getName(),
                              pStep.hit().attack().getName(),
                              pStep.defending().getName(),
                              pStep.hit().attack().getPoints());
        }
    }

    default void onHitApplied(FightStep pStep)
    {
        Player player1 = pStep.fight().getPlayers().get(0);
        String namePlayer1 = player1.getName();
        Player player2 = pStep.fight().getPlayers().get(1);
        String namePlayer2 = player2.getName();
        System.out.printf("-- Scores : '%s' [%d pts | %d pts] '%s'%n", namePlayer1, player1.getEnergy(), player2.getEnergy(), namePlayer2);
    }

    default void onFightFinished(Fight pFight)
    {
        System.out.printf("==================== COMBAT TERMINE ========================%n");
        System.out.printf("Vainqueur : %s (%d victoires)%n", pFight.getWinner().getName(), pFight.getWinner().getVictoryCount());
        System.out.printf("============================================================%n");
    }
}
