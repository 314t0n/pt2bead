package gui.actions;

import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import logic.DataSource;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Product;

public class ProductCrudAction implements ICrudServiceAction {

    private final JFrame frame;
    private final GenericTableModel<Product, GenericDAO<Product>> table;

    /**
     * @todo category injection
     * @param frame
     * @param table
     */
    public ProductCrudAction(JFrame frame, GenericTableModel table) {

        this.frame = frame;
        this.table = table;

        if (table == null) {
            System.out.println("table null");
        }

    }
   
    @Override
    public Action getCreateAction() {
        return new AbstractAction(Strings.NEW_PRODUCT) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {

                    Product product = new Product();

                    AddProductPanel addProductPanel = new AddProductPanel(product, DataSource.getInstance().getController("CATEGORY").readAll());
                    FormDialog formDialog = new FormDialog(frame, enabled, addProductPanel);

                    if (formDialog.isSaveRequired()) {

                        addProductPanel.setAttributes();

                        Logger.log(Strings.SAVE_DATA, "DEBUG");

                        table.create((Product) addProductPanel.getModel());

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
