package gui;


import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class TouchView {
	
	//Ce boolean sert à savoir si on est entrain d'affcher les informations
	boolean estAffiche = false;
	
	Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
    int longueur = tailleMoniteur.width;
    int hauteur = tailleMoniteur.height;
    
    Pane startRoot;
    Pane root;
    Button[] currentButton;
    Circle gauche;
    Circle droit;
    Pane canvas;
    
    
    //Middle bar pour aider à placer les objets dans le pane
    Rectangle milieu;
    
    //Mise en place des labels pour les informations
    
    
    ImageView joueur1;
    ImageView joueur2;
    ImageView quitter;
    ImageView reprendre;
    ImageView replay;
    ImageView menu;
    ImageView pause;
    
    
    ImageView touche_r;
    ImageView touche_r1;
    ImageView touche_p;
    ImageView touche_p1;
    ImageView touche_q;
    
    
    
	public TouchView(Pane startRoot,Pane root,Circle g,Circle d) {
		gauche = g;
		droit = d;
		this.startRoot = startRoot;
		this.root = root;
		canvas = new Pane();
	    canvas.setStyle("-fx-background-color: pink;");
	    canvas.setPrefSize(longueur*3/4,hauteur*3/4);
	    
	    canvas.setLayoutX(longueur/2-canvas.getPrefWidth()/2);
	    canvas.setLayoutY(hauteur/2-canvas.getPrefHeight()/2);
	    
	    
	    milieu= new Rectangle();
	    milieu.setWidth(1);
	    milieu.setHeight(canvas.getPrefHeight());
	    milieu.setFill(Color.BLACK);
	    milieu.setX(canvas.getPrefWidth()/2-milieu.getWidth()/2);
	    milieu.setY(0);
	    
	    canvas.getChildren().addAll(milieu);
	}
	
	public void copie(Button[] t) {
		currentButton = new Button[t.length];
		for (int i = 0;i<t.length;i++) {
			currentButton[i] = t[i];
		}
		
	}
	
	public void init(){
		
		reprendre = new ImageView();
		reprendre.setFitWidth(1424/5);
		reprendre.setFitHeight(216/5);
		Image imageReprendre = new Image(getClass().getResourceAsStream("reprendre.png"));
		reprendre.setImage(imageReprendre);
		reprendre.setLayoutX(10);
		reprendre.setLayoutY(150);
		canvas.getChildren().addAll(reprendre);
		
		touche_r = new ImageView();
		Image imageR = new Image(getClass().getResourceAsStream("Image_Info/touche-r.png"));
        touche_r.setImage(imageR);
		touche_r.setFitHeight(512/9);
		touche_r.setFitWidth(512/9);
		touche_r.setLayoutX(milieu.getX()/2 + touche_r.getFitWidth());
		touche_r.setLayoutY(reprendre.getLayoutY() + reprendre.getFitHeight()/2 - touche_r.getFitHeight()/2);
		canvas.getChildren().addAll(touche_r);
		
		
		quitter = new ImageView();
		quitter.setFitWidth(1424/5);
		quitter.setFitHeight(216/5);
		Image imageQuitter = new Image(getClass().getResourceAsStream("quitter.png"));
		quitter.setImage(imageQuitter);
		quitter.setLayoutX(10);
		quitter.setLayoutY(250);
		canvas.getChildren().addAll(quitter);
		
		
		touche_q = new ImageView();
		Image imageQ = new Image(getClass().getResourceAsStream("Image_Info/touche-q.png"));
        touche_q.setImage(imageQ);
		touche_q.setFitHeight(512/9);
		touche_q.setFitWidth(512/9);
		touche_q.setLayoutX(milieu.getX()/2 + touche_q.getFitWidth());
		touche_q.setLayoutY(quitter.getLayoutY() + quitter.getFitHeight()/2 - touche_q.getFitHeight()/2);
		canvas.getChildren().addAll(touche_q);
		
		
		replay = new ImageView();
		replay.setFitWidth(1424/5);
		replay.setFitHeight(216/5);
		Image imageRejouer = new Image(getClass().getResourceAsStream("rejouer.png"));
		replay.setImage(imageRejouer);
		replay.setLayoutX(10);
		replay.setLayoutY(350);
		canvas.getChildren().addAll(replay);
		
		touche_r1 = new ImageView();
        touche_r1.setImage(imageR);
		touche_r1.setFitHeight(512/9);
		touche_r1.setFitWidth(512/9);
		touche_r1.setLayoutX(milieu.getX()/2 + touche_q.getFitWidth());
		touche_r1.setLayoutY(replay.getLayoutY() + replay.getFitHeight()/2 - touche_r.getFitHeight()/2);
		canvas.getChildren().addAll(touche_r1);
		
		
		
		menu = new ImageView();
		menu.setFitWidth(1424/5);
		menu.setFitHeight(216/5);
		Image imageMenu = new Image(getClass().getResourceAsStream("menu.png"));
		menu.setImage(imageMenu);
		menu.setLayoutX(10);
		menu.setLayoutY(450);
		canvas.getChildren().addAll(menu);
		
		touche_p = new ImageView();
		Image imageP = new Image(getClass().getResourceAsStream("Image_Info/touche-p.png"));
        touche_p.setImage(imageP);
		touche_p.setFitHeight(512/9);
		touche_p.setFitWidth(512/9);
		touche_p.setLayoutX(milieu.getX()/2 + touche_p.getFitWidth());
		touche_p.setLayoutY(menu.getLayoutY() + menu.getFitHeight()/2 - touche_p.getFitHeight()/2);
		canvas.getChildren().addAll(touche_p);
		
		pause = new ImageView();
		pause.setFitWidth(1424/5);
		pause.setFitHeight(216/5);
		Image imagePause = new Image(getClass().getResourceAsStream("affichage_pause.png"));
		pause.setImage(imagePause);
		pause.setLayoutX(10);
		pause.setLayoutY(550);
		canvas.getChildren().addAll(pause);
		
		touche_p1 = new ImageView();
		touche_p1.setImage(imageP);
		touche_p1.setFitHeight(512/9);
		touche_p1.setFitWidth(512/9);
		touche_p1.setLayoutX(milieu.getX()/2 + touche_r.getFitWidth());
		touche_p1.setLayoutY(pause.getLayoutY() + pause.getFitHeight()/2 - touche_p1.getFitHeight()/2);
		canvas.getChildren().addAll(touche_p1);
		
		initJoueurTouche();
		
		startRoot.getChildren().addAll(canvas);
		canvas.setVisible(false);
	}
	
	
	public void initJoueurTouche() {
		joueur1 = new ImageView();
		joueur1.setFitHeight(216/3);
		joueur1.setFitWidth(1424/3);
		Image imJoueur1 = new Image(getClass().getResourceAsStream("Image_Info/joueur_1.png"));
		joueur1.setImage(imJoueur1);
		joueur1.setLayoutX(milieu.getX() + milieu.getX()/2-joueur1.getFitWidth()/2);
		joueur1.setLayoutY(25);
		
		canvas.getChildren().addAll(joueur1);
		
		
		joueur2 = new ImageView();
		joueur2.setFitHeight(216/3);
		joueur2.setFitWidth(1424/3);
		Image imJoueur2 = new Image(getClass().getResourceAsStream("Image_Info/joueur_2.png"));
		joueur2.setImage(imJoueur2);
		joueur2.setLayoutX(milieu.getX() + milieu.getX()/2-joueur2.getFitWidth()/2);
		joueur2.setLayoutY(milieu.getHeight()/2 + 25);
		
		canvas.getChildren().addAll(joueur2);
		
		
	}
	
	
	public void affiche(Button[] btn) {
		estAffiche = true;
		droit.setVisible(false);
		gauche.setVisible(false);
		copie(btn);
		GameStart.visible_change(currentButton, false);
		canvas.setVisible(true);	
	}
	
	public Button[] close() {
		estAffiche = false;
		droit.setVisible(true);
		gauche.setVisible(true);
		GameStart.visible_change(currentButton, true);
		canvas.setVisible(false);	
		
		return currentButton;
	}
	
	public boolean estAffiche() {
		return estAffiche;
	}
	
	
}