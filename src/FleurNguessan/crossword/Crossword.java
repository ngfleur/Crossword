package FleurNguessan.crossword;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Crossword extends Grid<CrosswordSquare> {

	private ObservableList<Clue> verticalClues;
	private ObservableList<Clue> horizontalClues;

	private final BooleanProperty horizontal = new SimpleBooleanProperty(true);

	public BooleanProperty horizontalProperty() {
		return horizontal;
	}

	public Crossword(int height, int width) {

		super(height, width);
		verticalClues = FXCollections.observableArrayList();
		horizontalClues = FXCollections.observableArrayList();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				CrosswordSquare square = new CrosswordSquare(row, column, this);
				setCell(row, column, square);
			}
		}

	}

	public StringProperty propositionProperty(int row, int column) {
		return getCell(row, column).propositionProperty();
	}

	public boolean isBlackSquare(int row, int column) {
		if (correctCoords(row, column)) {
			return getCell(row, column).isBlackSquare();
		} else {
			throw new RuntimeException();
		}
	}

	public void setBlackSquare(int row, int column, boolean black) {

		if (correctCoords(row, column)) {
			getCell(row, column).setBlackSquare(black);

		} else {
			throw new RuntimeException();
		}
	}

	public char getSolution(int row, int column) {

		if (correctCoords(row, column) && !isBlackSquare(row, column)) {
			return getCell(row, column).solution;
		} else {
			throw new RuntimeException();
		}
	}

	public void setSolution(int row, int column, char solution) {
		if (correctCoords(row, column) && !isBlackSquare(row, column)) {
			getCell(row, column).solution = solution;
		} else {
			throw new RuntimeException();
		}
	}

	public char getProposition(int row, int column) {
		if (correctCoords(row, column) && !isBlackSquare(row, column)) {
			return getCell(row, column).propositionProperty().get().charAt(0);
		} else {
			throw new RuntimeException();
		}
	}

	public void setProposition(int row, int column, char prop) {
		if (correctCoords(row, column) && !isBlackSquare(row, column)) {

			getCell(row, column).propositionProperty().set(String.valueOf(prop));

		} else {
			throw new RuntimeException();
		}
	}

	public String getDefinition(int row, int column, boolean horizontal) {
		if (correctCoords(row, column) && !isBlackSquare(row, column)) {
			if (horizontal) {
				return getCell(row, column).horizontal;
			}
			return getCell(row, column).vertical;
		} else {
			throw new RuntimeException();
		}
	}

	public void setDefinition(int row, int column, boolean horizontal, String definition) {
		if (correctCoords(row, column) && !isBlackSquare(row, column)) {
			if (horizontal) {
				getCell(row, column).horizontal = definition;
				horizontalClues.add(new Clue(definition, row, column, true));
			} else {
				getCell(row, column).vertical = definition;
				verticalClues.add(new Clue(definition, row, column, false));

			}
		} else {
			throw new RuntimeException();
		}

	}

	public ObservableList<Clue> getVerticalClues() {
		return verticalClues;
	}

	public ObservableList<Clue> getHorizontalClues() {
		return horizontalClues;
	}

	public static Crossword createPuzzle(Database database, int puzzleNumber) {
		return database.extractGrid(puzzleNumber);
	}

	public void printProposition() {

	}

	public void printSolution() {

	}

	public void gameOver() {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("gameOverView.fxml"));
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