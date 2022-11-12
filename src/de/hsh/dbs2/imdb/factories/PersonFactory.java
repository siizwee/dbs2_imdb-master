package de.hsh.dbs2.imdb.factories;

import de.hsh.dbs2.imdb.activerecords.Person;
import de.hsh.dbs2.imdb.util.DBConnection;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.hsh.dbs2.imdb.util.DBConnection.log_stderr;
import static de.hsh.dbs2.imdb.util.DBConnection.log_stdio;

public class PersonFactory {
    public static List<Person> getPersonsByName(String query) throws IMDBException {

        List<Person> result = new ArrayList<>();

        String sql = "SELECT * FROM " + Person.table + " WHERE UPPER(" + Person.col_name + ") LIKE UPPER('%' || ? || '%')";
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, query);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(Person.col_name);
                char sex = resultSet.getString(Person.col_sex).charAt(0);
                int id = resultSet.getInt(Person.col_personID);

                result.add(new Person(name, sex, id));
            }
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get the Person for Person with name " + query, e.getMessage());
        }

        try {
            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred while trying to close database resources", e.getMessage());
        }

        return result;
    }

    public static Person getPersonByID(int personID) throws IMDBException {
        Person person = null;

        String sql = "SELECT * FROM " + Person.table + " WHERE " + Person.col_personID + " = ?";
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            stmt = DBConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, personID);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new IMDBException("An error occurred trying to get the Person with ID " + personID, e.getMessage());
        }

        try {
            while (resultSet.next()) {
                String name = resultSet.getString(Person.col_name);
                char sex = resultSet.getString(Person.col_sex).charAt(0);
                int id = resultSet.getInt(Person.col_personID);
                person = new Person(name, sex, id);
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

        return person;
    }
}
