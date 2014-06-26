package gui.actions;

import gui.BasicEditor;
import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddOrderPanel;
import gui.panels.AddProductPanel;
import gui.panels.SingleNumberPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import logic.DataSource;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Order;
import logic.entites.Product;

/**
 * A read és update nincs megvalósítva, mivel a táblázat minden adatot
 * megjelenít és a módosítás is beállítható
 *
 * @author ag313w
 */
public class ProductCrudAction extends BasicAction {

    private Map<Product, Integer> myCart;
    private JButton jButtonCart;
    private JButton jButtonOrder;

    public ProductCrudAction(BasicEditor editor) {
        super(editor);

        myCart = new HashMap();

        addButtons();

        setEditAble(0);
        setEditAble(1);
        setEditAble(2);
        setEditAble(4);
        setEditAble(5);
        setEditAble(6);
    }

    private void setEditAble(int col) {
        ((GenericTableModel<Product, GenericDAO<Product>>) editor.getTable().getModel()).setColumnEditAble(col);
    }

    private void addButtons() {

        JButton jButtonCreate = new JButton(Strings.NEW_PRODUCT);

        jButtonCreate.setAction(getCreateAction());

        editor.addButton(jButtonCreate);

        JButton jButtonDelete = new JButton(Strings.DEL);

        jButtonDelete.setAction(getDeleteAction());

        editor.addButton(jButtonDelete);

        JButton jButtonNew = new JButton(Strings.NEW_ORDER);

        jButtonNew.setAction(newOrderAction());

        editor.addButton(jButtonNew);

        jButtonCart = new JButton(Strings.TO_CART);

        jButtonCart.setAction(cartAction());

        jButtonCart.setEnabled(false);

        editor.addButton(jButtonCart);

        jButtonOrder = new JButton(Strings.MY_CART);

        jButtonOrder.setAction(orderAction());

        jButtonOrder.setEnabled(false);

        editor.addButton(jButtonOrder);
    }

    public Action cartAction() {
        return new AbstractAction(Strings.TO_CART) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (editor.getTable().getSelectedRowCount() > 0) {

                    Integer row = editor.getTable().convertRowIndexToModel(editor.getTable().getSelectedRow());

                    Product product = ((GenericTableModel<Product, GenericDAO<Product>>) editor.getTable().getModel()).read(row);

                    SingleNumberPanel numberPanel = new SingleNumberPanel();
                    FormDialog formDialog = new FormDialog(editor.getParentFrame(), enabled, numberPanel);

                    if (formDialog.isSaveRequired()) {

                        if (product.isActive()) {

                            Integer number = numberPanel.getValue();

                            myCart.put(product, number);

                            Logger.log(myCart.toString());

                            setCartNumber();

                        } else {
                            
                            MainFrame.showError(Strings.ERROR_NOT_ACTIVE);
                            
                        }

                    }

                } else {

                    MainFrame.showError(Strings.ERROR_NO_SELECTION);

                }
            }
        };
    }

    public Action orderAction() {
        return new AbstractAction(Strings.MY_CART) {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {

                    Order order = new Order();

                    order.setProducts(myCart);

                    AddOrderPanel addOrdertPanel = new AddOrderPanel(order);
                    FormDialog formDialog = new FormDialog(editor.getParentFrame(), enabled, addOrdertPanel);

                    if (formDialog.isSaveRequired()) {

                        addOrdertPanel.setAttributes();

                        if (order.getProducts().isEmpty()) {

                            MainFrame.showError(Strings.ERROR_NO_PRODUCT);

                        } else {

                            Logger.log(Strings.SAVE_DATA, "DEBUG");

                            ((GenericTableModel<Order, GenericDAO<Order>>) editor.getTable().getModel()).create((Order) addOrdertPanel.getModel());

                        }
                        
                        myCart.clear();

                        setCartNumber();
                    }

                } else {

                    MainFrame.showError(Strings.ERROR_NO_CATEGORY);

                }
            }
        };
    }

    private void setCartNumber() {
        jButtonOrder.setText(Strings.MY_CART + " (" + myCart.size() + ")");
    }

    public Action newOrderAction() {
        return new AbstractAction(Strings.NEW_ORDER) {
            @Override
            public void actionPerformed(ActionEvent e) {

                jButtonCart.setEnabled(true);
                jButtonOrder.setEnabled(true);

                myCart.clear();

                setCartNumber();

                MainFrame.showInfo(Strings.START_SHOPPING);

            }
        };
    }

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

}
