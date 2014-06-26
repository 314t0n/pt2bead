/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.BasicEditor;
import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Category;

public class CategoryCrudAction extends BasicAction {

    public CategoryCrudAction(BasicEditor editor) {
        super(editor);

        addButtons();
    }

    private void addButtons() {

        JButton jButtonCreate = new JButton(Strings.NEW_CATEGORY);

        jButtonCreate.setAction(getCreateAction());

        editor.addButton(jButtonCreate);

        JButton jButtonDelete = new JButton(Strings.DEL);

        jButtonDelete.setAction(getDeleteAction());

        editor.addButton(jButtonDelete);

    }

    public Action getCreateAction() {
        return new AbstractAction(Strings.NEW_CATEGORY) {
            @Override
            public void actionPerformed(ActionEvent e) {

                Category category = new Category();

                AddCategoryPanel addCategoryPanel = new AddCategoryPanel(category);
                FormDialog formDialog = new FormDialog(editor.getParentFrame(), enabled, addCategoryPanel);

                if (formDialog.isSaveRequired()) {

                    Logger.log("Adatok mentése", "DEBUG");

                    addCategoryPanel.setAttributes();

                    ((GenericTableModel<Category, GenericDAO<Category>>) editor.getTable().getModel()).create((Category) addCategoryPanel.getModel());

                }
            }
        };
    }

}
