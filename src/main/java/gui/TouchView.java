package gui;


import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.control.Button;
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
    
    
    Button joueur1;
    Button joueur2;
    
    
	public TouchView(Pane startRoot,Pane root,Circle g,Circle d) {
		gauche = g;
		droit = d;
		this.startRoot = startRoot;
		this.root = root;
		canvas = new Pane();
	    canvas.setStyle("-fx-background-color: blue;");
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
		joueur1 = new Button();
		joueur1.setId("joueur1");
		joueur1.getStylesheets().addAll(this.getClass().getResource("style_info.css").toExternalForm());
		joueur1.setPrefSize(1424/3,216/3);
		
		joueur1.setLayoutX(milieu.getX() - joueur1.getPrefWidth()-100);
		joueur1.setLayoutY(50);
		
		canvas.getChildren().addAll(joueur1);
		
		
		joueur2 = new Button();
		joueur2.setId("joueur2");
		joueur2.getStylesheets().addAll(this.getClass().getResource("style_info.css").toExternalForm());
		joueur2.setPrefSize(1424/3,216/3);
		
		joueur2.setLayoutX(milieu.getX()+100);
		joueur2.setLayoutY(50);
		
		canvas.getChildren().addAll(joueur2);
		
		startRoot.getChildren().addAll(canvas);
		canvas.setVisible(false);
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