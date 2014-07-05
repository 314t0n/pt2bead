package gui.tablemodels.factory;

import gui.tablemodels.GenericTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;
/**
 * 
 * @author  
 */
public class TableFactory {
    /**
     * 
     * @param tableModel
     * @return Adott táblamodelhez JTable és Sorter
     */
    public static JTable createTable(GenericTableModel tableModel) {
        
        try {
            
            JTable table = new JTable();
            TableRowSorter<GenericTableModel> tableSorter = new TableRowSorter(tableModel);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setModel(tableModel);
            table.setRowSorter(tableSorter);
            table.setAutoCreateRowSorter(true);

            return table;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
