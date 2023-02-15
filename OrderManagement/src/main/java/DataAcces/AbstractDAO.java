/**
 * clasa pentru ce creeaza query urile pentru interogari si apeleaza metodoele ce operreaza asupra obiectelor
 */
package DataAcces;

import java.lang.reflect.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import Model.Client;
import Model.Comanda;
import Model.Produs;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;
    private final String typeName;

    @SuppressWarnings("unchecked")
    /**
     * constructor
     */
    public AbstractDAO(Object object) {
        this.type = (Class<T>) object.getClass();
        this.typeName = object.getClass().getSimpleName();
    }

    /**
     * metoda pentru crearea query urilor pentru insert
     * @param fields
     * @return
     */
    private String createInsertQuery(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT");
        sb.append(" INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        for (Field field : fields)
            sb.append(field.getName() + ", ");
        sb.delete(sb.lastIndexOf("id,"), sb.lastIndexOf("id,") + 4);
        sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(",") + 2);
        sb.append(")");
        sb.append(" VALUES(?,?,?)");
        return sb.toString();
    }

    /**
     * metoda pentru crearea query urilor pentru delete
     * @param fields
     * @return
     */
    private String createDeleteQuery(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        for (Field field : fields) {
            sb.append(" WHERE " + field.getName() + " =?");
            break;
        }
        return sb.toString();
    }

    /**
     * metoda pentru crearea query urilor pentru edit
     * @param fields
     * @return
     */
    private String createEditQuery(Field[] fields) {

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field field : fields)
            sb.append(field.getName() + " = ?, ");
        sb.delete(sb.lastIndexOf("id"), sb.lastIndexOf("id") + 8);
        sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(",") + 2);
        sb.append(" WHERE ID = ? ");
        return sb.toString();
    }

    /**
     * select
     * @return
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append("id").append(" =?");
        return sb.toString();
    }

    /**
     * metoda generica ce apeleaza metoda de find
     * @param id
     * @return
     */
    public Object findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next())
                return createObjects(resultSet);
            System.out.println("The object with id =" + id + " was not found!");


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.type.getSimpleName() + " DAO:findById " + e.getMessage());
        } finally {

            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * metoda generica ce apeleaza metoda de insert
     * @param object
     */
    public void insertObject(Object object) {
        String query = createInsertQuery(type.getDeclaredFields());
        if (type.getSimpleName().equals("Client")) {
            ClientDAO.insertClient(query, (Client) object);
            return;
        }
        if (type.getSimpleName().equals("Produs")) {
            ProdusDAO.insertProdus(query, (Produs) object);
            return;
        }
        OrderDAO.addOrder(query, (Comanda) object);
    }

    /**
     * metoda generica ce apeleaza metoda de delete
     * @param object
     */
    public void deleteObject(Object object) {
        String query = createDeleteQuery(type.getDeclaredFields());
        if (type.getSimpleName().equals("Client")) {
            ClientDAO.deleteClient(query, (Client) object);
            return;
        }
        ProdusDAO.deleteProdus(query, (Produs) object);

    }

    /**
     * edit
     * @param object
     */
    public void editObject(Object object) {
        String query = createEditQuery(type.getDeclaredFields());
        if (type.getSimpleName().equals("Client")) {
            ClientDAO.editClient(query, (Client) object);
            return;
        }
        ProdusDAO.editProdus(query, (Produs) object);

    }

    /**
     * constructor
     * @param resultSet
     * @return
     * @throws SQLException
     */

    private Object createObjects(ResultSet resultSet) throws SQLException {

        Client client = new Client(2);
        Produs produs = new Produs(0, "", 1, 1);
        Object object = null;
        try {
            if (type.getName().equals("Model.Client")) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int budget = resultSet.getInt("budget");
                client.setId(id);
                client.setName(name);
                client.setAge(age);
                client.setBudget(budget);
                object = client;
            } else {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");
                produs.setId(id);
                produs.setPrice(price);
                produs.setStock(stock);
                produs.setName(name);
                object = produs;
            }
            return object;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return object;
    }
}

