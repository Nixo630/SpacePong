package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameLost {

	public GameLost (Pane lostRoot, double scale, double width, double height,
			Scene courtScene, GameView gw) {
				
		lostRoot.setMinWidth(width * scale);
		lostRoot.setMinHeight(height * scale);
		
		Text t = new Text(0, (height * scale) / 4, "Vous avez perdu...\nVoulez-vous rejouer ?");
		t.setFont(new Font(50));
		t.setLayoutX((width * scale) / 2 - t.getLayoutBounds().getCenterX());
		t.setTextAlignment(TextAlignment.CENTER);
		
		Button replay = new Button("Rejouer");
		replay.setDefaultButton(true);
		replay.setMinWidth(width / 2);
		replay.setMinHeight(height / 2);
		replay.setLayoutX(0);
		replay.setLayoutY(height / 2);
		replay.setOnAction(value ->  {
			App.getStage().setScene(courtScene);
			gw.startAnimation();
	    });
		
		Button quit = new Button("Quitter");
		quit.setCancelButton(true);
		quit.setMinWidth(width / 2);
		quit.setMinHeight(height / 2);
		quit.setLayoutX(width / 2);
		quit.setLayoutY(height / 2);
		quit.setOnAction(value ->  {
	           System.exit(0);
	    });
		
		lostRoot.getChildren().addAll(t, replay, quit);
	}
	
}
