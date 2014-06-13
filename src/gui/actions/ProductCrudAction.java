package gui.actions;

import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import logic.DataSource;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Category;
import logic.entites.Product;

public class ProductCrudAction implements ICrudServiceAction {
    
    private final GenericDAO<Product> productController;
    private final GenericDAO<Category> categoryController;
    private final JFrame frame;
    private final GenericTableModel table;
    
    public ProductCrudAction(JFrame frame, GenericTableModel table) {
        
        this.frame = frame;
        this.table = table;
        
        productController = new GenericDAO(Product.class);
        categoryController = new GenericDAO(Category.class);
        
    }
    
    @Override
    public Action getCreateAction() {
        return new AbstractAction(Strings.NEW_PRODUCT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {
                    
                    GenericDAO<Product> productController = new GenericDAO(Product.class);
                    GenericDAO<Category> categoryController = new GenericDAO(Category.class);
                    
                    Product product = new Product();
                    
                    AddProductPanel addProductPanel = new AddProductPanel(product, DataSource.getInstance().getController("CATEGORY").readAll());
                    FormDialog formDialog = new FormDialog(frame, enabled, addProductPanel);
                    
                    if (formDialog.isSaveRequired()) {
                        
                        addProductPanel.setAttributes();
                        
                        Logger.log(Strings.SAVE_DATA, "DEBUG");
                        
                        productController.create(product);

                        //table.fireTableRowsInserted(0, table.getColumnCount() - 1);
                    }
                    
                } else {
                    
                    MainFrame.showError(Strings.ERROR_NO_CATEGORY);
                    
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
           return new AbstractAction(Strings.MOD) {
             @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        };
    }
    
    @Override
    public Action getDeleteAction() {
        return new AbstractAction(Strings.DEL) {
             @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        };
    }
    
}
