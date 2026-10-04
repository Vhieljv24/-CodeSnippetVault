import javax.swing.table.DefaultTableModel;


public class SnippetTableModel extends DefaultTableModel {

    public SnippetTableModel(Object[] columnNames) {
        super(columnNames, 0);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
