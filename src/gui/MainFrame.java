package gui;

import gui.dialogs.FormDialog;
import gui.panels.AddCategoryPanel;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableRowSorter;
import logic.GenericDAO;
import logic.Logger;
import logic.entites.Category;
import logic.entites.Product;

public class MainFrame extends JFrame {

    private GenericTableModel<Product, GenericDAO<Product>> productTableModel;
    private TableRowSorter<GenericTableModel<Product, GenericDAO<Product>>> productSorter;

    public MainFrame() throws HeadlessException {

        setTitle("Generic ZH");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(40, 60);
        setSize(700, 300);
        setLayout(new BorderLayout());
        JToolBar jToolBar = new JToolBar();

        getContentPane().add(jToolBar, BorderLayout.NORTH);
        
        setMenu();

        setProductTable();
    }

    private void setProductTable() {
        try{
        JTable productTable = new JTable();
        
        productTableModel = new GenericTableModel(new GenericDAO(Product.class), Product.getPropertyNames());
        productSorter = new TableRowSorter<>(productTableModel);

        productTableModel.setColumnEditAble(2);

        productTable.setRowSorter(productSorter);
        productTable.setModel(productTableModel);

        getContentPane().add(new JScrollPane(productTable), BorderLayout.CENTER);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private final GenericDAO<Product> productController = new GenericDAO(Product.class);
    private final GenericDAO<Category> categoryController = new GenericDAO(Category.class);

    /*
    
     MENU
     AND ACTIONS 
    
     */
    private void setMenu() {

        JMenuBar jMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("File");

        JMenuItem addProduct = new JMenuItem(addProductAction);
        JMenuItem addCategory = new JMenuItem(addCategoryAction);
        JMenuItem close = new JMenuItem(closeAction);

        mainMenu.add(addProduct);
        mainMenu.add(addCategory);
        mainMenu.addSeparator();
        mainMenu.add(close);
        jMenuBar.add(mainMenu);

        setJMenuBar(jMenuBar);

    }

    private Action addProductAction = new AbstractAction("Új termék") {
        @Override
        public void actionPerformed(ActionEvent e) {

            Product product = new Product();

            AddProductPanel addProductPanel = new AddProductPanel(product, categoryController.readAll());
            FormDialog formDialog = new FormDialog(MainFrame.this, enabled, addProductPanel);

            if (formDialog.isSave()) {

                addProductPanel.setAttributes();

                Logger.log("Adatok mentése", "DEBUG");

                productController.create(product);

            }

        }
    };

    private Action addCategoryAction = new AbstractAction("Új kategória") {
        @Override
        public void actionPerformed(ActionEvent e) {

            Category category = new Category();

            AddCategoryPanel addCategoryPanel = new AddCategoryPanel(category);
            FormDialog formDialog = new FormDialog(MainFrame.this, enabled, addCategoryPanel);

            if (formDialog.isSave()) {

                Logger.log("Adatok mentése", "DEBUG");

                addCategoryPanel.setAttributes();

                if (category != null) {

                    categoryController.create(category);

                }

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
