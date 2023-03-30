let sortilegePlayer1 = {
    nom: null,
    nbPointsDommages: 0,
    nbToursRestants: 0
};

let sortilegePlayer2 = {
    nom: null,
    nbPointsDommages: 0,
    nbToursRestants: 0
};

/**
 * Fonction qui lance une magie de régénération : augmentation du score du joueur
 * et ajout d'un nouveau coup reçu dans la liste des coups du joueur.
 *
 * @param numPlayer Numéro du joueur attaqué.
 * @param nameRegen Nom de l'attaque.
 * @param nbPoints Nombre de point en moins.
 */
function regeneratePlayer(numPlayer, nameRegen, nbPoints) {

    // On récupère les éléments du DOM avec lesquels on va travailler
    let divScorePlayer = document.getElementById('score-player' + numPlayer);
    let divCoupsRecus = document.getElementById('liste-coups-recus-player' + numPlayer);

    // On récupère le score actuel sous forme d'une string :
    let txtScore = divScorePlayer.innerText;

    // Conversion en integer :
    // https://www.tutorialspoint.com/how-to-convert-a-string-into-integer-in-javascript
    let newScore = parseInt(txtScore) + nbPoints;

    // On ne doit pas pouvoir régénérer au-delà de 100 pts
    let depassement = newScore > 100;
    if ( depassement ) {
        newScore = 100;
    }

    // Modification du score : on change la propriété 'innerHTML' de l'élément DOM
    divScorePlayer.innerText = newScore;

    if (!depassement) {
        // Ajout d'un nouveau coup reçu (ATTENTION à bien utiliser le caractère back-quote ` (touches ALTGR+7 puis ESPACE) et non pas ' (touche 4)
        divCoupsRecus.innerHTML += `<div class="coup"><div><p>Régénération</p><h1>${nameRegen}</h1></div><span class="hit-regen">+${nbPoints} pts</span></div>`;

        // Après avoir ajouté un coup, on scroll automatiquement pour l'afficher
        // Attention la fonction "Element.scroll()" prend en paramètre un dictionnaire au format "{clé1: valeur1, clé2: valeur2 ...}"
        divCoupsRecus.scroll({top: 1000, behavior: "smooth"});
    }

    tourSuivant(numPlayer);
}

/**
 * Ajoute une nouvelle attaque dans la liste des coups reçus par le joueur.
 *
 * @param numPlayerAttaquant Numéro du joueur attaquant.
 * @param nameAttack Nom de l'attaque.
 * @param nbPoints Nombre de point en moins.
 */
function attaquePlayer(numPlayerAttaquant, nameAttack, nbPoints) {

    let numPlayer = 1+(numPlayerAttaquant%2);

    // On récupère les éléments du DOM avec lesquels on va travailler
    let divScorePlayer = document.getElementById('score-player' + numPlayer);
    let divCoupsRecus = document.getElementById('liste-coups-recus-player' + numPlayer);

    // On récupère le score actuel sous forme d'une string :
    let txtScore = divScorePlayer.innerText;

    // Conversion en integer :
    // https://www.tutorialspoint.com/how-to-convert-a-string-into-integer-in-javascript
    let newScore = parseInt(txtScore) - nbPoints;

    // Attention pas de scores négatifs
    if ( newScore < 0) {
        newScore = 0;
    }

    // Modification du score : on change la propriété 'innerHTML' de l'élément DOM
    divScorePlayer.innerText = newScore;

    // Ajout d'un nouveau coup reçu : modif du innerHTML
    divCoupsRecus.innerHTML += `<div class="coup"><div><p>Attaque</p><h1>${nameAttack}</h1></div><span class="hit-damages">-${nbPoints} pts</span></div>`;

    // Après avoir ajouté un coup, on scroll automatiquement pour l'afficher
    // Attention la fonction "Element.scroll()" prend en paramètre un dictionnaire au format "{clé1: valeur1, clé2: valeur2 ...}"
    divCoupsRecus.scroll({top: 1000, behavior: "smooth"});

    if ( newScore == 0) {
        finDuCombat();
    } else {
        tourSuivant(numPlayerAttaquant);
    }
}

/**
 * Affecte un sortilège à l'adversaire du joueur courant pour les 3 prochains tours.
 *
 * @param numPlayerAttaquant Joueur courant qui lance le sortilège.
 * @param nameSortilege Nom du sortilège.
 * @param nbPoints Nombre de points de dommages du sortilège.
 */
function sortilege(numPlayerAttaquant, nameSortilege, nbPoints){

    console.info(`Lancement du sortilège ${nameSortilege} par le joueur ${numPlayerAttaquant}`);

    let numPlayerAttacked = 1+(numPlayerAttaquant%2);

    let sortilege = eval('sortilegePlayer' + numPlayerAttacked);
    sortilege.nom = nameSortilege;
    sortilege.nbPointsDommages = nbPoints;
    sortilege.nbToursRestants = 3;

    tourSuivant(numPlayerAttaquant);
}

/**
 * Applique les sortilèges en cours.
 */
function applySortileges(){
    // S'il existe des sortileges en cours, on retire les points de dommages
    // de ces sortilèges aux scores des joueurs sur lesquels ils s'appliquent.

    // Boucle sur les 2 joueurs
    for (numPlayer = 1; numPlayer <= 2; numPlayer++) {

        let divScore = document.getElementById('score-player' + numPlayer);
        let divCoupsRecus = document.getElementById('liste-coups-recus-player' + numPlayer);
        let txtScore = divScore.innerText;
        let iScore = parseInt(txtScore);

        // On récupère le sortilège applicable au joueur courant
        let jsonSortilege = eval('sortilegePlayer' + numPlayer);

        // On regarde si le sortilège est applicable. Pour qu'un sortilège soit applicable
        // il faut que le nombre de tours restants soit supérieur à zéro.
        if (jsonSortilege.nbToursRestants > 0) {

            // Modification du score du joueur impacté par le sortilège
            let newScore = iScore - jsonSortilege.nbPointsDommages;
            if (newScore < 0) {
                newScore = 0;
            }
            divScore.innerText = newScore.toString();

            // Ajout d'une occurrence du sortilège dans la liste des coups reçus
            divCoupsRecus.innerHTML += `<div class="coup"><div><p>Sortilège</p><h1>${jsonSortilege.nom}</h1></div><span class="hit-damages">-${jsonSortilege.nbPointsDommages} pts</span></div>`;
            divCoupsRecus.scroll({top: 1000, behavior: "smooth"});

            // Un tour en moins
            jsonSortilege.nbToursRestants--;

            // Fin du combat ?
            if (newScore == 0) {
                finDuCombat();
            }
        }
    }
    displaySortileges();
}

function displaySortileges(){

    for (numPlayer = 1; numPlayer <= 2; numPlayer++) {

        let divSortilegePlayer = document.getElementById('sortilege-player'+numPlayer);

        // On récupère le sortilège applicable au joueur courant
        let jsonSortilege = eval('sortilegePlayer' + numPlayer);

        if (jsonSortilege.nbToursRestants > 0){
            let html = `<span class="sortilege-actif">Sortilège en cours : ${jsonSortilege.nom} (-${jsonSortilege.nbPointsDommages})</span><br>`;
            html += `<span class="sortilege-actif-nbtours">Actif pendant encore ${jsonSortilege.nbToursRestants} tour(s)</span>`;
            divSortilegePlayer.innerHTML = html;
        } else {
            divSortilegePlayer.innerText = 'Aucun sortilège actif';
        }
    }
}

/**
 * Passe au tour suivant, changement de joueur attaquant.
 * @param numPlayer Numéro du joueur courant.
 */
function tourSuivant(numPlayer){

    let numPlayerCourant = numPlayer;
    let numPlayerSuivant = 1+(numPlayer%2);

    let divToHide = document.getElementById('attaques-player' + numPlayerCourant);
    let divToShow = document.getElementById('attaques-player' + numPlayerSuivant);

    divToHide.classList.remove('attaquant');
    divToHide.classList.add('non-attaquant');

    divToShow.classList.remove('non-attaquant');
    divToShow.classList.add('attaquant');

    applySortileges();
}

/**
 * Siffle la fin du combat.
 */
function finDuCombat(){

    document.getElementById('winner').className = 'winner';
    document.getElementById('attaques').style.display = 'none';

    let divScore1 = document.getElementById('score-player1');
    let iScore1 = parseInt(divScore1.innerText);
    let divScore2 = document.getElementById('score-player2');
    let iScore2 = parseInt(divScore2.innerText);

    if ( iScore1 == iScore2 ) {
        document.getElementById('winner-name').innerText = 'Egalité !';
    }
    else if ( iScore1 == 0){
        document.getElementById('winner-name').innerText = 'Joueur 2 a gagné !';
    }
    else {
        document.getElementById('winner-name').innerText = 'Joueur 1 a gagné !';
    }
}