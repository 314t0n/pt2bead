/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.panels.BasicEditorPanel;
import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import logic.db.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Category;
import logic.entites.Product;
/**
 * Kategória szerkesztő felülethez tartozó Action-ök.
 * 
 * @author  
 */
public class CategoryCrudAction extends BasicAction {

    private BasicEditorPanel productEditor;
    /**
     * 
     * @param editor kategória felület elemei
     * @param productEditor termék felület elemei
     */
    public CategoryCrudAction(BasicEditorPanel editor, BasicEditorPanel productEditor) {
        super(editor);
        this.productEditor = productEditor;
        
        editor.getTable().getModel().addTableModelListener(tableModifiedListener);
        
        addButtons();

        setEditAble(0);
    }
   
    private final TableModelListener tableModifiedListener = new TableModelListener() {

        @Override
        public void tableChanged(TableModelEvent e) {     
            ((GenericTableModel<Product, GenericDAO<Product>>) productEditor.getTable().getModel()).fireTableDataChanged();           
        }

    };
    /**
     * Gombok felvétele/beállítása
     */
    private void addButtons() {

        JButton jButtonCreate = new JButton(Strings.NEW_CATEGORY);

        jButtonCreate.setAction(getCreateAction());

        editor.addButton(jButtonCreate);

        JButton jButtonDelete = new JButton(Strings.DEL);

        jButtonDelete.setAction(getDeleteAction());

        editor.addButton(jButtonDelete);

    }
    /**
     * 
     * @return új kategória létrehozása
     */
    private Action getCreateAction() {
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
