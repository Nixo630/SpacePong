package gui;

import java.awt.Dimension;
import java.awt.Toolkit;


import javafx.application.Application;
import javafx.scene.Scene;
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

        guiStage.setFullScreenExitHint("");
    	
    	var start = new Pane();
    	start.setId("pane");
    	
    	var startScene = new Scene(start);
    	startScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
    	
        
        var root = new Pane();
        
        var gameScene = new Scene(root);
        gameScene.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
        
        var online = new Pane();
        var onlineScene = new Scene(online);
        onlineScene.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
        
        
        class Player implements RacketController {
            State state = State.IDLE;
            Power power = Power.IDLE;

            @Override
            public State getState() {
                return state;
            }
            @Override
            public void setState(RacketController.State s) {
                state = s;
            }
            @Override
            public void setPower(RacketController.Power p) {
                power = p;
            }
            @Override
            public Power getPower() {
                return power;
            }
        }
        
        var playerA = new Player();
        var playerB = new Player();
        var playerC = new Player();
        var playerD = new Player();
        		
        var playerOnline = new Player();
        
        var court = new Court(playerA, playerB, playerC, playerD, longueur, hauteur);
        var gameView = new GameView(court, root, 1.0,startScene);
        
        LoadView load = new LoadView(start);
        load.init();
        
        var gameStart = new GameStart(start,root,gameScene,gameView,court, online,onlineScene, playerOnline, load, startScene);
        
        Curseur curseur = new Curseur(start,gameStart.getStartButton());
        SettingView settingView = new SettingView(start,gameView);
        settingView.init();
        
        gameStart.setCurseur(curseur);
        
        TouchView touchView = new TouchView(start,curseur);
        touchView.init();
        
        TouchView touchViewOnline = new TouchView(online, curseur);
        touchViewOnline.init();
        
        
             
        startScene.setOnKeyPressed(ev ->{
        	switch (ev.getCode()) {
        	case ESCAPE:primaryStage.setFullScreen(true);break;
        	case Q : System.exit(0);break;
        	case I : touchView.affiche(curseur.getCurrentButton());break;
            case DOWN:
                curseur.IncrementeIndice(curseur.getCurrentButton());
                curseur.bouger_curseur(curseur.getCurrentButton()[curseur.getCurseurIndice()],start);
                break;
            case UP:
            	curseur.DecrementeIndice(curseur.getCurrentButton());
            	curseur.bouger_curseur(curseur.getCurrentButton()[curseur.getCurseurIndice()],start);
                break;
            case ENTER:
            	if(curseur.getCurrentButton().length!=0 && !touchView.estAffiche()) {
                	switch (curseur.getCurrentButton()[curseur.getCurseurIndice()].getId()) {
                	
                	case "start_button" : gameStart.start(curseur);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	case "solo_play_button": gameStart.chose_difficulty();curseur.setCurrentButton(gameStart.getButtonDifficulty());break; 
                	case "multiplay_play_button": gameStart.choose_multiplay();curseur.setCurrentButton(gameStart.getButtonMulti());break;
                	case "settings_button": settingView.affiche(curseur.getCurrentButton());curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "quit_button": System.exit(0);break;
                	
                	case "button_easy": gameStart.jouer_solo(1);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_medium": gameStart.jouer_solo(2);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_hard": gameStart.jouer_solo(3);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_insane": gameStart.jouer_solo(4);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	
                	case "button_1vs1": gameStart.afficheFun();curseur.setCurrentButton(gameStart.getButtonFun());break;
                	case "fun":gameStart.choisir_fun(true);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	case "normal": gameStart.choisir_fun(false);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	
                	case "button_2vs2": gameStart.jouer_multi(true);curseur.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_online":gameStart.jouer_online();curseur.setCurrentButton(gameStart.getButtonOnline());break;
                	
                	
                	case "ip":gameStart.choisirIp();curseur.setCurrentButton(gameStart.getButtonIp());break;
                	case "Valideip" :gameStart.valideIp();curseur.setCurrentButton(gameStart.getButtonOnline());break;
                	
                	case "pseudo":gameStart.choisirPseudo();curseur.setCurrentButton(gameStart.getButtonPseudo());break;
                	case "Validepseudo" :gameStart.validePseudo();curseur.setCurrentButton(gameStart.getButtonOnline());break;
                	case "ValiderOnline":gameStart.runOnline();break;
                	
                	
                	case "return":load.close();gameStart.retour(curseur.getCurrentButton());curseur.setCurrentButton(gameStart.getMenuButton());break;
                	
                	case "title_ball_skin":curseur.setCurrentButton(settingView.getButtonSkinBall());break;
                	case "title_middle_bar":curseur.setCurrentButton(settingView.getMBButtonYesNo());break;
                	case "title_choix_bg":curseur.setCurrentButton(settingView.getButtonBackground());break;
                	
                	case "middle_bar_no":gameStart.VisibleMiddleBar(false);curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "middle_bar_yes":gameStart.VisibleMiddleBar(true);curseur.setCurrentButton(settingView.getButtonParametre());break;
                	
                	case "choix_ball_sun":gameStart.setBallSkin("sun_ball.png"); gameView.setBallSkin("sun_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_ball_green":gameStart.setBallSkin("green_ball.png"); gameView.setBallSkin("green_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_ball_moon":gameStart.setBallSkin("moon_ball.png"); gameView.setBallSkin("moon_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_ball_jupiter":gameStart.setBallSkin("jupiter_ball.png"); gameView.setBallSkin("jupiter_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_ball_saturne":gameStart.setBallSkin("saturne_ball.png"); gameView.setBallSkin("saturne_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_ball_lila":gameStart.setBallSkin("lila_ball.png"); gameView.setBallSkin("lila_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_ball_earth":gameStart.setBallSkin("earth_ball.png"); gameView.setBallSkin("earth_ball.png");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	
                	case "choix_galaxie":gameStart.setBackground("choix_galaxie");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_earth":gameStart.setBackground("choix_earth");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_trou_noir":gameStart.setBackground("choix_trou_noir");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "choix_earth2":gameStart.setBackground("choix_earth2");curseur.setCurrentButton(settingView.getButtonParametre());break;
                	
                	
                	case "racket_difficulty":settingView.print_setting_racket_difficulty();curseur.setCurrentButton(settingView.getRDButtonYesNo());break;
                	
                	case "RD_yes" : settingView.reponseRacketDifficuly(true);curseur.setCurrentButton(settingView.getButtonParametre());break;
                	case "RD_no" : settingView.reponseRacketDifficuly(false);curseur.setCurrentButton(settingView.getButtonParametre());break;
                	
                	case "points_background" : settingView.afficherInput();curseur.setCurrentButton(settingView.getButtonInput());break;
                	case "pointsFinaux" : settingView.choisirPoint();curseur.setCurrentButton(settingView.getButtonParametre());break;
                
                	case "finish_button":curseur.setCurrentButton(settingView.close());break;

                	default: break;
                	}
            	}
            	break;
            default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
            break;
        	}
        });
        
        startScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {  
                case I : curseur.setCurrentButton(touchView.close());break;
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        
        TouchView touchView1 = new TouchView(root,curseur);
        touchView1.init();
        
        onlineScene.setOnKeyPressed(ev -> {
        	switch (ev.getCode()) {
            case Z:
                playerOnline.state = RacketController.State.GOING_UP;
                break;
            case X:
            	playerOnline.state = RacketController.State.GOING_DOWN;
                break;
			default:
				break;
        	}
        });
        
        onlineScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                case Z:
                    if (playerOnline.state == RacketController.State.GOING_UP) playerOnline.state = RacketController.State.IDLE;
                    break;
                case X:
                    if (playerOnline.state == RacketController.State.GOING_DOWN) playerOnline.state = RacketController.State.IDLE;
                    break;
			default:
				break;
            }
        });
        
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
                case I:gameView.pause();gameView.setVisiblePause(false);touchView1.affiche(curseur.getCurrentButton());break;
                case ESCAPE:primaryStage.setFullScreen(true);break;
                case P:if(!touchView1.estAffiche()) {
		                	gameView.pause();
		                	gameView.menu();
		                	break;
                }
                case R:
                	if(!touchView1.estAffiche()) {
	                	gameView.resume();
	                	gameView.replay();
	                	break;
                	}
                case Q:
                	if(!touchView1.estAffiche()){
                		gameView.quitter();
                    	break;
                	}
                case D:
                    if (court.nowTimerA == 0 && court.getPlayerA().getPower() != RacketController.Power.IDLE) {
                        court.useCurrentPowerA();//pour ne pas permettre à l'utilisateur de cliquer plusieurs fois pour avoir le meme pouvoir quand
                        court.powerUsedA = true;
                    }//il en a qui est actif
                case K:
                    if (court.nowTimerB == 0 && court.getPlayerB().getPower() != RacketController.Power.IDLE) {
                        court.useCurrentPowerB();
                        court.powerUsedB = true;
                    }
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        gameScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
            	case I:touchView1.close();gameView.setVisiblePause(true);break;
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
