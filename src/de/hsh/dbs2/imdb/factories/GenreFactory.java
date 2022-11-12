package de.hsh.dbs2.imdb.factories;

import de.hsh.dbs2.imdb.activerecords.Genre;
import de.hsh.dbs2.imdb.util.DBConnection;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.hsh.dbs2.imdb.util.DBConnection.log_stderr;

public class GenreFactory {
    public static List<Genre> getAllGenres() throws IMDBException {
        List<Genre> result = new ArrayList<>();


        String sql = "SELECT * FROM " + Genre.table + " ORDER BY " + Genre.col_genre;
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get Genres" , e.getMessage());
        }

        try {
            while (resultSet.next()) {
                String name = resultSet.getString(Genre.col_genre);
                int id = resultSet.getInt(Genre.col_genreID);

                result.add(new Genre(name, id));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to read data from the ResultSet", e.getMessage());
        }

        try {
            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }


        return result;
    }

    public static List<Genre> getGenresByName(String query) throws IMDBException, SQLException {
        List<Genre> result = new ArrayList<>();

        String sql = "SELECT * FROM " + Genre.table + " WHERE UPPER(" + Genre.col_genre + ") LIKE UPPER(?)";
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, "%" + query + "%");
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get Genres by name: " + query, e.getMessage());
        }

        try {
            while (resultSet.next()) {
                String name = resultSet.getString(Genre.col_genre);
                int id = resultSet.getInt(Genre.col_genreID);

                result.add(new Genre(name, id));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to read data from the ResultSet", e.getMessage());
        }

        try {
            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }

        return result;
    }

    public static List<Genre> getGenresByMovieID(int movieId) throws IMDBException {
        List<Genre> result = new ArrayList<>();

        String sql = "SELECT GENRE.* FROM GENRE JOIN HASGENRE H on GENRE.GENREID = H.GENREID JOIN MOVIE M on H.MOVIEID = M.MOVIEID WHERE M.MOVIEID = ?";
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, movieId);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get Genre for Movie with ID " + movieId, e.getMessage());
        }

        try {
            while (resultSet.next()) {
                String genre = resultSet.getString(Genre.col_genre);
                int id = resultSet.getInt(Genre.col_genreID);

                result.add(new Genre(genre, id));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to read data from the ResultSet", e.getMessage());
        }

        try {
            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }

        return result;
    }
}
