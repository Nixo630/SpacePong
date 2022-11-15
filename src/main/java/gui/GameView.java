package gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.awt.Dimension;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.Court;

public class GameView {
    // class parameters
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 10.0,
    		murThickness = 10.0; // pixels

    // children of the game main node
    private final Rectangle racketA, racketB, racketC, racketD, murA, murB, murC, murD, murE;
    private final Circle ball;
    
    private Label affScoreA, affScoreB;   
    private static AnimationTimer aTimer;
    
    public final Color[] colorList = {Color.BLACK, Color.BLUE,
    		Color.BROWN, Color.CYAN, Color.GOLD, Color.GREEN, Color.LIME,
    		Color.MAGENTA, Color.ORANGE, Color.PURPLE, Color.RED, Color.WHITE,
    		Color.YELLOW}; // liste de couleurs possibles pour la balle
    
    
    private boolean changement_taille_racket;
    private final Scene start;
    
    private boolean enPause = false;
    private Button quit ;
    private Button resume;
    

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale,Scene startScene) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;
        start = startScene;
        
        changement_taille_racket = false;
        quit = new Button();
        resume = new Button();

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.DARKGREY);

        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacketA() * scale);

        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.DARKGREY);

        racketB.setX(court.getWidth() * scale + xMargin);
        racketB.setY(court.getRacketB() * scale);

        racketC = new Rectangle();
        racketC.setWidth(court.getRacketSize() * scale);
        racketC.setHeight(racketThickness);
        racketC.setFill(Color.DARKGREY);

        racketC.setX(court.getRacketC() * scale);
        racketC.setY(xMargin + racketThickness);

        racketD = new Rectangle();
        racketD.setWidth(court.getRacketSize() * scale);
        racketD.setHeight(racketThickness);
        racketD.setFill(Color.DARKGREY);

        racketD.setX(court.getRacketD() * scale);
        racketD.setY(court.getHeight() * scale - xMargin);

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.getStyleClass().addAll(this.getClass().getResource("style_ball.css").toExternalForm());
        
        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);
        
        setBallSkin("saturne_ball.png");
        
        murA = new Rectangle();//mur du haut
        murA.setWidth(court.getWidth() * scale + 2 * xMargin);
        murA.setHeight(murThickness);
        murA.setFill(Color.BLACK);
        murA.setX(0);
        murA.setY(0);
        
        murB = new Rectangle();//mur du bas
        murB.setWidth(court.getWidth() * scale + 2 * xMargin);
        murB.setHeight(murThickness);
        murB.setFill(Color.BLACK);
        murB.setX(0);
        murB.setY(court.getHeight());
        
        murC = new Rectangle();//mur de gauche
        murC.setWidth(murThickness);
        murC.setHeight(court.getHeight() * scale);
        murC.setFill(Color.BLACK);
        murC.setX(0);
        murC.setY(0);
        
        murD = new Rectangle();//mur de droite
        murD.setWidth(murThickness);
        murD.setHeight(court.getHeight() * scale + murThickness);
        murD.setFill(Color.BLACK);
        murD.setX(court.getWidth() * scale + 2 * xMargin);
        murD.setY(0);
        
        murE = new Rectangle();//mur du milieu
        murE.setWidth(murThickness);
        murE.setHeight(court.getHeight() * scale + murThickness);
        murE.setFill(Color.BLACK);
        murE.setX(court.getBallX() * scale + xMargin-(court.getBallRadius()/2));
        murE.setY(0);
        
        
        affScoreA = new Label(""+court.getScoreA());
        affScoreA.setFont(Font.font("Cambria",250));
        affScoreA.setTextFill(Color.DARKGREY);
        affScoreA.setTranslateX((court.getBallX() * scale + xMargin)/2);
        
        affScoreB = new Label(""+court.getScoreB());
        affScoreB.setFont(Font.font("Cambria",250));
        affScoreB.setTextFill(Color.DARKGREY);
        affScoreB.setTranslateX((court.getBallX() * scale + xMargin)*1.25);
        
        gameRoot.getChildren().addAll(racketA, racketB, racketC, racketD, murA, murB, murC, murD,murE, affScoreA, affScoreB, ball);
    }
    
    public void Visible_middle_bar(boolean b) {
    	murE.setVisible(b);
    }
    
    public void animate() {
    	aTimer = new AnimationTimer() {
    		long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				
				if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                System.out.println (court.getRacketC());                                
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacketA() * scale);
                racketB.setY(court.getRacketB() * scale);
                racketC.setX(court.getRacketC() * scale * 2);
                racketD.setX(court.getRacketD() * scale * 2);

                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
                 
                if(court.getBallTouched() && changement_taille_racket) { // la balle touche la raquette
                	
                	Random rd = new Random();
                	
                	// Changement de tailles de raquettes
                	int h = rd.nextInt(50, 201);
                	court.setRacketSize(h);
                	racketA.setHeight(court.getRacketSize() * scale);
                	racketB.setHeight(court.getRacketSize() * scale);
                	racketC.setHeight(racketThickness);
                	racketD.setHeight(racketThickness);
            		        	
                	court.resetBallTouched();
                }
                             
                if(court.scored()) {             
                	affScoreA.setText(""+court.getScoreA());
                    affScoreB.setText(""+court.getScoreB());
                    
                	
                	court.setRacketSize(100);
                	racketA.setHeight(court.getRacketSize() * scale);
                	racketB.setHeight(court.getRacketSize() * scale);
                	racketC.setHeight(racketThickness);
                	racketD.setHeight(racketThickness);
                	court.resetScored();
                }
                if(court.getLost()) {   	
                	lost_game();
                }
			}
    		
    	};
    	aTimer.start();
    }
    
    public void reset() {
    	affScoreA.setLayoutY(25);
		affScoreB.setLayoutY(25);
    	affScoreA.setText("0");
    	affScoreB.setText("0");
    	court.reset();
    	court.reset_score();
    }
    
    public static void stopAnimation() {
    	aTimer.stop();
    }
    
    public void startAnimation() {
    	animate();
    }
    
    public boolean getEnPause() {
    	return enPause;
    }
    
    public void setEnPause(boolean b) {
    	enPause = b;
    }
    
    public void pause() {
    	enPause = true;
    	if (court.getPartiEnCours()) {
    		court.setPartiEnCours(false);
	    	stopAnimation();
	    	
			quit.setCancelButton(true);
			quit.setId("quit_button");
			quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			
			
			quit.setPrefSize(1238/4,461/4);
			quit.setLayoutX(court.getWidth()/2 - quit.getPrefWidth()/2);
			quit.setLayoutY(650);
			
			
			resume.setId("resume_button");
			resume.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			
			
			resume.setPrefSize(1329/4,138/4);
			resume.setLayoutX(court.getWidth()/2 - quit.getPrefWidth()/2);
			resume.setLayoutY(450);
			
			resume.setCursor(Cursor.HAND);
			resume.setOnAction(value ->  {
				resume();
		    });
			
			quit.setCursor(Cursor.HAND);
			quit.setOnAction(value ->  {
				enPause = false;
				reset();
				gameRoot.getChildren().removeAll(quit,resume);	
				court.setPartiEnCours(false);
				lost_game();
				
		    });
			
			gameRoot.getChildren().addAll(quit,resume);
    	}
    } 
    
    public void resume() {
    	enPause = false;
    	court.setPartiEnCours(true);
		startAnimation();
		gameRoot.getChildren().removeAll(quit,resume);	
    }
    
    public void lost_game() {
    	stopAnimation();
    	racketA.setVisible(false);
    	racketB.setVisible(false);
    	racketC.setVisible(false);
    	racketD.setVisible(false);
    	ball.setVisible(false);
    	
    	Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		
		//Mise en place de l'affichage de la fin de partie
	
		//Ici c'est le bouton qui affiche que la partie est termine
		
    	Button title_end = new Button();
		title_end.setId("title_end");
		title_end.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		title_end.setPrefSize(2111/3,477/3);
		title_end.setLayoutX(width/2 - title_end.getPrefWidth()/2);
		title_end.setLayoutY(50);
		
		//Affichage des score
		
		affScoreA.setLayoutY(200);
		affScoreB.setLayoutY(200);
		
		//Bouton pour rejouer
		Button replay = new Button();
		replay.setCursor(Cursor.HAND);
		replay.setId("replay_button");
		replay.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		replay.setPrefSize(1412/3,165/3);
		
		replay.setLayoutX(width/2 - width/2/2 - replay.getPrefWidth()/2);
		replay.setLayoutY(610);
		
		
		
		Button quit = new Button();
		
		quit.setId("menu_button");
		quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		quit.setPrefSize(1003/3,165/3);
		quit.setLayoutX(width/2 + width/2/2 - quit.getPrefWidth()/2);
		quit.setLayoutY(610);
		
		quit.setCursor(Cursor.HAND);
		quit.setOnAction(value ->  {
			reset();
			
			quit.setVisible(false);
			replay.setVisible(false);
			title_end.setVisible(false);
			court.setLost(false);
			racketA.setVisible(true);
	    	racketB.setVisible(true);
	    	racketC.setVisible(true);
	    	racketD.setVisible(true);
	    	ball.setVisible(true);
			App.getStage().setScene(start);
			App.getStage().setFullScreen(true);
	    });
		
		replay.setOnAction(value ->  {
			reset();
			court.setPartiEnCours(true);
			quit.setVisible(false);
			replay.setVisible(false);
			title_end.setVisible(false);
			court.setLost(false);
			racketA.setVisible(true);
	    	racketB.setVisible(true);
	    	racketC.setVisible(true);
	    	racketD.setVisible(true);
	    	ball.setVisible(true);
			startAnimation();
	    });
		
		gameRoot.getChildren().addAll(replay,title_end,quit);
    }
    
    public void setChangeRacketSize(boolean b) {
    	changement_taille_racket = b;
    }
    
    public void setBallSkin(String s) {
    	String t = "Ball_skin/"+s;
    	Image i = new Image(getClass().getResourceAsStream(t));
        ball.setFill(new ImagePattern(i));
    }
}
