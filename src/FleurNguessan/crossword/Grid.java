package FleurNguessan.crossword;

public class Grid<T> {

	private int height;
	private int width;
	private T[][] array;

	public Grid(int height, int width) {
		this.height = height;
		this.width = width;
		array = (T[][]) new Object[height][width];
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean correctCoords(int row, int column) {
		return row >= 0 && column >= 0 && row < height && column < width;
	}

	public T getCell(int row, int column) {

		return array[row][column];
	}

	public void setCell(int row, int column, T string) {

		array[row][column] = string;
	}

	public String toString() {
		StringBuilder tab = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tab.append(getCell(i, j));

				if (j != width - 1) {
					tab.append("|");
				}
			}
			tab.append("\n");
		}
		return tab.toString();
	}

}
