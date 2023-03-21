package fr.simplon.lordofsimplon.impl.persist;

import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.heroes.MagicType;
import fr.simplon.lordofsimplon.api.persist.ILordOfSimplonGameLoader;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.heroes.Heroe;
import fr.simplon.lordofsimplon.impl.heroes.HeroeAttack;
import fr.simplon.lordofsimplon.impl.heroes.HeroeMagic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Charge les caractéristiques du jeu à partir d'un fichier de propriétés.
 */
public class LordOfSimplonPropertiesGameLoader extends LordOfSimplonDefaultGameLoader implements ILordOfSimplonGameLoader
{
    // Heroes ========================================================
    public static final String P_MAGIC_POINTS   = ".points";
    public static final String P_NAME           = ".name";
    public static final String P_IMG            = ".imageUrl";
    public static final String P_ATTACKS        = ".attacks";
    public static final String P_ATTACK_NAME    = ".name";
    public static final String P_ATTACK_DAMAGES = ".damages";
    public static final String P_MAGICS         = ".magics";
    public static final String P_MAGIC_NAME     = ".name";
    public static final String P_MAGIC_TYPE     = ".type";

    // Players ========================================================
    public static final String P_PLAYER_NAME          = ".name";
    public static final String P_PLAYER_VICTORY_COUNT = ".victoryCount";
    public static final String P_PLAYER_HEROE         = ".heroeName";

    private final Properties   mHeroesProperties;
    private final Properties   mPlayersProperties;
    private final List<IHeroe> mHeroes;
    protected final List<Player> mPlayers;

    /**
     * Constructeur.
     */
    public LordOfSimplonPropertiesGameLoader()
    {
        mHeroes = new ArrayList<>();
        mPlayers = new ArrayList<>();
        mHeroesProperties = new Properties();
        mPlayersProperties = new Properties();
        try
        {
            mHeroesProperties.load(getClass().getResourceAsStream("/fr/simplon/lordofsimplon/heroes.properties"));
        }
        catch (IOException pE)
        {
            pE.printStackTrace();
            mHeroes.addAll(new LordOfSimplonDefaultGameLoader().loadHeroes());
        }
        try (FileInputStream fis = new FileInputStream(LordOfSimplonPropertiesGameSaver.FILE))
        {
            mPlayersProperties.load(fis);
        }
        catch (IOException pE)
        {
            pE.printStackTrace();
            mPlayers.addAll(new LordOfSimplonDefaultGameLoader().loadPlayers());
        }
    }

    @Override
    public List<IHeroe> loadHeroes()
    {
        String sHeroes = mHeroesProperties.getProperty("heroes", "");
        try (Scanner scanner = new Scanner(sHeroes))
        {
            scanner.useDelimiter("\\s*,\\s*");
            while (scanner.hasNext())
            {
                String sHeroKey = scanner.next().trim();
                Heroe heroe = createHeroe(sHeroKey);
                mHeroes.add(heroe);
            }
        }
        return mHeroes;
    }

    @Override
    public List<Player> loadPlayers()
    {
        String hCount = mPlayersProperties.getProperty("players.count", "0");
        int iCount = Integer.parseInt(hCount);
        if (iCount > 0)
        {
            mPlayers.clear();
            for (int i = 1; i <= iCount; i++)
            {
                mPlayers.add(createPlayer("players." + i));
            }
        }
        return mPlayers;
    }

    public List<IHeroe> getHeroes()
    {
        return mHeroes;
    }

    public Heroe createHeroe(String pHeroKey)
    {
        String heroeName = getHeroeName(pHeroKey);
        String heroeImage = getHeroeImage(pHeroKey);
        Heroe h1 = new Heroe(heroeName, heroeImage);
        h1.getAttacks().addAll(getHeroeAttacks(pHeroKey));
        h1.getMagics().addAll(getHeroeMagics(pHeroKey));
        return h1;

    }

    private String getHeroeName(String pHeroKey)
    {
        return mHeroesProperties.getProperty(pHeroKey + P_NAME);
    }

    private String getHeroeImage(String pHeroKey)
    {
        return mHeroesProperties.getProperty(pHeroKey + P_IMG);
    }

    private List<HeroeAttack> getHeroeAttacks(String pHeroKey)
    {
        List<HeroeAttack> attacks = new ArrayList<>();
        String sAttacks = mHeroesProperties.getProperty(pHeroKey + P_ATTACKS);
        int iAttacks = Integer.parseInt(sAttacks);
        for (int i = 1; i <= iAttacks; i++)
        {
            String sAttackKey = String.format("%s%s%s%d", pHeroKey, P_ATTACKS, ".", i);
            String sAttackName = mHeroesProperties.getProperty(sAttackKey + P_ATTACK_NAME);
            String sAttackDamages = mHeroesProperties.getProperty(sAttackKey + P_ATTACK_DAMAGES);
            int iAttackDamages = Integer.parseInt(sAttackDamages);
            HeroeAttack attack = new HeroeAttack(sAttackName, iAttackDamages);
            attacks.add(attack);
        }
        return attacks;
    }

    private List<HeroeMagic> getHeroeMagics(String pHeroKey)
    {
        List<HeroeMagic> magics = new ArrayList<>();
        String sMagics = mHeroesProperties.getProperty(pHeroKey + P_MAGICS);
        int iMagics = Integer.parseInt(sMagics);
        for (int i = 1; i <= iMagics; i++)
        {
            String sMagicKey = String.format("%s%s%s%d", pHeroKey, P_MAGICS, ".", i);
            String sMagicName = mHeroesProperties.getProperty(sMagicKey + P_MAGIC_NAME);
            String sMagicType = mHeroesProperties.getProperty(sMagicKey + P_MAGIC_TYPE);
            MagicType type = MagicType.valueOf(sMagicType);
            String sMagicPoints = mHeroesProperties.getProperty(sMagicKey + P_MAGIC_POINTS);
            int iMagicPoints = Integer.parseInt(sMagicPoints);
            HeroeMagic magic = new HeroeMagic(sMagicName, type, iMagicPoints);
            magics.add(magic);
        }
        return magics;
    }

    private Player createPlayer(String pPlayerKey)
    {
        String playerName = mPlayersProperties.getProperty(pPlayerKey + P_PLAYER_NAME);
        String sVictoryCount = mPlayersProperties.getProperty(pPlayerKey + P_PLAYER_VICTORY_COUNT);
        int iVictoryCount = Integer.parseInt(sVictoryCount);
        String heroeName = mPlayersProperties.getProperty(pPlayerKey + P_PLAYER_HEROE);
        Optional<IHeroe> playerHeroe = getHeroes().stream().filter(h -> h.getName().equals(heroeName)).findFirst();
        IHeroe heroe = null;
        if (playerHeroe.isEmpty())
        {
            heroe = getHeroes().get(0);
        }
        else
        {
            heroe = playerHeroe.get();
        }
        return new Player(heroe, playerName, iVictoryCount);
    }
}
