package fr.simplon.lordofsimplon.impl.persist;

import fr.simplon.lordofsimplon.api.heroes.IHeroe;
import fr.simplon.lordofsimplon.api.player.Player;
import fr.simplon.lordofsimplon.impl.jpa.HeroeDO;
import fr.simplon.lordofsimplon.impl.jpa.PlayerDO;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;
import java.util.Random;

/**
 * Charge les héros du jeu à partir d'un fichier de propriétés et récupère les joueurs en base de données.
 */
public class LordOfSimplonDatabaseGameLoader extends LordOfSimplonPropertiesGameLoader
{
    private final EntityManagerFactory mFactory;

    /**
     * Constructeur.
     */
    public LordOfSimplonDatabaseGameLoader(String persistentUnit)
    {
        super();
        mFactory = Persistence.createEntityManagerFactory(persistentUnit);
    }

    /**
     * Trace un message dans les logs en utilisant {@link String#format(String, Object...)}.
     *
     * @param s    Message à tracer.
     * @param args Les arguments du message.
     */
    private void log(String s, Object... args)
    {
        System.out.printf(s, args);
        // Logger.getLogger(getClass().getName()).log(Level.INFO, String.format(s, args));
    }

    @Override
    public List<Player> loadPlayers()
    {
        // On supprime tous les joueurs existants car on va les charger depuis la base de données.
        mPlayers.clear();

        // Utilisation du formalisme "try-with-resources" pour créer un EntityManager
        // car à la fin de la méthode on doit le refermer avec la méthode entityManager.close().
        try (EntityManager entityManager = mFactory.createEntityManager())
        {
            // Requête SELECT COUNT(*) "en dur" (attention, c'est plus rapide mais moins évolutif).
            Query nativeQuery = entityManager.createNativeQuery("select count(*) from player", Long.class);
            Long iCount = (Long) nativeQuery.getSingleResult();
            log("%d joueur(s) trouvé(s) en base de données%n", iCount);

            // Ici la bonne manière de faire en entreprise :
            /*
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> sql = criteriaBuilder.createQuery(Long.class);
            sql.select(criteriaBuilder.count(sql.from(PlayerDO.class)));
            TypedQuery<Long> query = entityManager.createQuery(sql);
            query.getSingleResult();
            */

            // On va ensuite faire un "SELECT * FROM player" avec l'API JPA Criteria
            //
            // Etape 1 : création d'un CriteriaBuilder pour gérer les mots-clés SELECT/FROM/WHERE/AND ...
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            // Etape 2 : Création d'une requête avec critères
            CriteriaQuery<PlayerDO> query = criteriaBuilder.createQuery(PlayerDO.class);

            // Etape 3 : traduire la requête de SQL vers l'API JPA Criteria
            query.select(query.from(PlayerDO.class)); // équivaut à "SELECT * FROM player"

            // Etape 4 : exécution de la requête
            List<PlayerDO> result = entityManager.createQuery(query).getResultList();

            // On parcourt tous les joueurs et on les converti en type "Player" au lieu de PlayerDO.
            for (PlayerDO dbPlayer : result)
            {
                Player player = createPlayer(dbPlayer);
                mPlayers.add(player);
                log("Player %s (%s) : %d victoires%n", dbPlayer.getName(), dbPlayer.getHeroe().getName(), dbPlayer.getVictoryCount());
            }
        }
        return mPlayers;
    }

    /**
     * Mapping d'un objet PlayerDO vers un objet Player. Ne pas utiliser directement les objets PlayerDO dans le jeu
     * permet de découpler le code du jeu lui-même, du code qui sert à stocker les données en base de données.
     *
     * @param pPlayer Le joueur au format  {@link PlayerDO}.
     * @return Le même joueur mais converti au format {@link Player}.
     */
    private Player createPlayer(PlayerDO pPlayer)
    {
        try (EntityManager entityManager = mFactory.createEntityManager())
        {
            // Nous allons maintenant expérimenter les requêtes JPQL, plus simples :
            TypedQuery<HeroeDO> query = entityManager.createQuery("FROM HeroeDO", HeroeDO.class);
            //
            // Le code précédent remplace tout le code suivant (au format JPA Criteria) !!!
            // CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            // CriteriaQuery<HeroeDO> sql = criteriaBuilder.createQuery(HeroeDO.class);
            // Root<HeroeDO> tableHeroe = sql.from(HeroeDO.class);
            // sql.select(tableHeroe).where(criteriaBuilder.equal(tableHeroe.get("id"), pPlayer.getHeroe().getId()));
            // TypedQuery<HeroeDO> query = entityManager.createQuery(sql);

            // Exécution de la requête
            List<HeroeDO> resultList = query.getResultList();

            IHeroe heroe = null;
            if (!resultList.isEmpty())
            {
                final HeroeDO heroeDo = resultList.get(0);
                heroe = getHeroes().stream().filter(h -> h.getName().equals(heroeDo.getName())).findFirst().orElse(null);
            }
            else
            {
                heroe = getHeroes().get(new Random(System.currentTimeMillis()).nextInt(getHeroes().size()));
                log("Player %s: aucun héros associé, assignation d'un héros automatique ==> %s", pPlayer.getName(), heroe.getName());
            }
            return new Player(heroe, pPlayer.getName(), pPlayer.getVictoryCount());
        }
    }
}
