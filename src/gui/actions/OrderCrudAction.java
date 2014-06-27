/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import gui.panels.BasicEditorPanel;
import gui.MainFrame;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import logic.db.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Order;
import logic.entites.Product;

/**
 * Rendelés Action-ök.
 *
 * @author ag313w
 */
public class OrderCrudAction extends BasicAction {

    private BasicEditorPanel productEditor;

    /**
     *
     * @param editor rendelés felület elemei
     * @param productEditor termék felület elemei
     */
    public OrderCrudAction(BasicEditorPanel editor, BasicEditorPanel productEditor) {
        super(editor);
        this.productEditor = productEditor;
        //kiszűri a teljesített rendeléseket
        RowFilter<Object, Object> startsWithAFilter = new RowFilter<Object, Object>() {

            @Override
            public boolean include(Entry entry) {
                Order order = ((GenericTableModel<Order, GenericDAO<Order>>) entry.getModel()).read((int) entry.getIdentifier());
                //nem teljesített => meg kell jeleníteni
                return !order.isFullfilled();
            }
        };
        System.out.println(startsWithAFilter);

        try {
            editor.getTableSorter().setRowFilter(startsWithAFilter);
        } catch (Exception ex) {
            
        }
        addButtons();

    }
    /**
     * Gombok felvétele
     */
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
    private Action getUpdateAction() {
        return new AbstractAction(Strings.FULFILL) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (editor.getTable().getSelectedRowCount() > 0) {

                    Integer row = editor.getTable().convertRowIndexToModel(editor.getTable().getSelectedRow());

                    Order order = ((GenericTableModel<Order, GenericDAO<Order>>) editor.getTable().getModel()).read(row);

                    int value = JOptionPane.showConfirmDialog(editor.getParentFrame(), Strings.CONFIRM_FULFILL);

                    if (value == JOptionPane.YES_OPTION) {

                        if (order.isFulfillAble()) {

                            Logger.log("Teljesít", "DEBUG");

                            order.setFullfilled(true);

                            ((GenericTableModel<Order, GenericDAO<Order>>) editor.getTable().getModel()).update(order);

                            setStock(order);

                        } else {

                            MainFrame.showError(Strings.ERROR_NOT_FULFILLABLE);

                        }
                    }

                }
            }
        };
    }
    /**
     * A rendeléshez tartozó termékek raktár készletét csökkenti.
     * 
     * @param order aktuális rendelés
     */
    private void setStock(Order order) {

        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            int currentAmount = entry.getKey().getStock();
            int requestedAmount = entry.getValue();
            Product product = entry.getKey();
            product.setStock(currentAmount - requestedAmount);

            System.out.println(currentAmount);
            System.out.println(requestedAmount);
            System.out.println(currentAmount - requestedAmount);

            ((GenericTableModel<Product, GenericDAO<Product>>) productEditor.getTable().getModel()).update(product);
        }
    }

}
