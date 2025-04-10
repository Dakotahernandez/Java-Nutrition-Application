import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NOTES
 * --------------------------------------------
 *  Progress bar at the top shows calorie total out of 2000
 *  Search bar included above the table for each column
 *  Table below the progress bar displays food entries
 *  Buttons (Add/Edit/Delete) are centered and placed at the bottom
 *  Josh: Used TemplateFrame, helper methods like addProgressBar, removed JFrame creation
 *  Added table from the extra credit assignment input safety: blank numeric fields default to 0
 */
public class CalorieMacroPage extends TemplateFrame {

    private static final int DAILY_LIMIT = 2000;
    private static int totalCalsSoFar = 0;
    private static JProgressBar calorieProgressBar;
    private static JLabel progressLabel;

    private final JTable table;
    private final FoodTableModel model;
    private final JTextField[] filters = new JTextField[7];
    private final TableRowSorter<FoodTableModel> sorter;

    public CalorieMacroPage() {
        setTitle("Calorie/Macro Tracker");

        // Progress bar setup
        calorieProgressBar = new JProgressBar(0, DAILY_LIMIT);
        progressLabel = new JLabel(getProgressText());

        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        progressPanel.add(progressLabel, BorderLayout.NORTH);
        progressPanel.add(calorieProgressBar, BorderLayout.CENTER);

        contentPane.add(progressPanel, BorderLayout.NORTH);

        // Table + Search Setup
        model = new FoodTableModel(new ArrayList<>());
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JPanel filterPanel = new JPanel(new GridLayout(1, 7));
        String[] columns = {"Food", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes"};
        for (int i = 0; i < filters.length; i++) {
            filters[i] = new JTextField();
            filterPanel.add(filters[i]);
            final int col = i;
            filters[i].getDocument().addDocumentListener(new MyDocumentListener() {
                public void update(DocumentEvent e) {
                    applyFilters();
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        add.addActionListener(e -> openDialog(null, -1));
        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                FoodEntry entry = model.getRecordAt(table.convertRowIndexToModel(row));
                openDialog(entry, row);
            }
        });
        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirmed = JOptionPane.showConfirmDialog(this, "Delete selected row?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    model.removeRecord(table.convertRowIndexToModel(row));
                    updateCalorieProgress();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setSize(1000, 500);
        setVisible(true);
    }

    private void applyFilters() {
        List<RowFilter<Object, Object>> filtersList = new ArrayList<>();
        for (int i = 0; i < filters.length; i++) {
            String text = filters[i].getText();
            if (!text.isEmpty()) {
                filtersList.add(RowFilter.regexFilter("(?i)" + text, i));
            }
        }
        sorter.setRowFilter(RowFilter.andFilter(filtersList));
    }

    private void openDialog(FoodEntry entry, int row) {
        FoodEntryDialog dialog = new FoodEntryDialog(this, entry);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            if (entry == null) model.addRecord(dialog.getRecord());
            else model.updateRecord(row, dialog.getRecord());
            updateCalorieProgress();
        }
    }

    private void updateCalorieProgress() {
        int sum = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            sum += model.getRecordAt(i).getCalories();
        }
        totalCalsSoFar = sum;
        calorieProgressBar.setValue(Math.min(sum, DAILY_LIMIT));
        progressLabel.setText(getProgressText());
    }

    private static String getProgressText() {
        if (totalCalsSoFar <= DAILY_LIMIT) {
            return String.format("Calories so far: %d / %d", totalCalsSoFar, DAILY_LIMIT);
        } else {
            return String.format("Calories so far: %d (Over by %d)", totalCalsSoFar, totalCalsSoFar - DAILY_LIMIT);
        }
    }

    // -------------------- Supporting Inner Classes ------------------------

    public static class FoodEntry {
        private String foodName;
        private int calories;
        private String protein;
        private String carbs;
        private String fats;
        private String fiber;
        private String notes;

        public FoodEntry(String foodName, int calories, String protein, String carbs, String fats, String fiber, String notes) {
            this.foodName = foodName;
            this.calories = calories;
            this.protein = protein;
            this.carbs = carbs;
            this.fats = fats;
            this.fiber = fiber;
            this.notes = notes;
        }

        public String getFoodName() { return foodName; }
        public int getCalories() { return calories; }
        public String getProtein() { return protein; }
        public String getCarbs() { return carbs; }
        public String getFats() { return fats; }
        public String getFiber() { return fiber; }
        public String getNotes() { return notes; }

        public void setFoodName(String s) { foodName = s; }
        public void setCalories(int c) { calories = c; }
        public void setProtein(String s) { protein = s; }
        public void setCarbs(String s) { carbs = s; }
        public void setFats(String s) { fats = s; }
        public void setFiber(String s) { fiber = s; }
        public void setNotes(String s) { notes = s; }
    }

    public static class FoodTableModel extends AbstractTableModel {
        private final String[] columns = {"Food", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes"};
        private List<FoodEntry> data;

        public FoodTableModel(List<FoodEntry> data) {
            this.data = data;
        }

        public void setData(List<FoodEntry> newData) {
            this.data = newData;
            fireTableDataChanged();
        }

        public FoodEntry getRecordAt(int row) {
            return data.get(row);
        }

        public void addRecord(FoodEntry record) {
            data.add(record);
            fireTableDataChanged();
        }

        public void removeRecord(int row) {
            data.remove(row);
            fireTableDataChanged();
        }

        public void updateRecord(int row, FoodEntry record) {
            data.set(row, record);
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int column) { return columns[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            FoodEntry e = data.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> e.getFoodName();
                case 1 -> e.getCalories();
                case 2 -> e.getProtein();
                case 3 -> e.getCarbs();
                case 4 -> e.getFats();
                case 5 -> e.getFiber();
                case 6 -> e.getNotes();
                default -> null;
            };
        }
    }

    public static class FoodEntryDialog extends JDialog {
        private final JTextField[] fields = new JTextField[7];
        private boolean saved = false;

        public FoodEntryDialog(Frame parent, FoodEntry record) {
            super(parent, "Food Entry Form", true);
            setLayout(new GridLayout(8, 2));

            String[] labels = {"Food Name", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes"};

            for (int i = 0; i < labels.length; i++) {
                add(new JLabel(labels[i]));
                fields[i] = new JTextField();
                add(fields[i]);
            }

            if (record != null) {
                fields[0].setText(record.getFoodName());
                fields[1].setText(String.valueOf(record.getCalories()));
                fields[2].setText(record.getProtein());
                fields[3].setText(record.getCarbs());
                fields[4].setText(record.getFats());
                fields[5].setText(record.getFiber());
                fields[6].setText(record.getNotes());
            }

            JButton save = new JButton("Save");
            JButton cancel = new JButton("Cancel");

            save.addActionListener(e -> {
                saved = true;
                setVisible(false);
            });
            cancel.addActionListener(e -> setVisible(false));

            add(save);
            add(cancel);
            pack();
            setLocationRelativeTo(parent);
        }

        public boolean isSaved() {
            return saved;
        }

        public FoodEntry getRecord() {
            String food = fields[0].getText().trim();
            int calories = parseIntSafe(fields[1].getText());
            String protein = defaultToZero(fields[2].getText());
            String carbs = defaultToZero(fields[3].getText());
            String fats = defaultToZero(fields[4].getText());
            String fiber = defaultToZero(fields[5].getText());
            String notes = fields[6].getText().trim();
            return new FoodEntry(food, calories, protein, carbs, fats, fiber, notes);
        }

        private int parseIntSafe(String text) {
            try {
                return Integer.parseInt(text.trim());
            } catch (Exception e) {
                return 0;
            }
        }

        private String defaultToZero(String text) {
            return text == null || text.trim().isEmpty() ? "0" : text.trim();
        }
    }

    @FunctionalInterface
    public interface MyDocumentListener extends DocumentListener {
        void update(DocumentEvent e);
        @Override default void insertUpdate(DocumentEvent e) { update(e); }
        @Override default void removeUpdate(DocumentEvent e) { update(e); }
        @Override default void changedUpdate(DocumentEvent e) { update(e); }
    }

    public static void main(String[] args) {
        new CalorieMacroPage();
    }
}
