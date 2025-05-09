package Admin_Interface;
import javax.swing.table.AbstractTableModel;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {
            "Trainer/User",
            "First Name",
            "Last Name",
            "Email",
            "ID Number",
            "Fitness Level",
            "Gender",
            "Security Question 1",
            "Security Question 1 Answer",
            "Security Question 2",
            "Security Question 2 Answer"};

    private final List<Object[]> data = new ArrayList<>();

    public MyTableModel(){
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("userData1.csv");
            if(is == null){
                throw new RuntimeException("userData.csv file not opened!!!");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean skipFirstLine = true;
            while((line = reader.readLine()) != null){
                if(skipFirstLine){
                    skipFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if(parts.length != columnNames.length){
                    continue;
                }
                Object[] row = new Object[11];
                row[0] = parts[0].trim(); //trainer/user
                row[1] = parts[1].trim(); //first name
                row[2] = parts[2].trim(); //last name
                row[3] = parts[3].trim(); //email
                row[4] = Integer.parseInt(parts[4].trim()); //ID number
                row[5] = parts[5].trim(); //fitness level
                row[6] = parts[6].trim(); //gender
                row[7] = SecurityQuestion.resolve(parts[7].trim()); // q1
                row[8] = SecurityQuestion.resolve(parts[8].trim()); // q2
                row[9] = parts[9].trim(); //q answer 1
                row[10] = parts[10].trim(); //q answer 2
                data.add(row);
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public Class<?> getColumnClass(int col) {
        Object value = getValueAt(0, col);
        return value != null ? value.getClass() : String.class;
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }

    public void addRow(Object[] newRow){
        data.add(newRow);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
    public void removeRow(int rowIndex){
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    public Object[] getRow(int rowIndex){
        return data.get(rowIndex);
    }
    public void updateRow(int rowIndex, Object[] rowData) {
        for (int col = 0; col < rowData.length; col++) {
            setValueAt(rowData[col], rowIndex, col);
        }
    }

}
