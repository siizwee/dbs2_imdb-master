package de.hsh.dbs2.imdb.activerecords;


import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {
    public static final String seq_genreID = "genre_id";
    public static final String table = "Genre";
    public static final String col_genre ="Genre";
    public static final String col_genreID = "GenreID";

    private String genre; // Spalte: Genre
    private int genreID; // Spalte: GenreID


    /**
     * Initialisiert die Entität.
     * @param genre Spalte: Genre
     * @param genreID Spalte: GenreID
     */
    public Genre(String genre, int genreID) {
        this.setGenre(genre);
        this.setGenreID(genreID);
    }

    public Genre(){}

    public void insert() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "INSERT INTO " + table + " VALUES (" + seq_genreID + ".nextval, ?)";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, this.getGenre());
            int rowsInserted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsInserted + " Zeilen hinzugefügt");


            //ID:
            sql = "Select " + seq_genreID + ".currval From DUAL";
            stmt = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) this.setGenreID(rs.getInt(1));
            rs.close();
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim einfügen in Genre", e.getMessage());
        }
    }


    public void update() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "UPDATE " + table + " SET " + col_genre + " = ?" + " WHERE " + col_genreID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, this.getGenre());
            stmt.setInt(2, this.getGenreID());
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsUpdated + " Zeilen verändert");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim updaten in Genre", e.getMessage());
        }
    }


    public void delete() throws SQLException {
        // SQL-Statement
        try {
            String sql ="DELETE FROM " + table+" WHERE " + col_genreID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getGenreID());
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Es wurden "+rowsDeleted+" Zeilen gelöscht");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim löschen des Genre", e.getMessage());
        }
    }


    public String getGenre() {
        return genre;
    }


    public void setGenre(String genre) {
        this.genre = genre;
    }


    public int getGenreID() {
        return genreID;
    }


    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    @Override
    public String toString() {
        return "[ genre: " + genre + ", " +
                "genreID: " + genreID + " ]";
    }
}