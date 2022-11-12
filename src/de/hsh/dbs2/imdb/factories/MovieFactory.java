package de.hsh.dbs2.imdb.factories;


import de.hsh.dbs2.imdb.activerecords.Movie;
import de.hsh.dbs2.imdb.util.DBConnection;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MovieFactory{
    /**
     * Findet einen Film anhand der ID.
     * @param movieID ID des Films
     * @return Zugehöriger Film
     * @throws SQLException Im Falle eins Datenbankfehlers
     */
    public static Movie getMovieById(int movieID) throws IMDBException {
        // SQL-Statement:
        String sql ="SELECT * FROM " + Movie.table + " WHERE " + Movie.col_movieID + " = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, movieID);

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get the Movie for Movie with ID " + movieID, e.getMessage());
        }

        Movie movie = null;
        try {
            if (!rs.next()) {
                System.out.println("Der Film mit der ID " + movieID + " existiert nicht!");
            } else {
                movie = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4).charAt(0));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to read data from the ResultSet", e.getMessage());
        }

        try {
            rs.close();
            stmt.close();
            DBConnection.getConnection().close();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }
        return movie;
    }


    /**
     * Findet eine Liste von Filmen, die das Suchkriterium im Titel beinhalten.
     * @param title Suchkriterium für Titel
     * @return Liste mit passenden Filmen
     * @throws SQLException Im Falle eins Datenbankfehlers
     */
    public static List<Movie> getMovieByTitle(String title) throws IMDBException {
        // SQL-Statement:
        String sql = "SELECT * FROM " + Movie.table +" WHERE UPPER(" + Movie.col_title +") LIKE UPPER('%"+ title+"%')";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = DBConnection.getConnection().createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get the Movie for Movie with Title " + title, e.getMessage());
        }

        List<Movie> movies = new ArrayList<Movie>();
        try {
            while (rs.next()) {
                movies.add(new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4).charAt(0)));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to read data from the ResultSet", e.getMessage());
        }

        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }

        return movies;
    }


}