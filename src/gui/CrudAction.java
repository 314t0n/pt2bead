package gui;

import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import logic.GenericDAO;
import logic.Logger;
import logic.entites.Category;
import logic.entites.Product;

public class CrudAction implements ICrudServiceAction {

    private final GenericDAO<Product> productController;
    private final GenericDAO<Category> categoryController;
    private final JFrame frame;
    private final GenericTableModel table;

    public CrudAction(JFrame frame, GenericTableModel table) {

        this.frame = frame;
        this.table = table;

        productController = new GenericDAO(Product.class);
        categoryController = new GenericDAO(Category.class);

    }

    @Override
    public Action getCreateAction() {
        return new AbstractAction("Új termék") {
            @Override
            public void actionPerformed(ActionEvent e) {

                GenericDAO<Product> productController = new GenericDAO(Product.class);
                GenericDAO<Category> categoryController = new GenericDAO(Category.class);

                Product product = new Product();

                AddProductPanel addProductPanel = new AddProductPanel(product, categoryController.readAll());
                FormDialog formDialog = new FormDialog(frame, enabled, addProductPanel);

                if (formDialog.isSaveRequired()) {

                    addProductPanel.setAttributes();

                    Logger.log("Adatok mentése", "DEBUG");

                    productController.create(product);

                    //table.fireTableRowsInserted(0, table.getColumnCount() - 1);

                }

            }
        };
    }

    @Override
    public Action getReadAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action getUpdateAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action getDeleteAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
