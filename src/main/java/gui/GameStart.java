package gui;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import model.Court;


public class GameStart {
	
	//Boolean pour savoir si la barre de chargement à deja été charge
	private boolean charge= false;
	
	private boolean start = false;
	
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


    
    //Input du nombre de points souhaités par l'utilisateur
	private Button points;
	private TextField intInput;

	//Message d'erreur si l'utilisateur n'entre pas une valeur entière
	private Label error;

	
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
    
    private Button start_button;
    private ProgressBar progressBar;
    
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
		App.getStage().setResizable(false);
		App.getStage().setFullScreenExitHint("appuyer sur 'd' et 'c' pour se deplacer\n appuyer sur 'm' pour accepter");
		
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
		
		title.setPrefSize(width*50/100,height*25/100);
		title.setLayoutX(width/2 - title.getPrefWidth()/2);
		title.setLayoutY(0);
		
		
		
		//Mise en place du boutton Play pour jouer au jeu en solo
		play = new Button();
		play.setCursor(Cursor.HAND);		
		play.setId("solo_play_button");
		play.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		play.setPrefSize(width*35/100,height*15/100);
		play.setLayoutX(width/2 - play.getPrefWidth()/2);
		play.setLayoutY(height*30/100);
		
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
		
		
		multiplay.setPrefSize(width*70/100,height*15/100);
		multiplay.setLayoutX(width/2 - multiplay.getPrefWidth()/2);
		multiplay.setLayoutY(height*45/100);
		
		//Boutton pour quitter le jeu
		quit = new Button();
		quit.setCancelButton(true);
		quit.setId("quit_button");
		quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		quit.setPrefSize(width*30/100,height*15/100);
		quit.setLayoutX(width/2 - quit.getPrefWidth()/2);
		quit.setLayoutY(height*65/100);
		
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
		
		setting_button.setPrefSize(height*20/100, height*20/100);
		
		setting_button.setLayoutX(width-setting_button.getPrefWidth());
		setting_button.setLayoutY(0);
		
		
		startRoot.getChildren().addAll(setting_button);
		setting_button.setVisible(false);
		
		
		//Mise en place de la barre de progression
		
		progressBar = new ProgressBar(0);
		
		progressBar.setPrefSize(width/2.5,75);
		
		progressBar.setLayoutX(width/2 - progressBar.getPrefWidth()/2);
		progressBar.setLayoutY(350);
		
		
		//Mise en place du boutton start
		
		start_button = new Button();
		start_button.setCursor(Cursor.HAND);
		start_button.setId("start_button");
		start_button.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		start_button.setDefaultButton(true);
		
		
		start_button.setPrefSize(width*75/100,height*20/100);
		
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
	
	public void start() {
		if (!start) {
			start = true;
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
		}
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

	public boolean isInt(TextField input, String message){
		try{
			int age = Integer.parseInt(input.getText());
			if(!message.equals("")){
				if(age>0){
					error.setVisible(false);
				}else{
					error.setVisible(true);
				}
			}
			return true;
		}catch(NumberFormatException e){
			if(message.equals("")){
				error.setVisible(false);
				return false;
			}
			error.setVisible(true);
			return false;
		}
	}
	
	public void parametre() {
		visible_change(getMenuButton(),false);
		title.setVisible(false);
		
		
		//Mise en place des settings
		
		title_s = new Button();
		title_s.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		title_s.setId("title");
		
		title_s.setPrefSize(width*21/100, height*6/100);
		title_s.setLayoutX(width/2 - title_s.getPrefWidth()/2);
		title_s.setLayoutY(height*5/100);
		
		
		//Mise en place du choix de l'arriere plan
		title_choix_bg = new Button();
		title_choix_bg.setId("title_choix_bg");
		title_choix_bg.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_choix_bg.setPrefSize(width*22/100,height*4/100);
		title_choix_bg.setLayoutX(width*1/100);
		title_choix_bg.setLayoutY(height*19/100);
		
		
		choix_galaxie = new Button();
		choix_galaxie.setId("choix_galaxie");
		choix_galaxie.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_galaxie.setPrefSize(width*(10.5/100),height*(11.2/100));
		choix_galaxie.setLayoutX(width*(24.4/100));
		choix_galaxie.setLayoutY(height*(18.6/100));
		
		
		choix_trou_noir = new Button();
		choix_trou_noir.setId("choix_trou_noir");
		choix_trou_noir.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		choix_trou_noir.setPrefSize(width*(10.5/100),height*(11.2/100));
		choix_trou_noir.setLayoutX(width*(36.9/100));
		choix_trou_noir.setLayoutY(height*(18.6/100));
		
		
		choix_earth = new Button();
		choix_earth.setId("choix_earth");
		choix_earth.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_earth.setCursor(Cursor.HAND);
		choix_earth.setPrefSize(width*(10.5/100),height*(11.2/100));
		choix_earth.setLayoutX(width*(49.8/100));
		choix_earth.setLayoutY(height*(18.6/100));
		
		choix_earth.setOnAction(value ->  {
			gameRoot.setId("choix_earth");
	    });
		
		
		choix_earth2 = new Button();
		choix_earth2.setId("choix_earth2");
		choix_earth2.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		choix_earth2.setCursor(Cursor.HAND);
		choix_earth2.setPrefSize(width*(10.5/100),height*(11.2/100));
		choix_earth2.setLayoutX(width*(62.7/100));
		choix_earth2.setLayoutY(height*(18.6/100));
		
		choix_earth2.setOnAction(value ->  {
			gameRoot.setId("choix_earth2");
	    });
		
		finish_button = new Button();
		finish_button.setId("finish_button");
		finish_button.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		finish_button.setPrefSize(width*(11.8/100),height*(3.8/100));
		finish_button.setLayoutX(width/2 - finish_button.getPrefWidth()/2);
		finish_button.setLayoutY(height-height*(9.3/100));
		
		
		startRoot.getChildren().addAll(title_s,title_choix_bg,choix_galaxie,choix_trou_noir,choix_earth,choix_earth2,finish_button);
		
		
		
		// User can choose between with middle bar or without middle bar 
		
		title_middle_bar = new Button();
		title_middle_bar.setId("title_middle_bar");
		title_middle_bar.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_middle_bar.setPrefSize(width*(18.7/100),height*(3.6/100));
		title_middle_bar.setLayoutX(width*(1.05/100));
		title_middle_bar.setLayoutY(height*(41.7/100));
		
		middle_bar_yes = new Button();
		middle_bar_yes.setId("middle_bar_yes");
		middle_bar_yes.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		middle_bar_yes.setPrefSize(width*(6.5/100),height*(10.8/100));
		middle_bar_yes.setLayoutX(width*(21.06/100));
		middle_bar_yes.setLayoutY(width*(20.84/100));
		
		
		
		middle_bar_no = new Button();
		middle_bar_no.setId("middle_bar_no");
		middle_bar_no.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		middle_bar_no.setPrefSize(width*(6.5/100),height*(10.8/100));
		middle_bar_no.setLayoutX(width*(28.86/100));
		middle_bar_no.setLayoutY(width*(20.84/100));
		
		//Mise en place du choix du skin de la balle pour le joueur
		
		title_ball_skin = new Button();
		title_ball_skin.setId("title_ball_skin");
		title_ball_skin.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		title_ball_skin.setPrefSize(width*(18.6/100),height*(3.86/100));
		title_ball_skin.setLayoutX(height*(3.86/100)/2);
		title_ball_skin.setLayoutY(height*(50.926/100));
		
		
		choix_ball_sun = new Button();
		choix_ball_sun.setId("choix_ball_sun");
		choix_ball_sun.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_sun.setCursor(Cursor.HAND);
		
		choix_ball_sun.setPrefSize(width*(2.64/100),width*(2.64/100));
		choix_ball_sun.setLayoutX(title_ball_skin.getLayoutX()+title_ball_skin.getPrefWidth()+height*(3.86/100)/2);
		choix_ball_sun.setLayoutY(height*(50.926/100));
		choix_ball_sun.setOnAction(value ->  {
			gw.setBallSkin("sun_ball.png");
	    });
		
		choix_ball_green = new Button();
		choix_ball_green.setId("choix_ball_green");
		choix_ball_green.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_green.setCursor(Cursor.HAND);
		
		choix_ball_green.setPrefSize(width*(2.553/100),height*(5.02/100));
		choix_ball_green.setLayoutX(choix_ball_sun.getLayoutX()+choix_ball_sun.getPrefWidth()+height*(4.63/100)/2);
		choix_ball_green.setLayoutY(height*(50.926/100));
		
		
		choix_ball_moon = new Button();
		choix_ball_moon.setId("choix_ball_moon");
		choix_ball_moon.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_moon.setCursor(Cursor.HAND);
		
		choix_ball_moon.setPrefSize(width*(2.71/100),height*(5.5/100));
		choix_ball_moon.setLayoutX(choix_ball_green.getLayoutX()+choix_ball_green.getPrefWidth()+height*(4.63/100)/2);
		choix_ball_moon.setLayoutY(height*(50.926/100));
		
		
		choix_ball_jupiter = new Button();
		choix_ball_jupiter.setId("choix_ball_jupiter");
		choix_ball_jupiter.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		choix_ball_jupiter.setCursor(Cursor.HAND);
		
		choix_ball_jupiter.setPrefSize(width*(2.06/100),height*(5.4/100));
		choix_ball_jupiter.setLayoutX(choix_ball_moon.getLayoutX()+choix_ball_moon.getPrefWidth()+height*(4.63/100)/2);
		choix_ball_jupiter.setLayoutY(height*(50.926/100));
		
		
		choix_ball_saturne = new Button();
		choix_ball_saturne.setId("choix_ball_saturne");
		choix_ball_saturne.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_saturne.setPrefSize(width*(3.1/100),height*(4.96/100));
		choix_ball_saturne.setLayoutX(choix_ball_jupiter.getLayoutX()+choix_ball_jupiter.getPrefWidth()+height*(4.63/100)/2);
		choix_ball_saturne.setLayoutY(height*(50.926/100));
		
		choix_ball_lila = new Button();
		choix_ball_lila.setId("choix_ball_lila");
		choix_ball_lila.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_lila.setPrefSize(width*(2.5912/100),height*(4.96/100));
		choix_ball_lila.setLayoutX(choix_ball_saturne.getLayoutX()+choix_ball_saturne.getPrefWidth()+height*(4.63/100)/2);
		choix_ball_lila.setLayoutY(height*(50.926/100));
		
		
		choix_ball_earth = new Button();
		choix_ball_earth.setId("choix_ball_earth");
		choix_ball_earth.getStylesheets().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
		
		choix_ball_earth.setPrefSize(width*(2.188),height*(5.417/100));
		choix_ball_earth.setLayoutX(choix_ball_lila.getLayoutX()+choix_ball_lila.getPrefWidth()+height*(4.63/100)/2);
		choix_ball_earth.setLayoutY(height*(50.926/100));
		
		startRoot.getChildren().addAll(title_ball_skin,choix_ball_sun,choix_ball_green
				,choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,
				choix_ball_earth);
		
		
		title_racket_difficult = new Button ();
		title_racket_difficult.setId("racket_difficulty");
		title_racket_difficult.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		
		title_racket_difficult.setPrefSize(width*(18.65/100),height*(5.247/100));
		title_racket_difficult.setLayoutX(width*(40.52/100));
		title_racket_difficult.setLayoutY(height*(41.7/100));
		
		startRoot.getChildren().addAll(title_middle_bar,middle_bar_yes,middle_bar_no,title_racket_difficult);
		

		//Ajout du nombre de points pour finir une partie
		intInput = new TextField();
		points = new Button();
		points.setOnAction(e -> isInt(intInput, intInput.getText()));
		points.setPrefSize(width*(3.5/100),height*(5.4/100));
		points.setLayoutX(width*(28.13/100));
		points.setLayoutY(width*(33.9/100));
		points.setId("pointsFinaux");
		points.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		points.setCursor(Cursor.HAND);

		intInput.setPrefSize(width*(2.4714/100),height*(5.4/100));
		intInput.setLayoutX(width*(25.13/100));
		intInput.setLayoutY(width*(33.9/100));

		//Message d'erreur
		error = new Label("incorrect, veuillez entrez un nombre superieur a 0");
		error.setLayoutX(width*(32.292/100));
		error.setLayoutY(height*(39.815/100));
		error.setFont(new Font("Serif", height*(1.852/100)));
		error.setTextFill(Color.web("#FF0000"));
		error.setMinHeight(height*(46.3/100));
		error.setMinWidth(height*(46.3/100));
		error.setVisible(false);



		startRoot.getChildren().addAll(intInput, points, error);



		points_bg = new Button();
		points_bg.setId("points_background");
		points_bg.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		points_bg.setPrefSize(width*(23.44/100), height*(4.4/100));
		points_bg.setLayoutX(height*(0.93/100));
		points_bg.setLayoutY(width*(33.9/100));

		startRoot.getChildren().add(points_bg);


		//Ajout de la flèche retour en arrière
		
		retour = new Button();
		retour.setId("return");
		retour.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
		retour.setLayoutX(height*(3.704/100));
		retour.setLayoutY(height*(3.704/100));
		retour.setPrefSize(height*(9.26/100), height*(9.26/100));
		

		startRoot.getChildren().add(retour);
		
	}
	
	public void finish() {
		intInput.setVisible(false);
		points.setVisible(false);
		points_bg.setVisible(false);
		if(isInt(intInput, intInput.getText())){
			gw.getCourt().setScoreFinal(Integer.valueOf(intInput.getText()));
		}
		error.setVisible(false);
		retour(getButtonParametre());
	}
	
	public void VisibleMiddleBar(boolean b) {
		gw.Visible_middle_bar(b);
	}
	
	//Cette fonction est rataché au boutton retour qu'on retrouve dans les settings, dans le choix de la difficulté et dans le choix du multijoueur
	public void retour(Button[] btn) {
		if (egal(btn,getButtonParametre())) {
			intInput.setVisible(false);
			points.setVisible(false);
			visible_change(getButtonSkinBall(),false);
			visible_change(getButtonBackground(),false);
			visible_change(getMBButtonYesNo(), false);
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
		
		easy.setPrefSize(width*45/100,height*10/100);
		easy.setLayoutX(width/2 - easy.getPrefWidth()/2);
		easy.setLayoutY(height*30/100);
		
		
		
		medium = new Button();
		medium.setId("button_medium");
		medium.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		medium.setPrefSize(width*45/100,height*10/100);
		medium.setLayoutX(width/2 - medium.getPrefWidth()/2);
		medium.setLayoutY(easy.getLayoutY() + easy.getLayoutY()*45/100);
		
		
		hard = new Button();
		hard.setId("button_hard");
		hard.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		hard.setPrefSize(width*45/100,height*10/100);
		hard.setLayoutX(width/2 - hard.getPrefWidth()/2);
		hard.setLayoutY(easy.getLayoutY() + easy.getLayoutY()*85/100);
		
		insane = new Button();
		insane.setId("button_insane");
		insane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		insane.setPrefSize(width*45/100,height*10/100);
		insane.setLayoutX(width/2 - insane.getPrefWidth()/2);
		insane.setLayoutY(easy.getLayoutY() + easy.getLayoutY()*125/100);
		
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
		
		button_1vs1.setPrefSize(width*45/100,height*10/100);
		button_1vs1.setLayoutX(width/2 - button_1vs1.getPrefWidth()/2);
		button_1vs1.setLayoutY(height*30/100);
		
		
		
		button_2vs2 = new Button();
		button_2vs2.setId("button_2vs2");
		button_2vs2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		button_2vs2.setPrefSize(width*45/100,height*10/100);
		button_2vs2.setLayoutX(width/2 - button_2vs2.getPrefWidth()/2);
		button_2vs2.setLayoutY(button_1vs1.getLayoutY() + button_1vs1.getLayoutY()*45/100);
		
		
		online = new Button();
		online.setId("button_online");
		online.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		online.setPrefSize(width*45/100,height*10/100);
		online.setLayoutX(width/2 - online.getPrefWidth()/2);
		online.setLayoutY(button_1vs1.getLayoutY() + button_1vs1.getLayoutY()*85/100);
		
		
		
		startRoot.getChildren().addAll(button_1vs1,button_2vs2,online);
	}
	
	public void jouer_online() {
		
	}
	
	public void VisibleMiseAJourMultiButton() {
		visible_change(getMenuButton(),true);
		visible_change(getButtonMulti(),false);
	}
	
	public void jouer_multi(boolean choix2v2) {
		
		VisibleMiseAJourMultiButton();
		court.setIsBot(false);
		court.setPartiEnCours(true);
		
		App.getStage().setScene(courtScene);
		App.getStage().setFullScreen(true);
		
		if (choix2v2){
			gw.startAnimation2();
		}
		else {
			gw.startAnimation();
		}
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

