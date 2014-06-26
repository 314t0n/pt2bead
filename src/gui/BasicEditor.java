package gui;

import gui.tablemodels.GenericTableModel;
import gui.tablemodels.TableFactory;
import gui.tablemodels.TableModelFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import logic.ICrudService;
import logic.IEntity;

/**
 * JPanel alapvető szerkesztési feladatokhoz.
 *
 * Tartalmaz:
 *
 * felvétel, szerkeszt, töröl gombokat, táblázatot amiben megjelennek az adatok.
 *
 * @author ag313w
 * @param <T> IEntity típus
 * @param <S> CrudService típus
 */
public class BasicEditor<T extends IEntity, S extends ICrudService<T>> extends JPanel {
    
    private final JPanel mainPanel;
    private GenericTableModel<T, S> tableModel;
    private TableRowSorter<GenericTableModel> tableSorter;
    private final JFrame parentFrame;
    private final IEntity entity;
    private final JTable table;
    private final JPanel buttonPanel;

    /**
     *
     * @param entity Konkrét entitás példány.
     * @param parentFrame szülő
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public BasicEditor(IEntity entity, JFrame parentFrame) throws InstantiationException, IllegalAccessException {
        super();
        
        this.parentFrame = parentFrame;
        this.mainPanel = new JPanel();
        this.entity = entity;
        this.tableModel = TableModelFactory.createTableModel(entity.getClass(), entity.getPropertyNames());
       
        this.buttonPanel = new JPanel();
        
        setMainPanel();
        
        //setTable();
        table = TableFactory.createTable(tableModel);
        
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Beállít egy layout-t a gomboknak
     *
     */
    private void setMainPanel() {
        
        BorderLayout layout = new BorderLayout();
        
        mainPanel.setLayout(layout);
        
        buttonPanel.setLayout(new FlowLayout());
        
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        
        this.add(mainPanel);
    }

    /**
     * Táblázat felvétele és a főpanelhez adása.
     */
    private void setTable() {
        
        try {
            
            tableSorter = new TableRowSorter(tableModel);
            
            table.setModel(tableModel);
            table.setRowSorter(tableSorter);
            table.setAutoCreateRowSorter(true);
            mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void addButton(JButton button) {
        buttonPanel.add(button);
    }
    
    public JTable getTable() {
        return table;
    }
    
    public JFrame getParentFrame() {
        return parentFrame;
    }
    
}
