package gui;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court;
import model.RacketController;
 
public class App extends Application {
	
	private static Stage guiStage;
    public static Stage getStage() {
        return guiStage;
    }
	
    @Override
    public void start(Stage primaryStage) {
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width-100;
        int hauteur = tailleMoniteur.height;

    	guiStage = primaryStage;
        guiStage.setHeight(hauteur);
        guiStage.setWidth(longueur);
    	
    	var start = new Pane();
    	start.setId("pane");
    	
    	var startScene = new Scene(start);
    	startScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
    	
        
        var root = new Pane();
        
        var gameScene = new Scene(root);
        gameScene.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
        
        
        
        class Player implements RacketController {
            State state = State.IDLE;

            @Override
            public State getState() {
                return state;
            }
        }
        var playerA = new Player();
        var playerB = new Player();
        var playerC = new Player();
        var playerD = new Player();
        		
        var court = new Court(playerA, playerB, playerD, playerD, longueur, hauteur);
        var gameView = new GameView(court, root, 1.0,startScene);
        var gameStart = new GameStart(start,root,gameScene,gameView,court);
        
        TouchView touchView = new TouchView(start,gameStart.getCurseurDroit(),gameStart.getCurseurGauche());
        touchView.init();
        
        startScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case UP:
                    break;
                case DOWN:
                    break;
                case ENTER:
                	break;
                case ESCAPE:primaryStage.setFullScreen(true);break;
                case S:gameStart.start();break;
                case Q : System.exit(0);break;
                case I : touchView.affiche(gameStart.getCurrentButton());break;
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        
        startScene.setOnKeyReleased(ev ->{
        	switch (ev.getCode()) {
        	case I :gameStart.setCurrentButton(touchView.close());break;
            case DOWN:
                gameStart.IncrementeIndice(gameStart.getCurrentButton());
                gameStart.bouger_curseur(gameStart.getCurrentButton()[gameStart.getCurseurIndice()],start);
                break;
            case UP:
            	gameStart.DecrementeIndice(gameStart.getCurrentButton());
                gameStart.bouger_curseur(gameStart.getCurrentButton()[gameStart.getCurseurIndice()],start);
                break;
            case ENTER:
            	if(gameStart.getCurrentButton().length!=0 && !touchView.estAffiche()) {
                	switch (gameStart.getCurrentButton()[gameStart.getCurseurIndice()].getId()) {
                	
                	case "solo_play_button": gameStart.chose_difficulty();gameStart.setCurrentButton(gameStart.getButtonDifficulty());break; 
                	case "multiplay_play_button": gameStart.choose_multiplay();gameStart.setCurrentButton(gameStart.getButtonMulti());break;
                	case "settings_button": gameStart.parametre();gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "quit_button": System.exit(0);break;
                	
                	case "button_easy": gameStart.jouer_solo(1);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_medium": gameStart.jouer_solo(2);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_hard": gameStart.jouer_solo(3);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_insane": gameStart.jouer_solo(4);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	
                	case "button_1vs1": gameStart.jouer_multi(false);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_2vs2": gameStart.jouer_multi(true);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_online":gameStart.jouer_online();gameStart.setCurrentButton(gameStart.getButtonMulti());break;
                	
                	case "return":gameStart.retour(gameStart.getCurrentButton());gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	
                	case "title_ball_skin":gameStart.setCurrentButton(gameStart.getButtonSkinBall());break;
                	case "title_middle_bar":gameStart.setCurrentButton(gameStart.getMBButtonYesNo());break;
                	case "title_choix_bg":gameStart.setCurrentButton(gameStart.getButtonBackground());break;
                	
                	case "middle_bar_no":gameStart.VisibleMiddleBar(false);gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "middle_bar_yes":gameStart.VisibleMiddleBar(true);gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	
                	case "choix_ball_sun":gameView.setBallSkin("sun_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_ball_green":gameView.setBallSkin("green_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_ball_moon":gameView.setBallSkin("moon_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_ball_jupiter":gameView.setBallSkin("jupiter_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_ball_saturne":gameView.setBallSkin("saturne_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_ball_lila":gameView.setBallSkin("lila_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_ball_earth":gameView.setBallSkin("earth_ball.png");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	
                	case "choix_galaxie":gameStart.setBackground("choix_galaxie");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_earth":gameStart.setBackground("choix_earth");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_trou_noir":gameStart.setBackground("choix_trou_noir");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "choix_earth2":gameStart.setBackground("choix_earth2");gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	
                	
                	case "racket_difficulty":gameStart.print_setting_racket_difficulty();gameStart.setCurrentButton(gameStart.getRDButtonYesNo());break;
                	
                	case "RD_yes" : gameStart.reponseRacketDifficuly(true);gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                	case "RD_no" : gameStart.reponseRacketDifficuly(false);gameStart.setCurrentButton(gameStart.getButtonParametre());break;
                
                	case "finish_button":gameStart.finish();gameStart.setCurrentButton(gameStart.getMenuButton());break;

                	default: break;
                	}
            	}
            	break;
            default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
            break;
        	}
        });
        
        TouchView touchView1 = new TouchView(root,gameStart.getCurseurDroit(),gameStart.getCurseurGauche());
        touchView1.init();
        
        gameScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case Z:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case X:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                case UP:
                    if (!court.getIsBot()){
                        playerB.state = RacketController.State.GOING_UP;
                    }
                    break;
                case DOWN :
                	if (!court.getIsBot()){
                        playerB.state = RacketController.State.GOING_DOWN;
                    }
                    break;
                case I:gameView.pause();touchView1.affiche(gameView.getCurrentButton());break;
                case P:
                	gameView.pause();
                	gameView.menu();
                	break;
                case R:
                	gameView.resume();
                	gameView.replay();
                	break;
                case Q:
                	gameView.quitter();
                	break;
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        gameScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
            	case I:touchView1.close();gameView.resume();break;
                case Z:
                    if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                    break;
                case X:
                    if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (!court.getIsBot()){
                        if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                    }
                    break;
                case DOWN:
                    if (!court.getIsBot()){
                        if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                    }
                    break;    
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        
        primaryStage.setScene(startScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        
    }
    
}
