package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class SettingView {

	Pane root;
    ImageView[] retourButton;
    Circle gauche;
    Circle droit;
    Pane canvas;
    
    Pane canvasRacketDifficulty;
    
    ImageView retour;
    
    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
    int width = tailleMoniteur.width;
    int height = tailleMoniteur.height;
    
    
    //Implementation de tous les boutons necessaires des parametres
    
    private ImageView title_s;
    private ImageView title_middle_bar;
    private ImageView finish_button;
    private ImageView title_ball_skin;
    private ImageView title_racket_difficult;
    private ImageView title_choix_bg;
    private ImageView points_bg;
    
    //Boutton pour la barre du milieu
    
    private ImageView middle_bar_no;
    private ImageView middle_bar_yes;
    
    //Boutton pour le changement de skin de la ball
    private ImageView choix_ball_sun,choix_ball_green,choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,choix_ball_earth;
    
    //Boutton pour changer le background de la game
    private ImageView choix_galaxie,choix_trou_noir,choix_earth,choix_earth2;
    
    //Boutton pour l'option du changement aléatoire de la taille de la raquette
    
    private ImageView title_racket_difficulty;
    private Label explication;
    private ImageView button_yes;
    private ImageView button_no;
    
    
    //Input du nombre de points souhaités par l'utilisateur
  	private ImageView points;
  	private TextField intInput;
  	

	//Message d'erreur si l'utilisateur n'entre pas une valeur entière
	private Label error;
	private GameView gw;

	
	public SettingView(Pane root,GameView gw) {
		this.root = root;
		this.gw = gw;
		
		canvas = new Pane();
		this.canvas.setId("pane");
		this.canvas.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		canvas.setPrefSize(width,height);
		
		canvasRacketDifficulty = new Pane();
	    this.canvasRacketDifficulty.setId("pane");
		this.canvasRacketDifficulty.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		canvasRacketDifficulty.setPrefSize(width,height);
	}
	
	public void init() {
		title_s = new ImageView();
		title_s.setId("title");
		Image imageTitleSetting = new Image(getClass().getResourceAsStream("settings_title.png"));
		title_s.setImage(imageTitleSetting);
		title_s.setFitWidth(width*21/100); 
		title_s.setFitHeight(height*6/100);
		title_s.setLayoutX(width/2 - title_s.getFitWidth()/2);
		title_s.setLayoutY(height*(4.63/100));
		canvas.getChildren().add(title_s);
		
		retour = new ImageView();
		retour.setId("finish_button");
		Image imageRetour = new Image(getClass().getResourceAsStream("retour.png"));
        retour.setImage(imageRetour);
		retour.setLayoutX(height*(3.7038/100));
		retour.setLayoutY(height*(3.7038/100));
		retour.setFitHeight(height*(4.63/100)*2);
		retour.setFitWidth(height*(4.63/100)*2);
		canvas.getChildren().add(retour);
		
		initBackgroundChoice();
		initSkinBall();
		initMiddleBar();
		initPointChoice();
		initPageRacketDifficulty();
		
		title_racket_difficult = new ImageView();
		title_racket_difficult.setId("racket_difficulty");
		Image imageTitleRacketDifficult = new Image(getClass().getResourceAsStream("raquet_difficulty.png"));
		title_racket_difficult.setImage(imageTitleRacketDifficult);
		title_racket_difficult.setFitWidth(width*(18.65/100));
		title_racket_difficult.setFitHeight(height*(5.247/100));
		title_racket_difficult.setLayoutX(width*(40.52/100));
		title_racket_difficult.setLayoutY(height*(41.7/100));
		canvas.getChildren().add(title_racket_difficult);
		

		finish_button = new ImageView();
		finish_button.setId("finish_button");
		Image imageFinir = new Image(getClass().getResourceAsStream("button_apply.png"));
		finish_button.setImage(imageFinir);
		finish_button.setFitWidth(width*(11.8/100));
		finish_button.setFitHeight(height*(3.8/100));
		finish_button.setLayoutX(width/2 - finish_button.getFitWidth()/2);
		finish_button.setLayoutY(height-height*(9.3/100));
		canvas.getChildren().add(finish_button);
		
		root.getChildren().add(canvas);
		canvas.setVisible(false);
	}
	
	public void initBackgroundChoice() {
		//Mise en place du choix de l'arriere plan
		title_choix_bg = new ImageView();
		title_choix_bg.setId("title_choix_bg");
		Image imageTitleBg = new Image(getClass().getResourceAsStream("choix_bg.png"));
		title_choix_bg.setImage(imageTitleBg);
		title_choix_bg.setFitWidth(width*22/100);
		title_choix_bg.setFitHeight(height*4/100);
		title_choix_bg.setLayoutX(width*1/100);
		title_choix_bg.setLayoutY(height*19/100);
		canvas.getChildren().add(title_choix_bg);
		
		choix_galaxie = new ImageView();
		choix_galaxie.setId("choix_galaxie");
		Image imageChoixGalaxie = new Image(getClass().getResourceAsStream("bg_galaxie.jpg"));
		choix_galaxie.setImage(imageChoixGalaxie);
		choix_galaxie.setFitWidth(width*(10.5/100));
		choix_galaxie.setFitHeight(height*(11.2/100));
		choix_galaxie.setLayoutX(width*(24.4/100));
		choix_galaxie.setLayoutY(height*(18.6/100));
		canvas.getChildren().add(choix_galaxie);
		
		choix_trou_noir = new ImageView();
		choix_trou_noir.setId("choix_trou_noir");
		Image imageTrouNoir = new Image(getClass().getResourceAsStream("bg_trou_noir.png"));
		choix_trou_noir.setImage(imageTrouNoir);
		choix_trou_noir.setFitWidth(width*(10.5/100));
		choix_trou_noir.setFitHeight(height*(11.2/100));
		choix_trou_noir.setLayoutX(width*(36.9/100));
		choix_trou_noir.setLayoutY(height*(18.6/100));
		canvas.getChildren().add(choix_trou_noir);
		
	
		choix_earth = new ImageView();
		choix_earth.setId("choix_earth");
		Image imageChoixEarth = new Image(getClass().getResourceAsStream("bg_earth.jpg"));
		choix_earth.setImage(imageChoixEarth);
		choix_earth.setFitWidth(width*(10.5/100));
		choix_earth.setFitHeight(height*(11.2/100));
		choix_earth.setLayoutX(width*(49.8/100));
		choix_earth.setLayoutY(height*(18.6/100));
		canvas.getChildren().add(choix_earth);
		

		choix_earth2 = new ImageView();
		choix_earth2.setId("choix_earth2");
		Image imageChoixEarth2 = new Image(getClass().getResourceAsStream("bg_earth2.jpg"));
		choix_earth2.setImage(imageChoixEarth2);
		choix_earth2.setFitWidth(width*(10.5/100));
		choix_earth2.setFitHeight(height*(11.2/100));
		choix_earth2.setLayoutX(width*(62.7/100));
		choix_earth2.setLayoutY(height*(18.6/100));
		canvas.getChildren().add(choix_earth2);
	}
	
	public void initSkinBall() {
		//Mise en place du choix du skin de la balle pour le joueur
		
		title_ball_skin = new ImageView();
		title_ball_skin.setId("title_ball_skin");
		Image imageTitleBallSkin = new Image(getClass().getResourceAsStream("Ball_skin/skin_ball_title.png"));
		title_ball_skin.setImage(imageTitleBallSkin);
		title_ball_skin.setFitWidth(width*(18.6/100));
		title_ball_skin.setFitHeight(height*(3.86/100));
		title_ball_skin.setLayoutX(height*(3.86/100)/2);
		title_ball_skin.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(title_ball_skin);
		
		choix_ball_sun = new ImageView();
		choix_ball_sun.setId("choix_ball_sun");
		Image imageSunBall = new Image(getClass().getResourceAsStream("Ball_skin/sun_ball.png"));
		choix_ball_sun.setImage(imageSunBall);	
		choix_ball_sun.setFitWidth(width*(2.64/100));
		choix_ball_sun.setFitHeight(width*(2.64/100));
		choix_ball_sun.setLayoutX(title_ball_skin.getLayoutX()+title_ball_skin.getFitWidth()+height*(3.86/100)/2);
		choix_ball_sun.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(choix_ball_sun);
		
		choix_ball_green = new ImageView();
		choix_ball_green.setId("choix_ball_sun");
		Image imageGreenBall = new Image(getClass().getResourceAsStream("Ball_skin/green_ball.png"));
		choix_ball_green.setImage(imageGreenBall);	
		choix_ball_green.setFitWidth(width*(2.553/100));
		choix_ball_green.setFitHeight(height*(5.02/100));
		choix_ball_green.setLayoutX(choix_ball_sun.getLayoutX()+choix_ball_sun.getFitWidth()+height*(4.63/100)/2);
		choix_ball_green.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(choix_ball_green);
		
		
		choix_ball_moon = new ImageView();
		choix_ball_moon.setId("choix_ball_moon");
		Image imageMoonBall = new Image(getClass().getResourceAsStream("Ball_skin/moon_ball.png"));
		choix_ball_moon.setImage(imageMoonBall);	
		choix_ball_moon.setFitWidth(width*(2.71/100));
		choix_ball_moon.setFitHeight(height*(5.5/100));
		choix_ball_moon.setLayoutX(choix_ball_green.getLayoutX()+choix_ball_green.getFitWidth()+height*(4.63/100)/2);
		choix_ball_moon.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(choix_ball_moon);
		

		choix_ball_jupiter = new ImageView();
		choix_ball_jupiter.setId("choix_ball_jupiter");
		Image imageJupiterBall = new Image(getClass().getResourceAsStream("Ball_skin/jupiter_ball.png"));
		choix_ball_jupiter.setImage(imageJupiterBall);	
		choix_ball_jupiter.setFitWidth(width*(2.06/100));
		choix_ball_jupiter.setFitHeight(height*(5.4/100));
		choix_ball_jupiter.setLayoutX(choix_ball_moon.getLayoutX()+choix_ball_moon.getFitWidth()+height*(4.63/100)/2);
		choix_ball_jupiter.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(choix_ball_jupiter);
		
		choix_ball_saturne = new ImageView();
		choix_ball_saturne.setId("choix_ball_saturne");
		Image imageSaturneBall = new Image(getClass().getResourceAsStream("Ball_skin/saturne_ball.png"));
		choix_ball_saturne.setImage(imageSaturneBall);	
		choix_ball_saturne.setFitWidth(width*(3.1/100));
		choix_ball_saturne.setFitHeight(height*(4.96/100));
		choix_ball_saturne.setLayoutX(choix_ball_jupiter.getLayoutX()+choix_ball_jupiter.getFitWidth()+height*(4.63/100)/2);
		choix_ball_saturne.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(choix_ball_saturne);
		
		choix_ball_lila = new ImageView();
		choix_ball_lila.setId("choix_ball_lila");
		Image imageLilaBall = new Image(getClass().getResourceAsStream("Ball_skin/lila_ball.png"));
		choix_ball_lila.setImage(imageLilaBall);	
		choix_ball_lila.setFitWidth(width*(2.5912/100));
		choix_ball_lila.setFitHeight(height*(4.96/100));
		choix_ball_lila.setLayoutX(choix_ball_saturne.getLayoutX()+choix_ball_saturne.getFitWidth()+height*(4.63/100)/2);
		choix_ball_lila.setLayoutY(height*(50.926/100));
		canvas.getChildren().add(choix_ball_lila);
		

		choix_ball_earth = new ImageView();
		choix_ball_earth.setId("choix_ball_earth");
		Image imageEarthBall = new Image(getClass().getResourceAsStream("Ball_skin/earth_ball.png"));
		choix_ball_earth.setImage(imageEarthBall);	
		choix_ball_earth.setFitWidth(width*(2.5912/100));
		choix_ball_earth.setFitHeight(height*(5.9/100));
		choix_ball_earth.setLayoutX(choix_ball_lila.getLayoutX()+choix_ball_lila.getFitWidth()+height*(4.63/100)/2);
		choix_ball_earth.setLayoutY(choix_ball_lila.getLayoutY());
		canvas.getChildren().add(choix_ball_earth);
	}
	
	public void initMiddleBar() {
		title_middle_bar = new ImageView();
		title_middle_bar.setId("title_middle_bar");
		Image imageTitleMiddleBar = new Image(getClass().getResourceAsStream("middle_bar_title.png"));
		title_middle_bar.setImage(imageTitleMiddleBar);
		title_middle_bar.setFitWidth(width*(18.7/100));
		title_middle_bar.setFitHeight(height*(3.6/100));
		title_middle_bar.setLayoutX(width*(1.05/100));
		title_middle_bar.setLayoutY(height*(41.7/100));
		canvas.getChildren().add(title_middle_bar);
		
		middle_bar_yes = new ImageView();
		middle_bar_yes.setId("middle_bar_yes");
		Image imageMiddleBarYes = new Image(getClass().getResourceAsStream("yes_button.png"));
		middle_bar_yes.setImage(imageMiddleBarYes);
		middle_bar_yes.setFitWidth(width*(6.5/100));
		middle_bar_yes.setFitHeight(height*(10.8/100));
		middle_bar_yes.setLayoutX(width*(21.06/100));
		middle_bar_yes.setLayoutY(width*(20.84/100));
		canvas.getChildren().add(middle_bar_yes);
		
		middle_bar_no = new ImageView();
		middle_bar_no.setId("middle_bar_no");
		Image imageMiddleBarNo = new Image(getClass().getResourceAsStream("no_button.png"));
		middle_bar_no.setImage(imageMiddleBarNo);
		middle_bar_no.setFitWidth(width*(6.5/100));
		middle_bar_no.setFitHeight(height*(10.8/100));
		middle_bar_no.setLayoutX(width*(28.86/100));
		middle_bar_no.setLayoutY(width*(20.84/100));
		canvas.getChildren().add(middle_bar_no);
		
	}
	
	public void initPointChoice() {
		
		points = new ImageView();
		Image imageOk = new Image(getClass().getResourceAsStream("ok.png"));
		points.setImage(imageOk);
		points.setFitWidth(width*(3.5/100));
		points.setFitHeight(height*(5.4/100));
		points.setLayoutX(width/2);
		points.setLayoutY(width*(33.9/100));
		points.setId("pointsFinaux");
		canvas.getChildren().add(points);
		
		intInput = new TextField();
		intInput.setPrefSize(width*(2.4714/100),height*(5.4/100));
		intInput.setLayoutX(width*(25.13/100));
		intInput.setLayoutY(width*(33.9/100));
		canvas.getChildren().add(intInput);
		intInput.setVisible(false);
		
		//Message d'erreur
		error = new Label();
		error.setLayoutX(width*(25.13/100));
		error.setLayoutY(height*(39.815/100));
		error.setFont(new Font("Serif", height*(1.852/100)));
		error.setTextFill(Color.web("#FF0000"));
		error.setMinHeight(height*(46.3/100));
		error.setMinWidth(height*(46.3/100));
		error.setVisible(false);
		canvas.getChildren().add(error);
		
		
		points_bg = new ImageView();
		points_bg.setId("points_background");
		Image imagePoints = new Image(getClass().getResourceAsStream("points.png"));
		points_bg.setImage(imagePoints);
		points_bg.setFitWidth(width*(23.44/100));
		points_bg.setFitHeight(height*(4.4/100));
		points_bg.setLayoutX(height*(0.93/100));
		points_bg.setLayoutY(width*(33.9/100));
		canvas.getChildren().add(points_bg);
		
	}
	
	public void afficherInput() {
		error.setVisible(false);
		intInput.setVisible(true);
	}
	
	public void initPageRacketDifficulty() {
		title_racket_difficulty = new ImageView();
		title_racket_difficulty.setId("racket_difficulty");
		Image imageTitleRacket = new Image(getClass().getResourceAsStream("raquet_difficulty.png"));
		title_racket_difficulty.setImage(imageTitleRacket);
		title_racket_difficulty.setFitWidth(width*(23.9584/100));
		title_racket_difficulty.setFitHeight(height*(6.49/100));
		title_racket_difficulty.setLayoutX(width/2 - title_s.getFitWidth()/2);
		title_racket_difficulty.setLayoutY(height*(4.63/100));
		canvasRacketDifficulty.getChildren().add(title_racket_difficulty);
		
		explication = new Label("Ici vous pouvez choisir si voulez un changent aleatoire de la taille des rackets a chaque rebond");
		explication.setFont(Font.font("Cambria",(height*(4.63/100))/2));
		explication.setTextFill(Color.DARKGREY);
		explication.setPrefWidth(width*(53.3855/100));
		explication.setLayoutX(width/2 - explication.getPrefWidth()/2);
		explication.setLayoutY(height*(4.63/100)*3);
		canvasRacketDifficulty.getChildren().add(explication);
		
		button_yes = new ImageView();
		button_yes.setId("RD_yes");
		Image imageYes = new Image(getClass().getResourceAsStream("yes_button.png"));
		button_yes.setImage(imageYes);
		button_yes.setFitWidth(width*(6.4714/100));
		button_yes.setFitHeight(width*(6.0417/100));
		button_yes.setLayoutX(width/2 - button_yes.getFitWidth()-(height*(4.63/100))/2);
		button_yes.setLayoutY(height*(4.63/100)*5);
		canvasRacketDifficulty.getChildren().add(button_yes);
		
		button_no = new ImageView();
		button_no.setId("RD_no");
		Image imageNo = new Image(getClass().getResourceAsStream("No_button.png"));
		button_no.setImage(imageNo);
		button_no.setFitWidth(width*(6.4714/100));
		button_no.setFitHeight(width*(6.0417/100));
		button_no.setLayoutX(width/2 + button_no.getFitWidth()+height*(4.63/100)/2);
		button_no.setLayoutY(height*(4.63/100)*5);
		canvasRacketDifficulty.getChildren().add(button_no);
		
		root.getChildren().add(canvasRacketDifficulty);
		canvasRacketDifficulty.setVisible(false);
		
	}
	public void choisirPoint() {
		isInt(intInput, intInput.getText());
		intInput.setVisible(false);
	}
	
	public boolean isInt(TextField input, String message){
		try{
			int age = Integer.parseInt(input.getText());
			if(!message.equals("")){
				if(age>0){
					error.setText(String.valueOf(age));
					error.setVisible(true);
				}else{
					error.setText("incorrect, veuillez entrez un nombre superieur a 0");
					error.setVisible(true);
				}
			}
			return true;
		}catch(NumberFormatException e){
			if(message.equals("")){
				error.setVisible(false);
				return false;
			}
			error.setText("incorrect, veuillez entrez un nombre superieur a 0");
			error.setVisible(true);
			return false;
		}
	}
	
	public void affiche(ImageView[] tab) {
		canvas.setVisible(true);
		retourButton = tab;
		GameStart.visible_change(retourButton, false);
	}
	
	
	public ImageView[] close() {
		if(isInt(intInput, intInput.getText())){
			gw.getCourt().setScoreFinal(Integer.valueOf(intInput.getText()));
		}
		canvas.setVisible(false);
		GameStart.visible_change(retourButton, true);
		return retourButton;
		
	}
	
	public ImageView[] getButtonParametre() {
		ImageView[] tab = {retour,title_choix_bg,title_middle_bar,title_ball_skin,title_racket_difficult,points_bg,finish_button};
		return tab;
	}
	
	public ImageView[] getButtonSkinBall() {
		ImageView[] tab_skin= {choix_ball_sun,choix_ball_green,
				choix_ball_moon,choix_ball_jupiter,choix_ball_saturne,choix_ball_lila,	choix_ball_earth};
		
		return tab_skin;
	}
	
	public ImageView[] getButtonBackground() {
		ImageView[] tab_setting = {choix_galaxie,choix_trou_noir,choix_earth,choix_earth2};
		
		return tab_setting;
	}
	
	public ImageView[] getMBButtonYesNo(){
		ImageView[] tab = {middle_bar_no,middle_bar_yes};
		
		return tab;
	}
	
	public ImageView[] getRDButtonYesNo(){
		ImageView[] tab = {button_yes,button_no};
		
		return tab;
	}
	
	public void reponseRacketDifficuly(boolean b) {
		gw.setChangeRacketSize(b);
		canvasRacketDifficulty.setVisible(false);
		canvas.setVisible(true);
	}
	
	public void print_setting_racket_difficulty() {
		canvas.setVisible(false);
		canvasRacketDifficulty.setVisible(true);	
	}
	
	public ImageView[] getButtonInput() {
		ImageView[] tab = {points};
		return tab;
	}
	
	
	
	
}
