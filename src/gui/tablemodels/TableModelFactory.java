package gui.tablemodels;

import logic.DataSource;
import logic.entites.Order;
import logic.entites.Product;

public class TableModelFactory {

    public static GenericTableModel getTableModel(String type) {

        if (type.equalsIgnoreCase("PRODUCT")) {
            
            return new GenericTableModel(DataSource.getInstance().getController(type), Product.getPropertyNames());
       
        }else if (type.equalsIgnoreCase("ORDER")) {
            
            return new GenericTableModel(DataSource.getInstance().getController(type), Order.getPropertyNames());
       
        }
        
        return null;
    }

}
