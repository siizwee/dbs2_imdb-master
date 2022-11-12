package de.hsh.dbs2.imdb.factories;

import de.hsh.dbs2.imdb.activerecords.Movie;
import de.hsh.dbs2.imdb.activerecords.MovieGenre;
import de.hsh.dbs2.imdb.util.DBConnection;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.hsh.dbs2.imdb.util.DBConnection.log_stderr;

public class MovieGenreFactory {
    public static List<MovieGenre> getMovieGenresByMovieId(int id) throws IMDBException {
        List<MovieGenre> result = new ArrayList<>();

        String sql = "SELECT H.* FROM " + Movie.table + " JOIN " + MovieGenre.table + " H on " + Movie.table + ".MOVIEID = H." + Movie.col_movieID + " WHERE H." + Movie.col_movieID + " = ?";
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get the MovieCharacters for Movie with ID " + id, e.getMessage());
        }

        try {
            while (resultSet.next()) {
                int genreId = resultSet.getInt(MovieGenre.col_genreID);
                int movieId = resultSet.getInt(MovieGenre.col_movieID);
                result.add(new MovieGenre(genreId, movieId));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to read data from the ResultSet", e.getMessage());
        }

        try {
            resultSet.close();
            stmt.close();
        }  catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }

        return result;
    }
}
