package gui.tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import logic.CrudService;
import logic.IEntity;
import org.eclipse.persistence.exceptions.DatabaseException;

public class GenericTableModel<T extends IEntity, S extends CrudService<T>> extends AbstractTableModel {

    private final S source;
    private final String[] columnNames;
    private List<T> items;
    private final boolean[] isEditAbleColumn;

    public GenericTableModel(S source, String[] columnNames) {
        this.source = source;
        this.columnNames = columnNames;
        this.isEditAbleColumn = new boolean[columnNames.length];
        this.items = new ArrayList();
        this.readAll();        
    }

    public void setColumnEditAble(int columnNumber) {
        try {
            isEditAbleColumn[columnNumber] = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return isEditAbleColumn[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        try {
            return (getRowCount() > 0) ? items.get(0).get(columnIndex).getClass() : null;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (getRowCount() > rowIndex) ? items.get(rowIndex).get(columnIndex) : null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        T item = items.get(rowIndex);
        System.out.println(columnIndex);
        item.set(columnIndex, aValue);
        try {
            source.update(item);
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (DatabaseException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void readAll() {
        try {
            items = source.readAll();
        } catch (DatabaseException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void create(T item) {
        try {
            source.create(item);
            readAll();
            fireTableDataChanged();
        } catch (DatabaseException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void delete(int rowIndex) {
        try {                    
            source.delete(items.get(rowIndex));
            readAll();
            fireTableDataChanged();
        } catch (DatabaseException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
