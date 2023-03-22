# Lord of Simplon

Le jeu Lord of Simplon est un jeu en ligne qui se joue à 2 joueurs. Chaque joueur
choisit son avatar parmi des guildes de héros. Pour le moment nous avons 3 guildes : 
Paladins, Shifumys et Rashuks.

Le principe du jeu est de réaliser des combats entre 2 joueurs à la fois.
À l'issue d'un combat, le joueur gagnant augmente son nombre de victoires et augmente 
son expérience. L'expérience d'un joueur est donc assimilée à son nombre de victoires.

# Déroulement d'un combat :

L'énergie maximum d'un joueur est de 100 points mais elle peut être supérieure en
fonction de l'expérience de chaque joueur.

Le joueur qui perd toute son énergie perd le combat.

Chaque joueur joue un seul coup par tour. Il doit choisir parmi les attaques
et la magie disponibles (fonction de la guilde à laquelle appartient l'avatar du joueur).

En fonction de sa nature, un coup peut :
- diminuer l'énergie de l'adversaire (attaques)
- augmenter les dommages de l'adversaire pour les tours suivants (sortilège)
- augmenter sa propre énergie (sans jamais excéder le maximum autorisé en fonction de l'expérience du joueur).

La magie se divise en 2 sous-catégories : 
- la magie régénérative
- les sortilèges

La magie régénérative augmente se propre énergie, tandis que les sortilèges 
augmentent les dommages portés à l'adversaire lors d'une attaque aux tours suivants.


# Règles :

1. Le même coup ne peut pas être donné deux tours de suite.
2. Un sortilège affecte l'adversaire pendant les 3 tours suivants.
3. Si un jour a lancé un sortilège au coup N-1 et qu'il lance une régénération au coup N, l'adversaire perd les points  correspondant au sortilège même s'il ne subit pas d'attaque.
4. Si un jour a lancé un sortilège au coup N-1 et qu'il lance une attaque au coup N, l'adversaire perd les points correspondant à l'attaque lancée + les points du sortilège.
5. L'expérience d'un joueur augmente son énergie maximale selon la règle suivante : `EnergieMax = 100 + (NbVictoires / 100)`
6. Au début d'un combat, les deux joueurs ont leur capital d'énergie au maximum.

# Caractéristiques des guildes :

| Type de coup     | Rashuk                | Paladin               | Shifumy                    |
|------------------|-----------------------|-----------------------|----------------------------|
| Attaque          | Sabre délicieux (-10) | Epée capricieuse (-12)| Coin de table (-6)         |
| Attaque          | Pied levé (-12)       | Poing malin (-14)     | Eau bouillante (-8)        |
| Attaque          | Verbe irrégulier (-8) | Croche-pied (-10)     | Clous rouillés (-7)        |
| Régénération     | Graines de chia (+10) | Picon-bière (+5)      | Fontaine de jouvence (+15) |
| Sortilège        | -                     | -                     | Mal de mer (-5 en +)       |


# Plan de développement :

- Step 1 : le jeu se déroule en mode automatique. Deux joueurs sont tirés au sort
puis un combat est lancé entre eux. Le nombre de victoires du gagnant est augmenté et 
l'expérience de chaque joueur est sauvegardée dans un fichier.

- Step 2 : développement des règles de combat

- Step 3 : sauvegarde des scores en base de données

- Step 4 : backend-ification

- Step 5 : maquette frontend HTML

- Step 6 : put all together !