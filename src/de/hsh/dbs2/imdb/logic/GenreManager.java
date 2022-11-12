package de.hsh.dbs2.imdb.logic;

import de.hsh.dbs2.imdb.activerecords.Genre;
import de.hsh.dbs2.imdb.factories.GenreFactory;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreManager {

	/**
	 * Ermittelt eine vollstaendige Liste aller in der Datenbank abgelegten Genres
	 * Die Genres werden alphabetisch sortiert zurueckgeliefert.
	 * @return Alle Genre-Namen als String-Liste
	 * @throws Exception
	 */
	public List<String> getGenres() throws IMDBException, SQLException {
		// TODO DONE
		List<Genre> genreList = GenreFactory.getAllGenres();

		List<String> result = new ArrayList<>();
		for (Genre genre : genreList) {
			result.add(genre.getGenre());
		}

		return result;
	}

}
