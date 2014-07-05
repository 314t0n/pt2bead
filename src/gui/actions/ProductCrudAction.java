package gui.actions;

import gui.panels.BasicEditorPanel;
import gui.MainFrame;
import gui.dialogs.FormDialog;
import gui.panels.AddOrderPanel;
import gui.panels.AddProductPanel;
import gui.panels.SingleNumberPanel;
import gui.tablemodels.GenericTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import logic.db.DataSource;
import logic.db.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Order;
import logic.entites.Product;

/**
 * Termék Action-ök.
 * @author  
 */
public class ProductCrudAction extends BasicAction {

    private Map<Product, Integer> myCart;
    private JButton jButtonCart;
    private JButton jButtonOrder;
    private BasicEditorPanel orderEditor;
    
    /**
     * 
     * @param editor termék elemek
     * @param orderEditor rendelés elemek
     */
    public ProductCrudAction(BasicEditorPanel editor, BasicEditorPanel orderEditor) {
        super(editor);

        myCart = new HashMap();
        this.orderEditor = orderEditor;
        addButtons();

        editor.getTable().getModel().addTableModelListener(tableModifiedListener);

        List<RowSorter.SortKey> sortKeys
                = new ArrayList();
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

        editor.getTableSorter().setSortKeys(sortKeys);

        setEditAble(0);
        setEditAble(1);
        setEditAble(2);
        setEditAble(4);
        setEditAble(5);
        setEditAble(6);
    }
    /**
     * Módosítás esetén értesíti a rendelések táblázatot
     */
    private final TableModelListener tableModifiedListener = new TableModelListener() {

        @Override
        public void tableChanged(TableModelEvent e) {
            ((GenericTableModel<Order, GenericDAO<Order>>) orderEditor.getTable().getModel()).fireTableDataChanged();
        }

    };
    /**
     * Gombok felvétele
     */
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

        editor.addButton(jButtonCart);

        jButtonOrder = new JButton(Strings.MY_CART);

        jButtonOrder.setAction(orderAction());

        editor.addButton(jButtonOrder);

        setCartEnabled(false);

    }
    /**
     * Kosár 
     * @param b engedélyez/tilt
     */
    private void setCartEnabled(boolean b) {
        jButtonCart.setEnabled(b);
        jButtonOrder.setEnabled(b);

    }
    /**
     * Kijelölt termék elhelyezése a kosárba, ha az aktív
     * 
     * @return 
     */
    private Action cartAction() {
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
    /**
     * Rendelés leadása
     * Kosár ürítése
     * 
     * @return 
     */
    private Action orderAction() {
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

                            order.setDate(Calendar.getInstance().getTime());

                            Logger.log(Strings.SAVE_DATA, "DEBUG");

                            ((GenericTableModel<Order, GenericDAO<Order>>) orderEditor.getTable().getModel()).create((Order) addOrdertPanel.getModel());

                        }

                        myCart.clear();

                        setCartNumber();

                        setCartEnabled(false);
                    }

                } else {

                    MainFrame.showError(Strings.ERROR_NO_CATEGORY);

                }
            }
        };
    }
    /**
     * Kosár gomb számláló
     */
    private void setCartNumber() {
        jButtonOrder.setText(Strings.MY_CART + " (" + myCart.size() + ")");
    }
    /**
     * Új rendelés init
     * @return 
     */
    private Action newOrderAction() {
        return new AbstractAction(Strings.NEW_ORDER) {
            @Override
            public void actionPerformed(ActionEvent e) {

                setCartEnabled(true);

                myCart.clear();

                setCartNumber();

                MainFrame.showInfo(Strings.START_SHOPPING);

            }
        };
    }
    /**
     * Új termék felvétele
     * @return 
     */
    private Action getCreateAction() {
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
