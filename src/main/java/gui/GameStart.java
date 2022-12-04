package gui;

import java.awt.Dimension;
import java.io.File;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Court;
import model.OnlineCourt;
import model.RacketController;
import network.Network;
import network.Requests;


public class GameStart {
	
	//Boolean pour savoir si la barre de chargement à deja été charge
	private boolean charge= false;
	
	private boolean start = false;
	
	private final ImageView quit,play,setting_button,multiplay,title;
	private int height;
	private int width;
	private Pane startRoot;
	private Pane gameRoot;
	private Pane onlineRoot;
	
	private GameView gw;
	private OnlineGameView ogv;
	
	private Court court;
	private OnlineCourt oc;
	
	private Scene courtScene;
	private Scene onlineCourtScene;
	
	private RacketController onlinePlayer;
	
	private Network n;
	private Requests r;

	//Boutton pour les parties en solo
	private ImageView easy,medium,hard,insane;
	
	//Boutton pour les parties en ligne
	
	private ImageView titleOnline;
	
	private ImageView imageIp;
	private TextField ipInput;
	private Label ip;
	private ImageView valideIp;
	
	private ImageView imagePseudo;
	private TextField pseudoInput;
	private Label pseudo;
	private ImageView validePseudo;
	
	private ImageView valider;
	
	//Boutton pour les parties en multijoueur
	private ImageView button_1vs1;
	private ImageView button_2vs2;
	private ImageView online;

	
	//Mise en place d'un boutton retour
	private ImageView retour;    
    
    private ImageView start_button;
    private ProgressBar progressBar;
    

	public GameStart (Pane startRoot,Pane root, Pane onlineRoot, Scene courtScene, GameView gw,Court court, Scene onlineScene, RacketController onlinePlayer) {
		
		this.startRoot = startRoot;
		this.gameRoot = root;
		this.gw = gw;
		this.court = court;
		this.courtScene = courtScene;
		this.onlineRoot = onlineRoot;
		this.onlineCourtScene = onlineScene;
		this.onlinePlayer = onlinePlayer;
		
		gameRoot.setId("choix_galaxie");
		onlineRoot.setId("choix_galaxie");
		
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
		
		titleOnline = new ImageView();
		imageIp = new ImageView();
		ipInput = new TextField();
		imagePseudo = new ImageView();
		pseudoInput = new TextField();
		valider = new ImageView();
		ip = new Label("Spacepong.fr");
		pseudo = new Label("Votre pseudo ?");
		validePseudo = new ImageView();
		valideIp = new ImageView();
				
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
		
		initButtonOnline();
		visible_change(getButtonOnline(),false);
		titleOnline.setVisible(false);
			
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
		onlineRoot.setId(s);
	}
	
	
	public void VisibleMiddleBar(boolean b) {
		gw.Visible_middle_bar(b);
	}
	
	//Cette fonction est rataché au boutton retour qu'on retrouve dans les settings, dans le choix de la difficulté et dans le choix du multijoueur
	public void retour(ImageView[] imageViews) {
		visible_change(getButtonMulti(),false);
		visible_change(getButtonDifficulty(),false);
		visible_change(getButtonOnline(),false);
		visible_change(getMenuButton(),true);
		ip.setVisible(false);
		pseudo.setVisible(false);
		titleOnline.setVisible(false);
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
	
	public void initButtonOnline() {
		Image imageTitleOnline = new Image(getClass().getResourceAsStream("online.png"));
		titleOnline.setImage(imageTitleOnline);
		titleOnline.setFitWidth(imageTitleOnline.getWidth()/2);
		titleOnline.setFitHeight(imageTitleOnline.getHeight()/2);
		titleOnline.setLayoutX(width/2 - titleOnline.getFitWidth()/2);
		titleOnline.setLayoutY(50);	
		startRoot.getChildren().addAll(titleOnline);
		
		
		imageIp.setId("ip");
		Image imageTextIp = new Image(getClass().getResourceAsStream("ip.png"));
		imageIp.setImage(imageTextIp);
		imageIp.setFitWidth(imageTextIp.getWidth()/2);
		imageIp.setFitHeight(imageTextIp.getHeight()/2);
		imageIp.setLayoutX(width*1/4);
		imageIp.setLayoutY(height*1/4);	
		startRoot.getChildren().addAll(imageIp);
		
		
		ipInput.setPrefSize(width/5,imageIp.getFitHeight());
		ipInput.setLayoutX(imageIp.getLayoutX()+imageIp.getFitWidth() + 50);
		ipInput.setLayoutY(imageIp.getLayoutY());
		ipInput.setVisible(false);
		startRoot.getChildren().addAll(ipInput);
		
		
		ip.setMinHeight(height*(46.3/100));
		ip.setMinWidth(height*(46.3/100));
		ip.setLayoutX(imageIp.getLayoutX()+imageIp.getFitWidth()+100);
		ip.setLayoutY(imageIp.getLayoutY() + imageIp.getFitHeight()/2 - ip.getMinHeight()/2);
		ip.setFont(new Font("Serif", imageIp.getFitHeight()/2));
		ip.setTextFill(Color.web("#FF0000"));
		ip.setVisible(false);
		startRoot.getChildren().add(ip);
		
		
		valideIp.setId("Valideip");
		Image imageOk = new Image(getClass().getResourceAsStream("ok.png"));
		valideIp.setImage(imageOk);
		valideIp.setFitWidth(imageOk.getWidth()/2);
		valideIp.setFitHeight(imageIp.getFitHeight());
		valideIp.setLayoutX(ip.getLayoutX()+ip.getMinWidth()+100);
		valideIp.setLayoutY(imageIp.getLayoutY());	
		startRoot.getChildren().addAll(valideIp);
		valideIp.setVisible(false);
		
		
		imagePseudo.setId("pseudo");
		Image imageTextPseudo = new Image(getClass().getResourceAsStream("pseudo.png"));
		imagePseudo.setImage(imageTextPseudo);
		imagePseudo.setFitWidth(imageTextPseudo.getWidth()/2);
		imagePseudo.setFitHeight(imageTextPseudo.getHeight()/2);
		imagePseudo.setLayoutX(width*1/4-imagePseudo.getFitWidth()/2);
		imagePseudo.setLayoutY(height*2/4);	
		startRoot.getChildren().addAll(imagePseudo);
		
		
		pseudoInput.setPrefSize(width/5,imagePseudo.getFitHeight());
		pseudoInput.setLayoutX(imagePseudo.getLayoutX()+imagePseudo.getFitWidth() + 50);
		pseudoInput.setLayoutY(imagePseudo.getLayoutY());
		pseudoInput.setVisible(false);
		startRoot.getChildren().addAll(pseudoInput);
		
		
		pseudo.setMinHeight(height*(46.3/100));
		pseudo.setMinWidth(height*(46.3/100));
		pseudo.setLayoutX(imagePseudo.getLayoutX()+imagePseudo.getFitWidth()+100);
		pseudo.setLayoutY(imagePseudo.getLayoutY() + imagePseudo.getFitHeight()/2 - pseudo.getMinHeight()/2);
		pseudo.setFont(new Font("Serif", imagePseudo.getFitHeight()/2));
		pseudo.setTextFill(Color.web("#FF0000"));
		pseudo.setVisible(false);
		startRoot.getChildren().add(pseudo);
		
		
		validePseudo.setId("Validepseudo");
		validePseudo.setImage(imageOk);
		validePseudo.setFitWidth(imageOk.getWidth()/2);
		validePseudo.setFitHeight(imagePseudo.getFitHeight());
		validePseudo.setLayoutX(pseudo.getLayoutX()+pseudo.getMinWidth()+100);
		validePseudo.setLayoutY(imagePseudo.getLayoutY());	
		startRoot.getChildren().addAll(validePseudo);
		validePseudo.setVisible(false);
		
		valider.setId("Valideonline");
		Image imageTextValider = new Image(getClass().getResourceAsStream("valider.png"));
		valider.setImage(imageTextValider);
		valider.setFitWidth(imageTextValider.getWidth()/2);
		valider.setFitHeight(imageTextValider.getHeight()/2);
		valider.setLayoutX(width/2 -valider.getFitWidth()/2);
		valider.setLayoutY(height*3/4);	
		startRoot.getChildren().addAll(valider);
		validePseudo.setVisible(false);
		
		
	}
	
	public void choisirIp() {
		valideIp.setVisible(true);
		ipInput.setVisible(true);
		ip.setVisible(false);
	}
	
	public void valideIp() {
		String s = ipInput.getText();
		if(s.equals("")) {
			ip.setText("spacepong.fr");
		}
		else {
			ip.setText(s);
		}
		valideIp.setVisible(false);
		ipInput.setVisible(false);
		ip.setVisible(true);
	}
	
	public void choisirPseudo() {
		validePseudo.setVisible(true);
		pseudoInput.setVisible(true);
		pseudo.setVisible(false);
	}
	
	public void validePseudo() {
		String s = pseudoInput.getText();
		if(s.equals("")) {
			pseudo.setText("Votre pseudo ?");
		}
		else {
			pseudo.setText(s);
		}
		validePseudo.setVisible(false);
		pseudoInput.setVisible(false);
		pseudo.setVisible(true);
	}
	
	
	public void jouer_online() {
		title.setVisible(false);
		titleOnline.setVisible(true);
		visible_change(getButtonMulti(),false);
		visible_change(getButtonOnline(),true);
		ip.setVisible(true);
		pseudo.setVisible(true);	
	}
	
	public void runOnline() {
		String ipServer = ip.getText();
		String ps = pseudo.getText();
		try {
			/* à chaque fois que le joueur veut jouer en ligne,
			 * on crée une nouvelle connexion,
			 * un nouvel objet Requests,
			 * un nouveau terrain (visuel et modélisé).
			 * Cela facilite les opérations et la gestion des bugs.
			 */
			n = new Network();
			// A MODIFIER EN RECUPERANT IP SERVEUR
			r = new Requests(n, ipServer);

			oc = new OnlineCourt(onlinePlayer, width, height, r);
			r.setOnlineCourt(oc);			

			ogv = new OnlineGameView(oc, onlineRoot, onlineCourtScene);

			App.getStage().setFullScreen(true);
			App.getStage().setScene(onlineCourtScene);
			App.getStage().setFullScreen(true);
			
			ogv.startAnimation();

			Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						boolean received = r.sendMessage(oc.getIdPlayer(), "PLAYER_JOINED", 0.0, 0.0, true);
						if (received == false) {
							sound("NoConnection.wav");
							return;
							// on revient à l'accueil en indiquant un pb connexion
						}
						
						
						while(true) {
							String[] response = n.listen(2);
							if (response != null) {
								r.onMessageReceived(response);
							}
						}					
					}			
				});
				t.start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			sound("NoConnection.wav");
			
			// on revient en arrière car problème connexion
			e.printStackTrace();
		}		
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
	
	public ImageView[] getButtonOnline(){
		ImageView[] tab = {retour,imageIp,imagePseudo,valider};
		return tab;
	}
	
	public ImageView[] getButtonIp(){
		ImageView[] tab = {valideIp};
		return tab;
	}
	
	public ImageView[] getButtonPseudo(){
		ImageView[] tab = {validePseudo};
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
	
	public void sound(String s) {
        // On joue le son
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/main/resources/"+s)));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
}

