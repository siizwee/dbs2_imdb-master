package de.hsh.dbs2.imdb;

import javax.swing.SwingUtilities;

import de.hsh.dbs2.imdb.gui.SearchMovieDialog;
import de.hsh.dbs2.imdb.gui.SearchMovieDialogCallback;
import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Starter {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws SQLException {
		if (args.length < 3) {
			System.out.println("Invoke with args: dbconnectString username password");
			System.exit(1);
		}
		DBConnection.setDbconnectString(args[0]);
		DBConnection.setUsername(args[1]);
		DBConnection.setPassword(args[2]);

		try {
			DBConnection.getConnection();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Starter().run();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.getConnection().close();
		}

	}
	
	public void run() {
		SearchMovieDialogCallback callback = new SearchMovieDialogCallback();
		SearchMovieDialog sd = new SearchMovieDialog(callback);
		sd.setVisible(true);
	}

}
