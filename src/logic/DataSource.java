package logic;

import logic.entites.Category;
import logic.entites.Order;
import logic.entites.Product;

public class DataSource {

    private final GenericDAO<Product> productController;
    private final GenericDAO<Category> categoryController;
    private final GenericDAO<Order> orderController;

    private DataSource() {

        productController = new GenericDAO(Product.class);
        categoryController = new GenericDAO(Category.class);
        orderController = new GenericDAO(Order.class);

    }

    /**
     * Controller Factory
     * @param type termék, rendelés ...
     * @return 
     */
    public CrudService getController(String type) {

        if (type.equalsIgnoreCase("PRODUCT")) {

            return productController;

        } else if (type.equalsIgnoreCase("ORDER")) {

            return orderController;

        } else if (type.equalsIgnoreCase("CATEGORY")) {

            return categoryController;

        }
        
        return null;
    }

    private static class DataSourceHandler {

        public static final DataSource INSTANCE = new DataSource();
    }

    public static DataSource getInstance() {
        return DataSourceHandler.INSTANCE;
    }

}
