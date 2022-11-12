package de.hsh.dbs2.imdb.activerecords;


import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MovieGenre {
    public static final String table = "hasGenre";
    public static final String col_genreID = "GenreID";
    public static final String col_movieID = "MovieID";

    private int genreID; // Spalte: GenreID
    private int movieID; // Spalte: MovieID


    /**
     * Initialisiert die Entität.
     * @param genreID Spalte: GenreID
     * @param movieID Spalte: MovieID
     */
    public MovieGenre(int genreID, int movieID) {
        this.setGenreID(genreID);
        this.setMovieID(movieID);
    }

    public MovieGenre(){}

    public void insert() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "INSERT INTO " + table + " VALUES (?, ?)";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getMovieID());
            stmt.setInt(2, this.getGenreID());

            // Insert:
            int rowsInserted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsInserted + " Zeilen hinzugefügt");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim einfügen in MovieGenre", e.getMessage());
        }
    }


    public void update() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "UPDATE " + table + " SET " + col_movieID + " = ?, " + col_genreID + " = ? WHERE " + col_movieID + " = ? AND " + col_genreID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getMovieID());
            stmt.setInt(2, this.getGenreID());
            stmt.setInt(3, this.getMovieID());
            stmt.setInt(4, this.getGenreID());

            // Update:
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsUpdated + " Zeilen verändert");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim updaten in MovieGenre", e.getMessage());
        }
    }

    public void delete() throws SQLException {
        // SQL-Statement
        try {
            String sql = "DELETE FROM " + table + " WHERE " + col_movieID + " = ? AND " + col_genreID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql.toString());
            stmt.setInt(1, this.getMovieID());
            stmt.setInt(2, this.getGenreID());

            // Delete:
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsDeleted + " Zeilen gelöscht");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim löschen in MovieGenre", e.getMessage());
        }
    }


    public int getGenreID() {
        return genreID;
    }


    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }


    public int getMovieID() {
        return movieID;
    }


    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    @Override
    public String toString() {
        return "[ genreID: " + genreID + ", " +
                "movieID: " + movieID + " ]";
    }
}