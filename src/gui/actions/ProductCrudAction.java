package gui.actions;

import gui.BasicEditor;
import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import logic.DataSource;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Product;

/**
 * A read egyelőre nincs megvalósítva, mivel a táblázat minden adatot
 * megjelenít.
 *
 * @author ag313w
 */
public class ProductCrudAction extends BasicAction implements ICrudServiceAction {

    public ProductCrudAction(BasicEditor editor) {
        super(editor);

        addButtons();
    }

    private void addButtons() {

        JButton jButtonCreate = new JButton(Strings.NEW_PRODUCT);
  
        jButtonCreate.setAction(getCreateAction());

        editor.addButton(jButtonCreate);

        JButton jButtonDelete = new JButton(Strings.DEL);

        jButtonDelete.setAction(getDeleteAction());

        editor.addButton(jButtonDelete);
    }

    @Override
    public Action getCreateAction() {
        return new AbstractAction(Strings.NEW_PRODUCT) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {

                    Product product = new Product();

                    AddProductPanel addProductPanel = new AddProductPanel(product, DataSource.getInstance().getController("CATEGORY").readAll());
                    FormDialog formDialog = new FormDialog(editor.getParentFrame(), enabled, addProductPanel);

                    if (formDialog.isSaveRequired()) {

                        addProductPanel.setAttributes();

                        Logger.log(Strings.SAVE_DATA, "DEBUG");

                        ((GenericTableModel<Product, GenericDAO<Product>>) editor.getTable().getModel()).create((Product) addProductPanel.getModel());

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
                try {

                    int[] rows = editor.getTable().getSelectedRows();

                
                    for (int i = 0; i < rows.length; i++) {
                        System.out.println(rows[i]);
                        rows[i] = editor.getTable().convertRowIndexToModel(rows[i]);
                        System.out.println(rows[i]);
                    }

                    for (int i = 0; i < rows.length; i++) {

                        int deleteIndex = rows[i];
                        ((GenericTableModel<Product, GenericDAO<Product>>) editor.getTable().getModel()).delete(deleteIndex);

                    }

                } catch (ArrayIndexOutOfBoundsException ex) {
                    //nem volt kijelölve semmi
                }
            }
        };
    }

}
