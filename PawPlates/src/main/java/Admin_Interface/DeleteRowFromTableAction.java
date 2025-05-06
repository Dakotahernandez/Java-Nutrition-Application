
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DeleteRowFromTableAction extends AbstractTableAction<JTable, DefaultTableModel> {
    public DeleteRowFromTableAction(JTable table, DefaultTableModel model) {
        super(table, model);
        putValue(NAME, "Delete selected rows");
        putValue(SHORT_DESCRIPTION, "Delete selected rows");
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setEnabled(getTable().getSelectedRowCount() > 0);
            }
        });
        setEnabled(getTable().getSelectedRowCount() > 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("...");
        JTable table = getTable();
        if (table.getSelectedRowCount() > 0) {
            List<Vector> selectedRows = new ArrayList<>(25);
            DefaultTableModel model = getModel();
            Vector rowData = model.getDataVector();
            for (int row : table.getSelectedRows()) {
                int modelRow = table.convertRowIndexToModel(row);
                Vector rowValue = (Vector) rowData.get(modelRow);
                selectedRows.add(rowValue);
            }

            for (Vector rowValue : selectedRows) {
                int rowIndex = rowData.indexOf(rowValue);
                model.removeRow(rowIndex);
            }
        }
    }
}
