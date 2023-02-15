/**
 * metoda de operatii asupra clientului
 */
package DataAcces;

import Model.Client;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;

public class ClientDAO  {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    /**
     * metoda pentru insert
     * @param query
     * @param client
     * @return
     */
    public static int insertClient(String query, Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();


        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, String.valueOf(client.getAge()));
            insertStatement.setString(3, String.valueOf(client.getBudget()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * metoda pentru delete
     * @param query
     * @param client
     * @return
     */
    public static int deleteClient(String query, Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        int clientID = client.getId();

        PreparedStatement deleteStatement = null;
        try{
            deleteStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setLong(1, clientID);
            deleteStatement.executeUpdate();
            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                clientID = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return clientID;

    }

    /**
     * delete
     * @param query
     * @param client
     * @return
     */
    public static int editClient(String query, Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement edit = null;
        try {
            edit = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            edit.setString(1, client.getName());
            edit.setString(2, String.valueOf(client.getAge()));
            edit.setString(3, String.valueOf(client.getBudget()));
            edit.setString(4, String.valueOf(client.getId()));
            edit.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "clientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(edit);
            ConnectionFactory.close(dbConnection);
        }
        return client.getId();
    }

}

