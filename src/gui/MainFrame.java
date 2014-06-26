package gui;

import gui.actions.BasicAction;
import gui.actions.CategoryCrudAction;
import gui.actions.OrderCrudAction;
import gui.actions.ProductCrudAction;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import logic.GenericDAO;
import logic.Strings;
import logic.entites.Category;
import logic.entites.Order;
import logic.entites.Product;

public class MainFrame extends JFrame {

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private final int SIZE_X = 700;
    private final int SIZE_Y = 500;

    private final JTabbedPane jTabbedPane;
    private final JPanel productPanel;
    private final JPanel categoryPanel;
    private final JPanel orderPanel;

    private final BasicAction productAction;
    private final BasicAction orderAction;
    private final BasicAction categoryAction;

    private BasicEditor<Product, GenericDAO<Product>> productEditor;
    private BasicEditor<Order, GenericDAO<Order>> orderEditor;
    private BasicEditor<Category, GenericDAO<Category>> categoryEditor;

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

        getContentPane().add(jToolBar, BorderLayout.NORTH);

        setMenu();

        Product product = new Product();
        Order order = new Order();
        Category category = new Category();

        productEditor = null;
        orderEditor = null;
        categoryEditor = null;

        try {

            productEditor = new BasicEditor(product, this);
            orderEditor = new BasicEditor(order, this);
            categoryEditor = new BasicEditor(category, this);

        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        productAction = new ProductCrudAction(productEditor);
        categoryAction = new CategoryCrudAction(categoryEditor);
        orderAction = new OrderCrudAction(orderEditor);

        jTabbedPane.addTab(Strings.ORDER, new JScrollPane(orderEditor));
        jTabbedPane.addTab(Strings.PRODUCT, new JScrollPane(productEditor));
        jTabbedPane.addTab(Strings.CATEGORY, new JScrollPane(categoryEditor));

        getContentPane().add(jTabbedPane, BorderLayout.CENTER);

    }
/*
    private ChangeListener changeListener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            productEditor.getTable().repaint();
            orderEditor.getTable().repaint();
            categoryEditor.getTable().repaint();
        }

    };
*/
    private void setMenu() {

        JMenuBar jMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("File");

        JMenuItem close = new JMenuItem(closeAction);

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
