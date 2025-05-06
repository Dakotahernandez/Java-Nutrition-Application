package tracking.Food;
/**
 * =============================================================================
 * File:           CalorieMacroPage.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A Java Swing frame for tracking daily calories and macros by meal. Presents
 *   tabbed tables for breakfast, lunch, and dinner, allows adding/editing/deleting
 *   entries, and displays an animated progress bar.
 *
 * Dependencies:
 *   - javax.swing.*           (Swing components)
 *   - java.awt.*              (AWT layout and events)
 *   - java.time.LocalDate     (date handling)
 *   - java.time.format.DateTimeFormatter (date formatting)
 *   - TemplateFrame           (application template frame)
 *   - AnimatedProgressBar     (smooth progress bar animation)
 *
 * Usage:
 *   // Create for today's date and show
 *   CalorieMacroPage page = new CalorieMacroPage(LocalDate.now());
 *   page.setVisible(true);
 *
 * =============================================================================
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import frame.*;

//database time!!!!
import tracking.Food.FoodEntryDatabase;
import tracking.weightAndGoals.WeightDatabase;
import tracking.weightAndGoals.WeightDatabase;
import tracking.weightAndGoals.WeightDatabase.WeightGoal;
import java.sql.SQLException;


public class CalorieMacroPage extends TemplateFrame {

    private static final FoodEntryDatabase db = new FoodEntryDatabase();

    private static int DAILY_LIMIT;
    private static int totalCalsSoFar = 0;
    private static AnimatedProgressBar calorieProgressBar;
    private static JLabel progressLabel;

    private final LocalDate date;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM d, yyyy");

    // Models and tables for each meal
    private FoodTableModel breakfastModel;
    private FoodTableModel lunchModel;
    private FoodTableModel dinnerModel;

    private JTable breakfastTable;
    private JTable lunchTable;
    private JTable dinnerTable;

    /**
     * Constructs the CalorieMacroPage for a given date. It initializes the
     * food entry tables by meal, sets up the progress bar based on the user's
     * daily calorie goal, and provides add/edit/delete functionality.
     *
     * @param date The date for which the food entries and macros are being tracked.
     */
    public CalorieMacroPage(LocalDate date) {
        this.date = date;

        addMenuBarPanel();
        setTitle("Calorie/Macro Tracker – " + date.format(FORMATTER));

        // -------- Progress Bar Setup --------
        // ─── fetch daily‐calorie goal from DB (default to 2000)
        try {
            WeightDatabase wdb = new WeightDatabase();
            DAILY_LIMIT = wdb
                    .getWeightGoal(LoginPage.CURRENT_USER.getId())
                    .map(g -> g.dailyCalGoal)
                    .orElse(2000);
        } catch (SQLException e) {
            DAILY_LIMIT = 2000;
            e.printStackTrace();
        }
        calorieProgressBar = new AnimatedProgressBar(0, DAILY_LIMIT);
        calorieProgressBar.setForeground(Theme.ACCENT_COLOR);
        calorieProgressBar.setBackground(Theme.BG_LIGHTER);
        progressLabel = addProgressBar(calorieProgressBar, 0, getProgressText());
        progressLabel.setFont(Theme.HEADER_FONT);
        progressLabel.setForeground(Theme.FG_LIGHT);

        //


        // -------- Initialize Models and Tables --------
        List<FoodEntry> breakfastEntries = new ArrayList<>();
        List<FoodEntry> lunchEntries     = new ArrayList<>();
        List<FoodEntry> dinnerEntries    = new ArrayList<>();

        List<FoodEntry> allEntries = new ArrayList<>();
        allEntries.addAll(db.loadEntries(LoginPage.CURRENT_USER.getId(), date, "Breakfast"));
        allEntries.addAll(db.loadEntries(LoginPage.CURRENT_USER.getId(), date, "Lunch"));
        allEntries.addAll(db.loadEntries(LoginPage.CURRENT_USER.getId(), date, "Dinner"));

        for (FoodEntry entry : allEntries) {
            switch (entry.getMealType()) {
                case "Breakfast": breakfastEntries.add(entry); break;
                case "Lunch":     lunchEntries.add(entry);     break;
                case "Dinner":    dinnerEntries.add(entry);    break;
            }
        }

        breakfastModel = new FoodTableModel(breakfastEntries);
        lunchModel     = new FoodTableModel(lunchEntries);
        dinnerModel    = new FoodTableModel(dinnerEntries);



        breakfastTable = createStyledTable(breakfastModel);
        lunchTable     = createStyledTable(lunchModel);
        dinnerTable    = createStyledTable(dinnerModel);

        // -------- Create Tabbed Pane for Meals --------
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Breakfast", createMealPanel(breakfastModel, breakfastTable));
        tabbedPane.addTab("Lunch",     createMealPanel(lunchModel, lunchTable));
        tabbedPane.addTab("Dinner",    createMealPanel(dinnerModel, dinnerTable));

        // -------- Buttons Setup --------
        JButton addBtn    = createStyledButton("Add");
        JButton editBtn   = createStyledButton("Edit");
        JButton deleteBtn = createStyledButton("Delete");

        addBtn.addActionListener(e -> openDialog(null, -1, tabbedPane));
        editBtn.addActionListener(e -> {
            JTable tbl = getCurrentTable(tabbedPane);
            int row = tbl != null ? tbl.getSelectedRow() : -1;
            if (row != -1) {
                FoodTableModel model = getModelForTable(tbl);
                FoodEntry entry = model.getRecordAt(tbl.convertRowIndexToModel(row));
                openDialog(entry, row, tabbedPane);
            }
        });
        deleteBtn.addActionListener(e -> {
            JTable tbl = getCurrentTable(tabbedPane);
            int row = tbl != null ? tbl.getSelectedRow() : -1;
            if (row != -1 && JOptionPane.showConfirmDialog(
                    this,
                    "Delete selected row?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                FoodTableModel model = getModelForTable(tbl);
                FoodEntry entry = model.getRecordAt(tbl.convertRowIndexToModel(row));
                model.removeRecord(tbl.convertRowIndexToModel(row));
                FoodEntryDatabase.deleteEntry(entry, date); // ← remove from DB too
                updateCalorieProgress();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Theme.BG_DARK);
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        // Assemble into frame
        add(tabbedPane,  BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        //progress bar updates with presaved data :)
        updateCalorieProgress();


        setSize(1000, 500);
        setVisible(true);
    }

    public static void setDailyLimit(int dailyLimit) { DAILY_LIMIT = dailyLimit; }

    /**
     * Constructs the CalorieMacroPage using the current session date.
     */
    public CalorieMacroPage() {
        this(SessionContext.getDate());
    }


    // === Helper methods ===
    /**
     * Creates a styled JButton with the provided text and applies the theme.
     *
     * @param text The button's label.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.NORMAL_FONT);
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        return button;
    }
    /**
     * Returns the current calorie tracking text to display above the progress bar.
     *
     * @return A formatted string showing current and goal calories.
     */
    private String getProgressText() {
        if (totalCalsSoFar <= DAILY_LIMIT) {
            return String.format("Calories so far: %d / %d", totalCalsSoFar, DAILY_LIMIT);
        } else {
            return String.format(
                    "Calories so far: %d (Over by %d)",
                    totalCalsSoFar,
                    totalCalsSoFar - DAILY_LIMIT
            );
        }
    }
    /**
     * Creates a panel for displaying a specific meal's food entry table and its filters.
     *
     * @param model The table model holding food entries.
     * @param table The JTable component.
     * @return A JPanel containing filters and the scrollable table.
     */
    private JPanel createMealPanel(FoodTableModel model, JTable table) {
        JPanel filterPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        filterPanel.setBackground(Theme.BG_DARK);
        for (int i = 0; i < 7; i++) {
            filterPanel.add(createStyledFilterField());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Theme.BG_DARKER);
        scrollPane.getViewport().setBackground(Theme.BG_DARKER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.BG_LIGHTER, 1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG_DARK);
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane,  BorderLayout.CENTER);
        return panel;
    }
    /**
     * Creates a themed JTextField for use in table filtering.
     *
     * @return A styled JTextField.
     */
    private JTextField createStyledFilterField() {
        JTextField field = new JTextField();
        field.setFont(Theme.NORMAL_FONT);
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Theme.FG_LIGHT);
        return field;
    }
    /**
     * Applies styling to a JTable component.
     *
     * @param model The data model for the table.
     * @return A JTable with custom styling.
     */
    private JTable createStyledTable(FoodTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(Theme.NORMAL_FONT);
        table.setBackground(Theme.BG_DARKER);
        table.setForeground(Theme.FG_LIGHT);
        table.setSelectionBackground(Theme.BG_LIGHTER);
        table.setSelectionForeground(Theme.FG_LIGHT);
        table.setGridColor(new Color(80, 80, 80));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Theme.FG_LIGHT);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        return table;
    }
    /**
     * Returns the currently visible JTable in the selected tab.
     *
     * @param tabs The JTabbedPane containing all meal tables.
     * @return The currently visible JTable, or null if not found.
     */
    private JTable getCurrentTable(JTabbedPane tabs) {
        Component sel = tabs.getSelectedComponent();
        if (sel instanceof JPanel) {
            for (Component comp : ((JPanel) sel).getComponents()) {
                if (comp instanceof JScrollPane) {
                    return (JTable) ((JScrollPane) comp).getViewport().getView();
                }
            }
        }
        return null;
    }
    /**
     * Returns the model corresponding to a specific JTable.
     *
     * @param table The JTable whose model is needed.
     * @return The associated FoodTableModel.
     */
    private FoodTableModel getModelForTable(JTable table) {
        if (table == breakfastTable) return breakfastModel;
        if (table == lunchTable)     return lunchModel;
        return dinnerModel;
    }
    /**
     * Opens the food entry dialog for adding or editing an entry.
     *
     * @param entry Existing entry if editing, null if adding
     * @param row   Row index in the table (for editing)
     * @param tabs  Reference to the tabbed pane to determine selected meal
     */
    private void openDialog(FoodEntry entry, int row, JTabbedPane tabs) {
        FoodEntryDialog dlg = new FoodEntryDialog(this, entry);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            FoodEntry rec = dlg.getRecord();
            FoodTableModel target = getModelForMeal(rec.getMealType());

            if (entry == null) {
                // Add new
                target.addRecord(rec);  // ⬅️ This already saves to DB. Removed duplicate saveEntry call.
            } else {
                // Edited entry
                if (!entry.getMealType().equals(rec.getMealType())) {
                    getModelForMeal(entry.getMealType()).removeRecord(row);
                    db.deleteEntry(entry, date);
                    target.addRecord(rec);  // saveEntry inside this method
                } else {
                    target.updateRecord(row, rec);
                    db.updateEntry(entry, rec, date);
                }
            }

            updateCalorieProgress();
        }
    }

    /**
     * Returns the model corresponding to the specified meal type.
     *
     * @param mealType "Breakfast", "Lunch", or "Dinner".
     * @return The corresponding FoodTableModel.
     */
    private FoodTableModel getModelForMeal(String mealType) {
        if ("Breakfast".equals(mealType)) return breakfastModel;
        if ("Lunch".equals(mealType))     return lunchModel;
        return dinnerModel;
    }
    /**
     * Recalculates total calories and updates the animated progress bar.
     */
    private void updateCalorieProgress() {
        int sum = 0;
        for (FoodEntry e : breakfastModel.getData()) sum += e.getCalories();
        for (FoodEntry e : lunchModel.getData())     sum += e.getCalories();
        for (FoodEntry e : dinnerModel.getData())    sum += e.getCalories();
        totalCalsSoFar = sum;
        calorieProgressBar.animateTo(Math.min(sum, DAILY_LIMIT));
        progressLabel.setText(getProgressText());
    }

    // -------------------- Supporting Inner Classes ------------------------
    /**
     * Represents a single food entry with nutritional information and meal context.
     * This class is used to store and retrieve user-inputted food tracking data.
     */
    public static class FoodEntry {
        private String foodName;
        private int calories;
        private String protein;
        private String carbs;
        private String fats;
        private String fiber;
        private String notes;
        private String mealType;

        /**
         * Constructs a FoodEntry object with all nutritional values and metadata.
         *
         * @param foodName  Name of the food item.
         * @param calories  Total calories in the food item.
         * @param protein   Amount of protein (in grams or description).
         * @param carbs     Amount of carbohydrates.
         * @param fats      Amount of fats.
         * @param fiber     Amount of fiber.
         * @param notes     Optional notes or description.
         * @param mealType  Meal category ("Breakfast", "Lunch", or "Dinner").
         */
        public FoodEntry(String foodName, int calories, String protein, String carbs,
                         String fats, String fiber, String notes, String mealType) {
            this.foodName = foodName;
            this.calories = calories;
            this.protein  = protein;
            this.carbs    = carbs;
            this.fats     = fats;
            this.fiber    = fiber;
            this.notes    = notes;
            this.mealType = mealType;
        }

        /** @return The name of the food item. */
        public String getFoodName() { return foodName; }

        /** @return The number of calories. */
        public int getCalories() { return calories; }

        /** @return The amount of protein. */
        public String getProtein() { return protein; }

        /** @return The amount of carbohydrates. */
        public String getCarbs() { return carbs; }

        /** @return The amount of fats. */
        public String getFats() { return fats; }

        /** @return The amount of fiber. */
        public String getFiber() { return fiber; }

        /** @return Any user notes associated with this entry. */
        public String getNotes() { return notes; }

        /** @return The meal type this entry belongs to. */
        public String getMealType() { return mealType; }

        /**
         * Sets the food item's name.
         * @param foodName The name to set.
         */
        public void setFoodName(String foodName) { this.foodName = foodName; }

        /**
         * Sets the calorie value.
         * @param calories The calorie count to set.
         */
        public void setCalories(int calories) { this.calories = calories; }

        /**
         * Sets the protein value.
         * @param protein The protein value to set.
         */
        public void setProtein(String protein) { this.protein = protein; }

        /**
         * Sets the carbohydrate value.
         * @param carbs The carbohydrate value to set.
         */
        public void setCarbs(String carbs) { this.carbs = carbs; }

        /**
         * Sets the fat value.
         * @param fats The fat value to set.
         */
        public void setFats(String fats) { this.fats = fats; }

        /**
         * Sets the fiber value.
         * @param fiber The fiber value to set.
         */
        public void setFiber(String fiber) { this.fiber = fiber; }

        /**
         * Sets the notes for the entry.
         * @param notes Any notes to attach to the entry.
         */
        public void setNotes(String notes) { this.notes = notes; }

        /**
         * Sets the meal type for the entry.
         * @param mealType The meal category ("Breakfast", "Lunch", or "Dinner").
         */
        public void setMealType(String mealType) { this.mealType = mealType; }
    }


    /**
     * Table model used to represent and manipulate a list of {@link FoodEntry} objects
     * in a JTable. Supports adding, updating, and removing entries, with automatic
     * database persistence and UI refresh.
     */
    public class FoodTableModel extends AbstractTableModel {
        private final String[] columns = {
                "Food", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes", "Meal"
        };
        private List<FoodEntry> data;

        /**
         * Constructs the table model using an existing list of food entries.
         *
         * @param data The list of {@link FoodEntry} records to manage.
         */
        public FoodTableModel(List<FoodEntry> data) {
            this.data = data;
        }

        /**
         * Returns the full list of current food entries.
         *
         * @return List of {@link FoodEntry} objects.
         */
        public List<FoodEntry> getData() {
            return data;
        }

        /**
         * Retrieves the food record at a specific row.
         *
         * @param row Row index in the table.
         * @return The {@link FoodEntry} at the specified row.
         */
        public FoodEntry getRecordAt(int row) {
            return data.get(row);
        }

        /**
         * Adds a new record to the model and database, then updates the table view.
         *
         * @param record The new {@link FoodEntry} to add.
         */
        public void addRecord(FoodEntry record) {
            data.add(record);
            CalorieMacroPage.this.db.saveEntry(record, SessionContext.getDate());
            fireTableDataChanged();
        }

        /**
         * Removes a record from the model and database, then updates the table view.
         *
         * @param row The row index of the record to remove.
         */
        public void removeRecord(int row) {
            FoodEntry entry = data.remove(row);
            FoodEntryDatabase.deleteEntry(entry, SessionContext.getDate());
            fireTableDataChanged();
        }

        /**
         * Updates a specific record in the model and database, then updates the table view.
         *
         * @param row    The index of the row to update.
         * @param record The new {@link FoodEntry} to replace the old one.
         */
        public void updateRecord(int row, FoodEntry record) {
            FoodEntry oldEntry = data.get(row);
            FoodEntryDatabase.updateEntry(oldEntry, record, SessionContext.getDate());
            data.set(row, record);
            fireTableDataChanged();
        }

        /** @return Number of rows in the table. */
        @Override
        public int getRowCount() {
            return data.size();
        }

        /** @return Number of columns in the table. */
        @Override
        public int getColumnCount() {
            return columns.length;
        }

        /**
         * Gets the column name at a specific index.
         *
         * @param column Column index.
         * @return Column name.
         */
        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        /**
         * Retrieves the value for a given cell in the table.
         *
         * @param rowIndex    Row index.
         * @param columnIndex Column index.
         * @return Object representing the cell value.
         */
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
        /**
         * Constructs the dialog window for editing or creating a food entry.
         *
         * @param parent The parent frame for positioning.
         * @param record The {@link FoodEntry} to edit, or null to create a new entry.
         */
        public FoodEntryDialog(Frame parent, FoodEntry record) {
            super(parent, "Food Entry Form", true);
            setLayout(new GridLayout(9, 2, 5, 5));

            Color dialogBg = Theme.BG_DARK;
            Color fieldBg  = new Color(60, 60, 60);
            Color fgLight  = Theme.FG_LIGHT;

            getContentPane().setBackground(dialogBg);

            String[] labels = {
                    "Food Name", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes"
            };
            for (int i = 0; i < labels.length; i++) {
                JLabel lbl = new JLabel(labels[i]);
                lbl.setForeground(fgLight);
                lbl.setFont(Theme.NORMAL_FONT);
                add(lbl);

                fields[i] = new JTextField();
                fields[i].setFont(Theme.NORMAL_FONT);
                fields[i].setBackground(fieldBg);
                fields[i].setForeground(fgLight);
                add(fields[i]);
            }

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

            JButton saveBtn   = new JButton("Save");
            JButton cancelBtn = new JButton("Cancel");
            saveBtn.setFont(Theme.NORMAL_FONT);
            saveBtn.setBackground(Theme.BUTTON_BG);
            saveBtn.setForeground(Theme.BUTTON_FG);
            cancelBtn.setFont(Theme.NORMAL_FONT);
            cancelBtn.setBackground(Theme.BUTTON_BG);
            cancelBtn.setForeground(Theme.BUTTON_FG);

            saveBtn.addActionListener(e -> {
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
            cancelBtn.addActionListener(e -> setVisible(false));

            add(saveBtn);
            add(cancelBtn);

            pack();
            setLocationRelativeTo(parent);
        }
        /**
         * Checks if the user pressed the "Save" button and passed validation.
         *
         * @return true if the user saved the entry, false if canceled or invalid.
         */
        public boolean isSaved() {
            return saved;
        }
        /**
         * Builds a {@link FoodEntry} from the user input fields.
         *
         * @return A new or updated {@link FoodEntry} instance.
         */
        public FoodEntry getRecord() {
            String food     = fields[0].getText().trim();
            int    calories = parseIntSafe(fields[1].getText());
            String protein  = defaultToZero(fields[2].getText());
            String carbs    = defaultToZero(fields[3].getText());
            String fats     = defaultToZero(fields[4].getText());
            String fiber    = defaultToZero(fields[5].getText());
            String notes    = fields[6].getText().trim();
            String mealType = (String) mealComboBox.getSelectedItem();
            return new FoodEntry(food, calories, protein, carbs, fats, fiber, notes, mealType);
        }
        /**
         * Parses a string into an integer, defaulting to 0 if parsing fails.
         *
         * @param text Input string to parse.
         * @return Parsed integer or 0.
         */

        private int parseIntSafe(String text) {
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        /**
         * Returns "0" if the text is null or empty, otherwise trims and returns it.
         *
         * @param text Input string.
         * @return Cleaned string or "0".
         */
        private String defaultToZero(String text) {
            return (text == null || text.trim().isEmpty()) ? "0" : text.trim();
        }

    }
    /**
     * A simplified DocumentListener that lets you handle all document changes
     * (insert, remove, update) with a single update() method.
     */
    @FunctionalInterface
    public interface MyDocumentListener extends DocumentListener {
        void update(DocumentEvent e);
        @Override default void insertUpdate(DocumentEvent e) { update(e); }
        @Override default void removeUpdate(DocumentEvent e) { update(e); }
        @Override default void changedUpdate(DocumentEvent e) { update(e); }
    }
}
