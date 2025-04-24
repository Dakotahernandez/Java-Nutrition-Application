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
 * TODO:
 *   - Connect to the database
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

public class CalorieMacroPage extends TemplateFrame {

    private static final int DAILY_LIMIT = 2000;
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
     * Primary constructor: accepts a date, sets title, and builds UI.
     */
    public CalorieMacroPage(LocalDate date) {
        this.date = date;

        addMenuBarPanel();
        setTitle("Calorie/Macro Tracker – " + date.format(FORMATTER));

        // -------- Progress Bar Setup --------
        calorieProgressBar = new AnimatedProgressBar(0, DAILY_LIMIT);
        calorieProgressBar.setForeground(Theme.ACCENT_GREEN);
        calorieProgressBar.setBackground(Theme.BG_LIGHTER);
        progressLabel = addProgressBar(calorieProgressBar, 0, getProgressText());
        progressLabel.setFont(Theme.HEADER_FONT);
        progressLabel.setForeground(Theme.FG_LIGHT);

        // -------- Initialize Models and Tables --------
        breakfastModel = new FoodTableModel(new ArrayList<>());
        lunchModel     = new FoodTableModel(new ArrayList<>());
        dinnerModel    = new FoodTableModel(new ArrayList<>());

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
                getModelForTable(tbl).removeRecord(tbl.convertRowIndexToModel(row));
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

        setSize(1000, 500);
        setVisible(true);
    }

    /**
     * No-arg constructor: defaults to the date in SessionContext.
     */
    public CalorieMacroPage() {
        this(SessionContext.getDate());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalorieMacroPage::new);
    }

    // === Helper methods ===

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.NORMAL_FONT);
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        return button;
    }

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

    private JTextField createStyledFilterField() {
        JTextField field = new JTextField();
        field.setFont(Theme.NORMAL_FONT);
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Theme.FG_LIGHT);
        return field;
    }

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

    private FoodTableModel getModelForTable(JTable table) {
        if (table == breakfastTable) return breakfastModel;
        if (table == lunchTable)     return lunchModel;
        return dinnerModel;
    }

    private void openDialog(FoodEntry entry, int row, JTabbedPane tabs) {
        FoodEntryDialog dlg = new FoodEntryDialog(this, entry);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            FoodEntry rec = dlg.getRecord();
            FoodTableModel target = getModelForMeal(rec.getMealType());
            if (entry == null) {
                target.addRecord(rec);
            } else {
                if (!entry.getMealType().equals(rec.getMealType())) {
                    getModelForMeal(entry.getMealType()).removeRecord(row);
                    target.addRecord(rec);
                } else {
                    target.updateRecord(row, rec);
                }
            }
            updateCalorieProgress();
        }
    }

    private FoodTableModel getModelForMeal(String mealType) {
        if ("Breakfast".equals(mealType)) return breakfastModel;
        if ("Lunch".equals(mealType))     return lunchModel;
        return dinnerModel;
    }

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

    public static class FoodEntry {
        private String foodName;
        private int calories;
        private String protein;
        private String carbs;
        private String fats;
        private String fiber;
        private String notes;
        private String mealType;

        public FoodEntry(
                String foodName,
                int calories,
                String protein,
                String carbs,
                String fats,
                String fiber,
                String notes,
                String mealType
        ) {
            this.foodName = foodName;
            this.calories = calories;
            this.protein  = protein;
            this.carbs    = carbs;
            this.fats     = fats;
            this.fiber    = fiber;
            this.notes    = notes;
            this.mealType = mealType;
        }

        public String getFoodName() { return foodName; }
        public int    getCalories() { return calories; }
        public String getProtein()  { return protein;  }
        public String getCarbs()    { return carbs;    }
        public String getFats()     { return fats;     }
        public String getFiber()    { return fiber;    }
        public String getNotes()    { return notes;    }
        public String getMealType() { return mealType; }

        public void setFoodName(String foodName) { this.foodName = foodName; }
        public void setCalories(int calories)    { this.calories = calories; }
        public void setProtein(String protein)   { this.protein  = protein;  }
        public void setCarbs(String carbs)       { this.carbs    = carbs;    }
        public void setFats(String fats)         { this.fats     = fats;     }
        public void setFiber(String fiber)       { this.fiber    = fiber;    }
        public void setNotes(String notes)       { this.notes    = notes;    }
        public void setMealType(String mealType) { this.mealType = mealType; }
    }

    public static class FoodTableModel extends AbstractTableModel {
        private final String[] columns = {
                "Food", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes", "Meal"
        };
        private List<FoodEntry> data;

        public FoodTableModel(List<FoodEntry> data) {
            this.data = data;
        }

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

        public boolean isSaved() {
            return saved;
        }

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

        private int parseIntSafe(String text) {
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException e) {
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
