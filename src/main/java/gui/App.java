package gui;


import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
    	guiStage = primaryStage;
    	
        var root = new Pane();
        var gameScene = new Scene(root);
        
        var lost = new Pane();
        var lostScene = new Scene(lost);
        
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
        gameScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case Z:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case X:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                    
                case UP:
                    playerB.state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    playerB.state = RacketController.State.GOING_DOWN;
                    break;

                case O :
                    playerC.state = RacketController.State.GOING_RIGHT;
                    break;
                case U :
                    playerC.state = RacketController.State.GOING_LEFT;
                    break;

                case L :
                    playerD.state = RacketController.State.GOING_RIGHT;
                    break;
                case J :
                    playerD.state = RacketController.State.GOING_LEFT;
                    break;    

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
                    if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                    break;

                case O:
                    if (playerC.state == RacketController.State.GOING_RIGHT) playerC.state = RacketController.State.IDLE;
                    break;
                case U:
                    if (playerC.state == RacketController.State.GOING_LEFT) playerC.state = RacketController.State.IDLE;
                    break;

                case L:
                    if (playerD.state == RacketController.State.GOING_RIGHT) playerD.state = RacketController.State.IDLE;
                    break;
                case J:
                    if (playerD.state == RacketController.State.GOING_LEFT) playerD.state = RacketController.State.IDLE;
                    break;

                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
               
        var court = new Court(playerA, playerB,playerC,playerD, 1000, 600, lostScene);
        var gameView = new GameView(court, root, 1.0);
        var gameLost = new GameLost(lost, 1.0, 1000, 600, gameScene, gameView);
        
        try
        {
    		Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/main/resources/starting.wav")));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
        
        primaryStage.setScene(gameScene);
        primaryStage.show();
        
        gameView.animate();
    }
    
}
