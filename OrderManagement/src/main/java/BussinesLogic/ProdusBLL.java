package BussinesLogic;

import BussinesLogic.validators.StockValidator;
import BussinesLogic.validators.Validator;
import Model.Produs;
import java.util.ArrayList;
import java.util.List;

public class ProdusBLL {
    private final List<Validator<Produs>> validators;

    public ProdusBLL() {
        validators = new ArrayList<>();
         validators.add(new StockValidator());
    }

    public List<Validator<Produs>> getValidators() {
        return validators;
    }

    public int validate(List<Validator<Produs>> validators, Produs produs){
        for(Validator<Produs> validator: validators)
            if(validator.validate(produs) == -1)
                return -1;

        return 1;
    }
}
