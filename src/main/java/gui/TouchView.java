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
    ImageView monter;
    ImageView descendre;
    ImageView pouvoir;
    ImageView monter1;
    ImageView descendre1;
    ImageView pouvoir1;
    
    
    ImageView touche_r;
    ImageView touche_r1;
    ImageView touche_p;
    ImageView touche_p1;
    ImageView touche_q;
    
    ImageView touche_z;
    ImageView touche_x;
    ImageView touche_d;
    
    ImageView touche_Up;
    ImageView touche_Down;
    ImageView touche_k;
    
    
    
	public TouchView(Pane startRoot,Circle g,Circle d) {
		gauche = g;
		droit = d;
		this.startRoot = startRoot;
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
		joueur1.setLayoutY(0);
		
		canvas.getChildren().addAll(joueur1);
		
		
		monter = new ImageView();
		monter.setFitHeight(216/4);
		monter.setFitWidth(1424/4);
		Image imageMonter = new Image(getClass().getResourceAsStream("Image_Info/monter.png"));
		monter.setImage(imageMonter);
		monter.setLayoutX(joueur1.getX() - (joueur1.getX()-milieu.getX()));
		monter.setLayoutY(milieu.getHeight()/2*1/4);
		canvas.getChildren().addAll(monter);
	
		
		touche_z = new ImageView();
		Image imageZ = new Image(getClass().getResourceAsStream("Image_Info/touche-z.png"));
        touche_z.setImage(imageZ);
		touche_z.setFitHeight(512/9);
		touche_z.setFitWidth(512/9);
		touche_z.setLayoutX(milieu.getX()+ milieu.getX()*2/3 + touche_z.getFitWidth());
		touche_z.setLayoutY(monter.getLayoutY() + monter.getFitHeight()/2 - touche_z.getFitHeight()/2);
		canvas.getChildren().addAll(touche_z);
		
		
		
		descendre = new ImageView();
		descendre.setFitHeight(216/4);
		descendre.setFitWidth(1424/4);
		Image imageDescendre = new Image(getClass().getResourceAsStream("Image_Info/descendre.png"));
		descendre.setImage(imageDescendre);
		descendre.setLayoutX(joueur1.getX() - (joueur1.getX()-milieu.getX()));
		descendre.setLayoutY(milieu.getHeight()/2*2/4);
		canvas.getChildren().addAll(descendre);
		
		touche_x = new ImageView();
		Image imageX = new Image(getClass().getResourceAsStream("Image_Info/touche-x.png"));
        touche_x.setImage(imageX);
		touche_x.setFitHeight(512/9);
		touche_x.setFitWidth(512/9);
		touche_x.setLayoutX(milieu.getX()+ milieu.getX()*2/3 + touche_z.getFitWidth());
		touche_x.setLayoutY(descendre.getLayoutY() + descendre.getFitHeight()/2 - touche_x.getFitHeight()/2);
		canvas.getChildren().addAll(touche_x);
		
		
		pouvoir = new ImageView();
		pouvoir.setFitHeight(216/4);
		pouvoir.setFitWidth(1424/4);
		Image imagePouvoir = new Image(getClass().getResourceAsStream("Image_Info/pouvoir_touche.png"));
		pouvoir.setImage(imagePouvoir);
		pouvoir.setLayoutX(joueur1.getX() - (joueur1.getX()-milieu.getX()));
		pouvoir.setLayoutY(milieu.getHeight()/2*3/4);
		canvas.getChildren().addAll(pouvoir);
		
		touche_d = new ImageView();
		touche_d.setFitHeight(512/9);
		touche_d.setFitWidth(512/9);
		Image imageD = new Image(getClass().getResourceAsStream("Image_Info/touche-d.png"));
		touche_d.setImage(imageD);
		touche_d.setLayoutX(milieu.getX()+ milieu.getX()*2/3 + touche_z.getFitWidth());
		touche_d.setLayoutY(pouvoir.getLayoutY() + pouvoir.getFitHeight()/2 - touche_d.getFitHeight()/2);
		canvas.getChildren().addAll(touche_d);
		
		
		
		
		joueur2 = new ImageView();
		joueur2.setFitHeight(216/3);
		joueur2.setFitWidth(1424/3);
		Image imJoueur2 = new Image(getClass().getResourceAsStream("Image_Info/joueur_2.png"));
		joueur2.setImage(imJoueur2);
		joueur2.setLayoutX(joueur1.getLayoutX());
		joueur2.setLayoutY(milieu.getHeight()/2);
		canvas.getChildren().addAll(joueur2);
		
		monter1 = new ImageView();
		monter1.setFitHeight(216/4);
		monter1.setFitWidth(1424/4);
		monter1.setImage(imageMonter);
		monter1.setLayoutX(joueur1.getX() - (joueur1.getX()-milieu.getX()));
		monter1.setLayoutY(milieu.getHeight()/2+milieu.getHeight()/2*1/4);
		canvas.getChildren().addAll(monter1);
	
		
		touche_Up = new ImageView();
		Image imageUp = new Image(getClass().getResourceAsStream("Image_Info/touche-up.png"));
        touche_Up.setImage(imageUp);
		touche_Up.setFitHeight(512/9);
		touche_Up.setFitWidth(512/9);
		touche_Up.setLayoutX(milieu.getX()+ milieu.getX()*2/3 + touche_Up.getFitWidth());
		touche_Up.setLayoutY(monter1.getLayoutY() + monter1.getFitHeight()/2 - touche_Up.getFitHeight()/2);
		canvas.getChildren().addAll(touche_Up);
		
		
		
		descendre1 = new ImageView();
		descendre1.setFitHeight(216/4);
		descendre1.setFitWidth(1424/4);
		descendre1.setImage(imageDescendre);
		descendre1.setLayoutX(joueur1.getX() - (joueur1.getX()-milieu.getX()));
		descendre1.setLayoutY(milieu.getHeight()/2+milieu.getHeight()/2*2/4);
		canvas.getChildren().addAll(descendre1);
		
		touche_Down = new ImageView();
		Image imageDown = new Image(getClass().getResourceAsStream("Image_Info/touche-down.png"));
        touche_Down.setImage(imageDown);
		touche_Down.setFitHeight(512/9);
		touche_Down.setFitWidth(512/9);
		touche_Down.setLayoutX(milieu.getX()+ milieu.getX()*2/3 + touche_z.getFitWidth());
		touche_Down.setLayoutY(descendre1.getLayoutY() + descendre1.getFitHeight()/2 - touche_Down.getFitHeight()/2);
		canvas.getChildren().addAll(touche_Down);
		
		
		pouvoir1 = new ImageView();
		pouvoir1.setFitHeight(216/4);
		pouvoir1.setFitWidth(1424/4);
		pouvoir1.setImage(imagePouvoir);
		pouvoir1.setLayoutX(joueur1.getX() - (joueur1.getX()-milieu.getX()));
		pouvoir1.setLayoutY(milieu.getHeight()/2+milieu.getHeight()/2*3/4);
		canvas.getChildren().addAll(pouvoir1);
		
		touche_k = new ImageView();
		touche_k.setFitHeight(512/9);
		touche_k.setFitWidth(512/9);
		Image imageK = new Image(getClass().getResourceAsStream("Image_Info/touche-k.png"));
		touche_k.setImage(imageK);
		touche_k.setLayoutX(milieu.getX()+ milieu.getX()*2/3 + touche_k.getFitWidth());
		touche_k.setLayoutY(pouvoir1.getLayoutY() + pouvoir1.getFitHeight()/2 - touche_k.getFitHeight()/2);
		canvas.getChildren().addAll(touche_k);
		
		
		
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