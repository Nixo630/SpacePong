package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Curseur {

	Circle curseur_gauche;
	Circle curseur_droit;
	int indice;
	ImageView[] currentButton;
	Pane root;
	
	public Curseur(Pane pane,ImageView[] tab) {
		root = pane;
		//Mise en place du curseur droit
		
		currentButton = tab;
		
		ImageView play = tab[0];
		
		curseur_droit= new Circle();
		curseur_droit.setRadius(25);
	    curseur_droit.setCenterX(play.getLayoutX()+play.getFitWidth()+25);
	    curseur_droit.setCenterY(play.getLayoutY()+play.getFitHeight()/2);
	    Image j = new Image(getClass().getResourceAsStream("curseur_droit.png"));
	    curseur_droit.setFill(new ImagePattern(j));
	    
	    //Mise en place du curseur gauche;
	    
	    curseur_gauche= new Circle();
		curseur_gauche.setRadius(25);
	    curseur_gauche.setCenterX(play.getLayoutX()-25);
	    curseur_gauche.setCenterY(play.getLayoutY()+play.getFitHeight()/2);
	    Image i = new Image(getClass().getResourceAsStream("curseur_gauche.png"));
	    curseur_gauche.setFill(new ImagePattern(i));
	    
	    root.getChildren().addAll(curseur_droit,curseur_gauche);
	}
	
	public void afficher() {
		curseur_gauche.setVisible(true);
	    curseur_droit.setVisible(true);
	}
	
	public void setPane(Pane p) {
		root = p;
	}
	
	public void setCurrentButton(ImageView[] tab) {
		if (tab.length == 1) {
			indice = 0;
		}
		else{
			indice=1;
		}
		currentButton = tab;
		if(tab.length!=0) {
			bouger_curseur(currentButton[indice],root);
		}
	}
	
	public void bouger_curseur(ImageView btn,Pane p) {
		//On supprime les curseur
		
		p.getChildren().removeAll(curseur_droit,curseur_gauche);
		
		//Changement de la position des curseurs en fonction du btn mis en paramètre

	    curseur_droit.setCenterX(btn.getLayoutX()+btn.getFitWidth()+25);
	    curseur_droit.setCenterY(btn.getLayoutY()+btn.getFitHeight()/2);
	    
	    //Mise en place du curseur gauche;
	    
		curseur_gauche.setCenterX(btn.getLayoutX()-25);
	    curseur_gauche.setCenterY(btn.getLayoutY()+btn.getFitHeight()/2);
	    
	    //On réaffiche les curseurs
	    p.getChildren().addAll(curseur_droit,curseur_gauche);
	}
	
	//Fonction pour incrémenter ou décrémenter l'indice des curseurs en fonction de la taille du tableau de bouton
	
	public void IncrementeIndice(ImageView[] imageViews) {
		if(indice==imageViews.length-1) {
			indice = 0;
		}
		else {
			indice+=1;
		}
	}
	
	public void DecrementeIndice(ImageView[] btn) {
		if(indice==0) {
			indice = btn.length-1;
		}
		else {
			indice-=1;
		}
	}
	
	//Fonction pour récuppérer l'indice du curseur
	
	public int getCurseurIndice() {
		return indice;
	}
	
	//Fonction pour recuperer le tableau des boutons courant
	
	public ImageView[] getCurrentButton() {
		return currentButton;
	}
	
	public void setVisible(boolean b) {
		curseur_gauche.setVisible(b);
	    curseur_droit.setVisible(b);
	}
	
	public void unclick() {
		currentButton = new ImageView[0];
	}
}
