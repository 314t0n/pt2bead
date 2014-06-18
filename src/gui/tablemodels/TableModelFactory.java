package gui.tablemodels;

import logic.DataSource;
import logic.GenericDAO;
import logic.IEntity;

public class TableModelFactory {

    public static <T extends IEntity> GenericTableModel createTableModel(Class<T> type, String[] propeties) throws InstantiationException, IllegalAccessException {
        return new GenericTableModel(new GenericDAO(type), propeties);
    }

    /**
     * @deprecated 
     * @param type
     * @return 
     */
    public static GenericTableModel getTableModel(String type) {

        String[] propeties = getPropetyName(type);

        if (propeties == null || type == null) {
            throw new NullPointerException("Nincs ilyen entitás: " + type);
        }

        return new GenericTableModel(DataSource.getInstance().getController(type), propeties);

    }
    /**
     * @deprecated 
     * @param type
     * @return 
     */
    public static String[] getPropetyName(String type) {
/*
        if (type.equalsIgnoreCase("PRODUCT")) {

            return Product.getPropertyNames();

        } else if (type.equalsIgnoreCase("ORDER")) {

            return Order.getPropertyNames();

        } else if (type.equalsIgnoreCase("CATEGORY")) {

            return Category.getPropertyNames();

        }
*/
        return null;

    }

}
