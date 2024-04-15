package FleurNguessan.crossword;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class CrosswordController implements Initializable {

	private Crossword model;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ListView<Clue> listH;

	@FXML
	private ListView<Clue> listV;

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {

		Database database = new Database();
		model = database.extractGrid(MenuController.numeroGrille);

		listH.setItems(model.getHorizontalClues());
		listV.setItems(model.getVerticalClues());

		listH.setOnMouseClicked(event -> {
			CrosswordSquare clueSquare = model.getCell(listH.getSelectionModel().getSelectedItem().getRow(),
					listH.getSelectionModel().getSelectedItem().getColumn());
			clueSquare.requestFocus();
			model.horizontalProperty().set(true);
		});

		listV.setOnMouseClicked(event -> {
			CrosswordSquare clueSquare = model.getCell(listV.getSelectionModel().getSelectedItem().getRow(),
					listV.getSelectionModel().getSelectedItem().getColumn());
			clueSquare.requestFocus();
			model.horizontalProperty().set(false);

		});

		listH.getStyleClass().add("current");

		model.horizontalProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				listH.getStyleClass().add("current");
				listV.getStyleClass().remove("current");
			} else {
				listV.getStyleClass().add("current");
				listH.getStyleClass().remove("current");

			}
		});

		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.setMaxSize(500, 500);

		for (int i = 0; i < model.getHeight(); i++) {
			grid.getRowConstraints().add(new RowConstraints(500 / model.getHeight()));
		}

		for (int j = 0; j < model.getWidth(); j++) {
			grid.getColumnConstraints().add(new ColumnConstraints(500 / model.getWidth()));
		}

		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				CrosswordSquare square = model.getCell(i, j);

				square.focusedProperty().addListener((observable, oldValue, newValue) -> {
					Clue hClue = null;
					Clue vClue = null;

					for (Clue clue : model.getHorizontalClues()) {
						if (square.getRow() == clue.getRow() && square.getColumn() >= clue.getColumn()) {
							hClue = clue;
							break;
						}
					}

					for (Clue clue : model.getVerticalClues()) {
						if (square.getColumn() == clue.getColumn() && square.getRow() >= clue.getRow()) {
							vClue = clue;
							break;
						}
					}

					if (hClue != null) {
						listH.getSelectionModel().select(hClue);
					} else {
						listH.getSelectionModel().clearSelection();
					}

					if (vClue != null) {
						listV.getSelectionModel().select(vClue);
					} else {
						listV.getSelectionModel().clearSelection();
					}

				});

				grid.add(square, j, i);
			}
		}

		anchorPane.getChildren().add(grid);

	}

}
