package de.hsh.dbs2.imdb.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsh.dbs2.imdb.activerecords.*;
import de.hsh.dbs2.imdb.factories.*;
import de.hsh.dbs2.imdb.logic.dto.*;

import static de.hsh.dbs2.imdb.util.DBConnection.log_stdio;

public class MovieManager {

	/**
	 * Ermittelt alle Filme, deren Filmtitel den Suchstring enthaelt.
	 * Wenn der String leer ist, sollen alle Filme zurueckgegeben werden.
	 * Der Suchstring soll ohne Ruecksicht auf Gross/Kleinschreibung verarbeitet werden.
	 * @param search Suchstring. 
	 * @return Liste aller passenden Filme als MovieDTO
	 * @throws Exception
	 */
	public List<MovieDTO> getMovieList(String search) throws Exception {
		// TODO DONE
		List<MovieDTO> result = new ArrayList<>();

		// 1. get movies
		List<Movie> movieList = MovieFactory.getMovieByTitle(search);
		for (Movie movie : movieList) {
			MovieDTO movieDTO = new MovieDTO();
			movieDTO.setId((long) movie.getMovieID());
			movieDTO.setTitle(movie.getTitle());
			movieDTO.setType(String.valueOf(movie.getType()));
			movieDTO.setYear(movie.getYear());

			result.add(movieDTO);
		}

		// 2. get genres
		for (MovieDTO movieDTO : result) {
			Set<String> genres = new HashSet<>();

			List<Genre> genreList = GenreFactory.getGenresByMovieID(Math.toIntExact(movieDTO.getId()));
			for (Genre genre : genreList) {
				genres.add(genre.getGenre());
			}

			movieDTO.setGenres(genres);
		}

		// 3. get characters
		for (MovieDTO movieDTO : result) {
			List<CharacterDTO> characters = new ArrayList<>();

			List<MovieCharacter> movieCharacterList = MovieCharacterFactory.getMovieCharactersByMovieId(Math.toIntExact(movieDTO.getId()));
			for (MovieCharacter movieCharacter : movieCharacterList) {
				String character = movieCharacter.getCharacter();
				String alias = movieCharacter.getAlias();
				String player = PersonFactory.getPersonByID(movieCharacter.getPersonID()).getName();

				CharacterDTO characterDTO = new CharacterDTO();
				characterDTO.setCharacter(character);
				characterDTO.setAlias(alias);
				characterDTO.setPlayer(player);

				characters.add(characterDTO);
			}

			movieDTO.setCharacters(characters);
		}

		return result;
	}

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movie Film-Objekt mit Genres und Charakteren.
	 * @throws Exception
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
		// TODO DONE
		if (movieDTO.getId() == null) {
			// new movie
			Movie movie = new Movie(-1, movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType().charAt(0));
			movie.insert();
			movieDTO.setId((long) movie.getMovieID());
			// genres
			for (String genreStr : movieDTO.getGenres()) {
				System.out.println(genreStr);
				Genre genre = GenreFactory.getGenresByName(genreStr).get(0);
				MovieGenre movieGenre = new MovieGenre(genre.getGenreID(), movie.getMovieID());
				movieGenre.insert();
			}
			// characters
			for (CharacterDTO characterDTO : movieDTO.getCharacters()) {
				// MovieCharacter needs person id
				List<Person> personList = PersonFactory.getPersonsByName(characterDTO.getPlayer());
				Person person = null;
				if (personList.size() > 0) {
					// actor exists
					person = personList.get(0);
				} else {
					// actor does not exist
					log_stdio("actor " + characterDTO.getPlayer() + "does not exist");
				}

				MovieCharacter movieCharacter = new MovieCharacter(-1, characterDTO.getCharacter(), characterDTO.getAlias(), 0, Math.toIntExact(movieDTO.getId()), person.getPersonID());
				movieCharacter.insert();
			}

		} else {
			// update movie
			log_stdio("update movie");
			Movie movie = MovieFactory.getMovieById(Math.toIntExact(movieDTO.getId()));
			movie.setTitle(movieDTO.getTitle());
			movie.setType(movieDTO.getType().charAt(0));
			movie.setYear(movieDTO.getYear());
			movie.update();
			
			// genres
			Set<String> genres = movieDTO.getGenres();
			// delete old MovieGenres
			List<MovieGenre> oldMovieGenres = MovieGenreFactory.getMovieGenresByMovieId(Math.toIntExact(movieDTO.getId()));
			for (MovieGenre movieGenre : oldMovieGenres) {
				movieGenre.delete();
			}
			// insert new MovieGenres
			for (String genreStr : genres) {
				int genreID = GenreFactory.getGenresByName(genreStr).get(0).getGenreID();
				int movieID = Math.toIntExact(movieDTO.getId());
				MovieGenre movieGenre = new MovieGenre(genreID, movieID);
				movieGenre.insert();
			}

			// characters
			List<CharacterDTO> characters = movieDTO.getCharacters();
			// delete old characters
			List<MovieCharacter> oldMovieCharacters = MovieCharacterFactory.getMovieCharactersByMovieId(Math.toIntExact(movieDTO.getId()));
			for (MovieCharacter character : oldMovieCharacters) {
				character.delete();
			}
			// insert new characters
			for (CharacterDTO characterDTO : characters) {
				int movCharID = -1;
				String character = characterDTO.getCharacter();
				String alias = characterDTO.getAlias();
				int position = 0;
				// FIXME this will produce a NPE if the person does not exist
				List<Person> personList = PersonFactory.getPersonsByName(characterDTO.getPlayer());
				log_stdio("update movie found " + personList.size() + " candidates");
				if (personList.size() > 0) {
					int personID = personList.get(0).getPersonID();
					MovieCharacter movieCharacter = new MovieCharacter(movCharID, character, alias, position, Math.toIntExact(movieDTO.getId()), personID);
					movieCharacter.insert();
				}
			}
		}
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movie
	 * @throws Exception
	 */
	public void deleteMovie(long movieId) throws Exception {
		// TODO DONE
		Movie movie = MovieFactory.getMovieById((int) movieId);

		// delete hasgenre
		List<MovieGenre> genres = MovieGenreFactory.getMovieGenresByMovieId((int) movieId);
		log_stdio("found " + genres.size() + " genres for movie");
		for (MovieGenre hasGenre : genres) {
			log_stdio("deleting " + hasGenre);
			hasGenre.delete();
		}

		// delete characters
		List<MovieCharacter> characters = MovieCharacterFactory.getMovieCharactersByMovieId((int) movieId);
		log_stdio("found " + characters.size() + " characters for movie");
		for (MovieCharacter character : characters) {
			log_stdio("deleting " + character);
			character.delete();
		}

		log_stdio("deleting " + movie);

		movie.delete();
	}

	/**
	 * Liefert die Daten eines einzelnen Movies zurück
	 * @param movieId
	 * @return
	 * @throws Exception
	 */
	public MovieDTO getMovie(long movieId) throws Exception {
		// TODO DONE
		MovieDTO result = new MovieDTO();

		Movie movie = MovieFactory.getMovieById((int) movieId);
		result.setId((long) movie.getMovieID());
		result.setTitle(movie.getTitle());
		result.setType(String.valueOf(movie.getType()));
		result.setYear(movie.getYear());

		// set genres
		Set<String> genres = new HashSet<>();
		List<Genre> genreList = GenreFactory.getGenresByMovieID(Math.toIntExact(result.getId()));
		for (Genre genre : genreList) {
			genres.add(genre.getGenre());
		}
		result.setGenres(genres);

		// set characters
		List<CharacterDTO> characters = new ArrayList<>();

		List<MovieCharacter> movieCharacterList = MovieCharacterFactory.getMovieCharactersByMovieId(Math.toIntExact(result.getId()));
		for (MovieCharacter movieCharacter : movieCharacterList) {
			String character = movieCharacter.getCharacter();
			String alias = movieCharacter.getAlias();
			String player = PersonFactory.getPersonByID(movieCharacter.getPersonID()).getName();

			CharacterDTO characterDTO = new CharacterDTO();
			characterDTO.setCharacter(character);
			characterDTO.setAlias(alias);
			characterDTO.setPlayer(player);

			characters.add(characterDTO);
		}
		result.setCharacters(characters);

		System.out.println("MovieManager:");
		System.out.println(result);
		return result;
	}

}
