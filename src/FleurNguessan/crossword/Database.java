package FleurNguessan.crossword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {
	private Connection connexion;

	public Database() {
		try {
			connexion = connecterBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection connecterBD() throws SQLException {
		String url = "jdbc:mysql://localhost/datacrossword";
		Connection connect = DriverManager.getConnection(url, "root", "");
		return connect;
	}

	public Connection getConnection() {
		return connexion;
	}

	// Retourne la liste des grilles disponibles dans la BD
	// Chaque grille est décrite par la concaténation des valeurs
	// respectives des colonnes nom_grille, hauteur et largeur.
	// L’élément de liste ainsi obtenu est indexé par le numéro de
	// la grille (colonne num_grille).
	// Ainsi "Français débutants (7x6)" devrait être associé à la clé 10

	public Map<Integer, String> availableGrids() {
		try {
			String requete = "SELECT * FROM GRID";
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery(requete);

			Map<Integer, String> grilles = new HashMap<>();

			while (rs.next()) {
				Integer numeroGrille = rs.getInt("numero_grille");
				String nomGrille = rs.getString("nom_grille");
				Integer hauteur = rs.getInt("hauteur");
				Integer largeur = rs.getInt("largeur");
				grilles.put(numeroGrille, nomGrille + " (" + hauteur + "x" + largeur + ")");
			}
			return grilles;

		} catch (SQLException e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}

	public Crossword extractGrid(int numGrille) {
		Map<Integer, String> grid = availableGrids();
		if (grid.containsKey(numGrille)) {

			try {
				String requete = " SELECT * FROM CROSSWORD, GRID WHERE CROSSWORD.numero_grille = " + numGrille
						+ " AND CROSSWORD.numero_grille = GRID.numero_grille";

				Statement stmt = connexion.createStatement();
				ResultSet rs = stmt.executeQuery(requete);

				if (!rs.next()) {
					return null;
				}
				int hauteur = rs.getInt("hauteur");
				int largeur = rs.getInt("largeur");
				Crossword crossword = new Crossword(hauteur, largeur);

				do {
					String definition = rs.getString("definition");
					boolean horizontal = rs.getBoolean("horizontal");
					int ligne = rs.getInt("ligne") - 1;
					int colonne = rs.getInt("colonne") - 1;
					String solution = rs.getString("solution").toUpperCase();

					if (horizontal) {
						for (int col = 0; col < solution.length(); col++) {
							crossword.setBlackSquare(ligne, colonne + col, false);
							crossword.setSolution(ligne, colonne + col, solution.charAt(col));
						}
					} else {

						for (int lig = 0; lig < solution.length(); lig++) {
							crossword.setBlackSquare(ligne + lig, colonne, false);
							crossword.setSolution(ligne + lig, colonne, solution.charAt(lig));
						}

					}

					crossword.setDefinition(ligne, colonne, horizontal, definition);

				} while (rs.next());

				return crossword;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
