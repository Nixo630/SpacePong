package gui;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Court;


public class GameStart {
	
	//Boolean pour savoir si la barre de chargement à deja été charge
	private boolean charge= false;
	
	private boolean start = false;
	
	private final ImageView quit,play,setting_button,multiplay,title;
	private int height;
	private int width;
	private Pane startRoot;
	private Pane gameRoot;
	
	private GameView gw;
	private Court court;
	private Scene courtScene;

	//Boutton pour les parties en solo
	private ImageView easy,medium,hard,insane;
	
	//Boutton pour les parties en multijoueur
	private ImageView button_1vs1;
	private ImageView button_2vs2;
	private ImageView online;

	
	//Mise en place d'un boutton retour
	private ImageView retour;    
    
    private ImageView start_button;
    private ProgressBar progressBar;
    

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
		
		easy = new ImageView();
		medium = new ImageView();
		hard = new ImageView();
		insane = new ImageView();
		
		initButtonDifficulty();
		
		
		button_1vs1 = new ImageView();
		button_2vs2 = new ImageView();
		online = new ImageView();
		
		initChooseMultiplay();
		
		//Le titre est un bouton sans commande dessus
		title = new ImageView();
		title.setId("title");
		Image imageTitle = new Image(getClass().getResourceAsStream("title.png"));
        title.setImage(imageTitle);
		title.setFitWidth(1082/2);
		title.setFitHeight(332/2);
		title.setLayoutX(width/2 - title.getFitWidth()/2);
		title.setLayoutY(50);
		
		
		
		//Mise en place du boutton Play pour jouer au jeu en solo
		play = new ImageView();
		play.setId("solo_play_button");
		Image imageSolo = new Image(getClass().getResourceAsStream("solo.png"));
		play.setImage(imageSolo);
		play.setFitWidth(1920/4.5);
		play.setFitHeight(463/4.5);
		play.setLayoutX(width/2 - play.getFitWidth()/2);
		play.setLayoutY(280);
		
		//Mise en place du bouton pour jouer à deux 
		
		multiplay = new ImageView();
		Image imageMulti = new Image(getClass().getResourceAsStream("multiplayer.png"));
        multiplay.setImage(imageMulti);
		multiplay.setId("multiplay_play_button");
		multiplay.setFitWidth(3376/3.5);
		multiplay.setFitHeight(574/3.5);
		multiplay.setLayoutX(width/2 - multiplay.getFitWidth()/2);
		multiplay.setLayoutY(400);
		
		//Boutton pour quitter le jeu
		quit = new ImageView();
		quit.setId("quit_button");
		Image imageQuitter = new Image(getClass().getResourceAsStream("quitter.png"));
        quit.setImage(imageQuitter);
		quit.setFitWidth(1424/3);
		quit.setFitHeight(216/3);
		quit.setLayoutX(width/2 - quit.getFitWidth()/2);
		quit.setLayoutY(650);
		
		retour = new ImageView();
		retour.setId("return");
		Image imageRetour = new Image(getClass().getResourceAsStream("retour.png"));
        retour.setImage(imageRetour);
		retour.setLayoutX(40);
		retour.setLayoutY(40);
		retour.setFitHeight(100);
		retour.setFitWidth(100);
		
		
		startRoot.getChildren().addAll(play, quit,multiplay,retour);
		
		retour.setVisible(false);
		play.setVisible(false);
		multiplay.setVisible(false);
		quit.setVisible(false);
		retour.setVisible(false);
		visible_change(getButtonDifficulty(),false);
		visible_change(getButtonMulti(),false);
		
		//Mise en place du boutton setting
		
		setting_button = new ImageView();
		setting_button.setId("settings_button");
		setting_button.setFitWidth(360/2);
		setting_button.setFitHeight(360/2);
		Image imageSetting = new Image(getClass().getResourceAsStream("setting_button.png"));
		setting_button.setImage(imageSetting);
		setting_button.setLayoutX(width-setting_button.getFitWidth());
		setting_button.setLayoutY(0);
		
		
		startRoot.getChildren().addAll(setting_button);
		setting_button.setVisible(false);
		
		
		//Mise en place de la barre de progression
		
		progressBar = new ProgressBar(0);
		progressBar.setPrefSize(width/2.5,75);
		progressBar.setLayoutX(width/2 - progressBar.getPrefWidth()/2);
		progressBar.setLayoutY(350);
		
		
		//Mise en place du boutton start
		
		start_button = new ImageView();
		start_button.setId("start_button");
		start_button.setFitWidth(2965/4.5);
		start_button.setFitHeight(491/4.5);
		Image imageStart = new Image(getClass().getResourceAsStream("start_button.png"));
		start_button.setImage(imageStart);
		start_button.setLayoutX(width/2 - start_button.getFitWidth()/2);
		start_button.setLayoutY(height/2 - start_button.getFitHeight()/2);
		
		startRoot.getChildren().addAll(title,start_button);
			
	}
	
	public void start(Curseur c) {
		if (!start) {
			c.setVisible(false);
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
							visible_change(getMenuButton(),true);
							c.setVisible(true);
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
	
	
	//Cette fonction fait apparaitre les bouton de jeu et pour quitter
	public static void visible_change(ImageView [] t,boolean b) {
		for (int i = 0;i<t.length;i++) {
			t[i].setVisible(b);
		}
	}
	
	public static void visible_change(Button[] t,boolean b) {
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
	
	
	public void VisibleMiddleBar(boolean b) {
		gw.Visible_middle_bar(b);
	}
	
	//Cette fonction est rataché au boutton retour qu'on retrouve dans les settings, dans le choix de la difficulté et dans le choix du multijoueur
	public void retour(ImageView[] imageViews) {
		visible_change(getButtonMulti(),false);
		visible_change(getButtonDifficulty(),false);
		visible_change(getMenuButton(),true);
		title.setVisible(true);
	}
	
	public void chose_difficulty() {
		visible_change(getMenuButton(),false);
		visible_change(getButtonDifficulty(),true);
		retour.setVisible(true);	
	}
	
	public void initButtonDifficulty() {

		easy.setId("button_easy");
		Image imageEasy = new Image(getClass().getResourceAsStream("easy_button.png"));
        easy.setImage(imageEasy);
		easy.setFitWidth(1424/3);
		easy.setFitHeight(216/3);
		easy.setLayoutX(width/2 - easy.getFitWidth()/2);
		
		
		
		
		medium.setId("button_medium");
		Image imageMedium = new Image(getClass().getResourceAsStream("medium_button.png"));
        medium.setImage(imageMedium);
		
		medium.setFitWidth(1424/3);
		medium.setFitHeight(216/3);
		medium.setLayoutX(width/2 - medium.getFitWidth()/2);
		medium.setLayoutY(height/2 - medium.getFitHeight()/2-50);
		
		easy.setLayoutY(medium.getLayoutY()-medium.getFitHeight() - 50);
		
		
		hard.setId("button_hard");
		Image imageHard = new Image(getClass().getResourceAsStream("hard_button.png"));
        hard.setImage(imageHard);
		hard.setFitWidth(1424/3);
		hard.setFitHeight(216/3);
		hard.setLayoutX(width/2 - hard.getFitWidth()/2);
		hard.setLayoutY(height/2 + hard.getFitHeight()/2+50);
		
		
		insane.setId("button_insane");
		Image imageInsane = new Image(getClass().getResourceAsStream("insane_button.png"));
		insane.setImage(imageInsane);
		insane.setFitWidth(1424/3);
		insane.setFitHeight(216/3);
		insane.setLayoutX(width/2 - insane.getFitWidth()/2);
		insane.setLayoutY(hard.getLayoutY()+hard.getFitHeight()+50);
		
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
	public void initChooseMultiplay() {
		button_1vs1.setId("button_1vs1");
		Image image1vs1 = new Image(getClass().getResourceAsStream("button_1vs1.png"));
		button_1vs1.setImage(image1vs1);
		button_1vs1.setFitWidth(1424/3);
		button_1vs1.setFitHeight(216/3);
		button_1vs1.setLayoutX(width/2 - button_1vs1.getFitWidth()/2);
		
		button_2vs2.setId("button_2vs2");
		Image image2vs2 = new Image(getClass().getResourceAsStream("button_2vs2.png"));
		button_2vs2.setImage(image2vs2);
		button_2vs2.setFitWidth(1424/3);
		button_2vs2.setFitHeight(216/3);
		button_2vs2.setLayoutX(width/2 - button_2vs2.getFitWidth()/2);
		button_2vs2.setLayoutY(height/2 - button_2vs2.getFitHeight()/2);
		
		button_1vs1.setLayoutY(button_2vs2.getLayoutY()-50-button_1vs1.getFitHeight());
		
		
		online.setId("button_online");
		Image imageOnline = new Image(getClass().getResourceAsStream("online.png"));
		online.setImage(imageOnline);
		online.setFitWidth(1424/3);
		online.setFitHeight(216/3);
		online.setLayoutX(width/2 - online.getFitWidth()/2);
		online.setLayoutY(button_2vs2.getLayoutY()+50+button_2vs2.getFitHeight());
	
		
		startRoot.getChildren().addAll(button_1vs1,button_2vs2,online);
	}
	
	public void choose_multiplay() {
		retour.setVisible(true);
		visible_change(getMenuButton(),false);
		visible_change(getButtonMulti(),true);
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
	
	public ImageView[] getMenuButton() {
		ImageView[] tab = {setting_button,play,multiplay,quit};
		return tab;
	}
	
	public ImageView[] getButtonDifficulty() {
		ImageView[] tab = {retour,easy,medium,hard,insane};
		return tab;
	}
	
	public ImageView[] getButtonMulti() {
		ImageView[] tab = {retour,button_1vs1,button_2vs2,online};
		return tab;
	}
	
	public ImageView[] getStartButton() {
		ImageView[] tab = {start_button};
		return tab;
	}
	
	
	
	//Mise en place de la fonction pour placer les curseur en fonction d'un bouton
	
	
	
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

