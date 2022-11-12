package de.hsh.dbs2.imdb.activerecords;


import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
    public static final String seq_movieID = "movie_id";
    public static final String table = "Movie";
    public static final String col_type = "Type";
    public static final String col_title = "Title";
    public static final String col_movieID = "MovieID";
    public static final String col_year ="Year";

    private String title; // Spalte: Title
    private char type; // Spalte: Type
    private int movieID; // Spalte: MovieID
    private int year; // Spalte: Year


    /**
     * Initialisiert die Entität.
     * @param title Spalte: Title
     * @param type Spalte: Type
     * @param year Spalte: Year
     * @param movieID Spalte: MovieID
     */
    public Movie(int movieID, String title, int year,char type) {
        this.setTitle(title);
        this.setType(type);
        this.setYear(year);
        this.setMovieID(movieID);
    }

    public Movie(){}

    public void insert() throws SQLException {
        // SQL-Statement:
        try {
            System.out.println(DBConnection.getConnection().toString());
            String sql = "INSERT INTO " + table + "(" + col_movieID + "," + col_title + "," + col_year + "," + col_type + ") VALUES (" + seq_movieID + ".nextval, ?, ?, ?)";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            System.out.println(DBConnection.getConnection().toString());
            stmt.setString(1, this.getTitle());
            stmt.setInt(2, this.getYear());
            stmt.setString(3, String.valueOf(this.getType()));

            int rowsInserted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsInserted + " Zeilen hinzugefügt");


            //ID:
            sql = "Select " + seq_movieID + ".currval From DUAL";
            stmt = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) this.setMovieID(rs.getInt(1));
            rs.close();
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim einfügen in Movie", e.getMessage());
        }
    }

    public void update() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "UPDATE " + table + " SET " + col_type + " = ?, " + col_year + " = ?, " + col_title + " = ? WHERE " + col_movieID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, String.valueOf(this.getType()));
            stmt.setInt(2, this.getYear());
            stmt.setString(3, this.getTitle());
            stmt.setInt(4, this.getMovieID());

            // Update:
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsUpdated + " Zeilen hinzugefügt");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim updaten in Movie", e.getMessage());
        }
    }

    public void delete() throws SQLException {
        // SQL-Statement
        try {
            String sql = "DELETE FROM " + table + " WHERE " + col_movieID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getMovieID());

            // Delete:
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsDeleted + " Zeilen gelöscht");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim löschen in Movie", e.getMessage());
        }
    }


    public char getType() {
        return type;
    }


    public void setType(char type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getMovieID() {
        return movieID;
    }


    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }


    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "[ title: " + title + ", " +
                "type: " + type + ", " +
                "movieID: " + movieID + ", " +
                "year: " + year + ", " +
                "movieID: " + movieID + " ]";
    }
}