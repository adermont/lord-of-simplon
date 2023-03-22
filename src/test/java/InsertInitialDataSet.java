import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.persist.ILordOfSimplonGameLoader;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.LordOfSimplonGameModel;
import fr.simplon.lordofsimplon.impl.jpa.HeroeDO;
import fr.simplon.lordofsimplon.impl.jpa.PlayerDO;
import fr.simplon.lordofsimplon.impl.persist.LordOfSimplonDefaultGameLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * On insère un jeu de données par défaut dans la BDD.
 */
public class InsertInitialDataSet
{
    public static void main(String[] args)
    {
        // Chargement du jeu par défaut (utilisation du DefaultGameLoader)
        ILordOfSimplonGameLoader gameLoader = new LordOfSimplonDefaultGameLoader();
        LordOfSimplonGameModel game = new LordOfSimplonGameModel(gameLoader);

        // Génération du schéma en BDD
        // Cf. https://docs.oracle.com/javaee/7/tutorial/persistence-intro005.htm
        Properties p = new Properties();
        p.put("jakarta.persistence.schema-generation.database.action", /*none | create | drop | drop-and-create */"create");
        //        p.put("jakarta.persistence.schema-generation.create-source", "script");
        //        p.put("jakarta.persistence.schema-generation.create-script-source", "META-INF/sql/create.sql");
        //        p.put("jakarta.persistence.schema-generation.drop-source", /*metadata | script | metadata-then-script | script-then-metadata*/"script");
        //        p.put("jakarta.persistence.schema-generation.drop-script-source", "META-INF/sql/drop.sql");
        //        p.put("jakarta.persistence.sql-load-script-source", "META-INF/sql/data.sql");
        Persistence.generateSchema("lordofsimplon", p);

        // Création de la factory permettant d'obtenir les EntityManagers JPA
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lord-of-simplon", p);

        // Création de l'EntityManager
        try (EntityManager entityManager = factory.createEntityManager();)
        {
            // Ouverture d'une transaction
            entityManager.getTransaction().begin();

            // Suppression des données existantes
            Query deleteFromPlayerDO = entityManager.createQuery("delete from PlayerDO");
            Query deleteFromHeroeDO = entityManager.createQuery("delete from HeroeDO");
            deleteFromPlayerDO.executeUpdate();
            deleteFromHeroeDO.executeUpdate();

            // Insertion des héros en BDD
            List<IHeroe> heroes = game.getHeroes();
            List<HeroeDO> heroesDOs = new ArrayList<>();
            for (IHeroe heroe : heroes)
            {
                // On crée l'objet JPA de type HeroeDO à partir du Heroe
                HeroeDO heroeDO = new HeroeDO();
                heroeDO.setName(heroe.getName());

                entityManager.persist(heroeDO);
                heroesDOs.add(heroeDO);
            }

            // Récupération des joueurs à insérer en BDD
            List<Player> players = game.getPlayers();
            for (Player player : players)
            {
                // On récupère une référence au HeroeDO qui sera associé au PlayerDO via son id
                int idHeroe = heroesDOs.stream().filter(h -> h.getName().equals(player.getHeroe().getName())).findFirst().orElseThrow().getId();
                HeroeDO heroeDO = entityManager.getReference(HeroeDO.class, idHeroe);

                // Création du PlayerDO
                PlayerDO playerData = new PlayerDO();
                playerData.setName(player.getName());
                playerData.setVictoryCount(player.getVictoryCount());
                playerData.setHeroe(heroeDO);

                // Persistence dans la BDD
                entityManager.persist(playerData);
            }
            // Validation de la transaction
            entityManager.getTransaction().commit();
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }
}
