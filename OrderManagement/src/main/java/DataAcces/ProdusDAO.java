/**
 * metoda de operatii asupra produselor
 */
package DataAcces;

import Model.Produs;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;

public class ProdusDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProdusDAO.class.getName());private final static String findStatementString = "SELECT * FROM Produs where id = ?";

//    public static Produs findById(int ProdusId) {
//        Produs toReturn = null;
//
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement findStatement = null;
//        ResultSet rs = null;
//        try {
//            findStatement = dbConnection.prepareStatement(findStatementString);
//            findStatement.setLong(1, ProdusId);
//            rs = findStatement.executeQuery();
//            rs.next();
//
//            String name = rs.getString("name");
//            String ageString = rs.getString("price");
//            String budgetString = rs.getString("stock");
//            int age = Integer.parseInt(ageString);
//            int budget = Integer.parseInt(budgetString);
//
//            toReturn = new Produs(1, name, age, budget);
//        } catch (SQLException e) {
//            LOGGER.log(Level.WARNING,"ProdusDAO:findById " + e.getMessage());
//        } finally {
//            ConnectionFactory.close(rs);
//            ConnectionFactory.close(findStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//        return toReturn;
//    }

    /**
     * metoda pentru inserarea produsului
     * @param query
     * @param Produs
     * @return
     */
    public static int insertProdus(String query, Produs Produs) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, Produs.getName());
            insertStatement.setString(2, String.valueOf(Produs.getPrice()));
            insertStatement.setString(3, String.valueOf(Produs.getStock()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProdusDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * metoda pentru stergerea produsului
     * @param query
     * @param produs
     * @return
     */
    public static int deleteProdus(String query, Produs produs) {
        Connection dbConnection = ConnectionFactory.getConnection();
        int ProdusID = produs.getId();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setLong(1, ProdusID);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProdusDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return ProdusID;
    }

    /**
     * metoda pentru editarea produsului
     * @param query
     * @param produs
     * @return
     */

    public static int editProdus(String query,Produs produs) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement edit = null;

        try {
            edit = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            edit.setString(1, produs.getName());
            edit.setString(2, String.valueOf(produs.getPrice()));
            edit.setString(3, String.valueOf(produs.getStock()));
            edit.setString(4, String.valueOf(produs.getId()));

            edit.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProdusDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(edit);
            ConnectionFactory.close(dbConnection);
        }
        return produs.getId();
    }
}
