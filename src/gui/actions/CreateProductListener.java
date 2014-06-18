package gui.actions;

import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import logic.DataSource;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Product;
/**
 * @deprecated 
 * @author ag313w
 */
public class CreateProductListener extends AbstractAction {

    private final JFrame frame;
    private final GenericTableModel<Product, GenericDAO<Product>> table;

    /**
     * @todo category injection
     * @param frame
     * @param table
     */
    public CreateProductListener(JFrame frame, GenericTableModel table) {

        this.frame = frame;
        this.table = table;

        if (table == null) {
            System.out.println("table null");
        }

    }
    Product product;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {

            product = new Product();

            AddProductPanel addProductPanel = new AddProductPanel(product, DataSource.getInstance().getController("CATEGORY").readAll());
            FormDialog formDialog = new FormDialog(frame, true, addProductPanel);

            if (formDialog.isSaveRequired()) {

                addProductPanel.setAttributes();

                Logger.log(Strings.SAVE_DATA, "DEBUG");
                try {

                    table.create((Product) addProductPanel.getModel());

                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                    if (table == null) {
                        System.out.println("table mull");
                    }
                    if (product == null) {
                        System.out.println("product");
                    }
                }
            }

        } else {

            MainFrame.showError(Strings.ERROR_NO_CATEGORY);

        }
    }
}
