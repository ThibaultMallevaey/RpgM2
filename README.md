# RpgM2 : guide d'installation 

## Pour jouer au jeu : 
télécharger le fichier .jar : https://github.com/ThibaultMallevaey/RpgM2/blob/main/RpgM2-1.0.0.jar

Ou : 

git clone le repository entier : 
```
git clone https://github.com/ThibaultMallevaey/RpgM2.git
```

#### Pour lancer le .jar : 
Ouvrir un terminal dans le dossier contenant le .jar et lancer la commande suivante :
	```
	java -jar RpgM2-1.0.0.jar
	```

Autre option sur macos et linux : 
Au niveau du répertoire, lancer la commande suivante dans un terminal : 
`./gradlew lwjgl3:run`


## Ajout de fonctionnalités : 

### Ajout de cartes : 
Pour ajouter des cartes dans le jeu, utiliser Tiled pour créer la map. Exporter la map au format .tmx et la placer dans le dossier assets/Maps. placer également les tilesets et images dans le dossier assets/Asset. Attention : vérifier que les liens dans les tilesets et la maps renvoient correctement aux bons fichiers. 

Créer une nouvelle classe dans le dossier core/src/main/java/com/UE36/RpgM2/Screens. Nous recommandons pour la clarté 
de ranger la map dans le package interiors ou exteriors. Pour plus d'aide concernant la création de cette classe, se 
référer au com.UE36.RpgM2.Screens.Template fournie dans le dépôt 

Créer une instance de cette classe dans la méthode create() de la classe RpgGame: 
```
NouvelleMap nouvelleMap = new nouvelleMap(this);
```

Pour créer des liens entre les cartes, ajouter dans les cartes correpondantes un couche d'objet Transition. 
Ajouter au sein de cette couche les éllipses permetant les transitions
Dans le code, ajouter dans les méthodes logic() des cartes correpondante les transtions sous forme de if / else if statement :
```
this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
    if (transitions.onTransition("TransitionNouvelleCarte")){
        game.setScreen(game.nouvelleCarte);
        game.nouvelleCarte.setUpMainCharacter(game.getMainCharacter(), new Vector2(x, y), mainCharacter.getSpeed());
    }
```

### Ajout de PNJ : 
Il est possible d'ajouter des PNJ dans les cartes via la méthode addNpc(). 
ajouter dans RpgGame.create() : 
```
nouvelleMap.addNpc("name", "texture", texte, position, scale);
name = String, texture = String, texte = ArrayList<String>, position = Vector2, scale = float
```

## Recompiler le jeu : 
Suite à l'ajout de fonctionnalités, il faut recompiler le jeu. 
Ouvrir un terminal au dossier RpgM2. Lancer la commande suivante : 
	`./gradlew build`








