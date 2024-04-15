package FleurNguessan.crossword.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import FleurNguessan.crossword.Database;

public class TestDatabase {

	private final Database db = new Database();

	@BeforeEach
	public void testConnection() {
		Connection connexion = db.getConnection();
		assertNotEquals(null, connexion);
	}

	@Test
	public void testAvailableGrids() {
		Map<Integer, String> grids = db.availableGrids();
		String expectedGridName = "Français débutants (7x6)";
		String actualGridName = grids.get(10);

		assertEquals(expectedGridName, actualGridName);
	}
}