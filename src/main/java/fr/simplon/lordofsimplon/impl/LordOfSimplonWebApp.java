package fr.simplon.lordofsimplon.impl;

import fr.simplon.www.requesthandlers.AbstractHttpRequestHandler;
import fr.simplon.www.requesthandlers.ReturnResource;
import fr.simplon.www.server.Endpoint;
import fr.simplon.www.server.HttpServer;

/**
 * Lance un serveur web simplifié sur le port 8080 par défaut, avec comme page d'accueil le site du jeu Lord of Simplon.
 * Le serveur ne gère pas les sessions ni les cookies.
 * <p>
 * Pour modifier le port du serveur, passez en argument le numéro de port que vous désirez. <br/> Exemple :
 * <pre>
 *     $ java fr.simplon.lordofsimplon.impl.LordOfSimplonWebApp 8082
 * </pre>
 *
 * <p>
 * Pour les formulaires HTML dont l'action est dirigée vers ce serveur, utilisez l'encodage
 * <strong>x-www-form-urlencoded</strong> :
 * <pre>
 *     enctype="application/x-www-form-urlencoded"
 * </pre>
 */
public class LordOfSimplonWebApp
{
    public static void main(String[] args)
    {
        // Création d'un objet HttpServeur
        HttpServer server = new HttpServer("src/main/html");

        // Création de 2 gestionnaires par défaut pour traiter les requêtes HTTP
        AbstractHttpRequestHandler returnIndexHtml = new ReturnResource("index.html"); // Pour la page d'accueil
        AbstractHttpRequestHandler returnSameAsRequested = new ReturnResource(); // Pour les autres ressources

        // Ajout de tous les "endpoints" (par défaut le serveur n'acceptera aucune requête donc
        // tous les fichiers susceptibles d'être demandés par le navigateur doivent être ajoutés ici)
        server.addEndpoint(new Endpoint("/", returnIndexHtml));
        server.addEndpoint(new Endpoint("/index.html", returnIndexHtml));
        server.addEndpoint(new Endpoint("/favicon.ico", returnSameAsRequested));
        server.addEndpoint(new Endpoint("/styles.css", returnSameAsRequested));
        server.addEndpoint(new Endpoint("/img/Paladin.png", returnSameAsRequested));
        server.addEndpoint(new Endpoint("/img/Rashuk.png", returnSameAsRequested));
        server.addEndpoint(new Endpoint("/img/Shifumy.png", returnSameAsRequested));

        // Ici les endpoints pour les pages du jeu
        server.addEndpoint(new Endpoint("/correction-fight.html", returnSameAsRequested));

        int port = 8080;
        if (args.length > 0)
        {
            try
            {
                port = Integer.parseInt(args[0]);

                // On vérifie que le port est entre 1 et 65535
                port = Math.min(65535, port);
                port = Math.max(1, port);
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }

        // Démarrage du serveur (méthode bloquante)
        server.listen(port);
    }
}
