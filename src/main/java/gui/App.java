package gui;


import java.awt.Dimension;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Application;
import javafx.scene.Scene;
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
    	guiStage = primaryStage;
    	
    	var start = new Pane();
    	start.setId("pane");
    	
    	var startScene = new Scene(start);
    	startScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
    	

        var root = new Pane();
        
        var gameScene = new Scene(root);
        gameScene.getStylesheets().addAll(this.getClass().getResource("style_setting.css").toExternalForm());
     
        
        var lost = new Pane();
        lost.setId("pane");
        var lostScene = new Scene(lost);
        lostScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
        class Player implements RacketController {
            State state = State.IDLE;

            @Override
            public State getState() {
                return state;
            }
        }
        var playerA = new Player();
        var playerB = new Player();
        
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double height = (int)dimension.getHeight();
		double width  = (int)dimension.getWidth();
        
        var court = new Court(playerA, playerB, width, height);
        var gameView = new GameView(court, root, 1.0,startScene);
        var gameStart = new GameStart(start,root,gameScene,gameView);
        
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
                case P:
                	gameView.pause();
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
                default: // Ajout d'un cas default pour éviter les warnings et être exhaustif
                	break;
            }
        });
        
        
               
       
        
        /*
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
        */
        
        primaryStage.setScene(startScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        
    }
    
}
