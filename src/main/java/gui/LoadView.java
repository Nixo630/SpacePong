package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LoadView {

	Pane pane;
	Pane canvas;
	ImageView retour;
	ImageView attente;
	ImageView load;
	ImageView[] currentButton;
	
	 Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	 int width = tailleMoniteur.width;
	 int height = tailleMoniteur.height;
	
	public LoadView(Pane p) {
		pane = p;
		canvas = new Pane();
		this.canvas.setId("pane");
		this.canvas.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		canvas.setPrefSize(width,height);
		pane.getChildren().add(canvas);
		canvas.setVisible(false);
	}
	
	public void affiche(ImageView[] tab) {
		currentButton = tab;
		GameStart.visible_change(tab, false);
		canvas.setVisible(true);
	}
	
	public void close() {
		canvas.setVisible(false);
		//GameStart.visible_change(currentButton, true);
		
	}
	
	public void init() {
		retour = new ImageView();
		retour.setId("return");
		Image imageRetour = new Image(getClass().getResourceAsStream("retour.png"));
        retour.setImage(imageRetour);
		retour.setLayoutX(40);
		retour.setLayoutY(40);
		retour.setFitHeight(100);
		retour.setFitWidth(100);
		
		canvas.getChildren().add(retour);
		
		attente = new ImageView();
		Image imageAttente = new Image(getClass().getResourceAsStream("en_attente.png"));
		attente.setImage(imageAttente);
		attente.setFitHeight(imageAttente.getHeight()/2);
		attente.setFitWidth(imageAttente.getWidth()/2);
		attente.setLayoutX(width/2 - attente.getFitWidth()/2);
		attente.setLayoutY(height*1/4);
		canvas.getChildren().addAll(attente);
		
		
		
		load = new ImageView();
		
		Image imageLoad = new Image(getClass().getResourceAsStream("load.gif"));
		load.setFitWidth(imageLoad.getWidth()/2);
		load.setFitHeight(imageLoad.getHeight()/2);
		load.setImage(imageLoad);
		load.setLayoutX(width/2 - load.getFitWidth()/2);
		load.setLayoutY(height/2);
		canvas.getChildren().addAll(load);
	}
	
	
	public ImageView[] getCurrentButton() {
		ImageView[] tab = {retour};
		return tab;
	}
}
