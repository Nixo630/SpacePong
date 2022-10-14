package gui;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ProgressBar;


public class GameStart {
	
	public GameStart (Pane startRoot,Scene courtScene, GameView gw) {
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		
		//Le titre est un bouton sans commande dessus
		Button title = new Button();
		title.setId("title");
		title.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		title.setPrefSize(1082/2,332/2);
		title.setLayoutX(width/2 - title.getPrefWidth()/2);
		title.setLayoutY(50);
		
		
		
		//Mise en place du boutton Play pour jouer au jeu en solo
		Button play = new Button();
		play.setCursor(Cursor.HAND);
		
		play.setId("solo_play_button");
		play.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		play.setPrefSize(1920/4.5,463/4.5);
		play.setLayoutX(width/2 - play.getPrefWidth()/2);
		play.setLayoutY(280);
		
		play.setOnAction(value ->  {
			App.getStage().setScene(courtScene);
			App.getStage().setFullScreen(true);
			gw.startAnimation();
	    });
		
		//Mise en place du bouton pour jouer à deux 
		
		Button multiplay = new Button();
		multiplay.setCursor(Cursor.HAND);
		
		multiplay.setId("multiplay_play_button");
		multiplay.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		multiplay.setPrefSize(3376/3.5,574/3.5);
		multiplay.setLayoutX(width/2 - multiplay.getPrefWidth()/2);
		multiplay.setLayoutY(400);
		
		multiplay.setOnAction(value ->  {
			App.getStage().setScene(courtScene);
			App.getStage().setFullScreen(true);
			gw.startAnimation();
	    });
		
		
		//Boutton pour quitter le jeu
		Button quit = new Button();
		quit.setCancelButton(true);
		quit.setId("quit_button");
		quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		quit.setPrefSize(1238/4,461/4);
		quit.setLayoutX(width/2 - quit.getPrefWidth()/2);
		quit.setLayoutY(650);
		
		quit.setCursor(Cursor.HAND);
		quit.setOnAction(value ->  {
	           System.exit(0);
	    });
		
		startRoot.getChildren().addAll(play, quit,multiplay);
		
		play.setVisible(false);
		multiplay.setVisible(false);
		quit.setVisible(false);
		
		//Mise en place du boutton setting
		
		Button setting_button = new Button();
		setting_button.setCursor(Cursor.HAND);
		setting_button.setId("settings_button");
		setting_button.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		setting_button.setPrefSize(360/2,360/2);
		
		setting_button.setLayoutX(width-setting_button.getPrefWidth());
		setting_button.setLayoutY(0);
		
		
		startRoot.getChildren().addAll(setting_button);
		setting_button.setVisible(false);
		
		
		//Mise en place de la barre de progression
		
		ProgressBar progressBar = new ProgressBar(0);
		
		progressBar.setPrefSize(width/2.5,75);
		
		progressBar.setLayoutX(width/2 - progressBar.getPrefWidth()/2);
		progressBar.setLayoutY(350);
		
		
		
		
		
		//Mise en place du boutton start
		
		Button start_button = new Button();
		start_button.setCursor(Cursor.HAND);
		start_button.setId("start_button");
		start_button.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		start_button.setPrefSize(2965/4.5,491/4.5);
		
		start_button.setLayoutX(width/2 - start_button.getPrefWidth()/2);
		start_button.setLayoutY(height/2 - start_button.getPrefHeight()/2);
		
		
		//Lorsqu'on clique sur le bouton, on active une fonction qui fait augmenter la jauge de chargement
		
		start_button.setOnAction(value ->  {
			startRoot.getChildren().removeAll(start_button);
			startRoot.getChildren().addAll(progressBar);
			
			Timer chrono = new Timer();
			chrono.schedule(new TimerTask() {

				int time = 100;
				@Override
				public void run() {
					System.out.println(time);
					avancer(progressBar);
					
					
					if (time ==0) {
						progressBar.setVisible(false);
						debuter(quit,play,setting_button,multiplay);
			        	chrono.cancel();
			        	
			        	
					}
					time--;
				}
				
			}, 100,25);
			
	    });
		
		startRoot.getChildren().addAll(title,start_button);
	}
	
	//Cette fonction fait apparaitre les bouton de jeu et pour quitter
	public static void debuter(Button q,Button p,Button d,Button m) {
		p.setVisible(true);
		q.setVisible(true);
		d.setVisible(true);
		m.setVisible(true);
	}
	
	//Cette fonction fait avancer la barre de chargmement
	public static void avancer(ProgressBar pb) {
		pb.setProgress(0.01+pb.getProgress());
		
	}
	
}

