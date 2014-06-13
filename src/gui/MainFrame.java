package gui;

import gui.actions.ICrudServiceAction;
import gui.actions.OrderCrudAction;
import gui.actions.ProductCrudAction;
import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
import gui.tablemodels.GenericTableModel;
import gui.tablemodels.TableModelFactory;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableRowSorter;
import logic.DataSource;
import logic.GenericDAO;
import logic.Logger;
import logic.Strings;
import logic.entites.Category;
import logic.entites.Order;
import logic.entites.Product;

public class MainFrame extends JFrame {

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private ICrudServiceAction productAction;
    private ICrudServiceAction orderAction;

    private GenericTableModel<Product, GenericDAO<Product>> productTableModel;
    private GenericTableModel<Order, GenericDAO<Order>> orderTableModel;
    private TableRowSorter<GenericTableModel<Product, GenericDAO<Product>>> productTableSorter;
    private TableRowSorter<GenericTableModel<Order, GenericDAO<Order>>> orderTableSorter;

    public MainFrame() throws HeadlessException {

        setTitle(Strings.APP_NAME);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(40, 60);
        setSize(700, 300);
        setLayout(new BorderLayout());
        JToolBar jToolBar = new JToolBar();

        getContentPane().add(jToolBar, BorderLayout.NORTH);

        productAction = new ProductCrudAction(this, productTableModel);
        orderAction = new OrderCrudAction(this, orderTableModel);

        setMenu();

        if (DataSource.getInstance().getController("PRODUCT").rowCount() > 0) {
            setProductTable();
        }

        if (DataSource.getInstance().getController("ORDER").rowCount() > 0) {
            setOrderable();
        }

    }
    //ez is mehetni a factoryba
    private void setProductTable() {

        try {

            JTable productTable = new JTable();

            productTableModel = TableModelFactory.getTableModel("PRODUCT");
            productTableSorter = new TableRowSorter<>(productTableModel);

            productTableModel.setColumnEditAble(2);

            productTable.setRowSorter(productTableSorter);
            productTable.setModel(productTableModel);

            getContentPane().add(new JScrollPane(productTable), BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setOrderable() {

        try {

            JTable orderTable = new JTable();

            orderTableModel = TableModelFactory.getTableModel("ORDER");
            orderTableSorter = new TableRowSorter<>(orderTableModel);

            orderTableModel.setColumnEditAble(2);

            orderTable.setRowSorter(orderTableSorter);
            orderTable.setModel(orderTableModel);

            getContentPane().add(new JScrollPane(orderTable), BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setMenu() {

        JMenuBar jMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("File");

        JMenuItem addProduct = new JMenuItem(productAction.getCreateAction());
        JMenuItem addOrder = new JMenuItem(orderAction.getCreateAction());
        JMenuItem addCategory = new JMenuItem(addCategoryAction);
        JMenuItem close = new JMenuItem(closeAction);

        mainMenu.add(addProduct);
        mainMenu.add(addOrder);
        mainMenu.add(addCategory);
        mainMenu.addSeparator();
        mainMenu.add(close);
        jMenuBar.add(mainMenu);

        setJMenuBar(jMenuBar);

    }

    private Action addCategoryAction = new AbstractAction("Új kategória") {
        @Override
        public void actionPerformed(ActionEvent e) {

            Category category = new Category();

            AddCategoryPanel addCategoryPanel = new AddCategoryPanel(category);
            FormDialog formDialog = new FormDialog(MainFrame.this, enabled, addCategoryPanel);

            if (formDialog.isSaveRequired()) {

                Logger.log("Adatok mentése", "DEBUG");

                addCategoryPanel.setAttributes();

                DataSource.getInstance().getController("CATEGORY").create(category);

            }

        }
    };

    private Action closeAction = new AbstractAction("Kilépés") {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };

}
