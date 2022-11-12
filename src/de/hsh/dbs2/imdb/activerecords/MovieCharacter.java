package de.hsh.dbs2.imdb.activerecords;


import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MovieCharacter{
    public static final String seq_movCharID = "movchar_id";
    public static final String table = "MovieCharacter";
    public static final String col_char = "Character";
    public static final String col_alias = "Alias";
    public static final String col_movcharID = "MovCharID";
    public static final String col_pos = "Position";
    public static final String col_movID = "hasCharacter";
    public static final String col_persID = "plays";

    private String character; // Spalte: Character
    private String alias; // Spalte: Alias
    private int movCharID; // Spalte: MovCharID
    private int position; // Spalte: Position
    private int movieID; // Spalte: MovieID
    private int personID; // Spalte: PersonID


    /**
     * Initialisiert die Entität
     * @param movCharID Spalte: MovCharID
     * @param character Spalte: Character
     * @param position Spalte: Position
     * @param movieID Spalte: MovieID
     * @param personID Spalte: PersonID

     */
    public MovieCharacter(int movCharID, String character, String alias, int position, int movieID, int personID) throws SQLException {
        this.setCharacter(character);
        this.setAlias(alias);
        this.setPosition(position);
        this.setMovieID(movieID);
        this.setPersonID(personID);
        this.setMovCharID(movCharID);
    }

    public MovieCharacter(){}

    public void insert() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "INSERT INTO " + table + "(" + col_movcharID + "," + col_char + "," + col_pos + "," + col_movID + "," + col_persID + "," + col_alias + ") VALUES (" + seq_movCharID + ".nextval, ?, ?, ?, ?,?)";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, this.getCharacter());
            stmt.setInt(2, this.getPosition());
            stmt.setInt(3, this.getMovieID());
            System.out.println(this.getMovieID());
            stmt.setInt(4, this.getPersonID());
            System.out.println(this.getPersonID());
            stmt.setString(5, this.getAlias());

            int rowsInserted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsInserted + " Zeilen hinzugefügt");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim einfügen in MovieCharacter", e.getMessage());
        }
    }



    public void update() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "UPDATE " + table + " SET " + col_char + " = ?, " + col_pos + " = ?, " + col_movID + " = ?, " + col_persID + " = ?, "
                    + col_alias + " = ? WHERE " + col_movcharID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);

            stmt.setString(1, this.getCharacter());
            stmt.setInt(2, this.getPosition());
            stmt.setInt(3, this.getMovieID());
            stmt.setInt(4, this.getPersonID());
            stmt.setString(5, this.getAlias());
            stmt.setInt(5, this.getMovCharID());

            // Update:
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsUpdated + " Zeilen verändert");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim updaten in MovieCharacter", e.getMessage());
        }
    }


    public void delete() throws SQLException {
        // SQL-Statement
        try {
            String sql ="DELETE FROM " + table+" WHERE " + col_movcharID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getMovCharID());

            // Delete:
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Es wurden "+rowsDeleted+" Zeilen gelöscht");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim löschen in MovieCharacter", e.getMessage());
        }
    }



    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public int getMovCharID() {
        return movCharID;
    }


    public void setMovCharID(int movCharID) {
        this.movCharID = movCharID;
    }


    public int getPosition() {
        return position;
    }


    public void setPosition(int position) throws SQLException {
        String sql = "select max(" + col_pos + ") from " + table;
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        this.position = rs.getInt(1) + 1;
        stmt.close();
        rs.close();
    }


    public int getMovieID() {
        return movieID;
    }


    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }


    public int getPersonID() {
        return personID;
    }


    public void setPersonID(int personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "[ character: " + character + ", " +
                "alias: " + alias + ", " +
                "movCharID: " + movCharID + ", " +
                "position: " + position + ", " +
                "movieID: " + movieID + ", " +
                "personID: " + personID + " ]";
    }
}