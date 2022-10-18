package gui;

import java.awt.Dimension;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;


public class GameLost {

	public GameLost (Pane lostRoot,Scene courtScene, GameView gw,Scene gameStart) {
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		//Mise en place de l'affichage de la fin de partie
	
		//Ici c'est la bouton qui affiche que la partie est termine
		
		Button title_end = new Button();
		title_end.setId("title_end");
		title_end.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		title_end.setPrefSize(2111/3,477/3);
		title_end.setLayoutX(width/2 - title_end.getPrefWidth()/2);
		title_end.setLayoutY(50);
		
		
		//Bouton pour rejouer
		Button replay = new Button();
		replay.setCursor(Cursor.HAND);
		replay.setId("replay_button");
		replay.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		replay.setPrefSize(2489/4.5,380/4.5);
		
		replay.setLayoutX(width/2 - replay.getPrefWidth());
		replay.setLayoutY(625);
		
		replay.setOnAction(value ->  {
			App.getStage().setScene(courtScene);
			App.getStage().setFullScreen(true);
			gw.startAnimation();
	    });
		
		
		//Travaux en cours pour l'affichage des score à la fin de la partie .......
		
		
		
		Text score1 = new Text(String.valueOf(gw.getScoreA()));
		lostRoot.getChildren().addAll(score1);
		
		
		
		 
		
		//Boutton pour quitter le jeu
		Button quit = new Button();
		quit.setCancelButton(true);
		quit.setId("quit_button");
		quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		quit.setPrefSize(1238/4,461/4);
		quit.setLayoutX(width/2 + quit.getPrefWidth()/1.5);
		quit.setLayoutY(610);
		
		quit.setCursor(Cursor.HAND);
		quit.setOnAction(value ->  {
	           App.getStage().setScene(gameStart);
	           App.getStage().setFullScreen(true);
	    });
		
		lostRoot.getChildren().addAll(title_end, replay, quit);
	}
	
}
