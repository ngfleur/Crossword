package FleurNguessan.crossword;

import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CrosswordSquare extends Label {

	public char solution;
	private StringProperty proposition;
	public String horizontal;
	public String vertical;
	private boolean black;
	private Crossword model;
	private int row;
	private int column;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isBlackSquare() {
		return black;
	}

	public void setBlackSquare(boolean black) {
		this.black = black;
		if (black) {
			getStyleClass().add("black");
		} else {
			getStyleClass().remove("black");
		}
	}

	public CrosswordSquare(int row, int column, Crossword model) {
		solution = ' ';
		proposition = new SimpleStringProperty(" ");
		horizontal = "";
		vertical = "";
		this.model = model;
		this.row = row;
		this.column = column;

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), this);
		scaleTransition.setFromX(0);
		scaleTransition.setFromY(0);
		scaleTransition.setToX(1.0);
		scaleTransition.setToY(1.0);

		getStyleClass().add("crossword-square");

		setBlackSquare(true);

		setMaxWidth(Double.MAX_VALUE);
		setMaxHeight(Double.MAX_VALUE);

		textProperty().bind(propositionProperty());

		propositionProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && !newValue.isEmpty() && Character.isLetter(newValue.charAt(0))) {
				scaleTransition.stop();
				scaleTransition.setRate(1.0);
				scaleTransition.playFromStart();
			}
			getStyleClass().remove("correct");

		});

		setOnMouseClicked(event -> {
			if (!isBlackSquare()) {
				requestFocus();
			}
		});

		setOnKeyPressed(event -> {
			if (isFocused()) {
				switch (event.getCode()) {
				case UP:
					changeSquare(row - 1, column);
					model.horizontalProperty().set(false);
					break;

				case DOWN:
					changeSquare(row + 1, column);
					model.horizontalProperty().set(false);
					break;

				case LEFT:
					changeSquare(row, column - 1);
					model.horizontalProperty().set(true);
					break;

				case RIGHT:
					changeSquare(row, column + 1);
					model.horizontalProperty().set(true);
					break;

				case ENTER:
					boolean gagner = true;
					for (int i = 0; i < model.getHeight(); i++) {
						for (int j = 0; j < model.getWidth(); j++) {

							if (!model.isBlackSquare(i, j)) {

								if (model.getSolution(i, j) == model.getProposition(i, j)) {
									if (!model.getCell(i, j).getStyleClass().contains("correct")) {
										model.getCell(i, j).getStyleClass().add("correct");
									}
									gagner &= true;

								} else {
									model.getCell(i, j).getStyleClass().remove("correct");
									gagner &= false;
								}
							}

						}
					}
					if (gagner) {
						model.gameOver();
					}

					break;

				case BACK_SPACE:
					propositionProperty().set(" ");
					getStyleClass().remove("correct");

					if (model.horizontalProperty().get()) {
						changeSquare(row, column - 1);

					} else {
						changeSquare(row - 1, column);
					}
					break;

				default:

					if (!event.getText().isEmpty() && Character.isLetter(event.getText().charAt(0))) {
						propositionProperty().set(event.getText().toUpperCase());
					}

					if (model.horizontalProperty().get()) {
						changeSquare(row, column + 1);
					} else {
						changeSquare(row + 1, column);
					}

					break;
				}
			}
		});

	}

	public void changeSquare(int row, int column) {
		if (model.correctCoords(row, column)) {
			if (!model.isBlackSquare(row, column)) {
				CrosswordSquare square = model.getCell(row, column);
				square.requestFocus();
			}
		}

	}

	public StringProperty propositionProperty() {
		return proposition;
	}

}