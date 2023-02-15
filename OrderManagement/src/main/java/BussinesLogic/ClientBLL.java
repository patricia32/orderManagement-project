package BussinesLogic;

import BussinesLogic.validators.ClientAgeValidator;
import BussinesLogic.validators.Validator;
import DataAcces.AbstractDAO;
import DataAcces.ClientDAO;
import Model.Client;
import Presentation.View.ClientView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validators;
    ClientView clientView;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
       // validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());
        Validator<Client> validator;

    }

    public List<Validator<Client>> getValidators() {
        return validators;
    }

    public int validate(List<Validator<Client>> validators, Client client){
        for(Validator<Client> validator: validators)
            if(validator.validate(client) == -1)

                return -1;

        return 1;
    }

    public Object findClientById(int id) {
        Object object = new Client(id);
        AbstractDAO abstractDAO = new AbstractDAO(object);
        if (object == null) {
            throw new NoSuchElementException("The Client with id =" + id + " was not found!");
        }
        return object;
    }
//    public  int insert(Client client){
//        int id = DataAcces.ClientDAO.insert(client);
//        return id;
//    }
//
//    public int delete(int clientID) throws SQLException {
//        clientID = ClientDAO.delete(clientID);
//        return clientID;
//    }
//
//    public  Client edit(Client client) throws SQLException {
//        client = ClientDAO.edit(client);
//        if (client == null) {
//            throw new NoSuchElementException("The Client with id =" + client.getId() + " was not found!");
//        }
//        return client;
//    }*/
}
