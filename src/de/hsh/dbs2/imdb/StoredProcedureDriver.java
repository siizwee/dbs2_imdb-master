package de.hsh.dbs2.imdb;

import de.hsh.dbs2.imdb.util.DBConnection;
import de.hsh.dbs2.imdb.util.IMDBException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoredProcedureDriver {
    public static void changePersonNameStoredProc(String oldName, String newName) throws SQLException {
        String sql = "BEGIN PERSON_CHANGE_NAME(?, ?); END;";
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
        stmt.setString(1, oldName);
        stmt.setString(2, newName);
        stmt.executeQuery();

        stmt.close();
    }

    public static void main(String[] args) throws IMDBException, SQLException {
        if (args.length < 3) {
            System.out.println("Invoke with args: dbconnectString username password");
            System.exit(1);
        }
        DBConnection.setDbconnectString(args[0]);
        DBConnection.setUsername(args[1]);
        DBConnection.setPassword(args[2]);

        changePersonNameStoredProc("Sofie Schultz", "Emilia Clarke");
    }
}
