package gui.actions;

import gui.panels.BasicEditorPanel;
import gui.MainFrame;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import javax.persistence.RollbackException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import logic.db.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Product;

/**
 * Alapvető szerkesztési felülethez tartozó Action-ök.
 * 
 * @author  
 */
abstract public class BasicAction {

    protected BasicEditorPanel editor;
    /**
     * 
     * @param editor tartalmazza a felület elemeit (táblázat, gombok)
     */
    public BasicAction(BasicEditorPanel editor) {
        this.editor = editor;
    }
    /**
     * Szerkeszthető oszlopok beállítása
     * 
     * @param col melyik oszlop
     */
    protected void setEditAble(int col) {
        ((GenericTableModel<Product, GenericDAO<Product>>) editor.getTable().getModel()).setColumnEditAble(col);
    }
    /**
     * 
     * @return kijelölt elem(ek) törlése a táblázatból
     */
    public Action getDeleteAction() {
        return new AbstractAction(Strings.DEL) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int[] rows = editor.getTable().getSelectedRows();

                    for (int i = 0; i < rows.length; i++) {
                        Logger.log(Integer.toString(rows[i]), "DEBUG");
                        rows[i] = editor.getTable().convertRowIndexToModel(rows[i]);
                        Logger.log(Integer.toString(rows[i]), "DEBUG");
                    }

                    for (int i = 0; i < rows.length; i++) {

                        int deleteIndex = rows[i];
                        ((GenericTableModel<Product, GenericDAO<Product>>) editor.getTable().getModel()).delete(deleteIndex);

                    }

                } catch (ArrayIndexOutOfBoundsException ex) {
                    //nem volt kijelölve semmi
                } catch (RollbackException ex) {
                    MainFrame.showError(Strings.ERROR_REFERENCE);
                }
            }
        };
    }

}
