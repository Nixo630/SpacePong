package gui;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
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

	//Boutton pour les parties en solo
	private Button easy,medium,hard,insane;
	
	//Boutton pour les parties en multijoueur
	private Button button_1vs1;
	private Button button_2vs2;
	private Button online;
	
	//Mise en place d'un curseur
	private Circle curseur_droit;
	private Circle curseur_gauche;
	
	//Mise en place d'un boutton retour
	private Button retour;
	
	//Cette variable permet de savoir où sont positionné les curseurs;
	private int indice = 1;
	
	//Ce tableau est le tableau des button courant
    private Button[] current_button= {};
    
    //Mise en place de tous les boutons pour les paramètres
    private Button title_s;
    private Button title_middle_bar;
    private Button finish_button;
    private Button title_ball_skin;
    private Button title_racket_difficult;
    private Button title_choix_bg;
    private Button points_bg;
    
    //Boutton pour la barre du milieu
    
    private Button middle_bar_no;
    private Button middle_bar_yes;
    
    //Boutton pour le changement de skin de la ball
    private Button choix_ball_sun,choix_ball_green,choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,choix_ball_earth;
    
    //Boutton pour changer le background de la game
    private Button choix_galaxie,choix_trou_noir,choix_earth,choix_earth2;
    
    //ChoseBox pour permettre à l'utilisateur de choisir en combien de points il veut finir la partie
    private ChoiceBox<Integer> choiceBox;
    
    //Boutton pour l'option du changement aléatoire de la taille de la raquette
    
    private Button title_racket_difficulty;
    private Label explication;
    private Button button_yes;
    private Button button_no;

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
		
		play.setId("solo_play_button");
		play.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		play.setPrefSize(1920/4.5,463/4.5);
		play.setLayoutX(width/2 - play.getPrefWidth()/2);
		play.setLayoutY(280);
		
		//Mise en place du curseur droit
		
		curseur_droit= new Circle();
		curseur_droit.setRadius(25);
	    curseur_droit.setCenterX(play.getLayoutX()+play.getPrefWidth()+25);
	    curseur_droit.setCenterY(play.getLayoutY()+play.getPrefHeight()/2);
	    Image j = new Image(getClass().getResourceAsStream("curseur_droit.png"));
	    curseur_droit.setFill(new ImagePattern(j));
	    
	    //Mise en place du curseur gauche;
	    
	    curseur_gauche= new Circle();
		curseur_gauche.setRadius(25);
	    curseur_gauche.setCenterX(play.getLayoutX()-25);
	    curseur_gauche.setCenterY(play.getLayoutY()+play.getPrefHeight()/2);
	    Image i = new Image(getClass().getResourceAsStream("curseur_gauche.png"));
	    curseur_gauche.setFill(new ImagePattern(i));
	    
	    startRoot.getChildren().addAll(curseur_droit,curseur_gauche);
	    curseur_gauche.setVisible(false);
	    curseur_droit.setVisible(false);
		
		//Mise en place du bouton pour jouer à deux 
		
		multiplay = new Button();
		
		multiplay.setId("multiplay_play_button");
		multiplay.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		multiplay.setPrefSize(3376/3.5,574/3.5);
		multiplay.setLayoutX(width/2 - multiplay.getPrefWidth()/2);
		multiplay.setLayoutY(400);
		
		//Boutton pour quitter le jeu
		quit = new Button();
		quit.setCancelButton(true);
		quit.setId("quit_button");
		quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		quit.setPrefSize(1424/3,216/3);
		quit.setLayoutX(width/2 - quit.getPrefWidth()/2);
		quit.setLayoutY(650);
		
		retour = new Button();
		retour.setId("return");
		retour.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		retour.setLayoutX(40);
		retour.setLayoutY(40);
		retour.setPrefSize(100, 100);
		
		startRoot.getChildren().addAll(play, quit,multiplay,retour);
		
		play.setVisible(false);
		multiplay.setVisible(false);
		quit.setVisible(false);
		retour.setVisible(false);
		
		//Mise en place du boutton setting
		
		setting_button = new Button();
		
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
		start_button.setDefaultButton(true);
		
		
		start_button.setPrefSize(2965/4.5,491/4.5);
		
		start_button.setLayoutX(width/2 - start_button.getPrefWidth()/2);
		start_button.setLayoutY(height/2 - start_button.getPrefHeight()/2);
		
		
		//Lorsqu'on clique sur le bouton, on active une fonction qui fait augmenter la jauge de chargement
		
		start_button.setOnAction(value ->  {
			court.sound("starting.wav");
			startRoot.getChildren().removeAll(start_button);
			startRoot.getChildren().addAll(progressBar);
			
			
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
							curseur_droit.setVisible(true);
							curseur_gauche.setVisible(true);
							visible_change(getMenuButton(),true);
							initCurrentButton();
				        	chrono.cancel();
				        	
				        	
						}
						time--;
					}
					
				}, 100,15);
			}
			else {
				visible_change(getMenuButton(),true);
			}
				
		  });
		
		startRoot.getChildren().addAll(title,start_button);
		
		
	}
	
	public void initCurrentButton() {
		this.current_button = getMenuButton();
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
	
	public void setBackground(String s) {
		gameRoot.setId(s);
	}
	
	public void parametre() {
		visible_change(getMenuButton(),false);
		title.setVisible(false);
		
		
		//Mise en place des settings
		
		title_s = new Button();
		title_s.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		title_s.setId("title");
		
		title_s.setPrefSize(2733/7,425/7);
		title_s.setLayoutX(width/2 - title_s.getPrefWidth()/2);
		title_s.setLayoutY(50);
		
		
		//Mise en place du choix de l'arriere plan
		title_choix_bg = new Button();
		title_choix_bg.setId("title_choix_bg");
		title_choix_bg.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_choix_bg.setPrefSize(3366/8,343/8);
		title_choix_bg.setLayoutX(20);
		title_choix_bg.setLayoutY(200);
		
		
		choix_galaxie = new Button();
		choix_galaxie.setId("choix_galaxie");
		choix_galaxie.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_galaxie.setPrefSize(1600/8,1200/10);
		choix_galaxie.setLayoutX(20+title_choix_bg.getPrefWidth()+25);
		choix_galaxie.setLayoutY(200);
		
		
		choix_trou_noir = new Button();
		choix_trou_noir.setId("choix_trou_noir");
		choix_trou_noir.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_trou_noir.setPrefSize(1600/8,1200/10);
		choix_trou_noir.setLayoutX(20 + choix_galaxie.getLayoutX()+choix_galaxie.getPrefWidth()+25);
		choix_trou_noir.setLayoutY(200);
		
		
		choix_earth = new Button();
		choix_earth.setId("choix_earth");
		choix_earth.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_earth.setCursor(Cursor.HAND);
		choix_earth.setPrefSize(1600/8,1200/10);
		choix_earth.setLayoutX(20 + choix_trou_noir.getLayoutX()+choix_trou_noir.getPrefWidth()+25);
		choix_earth.setLayoutY(200);
		
		choix_earth.setOnAction(value ->  {
			gameRoot.setId("choix_earth");
	    });
		
		
		choix_earth2 = new Button();
		choix_earth2.setId("choix_earth2");
		choix_earth2.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_earth2.setCursor(Cursor.HAND);
		choix_earth2.setPrefSize(1600/8,1200/10);
		choix_earth2.setLayoutX(20 + choix_earth.getLayoutX()+choix_earth.getPrefWidth()+25);
		choix_earth2.setLayoutY(200);
		
		choix_earth2.setOnAction(value ->  {
			gameRoot.setId("choix_earth2");
	    });
		
		finish_button = new Button();
		finish_button.setId("finish_button");
		finish_button.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		finish_button.setPrefSize(1795/8,332/8);
		finish_button.setLayoutX(width/2 - finish_button.getPrefWidth()/2);
		finish_button.setLayoutY(height-100);
		
		
		startRoot.getChildren().addAll(title_s,title_choix_bg,choix_galaxie,choix_trou_noir,choix_earth,choix_earth2,finish_button);
		
		
		
		// User can choose between with middle bar or without middle bar 
		
		title_middle_bar = new Button();
		title_middle_bar.setId("title_middle_bar");
		title_middle_bar.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_middle_bar.setPrefSize(2872/8,311/8);
		title_middle_bar.setLayoutX(20);
		title_middle_bar.setLayoutY(450);
		
		middle_bar_yes = new Button();
		middle_bar_yes.setId("middle_bar_yes");
		middle_bar_yes.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		middle_bar_yes.setPrefSize(497/4,464/4);
		middle_bar_yes.setLayoutX(20+title_middle_bar.getPrefWidth()+25);
		middle_bar_yes.setLayoutY(400);
		
		
		
		middle_bar_no = new Button();
		middle_bar_no.setId("middle_bar_no");
		middle_bar_no.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		middle_bar_no.setPrefSize(497/4,464/4);
		middle_bar_no.setLayoutX(middle_bar_yes.getLayoutX()+middle_bar_yes.getPrefWidth()+25);
		middle_bar_no.setLayoutY(400);
		
		//Mise en place du choix du skin de la balle pour le joueur
		
		title_ball_skin = new Button();
		title_ball_skin.setId("title_ball_skin");
		title_ball_skin.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		title_ball_skin.setPrefSize(3213/9,375/9);
		title_ball_skin.setLayoutX(20);
		title_ball_skin.setLayoutY(550);
		
		
		choix_ball_sun = new Button();
		choix_ball_sun.setId("choix_ball_sun");
		choix_ball_sun.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_sun.setPrefSize(202/4,202/4);
		choix_ball_sun.setLayoutX(title_ball_skin.getLayoutX()+title_ball_skin.getPrefWidth()+25);
		choix_ball_sun.setLayoutY(550);
		choix_ball_sun.setOnAction(value ->  {
			gw.setBallSkin("sun_ball.png");
	    });
		
		choix_ball_green = new Button();
		choix_ball_green.setId("choix_ball_green");
		choix_ball_green.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_green.setPrefSize(196/4,217/4);
		choix_ball_green.setLayoutX(choix_ball_sun.getLayoutX()+choix_ball_sun.getPrefWidth()+25);
		choix_ball_green.setLayoutY(550);
		
		
		choix_ball_moon = new Button();
		choix_ball_moon.setId("choix_ball_moon");
		choix_ball_moon.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_moon.setPrefSize(208/4,237/4);
		choix_ball_moon.setLayoutX(choix_ball_green.getLayoutX()+choix_ball_green.getPrefWidth()+25);
		choix_ball_moon.setLayoutY(550);
		
		
		choix_ball_jupiter = new Button();
		choix_ball_jupiter.setId("choix_ball_jupiter");
		choix_ball_jupiter.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_jupiter.setPrefSize(158/4,234/4);
		choix_ball_jupiter.setLayoutX(choix_ball_moon.getLayoutX()+choix_ball_moon.getPrefWidth()+25);
		choix_ball_jupiter.setLayoutY(550);
		
		
		choix_ball_saturne = new Button();
		choix_ball_saturne.setId("choix_ball_saturne");
		choix_ball_saturne.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_saturne.setPrefSize(239/4,214/4);
		choix_ball_saturne.setLayoutX(choix_ball_jupiter.getLayoutX()+choix_ball_jupiter.getPrefWidth()+25);
		choix_ball_saturne.setLayoutY(550);
		
		choix_ball_lila = new Button();
		choix_ball_lila.setId("choix_ball_lila");
		choix_ball_lila.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_lila.setPrefSize(199/4,214/4);
		choix_ball_lila.setLayoutX(choix_ball_saturne.getLayoutX()+choix_ball_saturne.getPrefWidth()+25);
		choix_ball_lila.setLayoutY(550);
		
		
		choix_ball_earth = new Button();
		choix_ball_earth.setId("choix_ball_earth");
		choix_ball_earth.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_earth.setPrefSize(168/4,234/4);
		choix_ball_earth.setLayoutX(choix_ball_lila.getLayoutX()+choix_ball_lila.getPrefWidth()+25);
		choix_ball_earth.setLayoutY(550);
		
		startRoot.getChildren().addAll(title_ball_skin,choix_ball_sun,choix_ball_green
				,choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,
				choix_ball_earth);
		
		
		title_racket_difficult = new Button ();
		title_racket_difficult.setId("racket_difficulty");
		title_racket_difficult.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_racket_difficult.setPrefSize(3222/9,492/9);
		title_racket_difficult.setLayoutX(middle_bar_no.getLayoutX()+middle_bar_no.getPrefWidth()+100);
		title_racket_difficult.setLayoutY(450);
		
		startRoot.getChildren().addAll(title_middle_bar,middle_bar_yes,middle_bar_no,title_racket_difficult);
		

		//Ajout du nombre de points pour finir une partie
		choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(5, 6, 7, 8, 9, 10, 15, 20);

		choiceBox.setPrefSize(168/4,234/4);
		choiceBox.setValue(5);
		choiceBox.setLayoutX(490);
		choiceBox.setLayoutY(650);

		startRoot.getChildren().add(choiceBox);

		points_bg = new Button();
		points_bg.setId("points_background");
		points_bg.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		points_bg.setPrefSize(450, 190/4);
		points_bg.setLayoutX(10);
		points_bg.setLayoutY(650);

		startRoot.getChildren().add(points_bg);


		//Ajout de la flèche retour en arrière
		
		retour = new Button();
		retour.setId("return");
		retour.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		retour.setLayoutX(40);
		retour.setLayoutY(40);
		retour.setPrefSize(100, 100);
		

		startRoot.getChildren().add(retour);
		
	}
	
	public void finish() {
		choiceBox.setVisible(false);
		points_bg.setVisible(false);
		gw.getCourt().setScoreFinal(choiceBox.getValue());
		retour(getButtonParametre());
	}
	
	public void VisibleMiddleBar(boolean b) {
		gw.Visible_middle_bar(b);
	}
	
	//Cette fonction est rataché au boutton retour qu'on retrouve dans les settings, dans le choix de la difficulté et dans le choix du multijoueur
	public void retour(Button[] btn) {
		if (egal(btn,getButtonParametre())) {
			choiceBox.setVisible(false);
			visible_change(getButtonSkinBall(),false);
			visible_change(getButtonBackground(),false);
			visible_change(getMBButtonYesNo(),false);
			title_s.setVisible(false);
			
		}
		visible_change(getCurrentButton(),false);
		visible_change(getMenuButton(),true);
		title.setVisible(true);
	}
	
	public void print_setting_racket_difficulty() {
		visible_change(getButtonParametre(),false);
		visible_change(getButtonBackground(),false);
		visible_change(getButtonSkinBall(),false);
		visible_change(getMBButtonYesNo(),false);
		choiceBox.setVisible(false);
		title_s.setVisible(false);
		
		title_racket_difficulty = new Button();
		title_racket_difficulty.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		title_racket_difficulty.setId("racket_difficulty");
		
		title_racket_difficulty.setPrefSize(3222/7,492/7);
		title_racket_difficulty.setLayoutX(width/2 - title_s.getPrefWidth()/2);
		title_racket_difficulty.setLayoutY(50);
		
		explication = new Label("Ici vous pouvez choisir si voulez un changent aleatoire de la taille des rackets a chaque rebond");
		explication.setFont(Font.font("Cambria",25));
		explication.setTextFill(Color.DARKGREY);
		explication.setPrefWidth(1025);
		explication.setLayoutX(width/2 - explication.getPrefWidth()/2);
		explication.setLayoutY(150);
		
		button_yes = new Button();
		button_yes.setId("RD_yes");
		button_yes.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		
		button_yes.setPrefSize(497/4,464/4);
		button_yes.setLayoutX(width/2 - button_yes.getPrefWidth()-25);
		button_yes.setLayoutY(250);
		
		button_no = new Button();
		button_no.setId("RD_no");
		button_no.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		
		button_no.setPrefSize(497/4,464/4);
		button_no.setLayoutX(width/2 + button_no.getPrefWidth()+25);
		button_no.setLayoutY(250);
		
		startRoot.getChildren().addAll(title_racket_difficulty,explication,button_yes,button_no);
		
	}
	
	public void reponseRacketDifficuly(boolean b) {
		title_racket_difficulty.setVisible(false);
		gw.setChangeRacketSize(b);
		explication.setVisible(false);
		title_racket_difficulty.setVisible(false);
		choiceBox.setVisible(true);
		title_s.setVisible(true);
		
		visible_change(getRDButtonYesNo(),false);
		visible_change(getButtonParametre(),true);
		visible_change(getButtonBackground(),true);
		visible_change(getButtonSkinBall(),true);
		visible_change(getMBButtonYesNo(),true);
	}
	
	
	public void chose_difficulty() {
		
		Button[] btn_accueil = getMenuButton();
		visible_change(btn_accueil,false);
		retour.setVisible(true);
		
		easy = new Button();
		easy.setId("button_easy");
		easy.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		easy.setPrefSize(1424/3,216/3);
		easy.setLayoutX(width/2 - easy.getPrefWidth()/2);
		
		
		
		medium = new Button();
		medium.setId("button_medium");
		medium.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		medium.setPrefSize(1424/3,216/3);
		medium.setLayoutX(width/2 - medium.getPrefWidth()/2);
		medium.setLayoutY(height/2 - medium.getHeight()- 50);
		
		easy.setLayoutY(medium.getLayoutY()-medium.getHeight() - 100);
		
		hard = new Button();
		hard.setId("button_hard");
		hard.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		hard.setPrefSize(1424/3,216/3);
		hard.setLayoutX(width/2 - hard.getPrefWidth()/2);
		hard.setLayoutY(height/2 + hard.getHeight()+ 50);
		
		insane = new Button();
		insane.setId("button_insane");
		insane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		insane.setPrefSize(1424/3,216/3);
		insane.setLayoutX(width/2 - insane.getPrefWidth()/2);
		insane.setLayoutY(hard.getLayoutY()+hard.getHeight()+100);
		
		startRoot.getChildren().addAll(easy,medium,hard,insane);
	}
	
	public void jouer_solo(int i) {
		court.setDifficulty(i);
		visible_change(getButtonDifficulty(),false);
		
		visible_change(getMenuButton(),true);
		court.setPartiEnCours(true);
		court.setIsBot(true);
		App.getStage().setFullScreen(true);
		App.getStage().setScene(courtScene);
		App.getStage().setFullScreen(true);
		gw.startAnimation();
	}

		//Cette fonction permet de choisir à l'utilisateur si il veut jouer en 1 vs 1 ou en 2 vs 2 robots
	public void choose_multiplay() {
		retour.setVisible(true);
		visible_change(getMenuButton(),false);
		
		button_1vs1 = new Button();
		button_1vs1.setId("button_1vs1");
		button_1vs1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		button_1vs1.setPrefSize(1424/3,216/3);
		button_1vs1.setLayoutX(width/2 - button_1vs1.getPrefWidth()/2);
		
		
		
		button_2vs2 = new Button();
		button_2vs2.setId("button_2vs2");
		button_2vs2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		button_2vs2.setPrefSize(1424/3,216/3);
		button_2vs2.setLayoutX(width/2 - button_2vs2.getPrefWidth()/2);
		button_2vs2.setLayoutY(height/2 - button_2vs2.getPrefHeight()/2);
		
		button_1vs1.setLayoutY(button_2vs2.getLayoutY()-50-button_1vs1.getPrefHeight());
		
		online = new Button();
		online.setId("button_online");
		online.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		online.setPrefSize(1424/3,216/3);
		online.setLayoutX(width/2 - online.getPrefWidth()/2);
		online.setLayoutY(button_2vs2.getLayoutY()+50+button_2vs2.getPrefHeight());
		
		
		
		startRoot.getChildren().addAll(button_1vs1,button_2vs2,online);
	}
	
	public void jouer_online() {
		
	}
	
	public void VisibleMiseAJourMultiButton() {
		visible_change(getMenuButton(),true);
		visible_change(getButtonMulti(),false);
	}
	
	public void jouer_multi(boolean x) {
		VisibleMiseAJourMultiButton();
		court.setIsBot(false);
		court.setPartiEnCours(true);
		App.getStage().setScene(courtScene);
		if (x){
		gw.startAnimation2();}
		else {gw.startAnimation(); }
	}
	
	public Button[] getMenuButton() {
		Button[] tab = {setting_button,play,multiplay,quit};
		return tab;
	}
	
	public Button[] getButtonDifficulty() {
		Button[] tab = {retour,easy,medium,hard,insane};
		return tab;
	}
	
	public Button[] getButtonMulti() {
		Button[] tab = {retour,button_1vs1,button_2vs2,online};
		return tab;
	}
	
	public Button[] getButtonParametre() {
		Button[] tab = {retour,title_choix_bg,title_middle_bar,title_ball_skin,title_racket_difficult,points_bg,finish_button};
		return tab;
	}
	
	public Button[] getButtonSkinBall() {
		Button[] tab_skin= {choix_ball_sun,choix_ball_green,
				choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,
				choix_ball_earth};
		
		return tab_skin;
	}
	
	public Button[] getButtonBackground() {
		Button[] tab_setting = {choix_galaxie,choix_trou_noir,choix_earth,choix_earth2};
		
		return tab_setting;
	}
	
	public Button[] getMBButtonYesNo(){
		Button[] tab = {middle_bar_no,middle_bar_yes};
		
		return tab;
	}
	
	public Button[] getRDButtonYesNo(){
		Button[] tab = {button_yes,button_no};
		
		return tab;
	}
	
	//Mise en place de la fonction pour placer les curseur en fonction d'un bouton
	
	public void bouger_curseur(Button btn,Pane p) {
		//On supprime les curseur
		
		p.getChildren().removeAll(curseur_droit,curseur_gauche);
		
		//Changement de la position des curseurs en fonction du btn mis en paramètre

	    curseur_droit.setCenterX(btn.getLayoutX()+btn.getPrefWidth()+25);
	    curseur_droit.setCenterY(btn.getLayoutY()+btn.getPrefHeight()/2);
	    
	    //Mise en place du curseur gauche;
	    
		curseur_gauche.setCenterX(btn.getLayoutX()-25);
	    curseur_gauche.setCenterY(btn.getLayoutY()+btn.getPrefHeight()/2);
	    
	    //On réaffiche les curseurs
	    p.getChildren().addAll(curseur_droit,curseur_gauche);
	}
	
	//Fonction pour incrémenter ou décrémenter l'indice des curseurs en fonction de la taille du tableau de bouton
	
	public void IncrementeIndice(Button[] btn) {
		if(indice==btn.length-1) {
			indice = 0;
		}
		else {
			indice+=1;
		}
	}
	
	public void DecrementeIndice(Button[] btn) {
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
	
	public Button[] getCurrentButton() {
		return current_button;
	}
	
	public void setCurrentButton(Button[] btn) {
		indice=0;
		current_button = btn;
		bouger_curseur(current_button[indice],startRoot);
	}
	
	//Fonction auxilliaire permettant de determiner si 2 tableaux ont le même contenu
	
	public static boolean egal(Button[] b1, Button[] b2) {
		if (b1.length!=b2.length) {
			return false;
		}
		for (int i = 0;i<b1.length;i++) {
			if (b1[i] != b2[i]) {
				return false;
			}
		}
		return true;
	}
}

