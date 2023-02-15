package BussinesLogic.validators;

import Model.Client;

public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 16;
    public int validate(Client t) {
            if(t.getAge() < MIN_AGE)
                return -1;
            return 1;
    }
}
