package FleurNguessan.crossword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("menuView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/FleurNguessan/crossword/style.css");

			scene.setOnKeyPressed(event -> {
				if (event.isControlDown() && event.getCode() == KeyCode.W) {
					primaryStage.close();
				}
			});

			primaryStage.setTitle("Crossword puzzle");
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
