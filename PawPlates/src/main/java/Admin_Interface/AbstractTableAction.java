import javax.swing.*;
import javax.swing.table.TableModel;

public abstract class AbstractTableAction<T extends JTable, M extends TableModel> extends AbstractAction {
    private T table;
    private M model;
    public AbstractTableAction(T table, M model) {
        this.table = table;
        this.model = model;
    }
    public T getTable() {
        return table;
    }
    public M getModel() {
        return model;
    }
}
