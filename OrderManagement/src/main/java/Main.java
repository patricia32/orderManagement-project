import Model.Client;
import Model.Produs;
import Presentation.Controller.Controller;
import Presentation.View.View;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        View view = new View();
        view.setVisible(true);
        Controller controller = new Controller(view);

        Produs produs = new Produs(2, "TV", 1300, 7);
        Client client = new Client(3, "Criste Denis", 34, 500);
        Produs produs1 = new Produs(2, "Table", 170, 18);
        ArrayList<Object> objects = new ArrayList<>();
        //objects.add(produs1);
        objects.add(client);
        //Reflection.retrieveProperties(objects);

    }
}
