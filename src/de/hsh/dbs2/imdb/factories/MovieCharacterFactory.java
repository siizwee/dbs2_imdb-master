package de.hsh.dbs2.imdb.factories;

import de.hsh.dbs2.imdb.activerecords.Movie;
import de.hsh.dbs2.imdb.activerecords.MovieCharacter;
import de.hsh.dbs2.imdb.util.DBConnection;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.hsh.dbs2.imdb.util.DBConnection.log_stderr;

public class MovieCharacterFactory {
    public static List<MovieCharacter> getMovieCharactersByMovieId(int id) throws IMDBException {
        List<MovieCharacter> result = new ArrayList<>();

        String sql = "SELECT * FROM " + MovieCharacter.table + " JOIN " + Movie.table + " M on " + MovieCharacter.table + ". " + MovieCharacter.col_movID + " = M." + Movie.col_movieID + " AND M." + Movie.col_movieID + " = ? ORDER BY " + MovieCharacter.col_pos;
        ResultSet resultSet;
        PreparedStatement stmt;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get the MovieCharacters for Movie with ID " + id, e.getMessage());
        }

        try {
            while (resultSet.next()) {
                int movCharID = resultSet.getInt(MovieCharacter.col_movcharID);
                String character = resultSet.getString(MovieCharacter.col_char);
                String alias = resultSet.getString(MovieCharacter.col_alias);
                int position = resultSet.getInt(MovieCharacter.col_pos);
                int movieID = resultSet.getInt(MovieCharacter.col_movID);
                int personID = resultSet.getInt(MovieCharacter.col_persID);

                result.add(new MovieCharacter(movCharID, character, alias, position, movieID, personID));
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
