/**
 * metoda pentru controlul interfetelor
 */
package Presentation.Controller;

import BussinesLogic.ClientBLL;
import BussinesLogic.ProdusBLL;
import DataAcces.AbstractDAO;
import DataAcces.OrderDAO;
import Model.Client;
import Model.Comanda;
import Model.Produs;
import Presentation.View.ClientView;
import Presentation.View.OrderView;
import Presentation.View.ProductView;
import Presentation.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class Controller {

    private final View controllerView;
    private ClientView controllerClientView;
    private ProductView controllerProductView;
    private OrderView controllerOrderView;

    public Controller(View view){
        controllerView = view;
        view.addClientListener(new ClientListener());
        view.addProductListener(new ProductListener());
        view.addOrderListener(new OrderListener());

    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de client
     */
    public class ClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controllerView.setVisible(false);
            ClientView clientView = new ClientView();
            clientView.setVisible(true);
            controllerClientView = clientView;

            clientView.addClientListener(new add_clientListener());
            clientView.editClientListener(new edit_clientListener());
            clientView.deleteClientListener(new delete_clientListener());
            clientView.mouseListenerClient(new mouseListenerClient());
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de produs
     */
    public class ProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controllerView.setVisible(false);
            ProductView productView = new ProductView(controllerView);
            productView.setVisible(true);
            controllerProductView = productView;

            productView.addProdusListener(new add_productListener());
            productView.editProdusListener(new edit_productListener());
            productView.deleteProdusListener(new delete_produsListener());
            productView.mouseListenerProdus(new mouseListenerProdus());
            
            
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de order
     */
    public class OrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controllerView.setVisible(false);
            OrderView orderView = new OrderView(controllerView);
            orderView.setVisible(true);
            controllerOrderView = orderView;

            orderView.mouseListenerOrderProdus(new mouse_ListenerOrderProdus());
            orderView.mouseListenerOrderClient(new mouse_ListenerOrderClient());
            orderView.addOrderListener(new orderCreateListener());
        }
    }

    public class orderCreateListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String cliendID = controllerOrderView.getClientID();
            String prodID = controllerOrderView.getProdID();
            String stock = controllerOrderView.getStock();
            String budget = controllerOrderView.getBudget();
            if(controllerOrderView.getClientID().equals("")){
                controllerOrderView.showError("Select a client.");
                return;
            }
            if(controllerOrderView.getProdID().equals("")) {
                controllerOrderView.showError("Select a product.");
                return;
            }
            Comanda comanda = new Comanda(Integer.parseInt(cliendID), Integer.parseInt(prodID), Integer.parseInt(stock));
            try {
                int ver = OrderDAO.verify(Integer.parseInt(budget), comanda);
                if(ver == 1) {
                    AbstractDAO<Object> Add = new AbstractDAO<>(comanda);
                    Add.insertObject(comanda);
                    OrderDAO.createBill(comanda);
                    controllerOrderView.setVisible(false);
                    controllerView.setVisible(true);
                }
                if (ver == -3)
                    controllerOrderView.showError("We couldn't find this product or client.");
               else if(ver < 0)
                    controllerOrderView.showError("Select less pieces.");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de add client
     */
    public class add_clientListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Client client = new Client(1,"", 1, 1);
            if (controllerClientView.getName().equals("") || controllerClientView.getAge().equals("") || controllerClientView.getBudget().equals("")) {
                controllerClientView.showError("Complete all fields.");
                return;
            }
            client.setId(Integer.parseInt(controllerClientView.getID()));
            client.setName(controllerClientView.getName());
            client.setAge(Integer.parseInt(controllerClientView.getAge()));
            client.setBudget(Integer.parseInt(controllerClientView.getBudget()));
            ClientBLL clientBLL = new ClientBLL();
            if(clientBLL.validate(clientBLL.getValidators(), client) == -1){
                controllerClientView.showError("The minimal required age is 16!");
                return;
            }
            AbstractDAO<Object> Add = new AbstractDAO<>(client);
            Add.insertObject(client);
            controllerClientView.setVisible(false);
            controllerView.setVisible(true);
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de add produs
     */
    public class add_productListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (controllerProductView.getName().equals("") || controllerProductView.getprice().equals("") || controllerProductView.getStock().equals("")) {
                controllerProductView.showError("Complete all fields.");
                return;
            }
            Produs produs = new Produs(1,"", 1, 1);
            produs.setId(Integer.parseInt(controllerProductView.getID()));
            produs.setName(controllerProductView.getName());
            produs.setPrice(Integer.parseInt(controllerProductView.getprice()));
            produs.setStock(Integer.parseInt(controllerProductView.getStock()));

            ProdusBLL produsBLL = new ProdusBLL();
            if(produsBLL.validate(produsBLL.getValidators(), produs) == -1) {
                controllerProductView.showError("Stock must be a positive number.");
                return;
            }
            AbstractDAO<Object> Add = new AbstractDAO<>(produs);
            Add.insertObject(produs);
            controllerProductView.setVisible(false);
            controllerView.setVisible(true);
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de edit client
     */
    public class edit_clientListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (controllerClientView.getName().equals("") || controllerClientView.getAge().equals("") || controllerClientView.getBudget().equals("")) {
                controllerClientView.showError("Complete all fields.");
                return;
            }
            Client client = new Client(1,"", 1, 1);
            client.setId(Integer.parseInt(controllerClientView.getID()));
            client.setName(controllerClientView.getName());
            client.setAge(Integer.parseInt(controllerClientView.getAge()));
            client.setBudget(Integer.parseInt(controllerClientView.getBudget()));
            AbstractDAO<Object> Edit = new AbstractDAO<>(client);
            Edit.editObject(client);
            controllerClientView.setVisible(false);
            controllerView.setVisible(true);

        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de edit produs
     */
    public class edit_productListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (controllerProductView.getName().equals("") || controllerProductView.getprice().equals("") || controllerProductView.getStock().equals("")) {
                controllerProductView.showError("Complete all fields.");
                return;
            }
            Produs produs = new Produs(1,"", 1, 1);
            produs.setId(Integer.parseInt(controllerProductView.getID()));
            produs.setName(controllerProductView.getName());
            produs.setPrice(Integer.parseInt(controllerProductView.getprice()));
            produs.setStock(Integer.parseInt(controllerProductView.getStock()));
            AbstractDAO<Object> Edit = new AbstractDAO<>(produs);
            Edit.editObject(produs);
            controllerProductView.setVisible(false);
            controllerView.setVisible(true);
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de delect client
     */
    public class delete_clientListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            if (controllerClientView.getName().equals("") || controllerClientView.getAge().equals("") || controllerClientView.getBudget().equals("")) {
                controllerClientView.showError("Complete all fields.");
                return;
            }
            Client client = new Client(1,"", 1, 1);
            client.setId(Integer.parseInt(controllerClientView.getID()));
            client.setName(controllerClientView.getName());
            client.setAge(Integer.parseInt(controllerClientView.getAge()));
            client.setBudget(Integer.parseInt(controllerClientView.getBudget()));
            AbstractDAO<Object> Delete = new AbstractDAO<>(client);
            Delete.deleteObject(client);
            controllerClientView.setVisible(false);
            controllerView.setVisible(true);
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru butonul de delete produs
     */
    public class delete_produsListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (controllerProductView.getName().equals("") || controllerProductView.getprice().equals("") || controllerProductView.getStock().equals("")) {
                controllerProductView.showError("Complete all fields.");
                return;
            }
            Produs produs = new Produs(1,"", 1, 1);
            produs.setId(Integer.parseInt(controllerProductView.getID()));
            produs.setName(controllerProductView.getName());
            produs.setPrice(Integer.parseInt(controllerProductView.getprice()));
            produs.setStock(Integer.parseInt(controllerProductView.getStock()));
            AbstractDAO<Object> Delete = new AbstractDAO<>(produs);
            Delete.deleteObject(produs);
            controllerProductView.setVisible(false);
            controllerView.setVisible(true);
        }
    }

    /**
     * clasa ce seteaza ActionListener pentru tabelul de clienti
     */

    public class mouseListenerClient implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Client client = controllerClientView.jTable1MouseClicked();
            controllerClientView.setId(String.valueOf(client.getId()));
            controllerClientView.setName(client.getName());
            controllerClientView.setAge(String.valueOf(client.getAge()));
            controllerClientView.setBudget(String.valueOf(client.getBudget()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * clasa ce seteaza ActionListener pentru tabelul de produse
     */
    public class mouseListenerProdus implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Produs produs = controllerProductView.jTable1MouseClicked();
            controllerProductView.setId(String.valueOf(produs.getId()));
            controllerProductView.setName(produs.getName());
            controllerProductView.setPrice(String.valueOf(produs.getPrice()));
            controllerProductView.setStock(String.valueOf(produs.getStock()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * clasa ce seteaza ActionListener pentru tabelul de produse din comanda
     */
    public class mouse_ListenerOrderProdus implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            Produs produs = controllerOrderView.jTable1MouseClicked();
            controllerOrderView.setProdId(String.valueOf(produs.getId()));
            controllerOrderView.setProdName(produs.getName());
            controllerOrderView.setPrice(String.valueOf(produs.getPrice()));
            controllerOrderView.setStock(String.valueOf(produs.getStock()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * clasa ce seteaza ActionListener pentru tabelul de clienti din comanda
     */
    public class mouse_ListenerOrderClient implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            Client client = controllerOrderView.jTable3MouseClicked();
            controllerOrderView.setClientId(String.valueOf(client.getId()));
            controllerOrderView.setClientName(client.getName());
            controllerOrderView.setAge(String.valueOf(client.getAge()));
            controllerOrderView.setBudget(String.valueOf(client.getBudget()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
