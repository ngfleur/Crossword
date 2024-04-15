package FleurNguessan.crossword;

public class Clue {

	// Attributs
	private String clue;
	private int row;
	private int column;
	private boolean horizontal;

	// Constructeur
	public Clue(String clue, int row, int column, boolean horizontal) {
		this.clue = clue;
		this.row = row;
		this.column = column;
		this.horizontal = horizontal;
	}

	// Méthodes
	public String getClue() {
		return clue;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	@Override
	public String toString() {
		return clue + " (" + row + "," + column + ")";
	}
}