package fr.simplon.lordofsimplon.impl.persist;

import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.heroes.MagicType;
import fr.simplon.lordofsimplon.api.persist.ILordOfSimplonGameLoader;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.heroes.Heroe;
import fr.simplon.lordofsimplon.impl.heroes.HeroeAttack;
import fr.simplon.lordofsimplon.impl.heroes.HeroeMagic;

import java.util.ArrayList;
import java.util.List;

/**
 * Charge les caractéristiques du jeu par défaut avec 3 personnages.
 */
public class LordOfSimplonDefaultGameLoader implements ILordOfSimplonGameLoader
{
    private List<IHeroe> heroes;
    private List<Player> mPlayers;

    @Override
    public List<IHeroe> loadHeroes()
    {
        if (heroes == null)
        {
            heroes = new ArrayList<>();
            heroes.add(createPaladin());
            heroes.add(createRashuk());
            heroes.add(createShifumy());
        }
        return heroes;
    }

    /**
     * Charge la liste des joueurs par défaut et la retourne.
     *
     * @return Une liste de 3 joueurs.
     */
    public List<Player> loadPlayers()
    {
        if (mPlayers == null)
        {
            mPlayers = new ArrayList<>();
            mPlayers.add(new Player(getHeroes().get(0), "Player 1", 0));
            mPlayers.add(new Player(getHeroes().get(1), "Player 2", 0));
            mPlayers.add(new Player(getHeroes().get(2), "Ordinateur", 0));
        }
        return mPlayers;
    }

    public List<IHeroe> getHeroes()
    {
        return loadHeroes();
    }

    public List<Player> getPlayers()
    {
        return loadPlayers();
    }

    /**
     * Les héros de la famille des Paladins donnent des coups très forts mais par contre leur sort
     * de régénération est assez faible.
     *
     * @return Un héros de la famille de "Paladins".
     */
    public Heroe createPaladin()
    {
        String urlPrefix = getClass().getPackageName().replaceAll(".", "/");
        Heroe h1 = new Heroe("Paladin", urlPrefix + "/Paladin.png");
        h1.getAttacks().add(new HeroeAttack("Epée capricieuse", 12));
        h1.getAttacks().add(new HeroeAttack("Poing malin", 14));
        h1.getAttacks().add(new HeroeAttack("Croche-pied", 10));
        h1.getMagics().add(new HeroeMagic("Picon-bière", MagicType.REGENERATE, 5));
        return h1;
    }

    /**
     * Les héros de la famille des Rashuks sont équilibrés.
     *
     * @return Un héros de la famille des "Rashuks".
     */
    public Heroe createRashuk()
    {
        String urlPrefix = getClass().getPackageName().replace(".", "/");
        Heroe heroe = new Heroe("Rashuk", urlPrefix + "/Rashuk.png");
        heroe.getAttacks().add(new HeroeAttack("Sabre délicieux", 10));
        heroe.getAttacks().add(new HeroeAttack("Pied levé", 12));
        heroe.getAttacks().add(new HeroeAttack("Verbe irrégulier", 8));
        heroe.getMagics().add(new HeroeMagic("Graines de shia", MagicType.REGENERATE, 10));
        return heroe;
    }

    /**
     * Les personnages Shifumy donnent des coups légers mais se régénèrent fortement grâce à un sort
     * de magie. Ils peuvent également augmenter leurs dégâts grâce à de la magie.
     *
     * @return Un Heroe de la famille "Shifumy".
     */
    public Heroe createShifumy()
    {
        String urlPrefix = getClass().getPackageName().replace(".", "/");
        Heroe heroe = new Heroe("Shifumy", urlPrefix + "/Shifumy.png");
        heroe.getAttacks().add(new HeroeAttack("Coin de table", 8));
        heroe.getAttacks().add(new HeroeAttack("Eau bouillante", 8));
        heroe.getAttacks().add(new HeroeAttack("Clous rouillés", 8));
        heroe.getMagics().add(new HeroeMagic("Fontaine de jouvence", MagicType.REGENERATE, 15));
        heroe.getMagics().add(new HeroeMagic("Mal de mer", MagicType.BOOST_DAMAGES, 5));
        return heroe;
    }
}
