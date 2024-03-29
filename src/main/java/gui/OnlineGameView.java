package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.OnlineCourt;

public class OnlineGameView {

	// class parameters
    private final OnlineCourt court;
    private final Pane gameRoot; // main node of the game
    private final double xMargin, racketThickness,
    		murThickness; // pixels

    // children of the game main node
    private final Rectangle racketA, racketB, murA, murB, murC, murD, murE;
    private final Circle ball;
    
    private Label affScoreA, affScoreB, pseudoLbl, pseudoAdvLbl;   
    private static AnimationTimer aTimer;
	
	//private Scene startScene;
    
    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */

    public OnlineGameView(OnlineCourt court, Pane root, Scene startScene) {
        this.court = court;
        this.gameRoot = root;
        
		this.xMargin = 50 * court.scaleX;
		this.racketThickness = 10 * court.scaleX;
		this.murThickness = 20.0 * court.scale;
		
        root.setMinWidth(court.getWidth() + 2 * xMargin);
        root.setMinHeight(court.getHeight());
                
        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize());
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.DARKGREY);

        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacketA());

        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize());
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.DARKGREY);

        racketB.setX(court.getWidth() - (xMargin));
        racketB.setY(court.getRacketB());

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        
        ball.setCenterX(court.getBallX());
        ball.setCenterY(court.getBallY());
        
        setBallSkin("earth_ball.png");
        
        murA = new Rectangle();//mur du haut
        murA.setWidth(court.getWidth() + 2 * xMargin);
        murA.setHeight(murThickness);
        murA.setFill(Color.BLACK);
        murA.setX(0);
        murA.setY(0);
        
        murB = new Rectangle();//mur du bas
        murB.setWidth(court.getWidth() + 2 * xMargin);
        murB.setHeight(murThickness);
        murB.setFill(Color.BLACK);
        murB.setX(0);
        murB.setY(court.getHeight() - murThickness);
        
        murC = new Rectangle();//mur de gauche
        murC.setWidth(murThickness);
        murC.setHeight(court.getHeight());
        murC.setFill(Color.BLACK);
        murC.setX(0);
        murC.setY(0);
        
        murD = new Rectangle();//mur de droite
        murD.setWidth(murThickness);
        murD.setHeight(court.getHeight() + murThickness);
        murD.setFill(Color.BLACK);
        murD.setX(court.getWidth() - murThickness);
        murD.setY(0);
        
        murE = new Rectangle();//mur du milieu
        murE.setWidth(murThickness);
        murE.setHeight(court.getHeight() + murThickness);
        murE.setFill(Color.BLACK);
        murE.setX(court.getBallX() - (court.getBallRadius() / 2));
        murE.setY(0);        
        
        affScoreA = new Label(""+court.getScoreA());
        affScoreA.setFont(Font.font("Cambria",250));
        affScoreA.setTextFill(Color.DARKGREY);
        affScoreA.setTranslateX((court.getBallX() + xMargin)/2);
        
        affScoreB = new Label(""+court.getScoreB());
        affScoreB.setFont(Font.font("Cambria",250));
        affScoreB.setTextFill(Color.DARKGREY);
        affScoreB.setTranslateX((court.getBallX() + xMargin)*1.25);
        
        pseudoLbl = new Label(court.getPseudo());
        pseudoLbl.setFont(Font.font("Cambria",25));
        pseudoLbl.setTextFill(Color.MAGENTA);
        pseudoLbl.setTranslateX((court.width / 6));
        pseudoLbl.setTranslateY(court.getHeight() - 100);
        
        pseudoAdvLbl = new Label("");
        pseudoAdvLbl.setFont(Font.font("Cambria",25));
        pseudoAdvLbl.setTextFill(Color.MAGENTA);
        pseudoAdvLbl.setTranslateX(court.width - (court.width / 3));
        pseudoAdvLbl.setTranslateY(court.getHeight() - 100);
        
        gameRoot.getChildren().addAll(racketA, racketB, murA, murB, murC, murD,murE, affScoreA, affScoreB, ball, pseudoLbl, pseudoAdvLbl);
        //this.startScene = startScene;
    }
    
    public void Visible_middle_bar(boolean b) {
    	murE.setVisible(b);
    }

	public OnlineCourt getCourt() {
		return court;
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
                racketA.setY(court.getRacketA());
                racketB.setY(court.getRacketB());

                ball.setCenterX(court.getBallX());
                ball.setCenterY(court.getBallY());
                
                if (court.pseudoAdvDef()) {
                	pseudoAdvLbl.setText(court.getPseudoAdv());
                }
                                     
                
                if(court.getScored()) {             
                	affScoreA.setText(""+court.getScoreA());
                    affScoreB.setText(""+court.getScoreB());
                                    	
                	racketA.setHeight(court.getRacketSize());
                	racketB.setHeight(court.getRacketSize());
                	
                	court.resetScored();
                }
			}
    		
    	};
    	aTimer.start();
    }
    
    public static void stopAnimation() {
    	aTimer.stop();
    }
    
    public void startAnimation() {
    	animate();
    }
    
    public void setBallSkin(String s) {
    	String t = "Ball_skin/"+s;
    	Image i = new Image(getClass().getResourceAsStream(t));
        ball.setFill(new ImagePattern(i));
    }
	
}