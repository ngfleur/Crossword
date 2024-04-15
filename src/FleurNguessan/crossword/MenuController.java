package FleurNguessan.crossword;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	public static int numeroGrille;

	@FXML
	private Button bouton;

	@FXML
	private Label numG;

	@FXML
	private ListView<String> list;

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {

		Database database = new Database();
		Map<Integer, String> grilles = database.availableGrids();
		ObservableList<String> listGrilles = FXCollections.observableArrayList(grilles.values());

		list.setItems(listGrilles);
		bouton.setOnAction(event -> bindingButton());

		numG.textProperty().bind(new StringBinding() {
			{
				bind(list.getSelectionModel().selectedIndexProperty());
			}

			@Override
			protected String computeValue() {
				int index = list.getSelectionModel().getSelectedIndex() + 1;
				return index != 0 ? "n° " + index : "";
			}
		});
	}

	public void bindingButton() {
		numeroGrille = list.getSelectionModel().getSelectedIndex() + 1;

		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("crosswordView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/FleurNguessan/crossword/style.css");
			Stage primaryStage = new Stage();

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
}
