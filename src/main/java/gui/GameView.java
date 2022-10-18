package gui;

import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private final Rectangle racketA, racketB, murA, murB, murC, murD, murE, background;
    private final Circle ball;
    
    private Label affScoreA, affScoreB;
    
    private static AnimationTimer aTimer;

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;

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

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);
        
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
        
        background = new Rectangle();
        background.setWidth(court.getWidth() * scale + 2 * xMargin);
        background.setHeight(court.getHeight() * scale);
        background.setFill(Color.DODGERBLUE);
        background.setX(0);
        background.setY(0);
        
        affScoreA = new Label(""+court.getScoreA());
        affScoreA.setFont(Font.font("Cambria",500));
        affScoreA.setTextFill(Color.DARKGREY);
        affScoreA.setTranslateX((court.getBallX() * scale + xMargin)/4);
        
        affScoreB = new Label(""+court.getScoreB());
        affScoreB.setFont(Font.font("Cambria",500));
        affScoreB.setTextFill(Color.DARKGREY);
        affScoreB.setTranslateX((court.getBallX() * scale + xMargin)*1.25);
        
        gameRoot.getChildren().addAll(background,racketA, racketB, murA, murB, murC, murD, murE, affScoreA, affScoreB, ball);
            
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
                                                
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacketA() * scale);
                racketB.setY(court.getRacketB() * scale);
                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
                affScoreA.setText(""+court.getScoreA());
                affScoreB.setText(""+court.getScoreB());
			}
    		
    	};
    	aTimer.start();
    }
    
    public int getScoreA() {
    	return court.getScoreA();
    }
    
    public int getScoreB() {
    	return court.getScoreB();
    }
    
    
    
    public static void stopAnimation() {
    	aTimer.stop();
    }
    
    public void startAnimation() {
    	animate();
    }
}
