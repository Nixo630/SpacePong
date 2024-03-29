package gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.awt.Dimension;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.Court;
import model.RacketController;

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
    private final Circle ballPower;
    
    private Label affScoreA, affScoreB;   
    private static AnimationTimer aTimer;
    
    public final Color[] colorList = {Color.BLACK, Color.BLUE,
    		Color.BROWN, Color.CYAN, Color.GOLD, Color.GREEN, Color.LIME,
    		Color.MAGENTA, Color.ORANGE, Color.PURPLE, Color.RED, Color.WHITE,
    		Color.YELLOW}; // liste de couleurs possibles pour la balle
    
    
    private boolean changement_taille_racket;
    private final Scene start;
    
    //Boutton lorsque le jeu est en Pause
    private boolean enPause = false;
    private Button quit ;
    private Button resume;
    
    //Boolean pour savoir si on est en 2vs2 ou 1vs1
    private boolean multi ;
    
    //Boolean pour savoir si on est dans le mode fun ou pas
    private boolean isFun = false;
    private int lastPowerSpend = 0;//pour savoir quand l'heure à laquelle la dernière ballPower a été faites
    public boolean powerUsedA = false;//pour savoir quand nous avons cliqué pour utiliser un des deux pouvoirs des deux joueurs
    public boolean powerUsedB = false;
    
    //Boutton pour les options lors de la fin du jeu
    private boolean endGame=false;
    private Button title_end;
    private Button menu,replay;
    
    //Boolean pour vérifier qu'une partie est en cours


	//Valeur de la taille de l'écran
	Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	double height = dimension.getHeight();
	double width  = dimension.getWidth();
    
    private boolean PartiEnCours=false;;
    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    
    private Button[] currentButton= {};

    public GameView(Court court, Pane root, double scale,Scene startScene) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;
        start = startScene;
        
        changement_taille_racket = false;
        quit = new Button();
        resume = new Button();
        
        title_end = new Button();
		replay = new Button();
		menu = new Button();

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
        racketC.setY(xMargin - racketThickness);

        racketC.setVisible(false);

        

        racketD = new Rectangle();
        racketD.setWidth(court.getRacketSize() * scale);
        racketD.setHeight(racketThickness);
        racketD.setFill(Color.DARKGREY);

        racketD.setX(court.getRacketD() * scale);
        racketD.setY(court.getHeight() * scale - xMargin + racketThickness);

        racketD.setVisible(false);

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        
        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);
        
        setBallSkin("earth_ball.png");
        
        ballPower = new Circle();
        ballPower.setRadius(court.getBallPowerRadius());
        ballPower.setFill(Color.BLACK);
        ballPower.setVisible(false);
        
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
        affScoreA.setFont(Font.font("Cambria", height*(23.1482/100)));
        affScoreA.setTextFill(Color.DARKGREY);
        affScoreA.setTranslateX((court.getBallX() * scale + xMargin)/2);
        
        affScoreB = new Label(""+court.getScoreB());
        affScoreB.setFont(Font.font("Cambria",height*(23.1482/100)));
        affScoreB.setTextFill(Color.DARKGREY);
        affScoreB.setTranslateX((court.getBallX() * scale + xMargin)*1.25);
        
        gameRoot.getChildren().addAll(racketA, racketB, racketC, racketD, murA, murB, murC, murD,murE, affScoreA, affScoreB, ball,ballPower);
        
        //Mise en place de l'affichage de la fin de partie
    	
		//Ici c'est le bouton qui affiche que la partie est termine
		
    	
		title_end.setId("title_end");
		title_end.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		title_end.setPrefSize(width*(36.65/100),height*(14.7223/100));
		title_end.setLayoutX(width/2 - title_end.getPrefWidth()/2);
		title_end.setLayoutY(height*(4.63/100));
		
		
		
		//Bouton pour rejouer
		
		replay.setCursor(Cursor.HAND);
		replay.setId("replay_button");
		replay.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		
		replay.setPrefSize(width*(24.7222/100),height*(6.6667/100));
		
		replay.setLayoutX(width/2 - width/2/2 - replay.getPrefWidth()/2);
		replay.setLayoutY(height*(56.4815/100));
		
		
		menu.setId("menu_button");
		menu.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		menu.setPrefSize(width*(24.7222/100),height*(6.6667/100));
		menu.setLayoutX(width/2 + width/2/2 - menu.getPrefWidth()/2);
		menu.setLayoutY(height*(56.4815/100));
		
		menu.setCursor(Cursor.HAND);
		menu.setOnAction(value ->  {
			menu();
	    });
		
		replay.setOnAction(value ->  {
			replay();
	    });
		
		gameRoot.getChildren().addAll(replay,title_end,menu);
		GameStart.visible_change(getButtonEnd(), false);
    }
    
    public void Visible_middle_bar(boolean b) {
    	murE.setVisible(b);
    }

	public Court getCourt() {
		return court;
	}
	
	public void setIsFun(boolean b) {
		isFun = b;
	}
	
	
	public void popBallPower(int time) {
        if (time == this.lastPowerSpend) {
            return;
        }
        else {
            this.lastPowerSpend = time;
        }
        int randomX = (int) (court.getWidth()/4 + (Math.random() * (court.getWidth()*(4/3) - court.getWidth()/4)));//coordonnee en x entre les 3 quarts 2 quarts du terrain
        int randomY = (int) (Math.random() * (court.getHeight()));//coordonnee en y entre 0 et la hauteur de l'ecran
        ballPower.setCenterX(randomX * scale);
        ballPower.setCenterY(randomY * scale);
        ballPower.setVisible(true);
        court.setBallPowerX(randomX * scale);
        court.setBallPowerY(randomY * scale);
        int rd = (int) (Math.random() * 8);
        switch (rd) {
	        case 0 : court.setCurrentPower(RacketController.Power.STRENGHT);setBallPowerSkin("STRENGHT.png");break;
	        case 1 : court.setCurrentPower(RacketController.Power.ELECTRICAL);setBallPowerSkin("ELECTRICAL.png");break;
	        case 2 : court.setCurrentPower(RacketController.Power.WIND);setBallPowerSkin("WIND.png");break;
	        case 3 : court.setCurrentPower(RacketController.Power.INVISIBLE);setBallPowerSkin("INVISIBLE.png");break;
	        case 4 : court.setCurrentPower(RacketController.Power.BIGGER);setBallPowerSkin("BIGGER.png");break;
	        case 5 : court.setCurrentPower(RacketController.Power.SMALLER);setBallPowerSkin("SMALLER.png");break;
	        case 6 : court.setCurrentPower(RacketController.Power.SLOWER);setBallPowerSkin("SLOWER.png");break;
	        case 7 : court.setCurrentPower(RacketController.Power.FASTER);setBallPowerSkin("FASTER.png");break;
	        default : break;
        }
    }
    
	public void animate() {
		currentButton = getButtonPause();
		racketD.setVisible(false);
    	racketC.setVisible(false);
    	aTimer = new AnimationTimer() {
    		long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				
				if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                 
				powerUsedA = court.powerUsedA;
                powerUsedB = court.powerUsedB;

                court.time = now;
                if (now >= court.nowTimerA && powerUsedA && court.getPlayerA().getPower() != RacketController.Power.STRENGHTACTIVATE && court.getPlayerA().getPower() != RacketController.Power.STRENGHTACTIVATED) {//pour verifier quand nous avons
                    court.endPowerA();//dépasser le timer alors on met fin au pouvoir
                    resetStyleRacketA();
                    powerUsedA = false;
                    court.powerUsedA = false;
                }

                if (now >= court.nowTimerB && powerUsedB && court.getPlayerB().getPower() != RacketController.Power.STRENGHTACTIVATE && court.getPlayerB().getPower() != RacketController.Power.STRENGHTACTIVATED) {//exclure STRENGHTACTIVATED du cas car il faut
                    court.endPowerB();//garder ce pouvoir meme si on ne l'utilise pas tout de suite car il n'a aucun timer
                    resetStyleRacketB();
                    powerUsedB = false;
                    court.powerUsedB = false;
                }
                
                
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacketA() * scale);
                racketB.setY(court.getRacketB() * scale);

                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
                
                if (court.getBallRadius() == 0) {
                    ball.setVisible(false);
                }
                else {
                    ball.setVisible(true);
                }

                if (isFun && Math.round(last*1.0e-9)%7 == 0) {//toutes les 7 secondes
                    popBallPower((int)Math.round(last*1.0e-9));
                }

                racketA.setHeight(court.getRacketSizeA() * scale);//on actualise constamment les tailles des raquettes
                racketB.setHeight(court.getRacketSizeB() * scale);//au cas ou un pouvoir doit etre applique

                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
                
                if (isFun && court.getBallPowerX() == -1 && court.getBallPowerY() == -1 && court.getCurrentPower() == null) {
                    ballPower.setVisible(false);//si la balle vient de toucher la ballPower alors elle disparait
                }
                 
                if(court.getBallTouched() && changement_taille_racket) { // la balle touche la raquette
                	
                	Random rd = new Random();
                	
                	// Changement de tailles de raquettes
                	int h = rd.nextInt(50, 201);
                	court.setRacketSize(h);
                	racketA.setHeight(court.getRacketSize() * scale);
                	racketB.setHeight(court.getRacketSize() * scale);
            		        	
                	court.resetBallTouched();
                }
                             
                if(court.scored()) {             
                	affScoreA.setText(""+court.getScoreA());
                    affScoreB.setText(""+court.getScoreB());
                    
                	
                	court.setRacketSize(150);
                	racketA.setHeight(court.getRacketSize() * scale);
                	racketB.setHeight(court.getRacketSize() * scale);
                	court.resetScored();
                }
                if(court.getLost()) {   	
                	lost_game();
                }
			}
    		
    	};
    	aTimer.start();
    }

    public void animate2() {
    	currentButton = getButtonPause();
     	racketD.setVisible(true);
    	racketC.setVisible(true);
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
                court.update2((now - last) * 1.0e-9); // convert nanoseconds to seconds
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
                    
                	
                	court.setRacketSize(150);
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
    	affScoreA.setLayoutY(height*(2.315/100));
		affScoreB.setLayoutY(height*(2.315/100));
    	affScoreA.setText("0");
    	affScoreB.setText("0");
    	court.reset();
    	court.reset_score();
    }
    
    public static void stopAnimation() {
    	aTimer.stop();
    }
    
    public void startAnimation() {
    	PartiEnCours = true;
    	multi = false;
    	animate();
    }

    public void startAnimation2() {
    	PartiEnCours = true;
    	multi = true;
    	animate2();
    }

    
    public boolean getEnPause() {
    	return enPause;
    }
    
    public void setEnPause(boolean b) {
    	enPause = b;
    }

    
    public void pause() {
    	if (PartiEnCours) {
    		currentButton = getButtonPause();
    		enPause = true;
    		PartiEnCours=false;
    		court.setPartiEnCours(false);
	    	stopAnimation();
	    	
			quit.setId("quit_button");
			quit.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			
			
			quit.setPrefSize(width*(24.7222/100), height*(6.6667/100));
			quit.setLayoutX(court.getWidth()/2 - quit.getPrefWidth()/2);
			quit.setLayoutY(height*(60.1852/100));
			
			
			resume.setId("resume_button");
			resume.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			
			
			resume.setPrefSize(width*(17.3/100),height*(3.4/100));
			resume.setLayoutX(court.getWidth()/2 - resume.getPrefWidth()/2);
			resume.setLayoutY(height*(41.6667/100));
			
			resume.setCursor(Cursor.HAND);
			resume.setOnAction(value ->  {
				resume();
		    });
			
			quit.setCursor(Cursor.HAND);
			quit.setOnAction(value ->  {
				quitter();
		    });
			
			gameRoot.getChildren().addAll(quit,resume);
    	}
    } 
    
    public void resume() {
    	if(enPause) {
    		currentButton = new Button[0];
    		enPause = false;
        	court.setPartiEnCours(true);
        	if(multi) {
        		startAnimation2();
        	}
        	else{
        		startAnimation();
        	}
    		gameRoot.getChildren().removeAll(quit,resume);	
    	}
    }
    
    public void quitter() {
    	if(enPause) {
    		PartiEnCours=false;
    		court.setPartiEnCours(false);
	    	enPause = false;
	    	endGame=true;
			gameRoot.getChildren().removeAll(quit,resume);	
			lost_game();
    	}
    }
    
    public void lost_game() {
    	currentButton = getButtonEnd();
    	PartiEnCours=false;
		court.setPartiEnCours(false);
    	enPause = false;
    	endGame=true;
    	stopAnimation();
    	racketA.setVisible(false);
    	racketB.setVisible(false);
    	racketC.setVisible(false);
    	racketD.setVisible(false);
    	ball.setVisible(false);
    	
    	//Affichage des score
		
    	affScoreA.setLayoutY(height*(18.5186/100));
   		affScoreB.setLayoutY(height*(18.5186/100));
   		
		GameStart.visible_change(getButtonEnd(), true);
    }
    
    public void replay() {
    	if(endGame) {
    		GameStart.visible_change(getButtonEnd(), false);
    		currentButton = new Button[0];
    		endGame=false;
    		reset();
    		stopAnimation();
			court.setPartiEnCours(true);
			menu.setVisible(false);
			replay.setVisible(false);
			title_end.setVisible(false);
			court.setLost(false);
			racketA.setVisible(true);
	    	racketB.setVisible(true);
	    	racketC.setVisible(true);
	    	racketD.setVisible(true);
	    	ball.setVisible(true);
	    	
			if (multi){
	            ball.setVisible(true);
	            startAnimation2();
	        }
	        else {
	            ball.setVisible(true);
	            startAnimation();    
	        }
    	}
    }
    
    public void setmulti(boolean x) { 
        multi = x;
    }
    
    public void menu() {
    	if(endGame) {
    		GameStart.visible_change(getButtonEnd(), false);
    		reset();
			endGame=false;
			menu.setVisible(false);
			replay.setVisible(false);
			title_end.setVisible(false);
			court.setLost(false);
			racketA.setVisible(true);
	    	racketB.setVisible(true);
	    	racketC.setVisible(false);
	    	racketD.setVisible(false);
	    	ball.setVisible(true);
	    	ballPower.setVisible(false);
	    	isFun = false;
	    	court.setIsFun(false);
			App.getStage().setScene(start);
			App.getStage().setFullScreen(true);
    	}
    }
    
    public void setChangeRacketSize(boolean b) {
    	changement_taille_racket = b;
    }
    
    public void setBallSkin(String s) {
    	String t = "Ball_skin/"+s;
    	Image i = new Image(getClass().getResourceAsStream(t));
        ball.setFill(new ImagePattern(i));
    }
    
    public void setBallPowerSkin(String s) {
    	String t = "BallPower_skin/"+s;
    	Image i = new Image(getClass().getResourceAsStream(t));
        ballPower.setFill(new ImagePattern(i));
    }
    
    public Button[] getButtonPause() {
    	Button[] tab = {resume,quit};
    	return tab;
    }
    
    public Button[] getButtonEnd() {
    	Button[] tab = {title_end,replay,menu};
    	return tab;
    }
    
    public Button[] getCurrentButton() {
    	return currentButton;
    }
    
    public void setVisiblePause(boolean b) {
    	resume.setVisible(b);
    	quit.setVisible(b);
    }
    
    public void setStyleRacketA() {
    	racketA.setFill(Color.RED);
    	
    }
    
    public void setStyleRacketB() {
    	racketB.setFill(Color.RED);
    }
    
    public void resetStyleRacketA() {
    	racketA.setFill(Color.DARKGREY);
    }
    
    public void resetStyleRacketB() {
    	racketB.setFill(Color.DARKGREY);
    }
    
    
    //Cette focntion affiche un message lorsqu'une persone essaie d'activer un pouvoir alors qu'il en a pas
    //Il prend en paramètre les coordonnées où il doit s'afficher
    
    public void messageErreur(int x,int y) {
    	
    	Label error = new Label("Aucun pouvoir disponible");
		error.setLayoutX(x);
		error.setLayoutY(y);
		error.setFont(new Font("Serif", 20));
		error.setTextFill(Color.web("#FF0000"));
		error.setMinHeight(10);
		error.setMinWidth(30);
		gameRoot.getChildren().add(error);
    	
		Timer chrono = new Timer();
		chrono.schedule(new TimerTask() {

			int time = 50;
			@Override
			public void run() {
				
				if (time ==0) {
					error.setVisible(false);
		        	chrono.cancel();
				}
				time--;
			}
			
		}, 100,15);
		
	}
	
}
