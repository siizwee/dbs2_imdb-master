package de.hsh.dbs2.imdb.activerecords;



import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person{

    public static final String seq_personid="person_id";
    public static final String table = "Person";
    public static final String col_name = "Name";
    public static final String col_sex = "Sex";
    public static final String col_personID = "PersonID";

    private String name; // Spalte: Name
    private char sex; // Spalte: Sex
    private int personID; // Spalte: PersonID


    /**
     * Initialisiert die Entität.
     * @param name Spalte: Name:
     * @param sex Spalte: Sex
     * @param personID Spalte: PersonID
     */
    public Person(String name, char sex, int personID) {
        this.setName(name);
        this.setSex(sex);
        this.setPersonID(personID);
    }

    public Person(){}

    public void insert() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "INSERT INTO Person (" + col_personID + "," + col_name + "," + col_sex + ") VALUES(" + seq_personid + ".nextval,?,?)";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, this.getName());
            stmt.setString(2, String.valueOf(this.getSex()));
            int rowsInserted = stmt.executeUpdate();
            stmt.close();
            if (rowsInserted < 1) System.out.println("Es wurde kein Datensatz eingefügt");
            else System.out.println("Es wurde " + rowsInserted + " Zeilen hinzugefügt");

            //ID:
            sql = "Select " + seq_personid + ".currval From DUAL";
            stmt = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) this.setPersonID(rs.getInt(1));
            rs.close();
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim einfügen in Person", e.getMessage());
        }
    }

    public void update() throws SQLException {
        // SQL-Statement:
        try {
            String sql = "UPDATE Person" + table + "SET " + col_name + " = ?, " + col_sex + " = ? WHERE " + col_personID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, this.getName());
            stmt.setString(2, String.valueOf(this.getSex()));
            stmt.setInt(3, this.getPersonID());

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsUpdated + " Zeilen verändert");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim updaten in Person", e.getMessage());
        }
    }

    public void delete() throws SQLException {
        // SQL-Statement
        try {
            String sql = "DELETE FROM " + table + " WHERE " + col_personID + " = ?";
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getPersonID());
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Es wurden " + rowsDeleted + " Zeilen gelöscht");
            stmt.close();
            DBConnection.getConnection().commit();
        } catch (SQLException e) {
            DBConnection.getConnection().rollback();
            throw new SQLException("Fehler beim löschen in Person", e.getMessage());
        }
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public char getSex() {
        return sex;
    }


    public void setSex(char sex) {
        this.sex = sex;
    }


    public int getPersonID() {
        return personID;
    }


    public void setPersonID(int personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "[ name: " + name + ", " +
                "sex: " + sex + ", " +
                "personID: " + personID + " ]";
    }
}