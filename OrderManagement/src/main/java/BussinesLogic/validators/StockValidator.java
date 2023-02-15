package BussinesLogic.validators;

import Model.Produs;

public class StockValidator implements Validator<Produs>{
    @Override
    public int validate(Produs t) {
        if(t.getStock() < 0)
            return -1;
        return 1;
    }
}
