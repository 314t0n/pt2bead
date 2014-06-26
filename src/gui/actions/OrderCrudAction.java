/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.BasicEditor;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import logic.DataSource;
import logic.GenericDAO;
import logic.Strings;
import logic.entites.Order;
import logic.entites.Product;

/**
 *
 * @author ag313w
 */
public class OrderCrudAction extends BasicAction {

    public OrderCrudAction(BasicEditor editor) {
        super(editor);
        addButtons();
    }

    private void addButtons() {

        JButton jButtonFulfill = new JButton(Strings.FULFILL);

        jButtonFulfill.setAction(getUpdateAction());

        editor.addButton(jButtonFulfill);

        JButton jButtonDelete = new JButton(Strings.DEL);

        jButtonDelete.setAction(getDeleteAction());

        editor.addButton(jButtonDelete);

    }

    /**
     * A teljesíthető megrendeléseket lehessen teljesíteni azok kiválasztásával.
     * Teljesítéskor a program kérjen megerősítést, majd automatikusan módosítsa
     * a raktárkészletet is. A teljesített megrendelések többé ne legyenek
     * láthatóak.
     *
     * @return
     */
    public Action getUpdateAction() {
        return new AbstractAction(Strings.MOD) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (editor.getTable().getSelectedRowCount() > 0) {

                    Integer row = editor.getTable().convertRowIndexToModel(editor.getTable().getSelectedRow());

                    Order order = ((GenericTableModel<Order, GenericDAO<Order>>) editor.getTable().getModel()).read(row);

                    JOptionPane optionPane = new JOptionPane(Strings.CONFIRM,
                            JOptionPane.QUESTION_MESSAGE,
                            JOptionPane.YES_NO_OPTION);

                    int value = ((Integer) optionPane.getValue()).intValue();

                    if (value == JOptionPane.YES_OPTION) {

                        order.setFullfilled(true);

                        ((GenericTableModel<Order, GenericDAO<Order>>) editor.getTable().getModel()).update(order);

                        setStock(order);

                    }

                }
            }
        };
    }

    private void setStock(Order order) {

        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            int currentAmount = entry.getKey().getStock();
            int requestedAmount = entry.getValue();
            entry.getKey().setStock(currentAmount - requestedAmount);
            DataSource.getInstance().getController("Product").update(entry.getKey());
        }
    }

}
