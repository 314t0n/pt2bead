package gui.tablemodels;

import logic.GenericDAO;
import logic.entites.Product;

public class TableModelFactory {

    public static GenericTableModel getTableModel(String type) {

        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("PRODUCT")) {
            
            return new GenericTableModel(new GenericDAO(Product.class), Product.getPropertyNames());
       
        }
        return null;
    }

}
