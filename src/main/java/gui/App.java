package gui;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
        
 
        
        
        startScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case C:
                    gameStart.IncrementeIndice(gameStart.getCurrentButton());
                    gameStart.bouger_curseur(gameStart.getCurrentButton()[gameStart.getCurseurIndice()]);
                    break;
                case D:
                	gameStart.DecrementeIndice(gameStart.getCurrentButton());
                    gameStart.bouger_curseur(gameStart.getCurrentButton()[gameStart.getCurseurIndice()]);
                    break;
                case M:
                	switch (gameStart.getCurrentButton()[gameStart.getCurseurIndice()].getId()) {
                	case "solo_play_button": gameStart.chose_difficulty();gameStart.setCurrentButton(gameStart.getButtonDifficulty());
                		break; 
                	case "multiplay_play_button": gameStart.choose_multiplay();gameStart.setCurrentButton(gameStart.getButtonMulti());
                		break;
                	case "settings_button": gameStart.parametre();
                		break;
                	case "quit_button": System.exit(0);break;
                	case "button_easy": gameStart.jouer_solo(1);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_medium": gameStart.jouer_solo(2);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_hard": gameStart.jouer_solo(3);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	case "button_insane": gameStart.jouer_solo(4);gameStart.setCurrentButton(gameStart.getMenuButton());break;
                	default: break;
                	}
                	break;
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
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
                case P:
                	gameView.pause();
                	break;
                case R:
                	if(gameView.getEnPause()) gameView.resume(); gameView.setEnPause(false);
                	break;
                case ESCAPE:
                	System.exit(0);
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        gameScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
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
