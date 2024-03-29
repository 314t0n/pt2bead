package gui.panels;

import gui.tablemodels.GenericTableModel;
import gui.tablemodels.factory.TableFactory;
import gui.tablemodels.factory.TableModelFactory;
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
 * @author  
 * @param <T> IEntity típus
 * @param <S> CrudService típus
 */
public class BasicEditorPanel<T extends IEntity, S extends ICrudService<T>> extends JPanel {

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
    public BasicEditorPanel(IEntity entity, JFrame parentFrame) throws InstantiationException, IllegalAccessException {
        super();

        this.parentFrame = parentFrame;
        this.mainPanel = new JPanel();
        this.entity = entity;
        this.tableModel = TableModelFactory.createTableModel(entity.getClass(), entity.getPropertyNames());

        this.buttonPanel = new JPanel();

        setMainPanel();

        table = TableFactory.createTable(tableModel);
        tableSorter = new TableRowSorter(tableModel);
 
        table.setRowSorter(tableSorter);
        //table.setAutoCreateRowSorter(true);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
             
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

            table.setModel(tableModel);
            table.setRowSorter(tableSorter);
            table.setAutoCreateRowSorter(true);
            mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    /**
     * 
     * @return a táblához tartozó rendező elem
     */
    public TableRowSorter getTableSorter() {
        return tableSorter;
    }
    /**
     * Új gomb felvétele
     * @param button 
     */
    public void addButton(JButton button) {
        buttonPanel.add(button);
    }
    /**
     * 
     * @return a tábla elem
     */
    public JTable getTable() {
        return table;
    }
    /**
     * 
     * @return szülőkeret
     */
    public JFrame getParentFrame() {
        return parentFrame;
    }

}
