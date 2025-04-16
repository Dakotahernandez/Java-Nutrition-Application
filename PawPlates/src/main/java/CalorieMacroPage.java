import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalorieMacroPage extends TemplateFrame {

    private static final int DAILY_LIMIT = 2000;
    private static int totalCalsSoFar = 0;
    private static JProgressBar calorieProgressBar;
    private static JLabel progressLabel;

    // Models and tables for each meal
    private FoodTableModel breakfastModel;
    private FoodTableModel lunchModel;
    private FoodTableModel dinnerModel;

    private JTable breakfastTable;
    private JTable lunchTable;
    private JTable dinnerTable;

    public CalorieMacroPage() {
        super(); // sets up the menu and content pane via TemplateFrame
        setTitle("Calorie/Macro Tracker");

        // -------- Progress Bar Setup --------
        calorieProgressBar = new JProgressBar(0, DAILY_LIMIT);
        calorieProgressBar.setForeground(Theme.ACCENT_GREEN);
        calorieProgressBar.setBackground(Theme.MID_GRAY);
        progressLabel = new JLabel(getProgressText());
        progressLabel.setFont(Theme.HEADER_FONT);
        progressLabel.setForeground(Theme.FG_LIGHT);

        // Use TemplateFrame's addProgressBar method to insert progress bar into the top panel
        addProgressBar(calorieProgressBar, 0, progressLabel, getProgressText());

        // -------- Initialize Models and Tables --------
        breakfastModel = new FoodTableModel(new ArrayList<>());
        lunchModel = new FoodTableModel(new ArrayList<>());
        dinnerModel = new FoodTableModel(new ArrayList<>());

        breakfastTable = createStyledTable(breakfastModel);
        lunchTable = createStyledTable(lunchModel);
        dinnerTable = createStyledTable(dinnerModel);

        // -------- Create Tabbed Pane for Meal Panels --------
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Breakfast", createMealPanel(breakfastModel, breakfastTable));
        tabbedPane.addTab("Lunch", createMealPanel(lunchModel, lunchTable));
        tabbedPane.addTab("Dinner", createMealPanel(dinnerModel, dinnerTable));

        // -------- Buttons Setup --------
        JButton add = createStyledButton("Add");
        JButton edit = createStyledButton("Edit");
        JButton delete = createStyledButton("Delete");

        // Action listeners: work with the currently selected tab's table.
        add.addActionListener(e -> openDialog(null, -1, tabbedPane));
        edit.addActionListener(e -> {
            JTable currentTable = getCurrentTable(tabbedPane);
            if (currentTable != null) {
                int row = currentTable.getSelectedRow();
                if (row != -1) {
                    FoodTableModel model = getModelForTable(currentTable);
                    FoodEntry entry = model.getRecordAt(currentTable.convertRowIndexToModel(row));
                    openDialog(entry, row, tabbedPane);
                }
            }
        });
        delete.addActionListener(e -> {
            JTable currentTable = getCurrentTable(tabbedPane);
            if (currentTable != null) {
                int row = currentTable.getSelectedRow();
                if (row != -1) {
                    int confirmed = JOptionPane.showConfirmDialog(
                            this,
                            "Delete selected row?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmed == JOptionPane.YES_OPTION) {
                        getModelForTable(currentTable).removeRecord(currentTable.convertRowIndexToModel(row));
                        updateCalorieProgress();
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Theme.BG_DARK);
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);

        // Add the tabbed pane and button panel to the content pane.
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setSize(1000, 500);
        setVisible(true);
    }

    // Helper method: creates a button styled with the theme.
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.NORMAL_FONT);
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        return button;
    }

    // Returns the progress text based on total calories.
    private String getProgressText() {
        if (totalCalsSoFar <= DAILY_LIMIT) {
            return String.format("Calories so far: %d / %d", totalCalsSoFar, DAILY_LIMIT);
        } else {
            return String.format("Calories so far: %d (Over by %d)", totalCalsSoFar, totalCalsSoFar - DAILY_LIMIT);
        }
    }

    // Creates a panel for a meal with a filter panel and a scroll pane for the table.
    private JPanel createMealPanel(FoodTableModel model, JTable table) {
        JPanel filterPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        filterPanel.setBackground(Theme.BG_DARK);
        for (int i = 0; i < 7; i++) {
            filterPanel.add(createStyledFilterField());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Theme.BG_DARKER);
        scrollPane.getViewport().setBackground(Theme.BG_DARKER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.MID_GRAY, 1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG_DARK);
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Helper method: creates a styled text field for filters.
    private JTextField createStyledFilterField() {
        JTextField field = new JTextField();
        field.setFont(Theme.NORMAL_FONT);
        // If you prefer, you can use a Theme constant instead of new Color(60,60,60)
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Theme.FG_LIGHT);
        return field;
    }

    // Returns a styled JTable for the given model.
    private JTable createStyledTable(FoodTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(Theme.NORMAL_FONT);
        table.setBackground(Theme.BG_DARKER);
        table.setForeground(Theme.FG_LIGHT);
        table.setSelectionBackground(Theme.MID_GRAY);
        table.setSelectionForeground(Theme.FG_LIGHT);
        table.setGridColor(new Color(80, 80, 80));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Theme.FG_LIGHT);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        return table;
    }

    // Utility method to obtain the currently active table from the tabbed pane.
    private JTable getCurrentTable(JTabbedPane tabbedPane) {
        Component selected = tabbedPane.getSelectedComponent();
        if (selected instanceof JPanel) {
            JPanel panel = (JPanel) selected;
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) comp;
                    return (JTable) scrollPane.getViewport().getView();
                }
            }
        }
        return null;
    }

    // Returns the model corresponding to the given table.
    private FoodTableModel getModelForTable(JTable table) {
        if (table == breakfastTable) {
            return breakfastModel;
        } else if (table == lunchTable) {
            return lunchModel;
        } else {
            return dinnerModel;
        }
    }

    // Opens the FoodEntryDialog. Uses the current model depending on the selected tab.
    private void openDialog(FoodEntry entry, int row, JTabbedPane tabbedPane) {
        FoodEntryDialog dialog = new FoodEntryDialog(this, entry);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            FoodEntry newEntry = dialog.getRecord();
            FoodTableModel targetModel = getModelForMeal(newEntry.getMealType());
            if (entry == null) {
                targetModel.addRecord(newEntry);
            } else {
                // If the meal type has changed, remove from the old model and add to the new one.
                if (!entry.getMealType().equals(newEntry.getMealType())) {
                    getModelForMeal(entry.getMealType()).removeRecord(row);
                    targetModel.addRecord(newEntry);
                } else {
                    targetModel.updateRecord(row, newEntry);
                }
            }
            updateCalorieProgress();
        }
    }

    // Returns the proper model for a given meal type.
    private FoodTableModel getModelForMeal(String mealType) {
        if ("Breakfast".equals(mealType)) {
            return breakfastModel;
        } else if ("Lunch".equals(mealType)) {
            return lunchModel;
        } else {
            return dinnerModel;
        }
    }

    // Sums calories from all models and updates the progress bar.
    private void updateCalorieProgress() {
        int sum = 0;
        for (FoodEntry entry : breakfastModel.getData()) {
            sum += entry.getCalories();
        }
        for (FoodEntry entry : lunchModel.getData()) {
            sum += entry.getCalories();
        }
        for (FoodEntry entry : dinnerModel.getData()) {
            sum += entry.getCalories();
        }
        totalCalsSoFar = sum;
        calorieProgressBar.setValue(Math.min(sum, DAILY_LIMIT));
        progressLabel.setText(getProgressText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalorieMacroPage());
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
        private String mealType; // Specifies meal type

        public FoodEntry(String foodName, int calories, String protein,
                         String carbs, String fats, String fiber, String notes, String mealType) {
            this.foodName = foodName;
            this.calories = calories;
            this.protein = protein;
            this.carbs = carbs;
            this.fats = fats;
            this.fiber = fiber;
            this.notes = notes;
            this.mealType = mealType;
        }

        // Getters
        public String getFoodName() { return foodName; }
        public int getCalories() { return calories; }
        public String getProtein() { return protein; }
        public String getCarbs() { return carbs; }
        public String getFats() { return fats; }
        public String getFiber() { return fiber; }
        public String getNotes() { return notes; }
        public String getMealType() { return mealType; }

        // Setters
        public void setFoodName(String foodName) { this.foodName = foodName; }
        public void setCalories(int calories) { this.calories = calories; }
        public void setProtein(String protein) { this.protein = protein; }
        public void setCarbs(String carbs) { this.carbs = carbs; }
        public void setFats(String fats) { this.fats = fats; }
        public void setFiber(String fiber) { this.fiber = fiber; }
        public void setNotes(String notes) { this.notes = notes; }
        public void setMealType(String mealType) { this.mealType = mealType; }
    }

    public static class FoodTableModel extends AbstractTableModel {
        private final String[] columns = {"Food", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes", "Meal"};
        private List<FoodEntry> data;

        public FoodTableModel(List<FoodEntry> data) {
            this.data = data;
        }

        // Expose the internal list for calorie summing.
        public List<FoodEntry> getData() {
            return data;
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

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            FoodEntry e = data.get(rowIndex);
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
    }

    public static class FoodEntryDialog extends JDialog {
        private final JTextField[] fields = new JTextField[7];
        private JComboBox<String> mealComboBox;
        private boolean saved = false;

        public FoodEntryDialog(Frame parent, FoodEntry record) {
            super(parent, "Food Entry Form", true);
            // 7 fields + meal type = 8 rows; plus one row for buttons gives 9 rows
            setLayout(new GridLayout(9, 2, 5, 5));

            Color dialogBg = Theme.BG_DARK;
            Color fieldBg = new Color(60, 60, 60);
            Color fgLight = Theme.FG_LIGHT;

            getContentPane().setBackground(dialogBg);

            String[] labels = {"Food Name", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes"};
            for (int i = 0; i < labels.length; i++) {
                JLabel lbl = new JLabel(labels[i]);
                lbl.setForeground(fgLight);
                lbl.setFont(Theme.NORMAL_FONT);
                add(lbl);

                fields[i] = createStyledField();
                add(fields[i]);
            }

            // Add meal type selection
            JLabel mealLabel = new JLabel("Meal Type");
            mealLabel.setForeground(fgLight);
            mealLabel.setFont(Theme.NORMAL_FONT);
            add(mealLabel);

            mealComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner"});
            mealComboBox.setFont(Theme.NORMAL_FONT);
            mealComboBox.setBackground(fieldBg);
            mealComboBox.setForeground(fgLight);
            add(mealComboBox);

            if (record != null) {
                fields[0].setText(record.getFoodName());
                fields[1].setText(String.valueOf(record.getCalories()));
                fields[2].setText(record.getProtein());
                fields[3].setText(record.getCarbs());
                fields[4].setText(record.getFats());
                fields[5].setText(record.getFiber());
                fields[6].setText(record.getNotes());
                mealComboBox.setSelectedItem(record.getMealType());
            }

            JButton save = createDialogStyledButton("Save");
            JButton cancel = createDialogStyledButton("Cancel");

            save.addActionListener(e -> {
                if (fields[0].getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Food Name cannot be blank.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                saved = true;
                setVisible(false);
            });
            cancel.addActionListener(e -> setVisible(false));

            add(save);
            add(cancel);

            pack();
            setLocationRelativeTo(parent);
        }

        // Helper method for FoodEntryDialog: creates a styled text field.
        private JTextField createStyledField() {
            JTextField field = new JTextField();
            field.setFont(Theme.NORMAL_FONT);
            field.setBackground(new Color(60, 60, 60));
            field.setForeground(Theme.FG_LIGHT);
            return field;
        }

        // Helper method for FoodEntryDialog: creates a styled button.
        private JButton createDialogStyledButton(String text) {
            JButton button = new JButton(text);
            button.setFont(Theme.NORMAL_FONT);
            button.setBackground(Theme.BUTTON_BG);
            button.setForeground(Theme.BUTTON_FG);
            return button;
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
            String mealType = (String) mealComboBox.getSelectedItem();
            return new FoodEntry(food, calories, protein, carbs, fats, fiber, notes, mealType);
        }

        private int parseIntSafe(String text) {
            try {
                return Integer.parseInt(text.trim());
            } catch (Exception e) {
                return 0;
            }
        }

        private String defaultToZero(String text) {
            return (text == null || text.trim().isEmpty()) ? "0" : text.trim();
        }
    }

    @FunctionalInterface
    public interface MyDocumentListener extends DocumentListener {
        void update(DocumentEvent e);
        @Override default void insertUpdate(DocumentEvent e) { update(e); }
        @Override default void removeUpdate(DocumentEvent e) { update(e); }
        @Override default void changedUpdate(DocumentEvent e) { update(e); }
    }
}
