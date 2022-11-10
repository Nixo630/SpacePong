package gui;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ProgressBar;
import model.Court;


public class GameStart {
	
	//Boolean pour savoir si la barre de chargement à deja été charge
	private boolean charge= false;
	
	private final Button quit,play,setting_button,multiplay,title;
	private int height;
	private int width;
	private Pane startRoot;
	private Pane gameRoot;
	
	private GameView gw;
	private Court court;
	private Scene courtScene;
	
	
	public GameStart (Pane startRoot,Pane root,Scene courtScene, GameView gw,Court court) {
		
		this.startRoot = startRoot;
		this.gameRoot = root;
		this.gw = gw;
		this.court = court;
		this.courtScene = courtScene;
		
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
			chose_difficulty();
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
			court.setIsBot(false);
			court.setPartiEnCours(true);
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
	
					int time = 25;
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
					
				}, 100,15);
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
		pb.setProgress(0.1+pb.getProgress());
		
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
		choix_galaxie.setCursor(Cursor.HAND);
		choix_galaxie.setPrefSize(1600/8,1200/10);
		choix_galaxie.setLayoutX(20+title_choix_bg.getPrefWidth()+25);
		choix_galaxie.setLayoutY(200);
		
		choix_galaxie.setOnAction(value ->  {
			gameRoot.setId("choix_galaxie");
	    });
		
		Button choix_trou_noir = new Button();
		choix_trou_noir.setId("choix_trou_noir");
		choix_trou_noir.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_trou_noir.setCursor(Cursor.HAND);
		choix_trou_noir.setPrefSize(1600/8,1200/10);
		choix_trou_noir.setLayoutX(20 + choix_galaxie.getLayoutX()+choix_galaxie.getPrefWidth()+25);
		choix_trou_noir.setLayoutY(200);
		
		choix_trou_noir.setOnAction(value ->  {
			gameRoot.setId("choix_trou_noir");
	    });
		
		
		Button choix_earth = new Button();
		choix_earth.setId("choix_earth");
		choix_earth.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_earth.setCursor(Cursor.HAND);
		choix_earth.setPrefSize(1600/8,1200/10);
		choix_earth.setLayoutX(20 + choix_trou_noir.getLayoutX()+choix_trou_noir.getPrefWidth()+25);
		choix_earth.setLayoutY(200);
		
		choix_earth.setOnAction(value ->  {
			gameRoot.setId("choix_earth");
	    });
		
		
		Button choix_earth2 = new Button();
		choix_earth2.setId("choix_earth2");
		choix_earth2.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_earth2.setCursor(Cursor.HAND);
		choix_earth2.setPrefSize(1600/8,1200/10);
		choix_earth2.setLayoutX(20 + choix_earth.getLayoutX()+choix_earth.getPrefWidth()+25);
		choix_earth2.setLayoutY(200);
		
		choix_earth2.setOnAction(value ->  {
			gameRoot.setId("choix_earth2");
	    });
		
		Button finish_button = new Button();
		finish_button.setId("finish_button");
		finish_button.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		finish_button.setCursor(Cursor.HAND);
		finish_button.setPrefSize(1795/8,332/8);
		finish_button.setLayoutX(width/2 - finish_button.getPrefWidth()/2);
		finish_button.setLayoutY(height-100);
		
		
		startRoot.getChildren().addAll(title_s,title_choix_bg,choix_galaxie,choix_trou_noir,choix_earth,choix_earth2,finish_button);
		
		
		
		// User can choose between with middle bar or without middle bar 
		
		Button title_middle_bar = new Button();
		title_middle_bar.setId("title_middle_bar");
		title_middle_bar.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_middle_bar.setPrefSize(2872/8,311/8);
		title_middle_bar.setLayoutX(20);
		title_middle_bar.setLayoutY(450);
		
		Button middle_bar_yes = new Button();
		middle_bar_yes.setId("middle_bar_yes");
		middle_bar_yes.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		middle_bar_yes.setCursor(Cursor.HAND);
		middle_bar_yes.setPrefSize(497/4,464/4);
		middle_bar_yes.setLayoutX(20+title_middle_bar.getPrefWidth()+25);
		middle_bar_yes.setLayoutY(400);
		
		middle_bar_yes.setOnAction(value ->  {
			gw.Visible_middle_bar(true);
	    });
		
		Button middle_bar_no = new Button();
		middle_bar_no.setId("middle_bar_no");
		middle_bar_no.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		middle_bar_no.setCursor(Cursor.HAND);
		middle_bar_no.setPrefSize(497/4,464/4);
		middle_bar_no.setLayoutX(middle_bar_yes.getLayoutX()+middle_bar_yes.getPrefWidth()+25);
		middle_bar_no.setLayoutY(400);
		
		middle_bar_no.setOnAction(value ->  {
			gw.Visible_middle_bar(false);
	    });
		
		
		
		//Mise en place du choix du skin de la balle pour le joueur
		
		Button title_ball_skin = new Button();
		title_ball_skin.setId("title_ball_skin");
		title_ball_skin.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		title_ball_skin.setPrefSize(3213/9,375/9);
		title_ball_skin.setLayoutX(20);
		title_ball_skin.setLayoutY(550);
		
		
		Button choix_ball_sun = new Button();
		choix_ball_sun.setId("choix_ball_sun");
		choix_ball_sun.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_sun.setCursor(Cursor.HAND);
		
		choix_ball_sun.setPrefSize(202/4,202/4);
		choix_ball_sun.setLayoutX(title_ball_skin.getLayoutX()+title_ball_skin.getPrefWidth()+25);
		choix_ball_sun.setLayoutY(550);
		choix_ball_sun.setOnAction(value ->  {
			gw.setBallSkin("sun_ball.png");
	    });
		
		Button choix_ball_green = new Button();
		choix_ball_green.setId("choix_ball_green");
		choix_ball_green.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_green.setCursor(Cursor.HAND);
		
		choix_ball_green.setPrefSize(196/4,217/4);
		choix_ball_green.setLayoutX(choix_ball_sun.getLayoutX()+choix_ball_sun.getPrefWidth()+25);
		choix_ball_green.setLayoutY(550);
		choix_ball_green.setOnAction(value ->  {
			gw.setBallSkin("green_ball.png");
	    });
		
		Button choix_ball_moon = new Button();
		choix_ball_moon.setId("choix_ball_moon");
		choix_ball_moon.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_moon.setCursor(Cursor.HAND);
		
		choix_ball_moon.setPrefSize(208/4,237/4);
		choix_ball_moon.setLayoutX(choix_ball_green.getLayoutX()+choix_ball_green.getPrefWidth()+25);
		choix_ball_moon.setLayoutY(550);
		choix_ball_moon.setOnAction(value ->  {
			gw.setBallSkin("moon_ball.png");
	    });
		
		Button choix_ball_jupiter = new Button();
		choix_ball_jupiter.setId("choix_ball_jupiter");
		choix_ball_jupiter.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_jupiter.setCursor(Cursor.HAND);
		
		choix_ball_jupiter.setPrefSize(158/4,234/4);
		choix_ball_jupiter.setLayoutX(choix_ball_moon.getLayoutX()+choix_ball_moon.getPrefWidth()+25);
		choix_ball_jupiter.setLayoutY(550);
		choix_ball_jupiter.setOnAction(value ->  {
			gw.setBallSkin("jupiter_ball.png");
	    });
		
		Button choix_ball_saturne = new Button();
		choix_ball_saturne.setId("choix_ball_saturne");
		choix_ball_saturne.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_saturne.setCursor(Cursor.HAND);
		
		choix_ball_saturne.setPrefSize(239/4,214/4);
		choix_ball_saturne.setLayoutX(choix_ball_jupiter.getLayoutX()+choix_ball_jupiter.getPrefWidth()+25);
		choix_ball_saturne.setLayoutY(550);
		choix_ball_saturne.setOnAction(value ->  {
			gw.setBallSkin("saturne_ball.png");
	    });
		
		Button choix_ball_lila = new Button();
		choix_ball_lila.setId("choix_ball_lila");
		choix_ball_lila.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_lila.setCursor(Cursor.HAND);
		
		choix_ball_lila.setPrefSize(199/4,214/4);
		choix_ball_lila.setLayoutX(choix_ball_saturne.getLayoutX()+choix_ball_saturne.getPrefWidth()+25);
		choix_ball_lila.setLayoutY(550);
		choix_ball_lila.setOnAction(value ->  {
			gw.setBallSkin("lila_ball.png");
	    });
		
		Button choix_ball_earth = new Button();
		choix_ball_earth.setId("choix_ball_earth");
		choix_ball_earth.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_earth.setCursor(Cursor.HAND);
		
		choix_ball_earth.setPrefSize(168/4,234/4);
		choix_ball_earth.setLayoutX(choix_ball_lila.getLayoutX()+choix_ball_lila.getPrefWidth()+25);
		choix_ball_earth.setLayoutY(550);
		choix_ball_earth.setOnAction(value ->  {
			gw.setBallSkin("earth_ball.png");
	    });
		
		
		Button[] tab_skin= {title_ball_skin,choix_ball_sun,choix_ball_green,
				choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,
				choix_ball_earth};
		
		
		startRoot.getChildren().addAll(title_ball_skin,choix_ball_sun,choix_ball_green
				,choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,
				choix_ball_earth);
		
		
		Button title_ball_difficult = new Button ();
		title_ball_difficult.setId("ball_difficulty");
		title_ball_difficult.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		title_ball_difficult.setCursor(Cursor.HAND);
		
		title_ball_difficult.setPrefSize(3222/9,492/9);
		title_ball_difficult.setLayoutX(middle_bar_no.getLayoutX()+middle_bar_no.getPrefWidth()+100);
		title_ball_difficult.setLayoutY(450);
		
		startRoot.getChildren().addAll(title_middle_bar,middle_bar_yes,middle_bar_no,title_ball_difficult);
		
		Button[] tab_setting = {title_s,title_choix_bg,choix_galaxie,choix_trou_noir,
				choix_earth,choix_earth2,finish_button,title_middle_bar,middle_bar_yes,
				middle_bar_no,title_ball_difficult};
		
		
		title_ball_difficult.setOnAction(value ->  {
			visible_change(tab_skin,false);
			visible_change(tab_setting,false);
			print_setting_ball_difficulty(tab_setting,tab_skin);
			
	    });
		
		finish_button.setOnAction(value ->  {
			visible_change(tab_skin,false);
			visible_change(tab_setting,false);
			visible_change(tab_init,true);
			
	    });
		
	}
	
	public void print_setting_ball_difficulty(Button [] tab1, Button[] tab2) {
		Button title_s = new Button();
		title_s.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		title_s.setId("ball_difficulty");
		
		title_s.setPrefSize(3222/7,492/7);
		title_s.setLayoutX(width/2 - title_s.getPrefWidth()/2);
		title_s.setLayoutY(50);
		
		Label explication = new Label("Ici vous pouvez choisir si voulez un changent aleatoire de la taille des rackets a chaque rebond");
		explication.setFont(Font.font("Cambria",25));
		explication.setTextFill(Color.DARKGREY);
		explication.setPrefWidth(1025);
		explication.setLayoutX(width/2 - explication.getPrefWidth()/2);
		explication.setLayoutY(150);
		
		Button button_yes = new Button();
		button_yes.setId("middle_bar_yes");
		button_yes.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		button_yes.setCursor(Cursor.HAND);
		
		button_yes.setPrefSize(497/4,464/4);
		button_yes.setLayoutX(width/2 - button_yes.getPrefWidth()-25);
		button_yes.setLayoutY(250);
		
		Button button_no = new Button();
		button_no.setId("middle_bar_no");
		button_no.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		button_no.setCursor(Cursor.HAND);
		
		button_no.setPrefSize(497/4,464/4);
		button_no.setLayoutX(width/2 + button_no.getPrefWidth()+25);
		button_no.setLayoutY(250);
		
		startRoot.getChildren().addAll(title_s,explication,button_yes,button_no);
		
		Button[] difficulty= {title_s,button_yes,button_no};
		
		button_yes.setOnAction(value ->  {
			gw.setChangeRacketSize(true);
			visible_change(tab1,true);
			visible_change(tab2,true);
			visible_change(difficulty,false);
			explication.setVisible(false);
	    });
		
		button_no.setOnAction(value ->  {
			gw.setChangeRacketSize(false);
			visible_change(tab1,true);
			visible_change(tab2,true);
			visible_change(difficulty,false);
			explication.setVisible(false);
	    });
		
	}
	
	public void chose_difficulty() {
		Button[] btn_accueil = {quit,play,setting_button,multiplay,title};
		visible_change(btn_accueil,false);
		
		Button easy = new Button();
		easy.setId("button_easy");
		easy.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		easy.setCursor(Cursor.HAND);
		
		easy.setPrefSize(874/3,159/3);
		easy.setLayoutX(width/2 - easy.getPrefWidth()/2);
		easy.setLayoutY(50);
		
		
		Button medium = new Button();
		medium.setId("button_medium");
		medium.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		medium.setCursor(Cursor.HAND);
		
		medium.setPrefSize(1183/3,157/3);
		medium.setLayoutX(width/2 - medium.getPrefWidth()/2);
		medium.setLayoutY(150);
		
		Button hard = new Button();
		hard.setId("button_hard");
		hard.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		hard.setCursor(Cursor.HAND);
		
		hard.setPrefSize(869/3,154/3);
		hard.setLayoutX(width/2 - hard.getPrefWidth()/2);
		hard.setLayoutY(250);
		
		Button insane = new Button();
		insane.setId("button_insane");
		insane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		insane.setCursor(Cursor.HAND);
		
		insane.setPrefSize(1257/2,168/2);
		insane.setLayoutX(width/2 - insane.getPrefWidth()/2);
		insane.setLayoutY(350);
		
		Button[] diff = {easy,medium,hard,insane};
		
		insane.setOnAction(value ->  {
			court.setDifficulty(4);
			jouer_solo();
			visible_change(diff,false);
	    });
		
		easy.setOnAction(value ->  {
			court.setDifficulty(1);
			jouer_solo();
			visible_change(diff,false);
	    });
		
		hard.setOnAction(value ->  {
			court.setDifficulty(3);
			jouer_solo();
			visible_change(diff,false);
	    });
		
		medium.setOnAction(value ->  {
			court.setDifficulty(2);
			jouer_solo();
			visible_change(diff,false);
	    });
		startRoot.getChildren().addAll(easy,medium,hard,insane);
			
			
		
	}
	
	public void jouer_solo() {
		Button[] btn_accueil = {quit,play,setting_button,multiplay,title};
		visible_change(btn_accueil,true);
		court.setPartiEnCours(true);
		court.setIsBot(true);
		App.getStage().setScene(courtScene);
		App.getStage().setFullScreen(true);
		gw.startAnimation();
	}
}

