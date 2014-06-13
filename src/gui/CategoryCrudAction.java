/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.actions.ICrudServiceAction;
import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
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

public class CategoryCrudAction implements ICrudServiceAction {

    private final GenericDAO<Category> categoryController;
    private final JFrame frame;
    private final GenericTableModel table;

    public CategoryCrudAction(JFrame frame, GenericTableModel table) {

        this.frame = frame;
        this.table = table;

        categoryController = new GenericDAO(Category.class);

    }

    @Override
    public Action getCreateAction() {
        return new AbstractAction(Strings.NEW_CATEGORY) {
            @Override
            public void actionPerformed(ActionEvent e) {

                Category category = new Category();

                AddCategoryPanel addCategoryPanel = new AddCategoryPanel(category);
                FormDialog formDialog = new FormDialog(frame, enabled, addCategoryPanel);

                if (formDialog.isSaveRequired()) {

                    Logger.log("Adatok mentése", "DEBUG");

                    addCategoryPanel.setAttributes();

                    DataSource.getInstance().getController("CATEGORY").create(category);

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
