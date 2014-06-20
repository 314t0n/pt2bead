/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import logic.DataSource;
import logic.Logger;
import logic.Strings;
import logic.entites.Product;

/**
 *
 * @author ag313w
 */
public class OrderCrudAction implements ICrudServiceAction {

    private final JFrame frame;

    public OrderCrudAction(JFrame frame) {

        this.frame = frame;

    }

    @Override
    public Action getCreateAction() {
        return new AbstractAction(Strings.NEW_ORDER) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {

                    Product product = new Product();

                    AddProductPanel addProductPanel = new AddProductPanel(product, DataSource.getInstance().getController("ORDER").readAll());
                    FormDialog formDialog = new FormDialog(frame, enabled, addProductPanel);

                    if (formDialog.isSaveRequired()) {

                        addProductPanel.setAttributes();

                        Logger.log("Adatok mentése", "DEBUG");

                        //table.fireTableRowsInserted(0, table.getColumnCount() - 1);
                    }

                } else {

                    MainFrame.showError(Strings.ERROR_NO_PRODUCT);

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
