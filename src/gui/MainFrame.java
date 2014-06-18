package gui;

import gui.actions.CategoryCrudAction;
import gui.actions.ICrudServiceAction;
import gui.actions.OrderCrudAction;
import gui.actions.ProductCrudAction;
import gui.dialogs.FormDialog;
import gui.panels.AddProductPanel;
import gui.tablemodels.GenericTableModel;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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

    private final int SIZE_X = 700;
    private final int SIZE_Y = 500;

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
        setSize(SIZE_X, SIZE_Y);
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

        setMenu();
        
        Product product = new Product();

        BasicEditor<Product, GenericDAO<Product>> productEditor = null;

        productEditor = new BasicEditor(product, this);

        jTabbedPane.addTab(Strings.PRODUCT, new JScrollPane(productEditor));
        /*
         jTabbedPane.addTab(Strings.PRODUCT, new JScrollPane(productPanel));
         jTabbedPane.addTab(Strings.ORDER, new JScrollPane(orderPanel));
         jTabbedPane.addTab(Strings.CATEGORY, new JScrollPane(categoryPanel));
         */
        getContentPane().add(jTabbedPane, BorderLayout.CENTER);
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

    private Action saveAction = new AbstractAction(Strings.NEW_PRODUCT) {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (DataSource.getInstance().getController("CATEGORY").rowCount() > 0) {

                GenericDAO<Product> productController = new GenericDAO(Product.class);
                GenericDAO<Category> categoryController = new GenericDAO(Category.class);

                Product product = new Product();

                AddProductPanel addProductPanel = new AddProductPanel(product, DataSource.getInstance().getController("CATEGORY").readAll());
                FormDialog formDialog = new FormDialog(MainFrame.this, enabled, addProductPanel);

                if (formDialog.isSaveRequired()) {

                    addProductPanel.setAttributes();

                    Logger.log(Strings.SAVE_DATA, "DEBUG");

                    productTableModel.create(product);
                }

            } else {

                MainFrame.showError(Strings.ERROR_NO_CATEGORY);

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
