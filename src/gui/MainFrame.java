package gui;

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

public class MainFrame extends JFrame {

    //private final GenericTableModel<Album, GenericDAO<Album>> albumTableModel;
    //private final TableRowSorter<GenericTableModel<Album, GenericDAO<Album>>> albumSorter;
    public MainFrame() throws HeadlessException {

        setTitle("Generic ZH");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(40, 60);
        setSize(700, 300);
        setLayout(new BorderLayout());
        JToolBar jToolBar = new JToolBar();

        getContentPane().add(jToolBar, BorderLayout.NORTH);
        JTable albumTable = new JTable();
        
        setMenu();
        /*
         albumTableModel = new GenericTableModel(new GenericDAO(Album.class), Album.getPropertyNames());
         albumSorter = new TableRowSorter<>(albumTableModel);

         albumTableModel.setColumnEditAble(2);

         albumTable.setRowSorter(albumSorter);
         albumTable.setModel(albumTableModel);

         getContentPane().add(new JScrollPane(albumTable), BorderLayout.CENTER);
         */
    }
    
    /*
    
    MENU
    AND FUNCTIONS
    
    
    */
    
     private void setMenu() {

        JMenuBar jMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("File");

        JMenuItem newGame = new JMenuItem(newProductAction);            
        JMenuItem close = new JMenuItem(closeAction);

        mainMenu.add(newGame);     
        mainMenu.addSeparator();
        mainMenu.add(close);
        jMenuBar.add(mainMenu);

        setJMenuBar(jMenuBar);

    }
     
     /**
     * Új játék kezdése
     */
    private Action newProductAction = new AbstractAction("Új termék") {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    };
   
    private Action closeAction = new AbstractAction("Kilépés") {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };

}
