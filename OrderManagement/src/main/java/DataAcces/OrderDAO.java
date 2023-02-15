/**
 * metoda de operatii asupra comenzii
 */
package DataAcces;

import Model.Client;
import Model.Comanda;
import Connection.ConnectionFactory;
import Model.Produs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;
import java.sql.*;
import java.util.logging.Level;

public class OrderDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    /**
     * motoda pentru adaugarea unei comenzi
     * @param query
     * @param comanda
     * @return
     */
    public static int addOrder(String query, Comanda comanda) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, String.valueOf(comanda.getId_client()));
            insertStatement.setString(2, String.valueOf(comanda.getId_product()));
            insertStatement.setString(3, String.valueOf(comanda.getPieces()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * metoda pentru verificarea datelor unei comenzi
     * @param budget
     * @param comanda
     * @return
     * @throws SQLException
     */
    public static int verify(int budget, Comanda comanda) throws SQLException {
        Produs produs = new Produs(1,"",1,1);
        Client client = new Client(1,"", 2, 2);
        AbstractDAO<Object> abstractDAO1 = new AbstractDAO<>(produs);
        AbstractDAO<Object> abstractDAO2 = new AbstractDAO<>(client);
        produs = (Produs)abstractDAO1.findById(comanda.getId_product());
        client = (Client)abstractDAO2.findById(comanda.getId_client());
        if(produs != null && client != null){
            if(comanda.getPieces() > produs.getStock())
                return -1;
            if(comanda.getPieces() * produs.getPrice() > budget)
                return -2;
            produs.setStock(produs.getStock() - comanda.getPieces());
            abstractDAO1.editObject(produs);
            client.setBudget(client.getBudget() - comanda.getPieces()*produs.getPrice());
            abstractDAO2.editObject(client);
            return 1;
        }
        return -3;
    }

    /**
     * metoda pentru creeaza factura
     * @param comanda
     * @throws SQLException
     */
    public static void createBill(Comanda comanda) throws SQLException {
        FileOutputStream g= null;
        try {
            g = new FileOutputStream("Bill.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Produs produs = new Produs(1,"",1,1);
        Client client = new Client(1,"", 2, 2);
        AbstractDAO<Object> abstractDAO1 = new AbstractDAO<>(produs);
        AbstractDAO<Object> abstractDAO2 = new AbstractDAO<>(client);
        produs = (Produs)abstractDAO1.findById(comanda.getId_product());
        client = (Client)abstractDAO2.findById(comanda.getId_client());
        String write = "";
        write = write.concat(client.toString());
        write = write.concat(" a cumparat ");
        write = write.concat(produs.toString());
        assert g != null;
        PrintStream gchar=new PrintStream(g);
        gchar.println(write);
    }
}
