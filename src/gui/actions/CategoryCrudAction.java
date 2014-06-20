/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import logic.Logger;
import logic.Strings;
import logic.entites.Category;

public class CategoryCrudAction implements ICrudServiceAction {

    private final JFrame frame;

    public CategoryCrudAction(JFrame frame) {
        this.frame = frame;
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
