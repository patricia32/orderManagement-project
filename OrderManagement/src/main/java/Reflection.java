import BussinesLogic.ClientBLL;
import DataAcces.AbstractDAO;
import Model.Client;
import Model.Produs;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Reflection {

    public static void retrieveProperties(ArrayList<Object> objects) {

        for (Object object : objects) {
            AbstractDAO<Object> abstractDAO = new AbstractDAO<>(object);
            abstractDAO.insertObject(object);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(object);
                      System.out.println(field.getName() + "=" + value);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



