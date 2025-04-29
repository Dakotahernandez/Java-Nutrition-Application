package tracking; /**
 * =============================================================================
 * File:           tracking.FoodTableModel.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A custom AbstractTableModel for displaying and managing a list of tracking.FoodEntry
 *   objects in a JTable. Includes built-in sorting and filtering support.
 *
 * Dependencies:
 *   - javax.swing.table.AbstractTableModel
 *   - java.util.List
 *   - java.util.ArrayList
 *   - java.util.Comparator
 *   - tracking.FoodEntry
 *
 * Usage:
 *   tracking.FoodTableModel model = new tracking.FoodTableModel(foodList);
 *   model.filter("Chicken");
 *   model.sortByColumn(1, true); // Sort by Calories ascending
 *
 * TODO:
 *   - Improve filtering logic (regex matching, etc.)
 * =============================================================================
 */

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FoodTableModel extends AbstractTableModel {
    private final String[] columns = {
            "Food", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes", "Meal"
    };

    private List<FoodEntry> fullData; // All entries
    private List<FoodEntry> filteredData; // Visible entries (after filtering)
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public FoodTableModel(List<FoodEntry> data) {
        this.fullData = new ArrayList<>(data);
        this.filteredData = new ArrayList<>(data);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public List<FoodEntry> getData() {
        return filteredData;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public FoodEntry getRecordAt(int row) {
        return filteredData.get(row);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void addRecord(FoodEntry record) {
        fullData.add(record);
        filteredData.add(record);
        fireTableDataChanged();
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void removeRecord(int row) {
        FoodEntry toRemove = filteredData.get(row);
        fullData.remove(toRemove);
        filteredData.remove(row);
        fireTableDataChanged();
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void updateRecord(int row, FoodEntry record) {
        FoodEntry old = filteredData.get(row);
        int fullIndex = fullData.indexOf(old);
        if (fullIndex != -1) {
            fullData.set(fullIndex, record);
        }
        filteredData.set(row, record);
        fireTableDataChanged();
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    public int getRowCount() {
        return filteredData.size();
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FoodEntry e = filteredData.get(rowIndex);
        switch (columnIndex) {
            case 0: return e.getFoodName();
            case 1: return e.getCalories();
            case 2: return e.getProtein();
            case 3: return e.getCarbs();
            case 4: return e.getFats();
            case 5: return e.getFiber();
            case 6: return e.getNotes();
            case 7: return e.getMealType();
            default: return null;
        }
    }



    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void filter(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredData = new ArrayList<>(fullData);
        } else {
            String q = query.trim().toLowerCase();
            filteredData = new ArrayList<>();
            for (FoodEntry entry : fullData) {
                if (entry.getFoodName().toLowerCase().contains(q) ||
                        entry.getNotes().toLowerCase().contains(q) ||
                        entry.getMealType().toLowerCase().contains(q)) {
                    filteredData.add(entry);
                }
            }
        }
        fireTableDataChanged();
    }

    /**
     * Sorts the data based on the selected column.
     * @param column Index of column (0=Food, 1=Calories, etc.)
     * @param ascending True for ascending, false for descending
     */
    public void sortByColumn(int column, boolean ascending) {
        Comparator<FoodEntry> comparator;
        switch (column) {
            case 0: // Food
                comparator = Comparator.comparing(FoodEntry::getFoodName, String.CASE_INSENSITIVE_ORDER);
                break;
            case 1: // Calories
                comparator = Comparator.comparingInt(FoodEntry::getCalories);
                break;
            case 2: // Protein
                comparator = Comparator.comparing(FoodEntry::getProtein, String.CASE_INSENSITIVE_ORDER);
                break;
            case 3: // Carbs
                comparator = Comparator.comparing(FoodEntry::getCarbs, String.CASE_INSENSITIVE_ORDER);
                break;
            case 4: // Fats
                comparator = Comparator.comparing(FoodEntry::getFats, String.CASE_INSENSITIVE_ORDER);
                break;
            case 5: // Fiber
                comparator = Comparator.comparing(FoodEntry::getFiber, String.CASE_INSENSITIVE_ORDER);
                break;
            case 6: // Notes
                comparator = Comparator.comparing(FoodEntry::getNotes, String.CASE_INSENSITIVE_ORDER);
                break;
            case 7: // Meal
                comparator = Comparator.comparing(FoodEntry::getMealType, String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                return;
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        filteredData.sort(comparator);
        fireTableDataChanged();
    }
}
