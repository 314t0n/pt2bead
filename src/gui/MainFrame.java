package gui;

import gui.actions.ICrudServiceAction;
import gui.actions.OrderCrudAction;
import gui.actions.ProductCrudAction;
import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
import gui.tablemodels.GenericTableModel;
import gui.tablemodels.TableModelFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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

    private final JTabbedPane jTabbedPane;
    private final JPanel productPanel;
    private final JPanel categoryPanel;
    private final JPanel orderPanel;

    private ICrudServiceAction productAction;
    private ICrudServiceAction orderAction;
    private ICrudServiceAction categoryAction;

    private GenericTableModel<Product, GenericDAO<Product>> productTableModel;
    private GenericTableModel<Product, GenericDAO<Product>> categoryTableModel;
    private GenericTableModel<Order, GenericDAO<Order>> orderTableModel;
    private TableRowSorter<GenericTableModel<Product, GenericDAO<Product>>> productTableSorter;
    private TableRowSorter<GenericTableModel<Order, GenericDAO<Order>>> categoryTableSorter;
    private TableRowSorter<GenericTableModel<Order, GenericDAO<Order>>> orderTableSorter;

    public MainFrame() throws HeadlessException {

        setTitle(Strings.APP_NAME);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(40, 60);
        setSize(700, 300);
        setLayout(new BorderLayout());

        JToolBar jToolBar = new JToolBar();
        jTabbedPane = new JTabbedPane();
        productPanel = new JPanel();
        categoryPanel = new JPanel();
        orderPanel = new JPanel();

        productAction = new ProductCrudAction(this, productTableModel);
        categoryAction = new CategoryCrudAction(this, categoryTableModel);
        orderAction = new OrderCrudAction(this, orderTableModel);

        getContentPane().add(jToolBar, BorderLayout.NORTH);

        setControllerPanels();
        
        setMenu();

        setProductTable();

        if (DataSource.getInstance().getController("ORDER").rowCount() > 0) {
            setOrderable();
        }

        jTabbedPane.addTab(Strings.PRODUCT, new JScrollPane(productPanel));
        jTabbedPane.addTab(Strings.ORDER, new JScrollPane(orderPanel));
        jTabbedPane.addTab(Strings.CATEGORY, new JScrollPane(categoryPanel));

        getContentPane().add(new JScrollPane(jTabbedPane), BorderLayout.CENTER);
    }

    private void setControllerPanels() {
        
        setControllPanel(productPanel, productAction);
        setControllPanel(categoryPanel, categoryAction);
        setControllPanel(orderPanel, orderAction);
        
    }

    private void setControllPanel(JPanel panel, ICrudServiceAction action) {

        GridLayout layout = new GridLayout(2, 1);

        panel.setLayout(layout);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btn1 = new JButton(Strings.NEW);
        JButton btn2 = new JButton(Strings.DEL);
        JButton btn3 = new JButton(Strings.MOD);

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);

        btn1.setAction(action.getCreateAction());
        btn2.setAction(action.getDeleteAction());
        btn3.setAction(action.getUpdateAction());

        panel.add(buttonPanel);

    }

    //ez is mehetni a factoryba
    private void setProductTable() {

        try {

            JTable productTable = new JTable();

            productTableModel = TableModelFactory.getTableModel("PRODUCT");
            productTableSorter = new TableRowSorter(productTableModel);

            //productTableModel.setColumnEditAble(2);        

              productTable.setModel(productTableModel);
            productTable.setRowSorter(productTableSorter);            
          

            productPanel.add(productTable, BorderLayout.CENTER);

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

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setMenu() {

        JMenuBar jMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("File");

        JMenuItem addProduct = new JMenuItem(productAction.getCreateAction());
        JMenuItem addOrder = new JMenuItem(orderAction.getCreateAction());
        JMenuItem addCategory = new JMenuItem(categoryAction.getCreateAction());
        JMenuItem close = new JMenuItem(closeAction);

        mainMenu.add(addProduct);
        mainMenu.add(addOrder);
        mainMenu.add(addCategory);
        mainMenu.addSeparator();
        mainMenu.add(close);
        jMenuBar.add(mainMenu);

        setJMenuBar(jMenuBar);

    }

    private Action closeAction = new AbstractAction("Kilépés") {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };

}
