import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class dataModel implements TableModel{

	public int getRowCount() {

		return 0;
	}

	public int getColumnCount() {

		return 0;
	}

	public String getColumnName(int columnIndex) {

		return null;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return null;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {

		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		return null;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}

	public void addTableModelListener(TableModelListener l) {
		
	}

	public void removeTableModelListener(TableModelListener l) {
		
	}

}
