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
	
	//Boolean pour savoir si la barre de chargement à deja été charge
	private boolean charge= false;
	
	private final Button quit,play,setting_button,multiplay,title;
	private int height;
	private int width;
	private Pane startRoot;
	private Pane gameRoot;
	
	public GameStart (Pane startRoot,Pane root,Scene courtScene, GameView gw) {
		
		this.startRoot = startRoot;
		this.gameRoot = root;
		
		gameRoot.setId("choix_galaxie");
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
		width  = (int)dimension.getWidth();
		
		
		//Le titre est un bouton sans commande dessus
		title = new Button();
		title.setId("title");
		title.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		title.setPrefSize(1082/2,332/2);
		title.setLayoutX(width/2 - title.getPrefWidth()/2);
		title.setLayoutY(50);
		
		
		
		//Mise en place du boutton Play pour jouer au jeu en solo
		play = new Button();
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
		
		multiplay = new Button();
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
		quit = new Button();
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
		
		setting_button = new Button();
		setting_button.setCursor(Cursor.HAND);
		setting_button.setId("settings_button");
		setting_button.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		setting_button.setPrefSize(360/2,360/2);
		
		setting_button.setLayoutX(width-setting_button.getPrefWidth());
		setting_button.setLayoutY(0);
		
		setting_button.setOnAction(value ->  {
			parametre();
	    });
		
		
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
			Button[] tab = {quit,play,setting_button,multiplay};
			
			if (charge == false) {
				charge = true;
				Timer chrono = new Timer();
				chrono.schedule(new TimerTask() {
	
					int time = 100;
					@Override
					public void run() {
						
						avancer(progressBar);
						
						
						if (time ==0) {
							charge=true;
							progressBar.setVisible(false);
							visible_change(tab,true);
				        	chrono.cancel();
				        	
				        	
						}
						time--;
					}
					
				}, 100,25);
			}
			else {
				visible_change(tab,true);
			}
				
		  });
		
		startRoot.getChildren().addAll(title,start_button);
	}
	
	//Cette fonction fait apparaitre les bouton de jeu et pour quitter
	public static void visible_change(Button [] t,boolean b) {
		for (int i = 0;i<t.length;i++) {
			t[i].setVisible(b);
		}
	}
	
	void setCharge(boolean t) {
		charge = t;
	}
	//Cette fonction fait avancer la barre de chargmement
	public static void avancer(ProgressBar pb) {
		pb.setProgress(0.01+pb.getProgress());
		
	}
	
	
	public void parametre() {
		Button[] tab_init = {quit,play,setting_button,multiplay,title};
		visible_change(tab_init,false);
		
		
		//Mise en place des settings
		
		Button title_s = new Button();
		title_s.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		title_s.setId("title");
		
		title_s.setPrefSize(2733/7,425/7);
		title_s.setLayoutX(width/2 - title_s.getPrefWidth()/2);
		title_s.setLayoutY(50);
		
		
		//Mise en place du choix de l'arriere plan
		Button title_choix_bg = new Button();
		title_choix_bg.setId("title_choix_bg");
		title_choix_bg.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_choix_bg.setPrefSize(3366/8,343/8);
		title_choix_bg.setLayoutX(20);
		title_choix_bg.setLayoutY(200);
		
		
		Button choix_galaxie = new Button();
		choix_galaxie.setId("choix_galaxie");
		choix_galaxie.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_galaxie.setPrefSize(1600/8,1200/10);
		choix_galaxie.setLayoutX(20+title_choix_bg.getPrefWidth()+25);
		choix_galaxie.setLayoutY(200);
		
		choix_galaxie.setOnAction(value ->  {
			gameRoot.setId("choix_galaxie");
	    });
		
		Button choix_trou_noir = new Button();
		choix_trou_noir.setId("choix_trou_noir");
		choix_trou_noir.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_trou_noir.setPrefSize(1600/8,900/7.5);
		choix_trou_noir.setLayoutX(20 + choix_galaxie.getLayoutX()+choix_galaxie.getPrefWidth()+25);
		choix_trou_noir.setLayoutY(200);
		
		choix_trou_noir.setOnAction(value ->  {
			gameRoot.setId("choix_trou_noir");
	    });
		
		
		Button choix_earth = new Button();
		choix_earth.setId("choix_earth");
		choix_earth.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_earth.setPrefSize(1920/9.6,1080/9);
		choix_earth.setLayoutX(20 + choix_trou_noir.getLayoutX()+choix_trou_noir.getPrefWidth()+25);
		choix_earth.setLayoutY(200);
		
		choix_earth.setOnAction(value ->  {
			gameRoot.setId("choix_earth");
	    });
		
		
		Button choix_earth2 = new Button();
		choix_earth2.setId("choix_earth2");
		choix_earth2.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_earth2.setPrefSize(1920/9.6,1080/9);
		choix_earth2.setLayoutX(20 + choix_earth.getLayoutX()+choix_earth.getPrefWidth()+25);
		choix_earth2.setLayoutY(200);
		
		choix_earth2.setOnAction(value ->  {
			gameRoot.setId("choix_earth2");
	    });
		
		Button finish_button = new Button();
		finish_button.setId("finish_button");
		finish_button.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		finish_button.setPrefSize(1795/8,332/8);
		finish_button.setLayoutX(width/2 - finish_button.getPrefWidth()/2);
		finish_button.setLayoutY(height-100);
		
		Button[] tab_setting = {title_s,title_choix_bg,choix_galaxie,choix_trou_noir,choix_earth,choix_earth2,finish_button};
		
		finish_button.setOnAction(value ->  {
			
			visible_change(tab_setting,false);
			visible_change(tab_init,true);
			
			
	    });
		
		startRoot.getChildren().addAll(title_s,title_choix_bg,choix_galaxie,choix_trou_noir,choix_earth,choix_earth2,finish_button);
		
	}
}

