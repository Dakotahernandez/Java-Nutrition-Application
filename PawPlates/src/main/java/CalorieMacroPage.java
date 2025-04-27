/**
 * =============================================================================
 * File:           CalorieMacroPage.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A Java Swing frame for tracking daily calories and macros by meal. Presents
 *   tabbed tables for breakfast, lunch, and dinner, allows adding/editing/deleting
 *   entries, and displays an animated progress bar. Now includes live filtering
 *   and column sorting using TableRowSorter.
 *
 * Dependencies:
 *   - javax.swing.*           (Swing components)
 *   - java.awt.*              (AWT layout and events)
 *   - java.time.LocalDate     (date handling)
 *   - java.time.format.DateTimeFormatter (date formatting)
 *   - java.util.regex.Pattern (regex filtering)
 *   - TemplateFrame           (application template frame)
 *   - AnimatedProgressBar     (smooth progress bar animation)
 *   - FoodEntry, FoodTableModel, FoodEntryDialog
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

public class CalorieMacroPage extends TemplateFrame {

    private static final int DAILY_LIMIT = 2000;
    private static int totalCalsSoFar = 0;
    private static AnimatedProgressBar calorieProgressBar;
    private static JLabel progressLabel;

    private final LocalDate date;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM d, yyyy");

    private FoodTableModel breakfastModel;
    private FoodTableModel lunchModel;
    private FoodTableModel dinnerModel;

    private JTable breakfastTable;
    private JTable lunchTable;
    private JTable dinnerTable;

    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public CalorieMacroPage(LocalDate date) {
        this.date = date;
        addMenuBarPanel();
        setTitle("Calorie/Macro Tracker – " + date.format(FORMATTER));

        calorieProgressBar = new AnimatedProgressBar(0, DAILY_LIMIT);
        calorieProgressBar.setForeground(Theme.ACCENT_GREEN);
        calorieProgressBar.setBackground(Theme.BG_LIGHTER);
        progressLabel = addProgressBar(calorieProgressBar, 0, getProgressText());
        progressLabel.setFont(Theme.HEADER_FONT);
        progressLabel.setForeground(Theme.FG_LIGHT);

        breakfastModel = new FoodTableModel(new ArrayList<>());
        lunchModel = new FoodTableModel(new ArrayList<>());
        dinnerModel = new FoodTableModel(new ArrayList<>());

        breakfastTable = createStyledTable(breakfastModel);
        lunchTable = createStyledTable(lunchModel);
        dinnerTable = createStyledTable(dinnerModel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Breakfast", createMealPanel(breakfastModel, breakfastTable));
        tabbedPane.addTab("Lunch", createMealPanel(lunchModel, lunchTable));
        tabbedPane.addTab("Dinner", createMealPanel(dinnerModel, dinnerTable));

        JButton addBtn = createStyledButton("Add");
        JButton editBtn = createStyledButton("Edit");
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
                    this, "Delete selected row?", "Confirm", JOptionPane.YES_NO_OPTION
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

        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(1000, 500);
        setVisible(true);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public CalorieMacroPage() {
        this(SessionContext.getDate());
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.NORMAL_FONT);
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        return button;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private String getProgressText() {
        if (totalCalsSoFar <= DAILY_LIMIT) {
            return String.format("Calories so far: %d / %d", totalCalsSoFar, DAILY_LIMIT);
        } else {
            return String.format("Calories so far: %d (Over by %d)", totalCalsSoFar, totalCalsSoFar - DAILY_LIMIT);
        }
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private JPanel createMealPanel(FoodTableModel model, JTable table) {
        JPanel filterPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        filterPanel.setBackground(Theme.BG_DARK);

        JTextField[] filterFields = new JTextField[7];
        for (int i = 0; i < 7; i++) {
            filterFields[i] = createStyledFilterField();
            filterFields[i].getDocument().addDocumentListener((MyDocumentListener) e -> applyFilter(table, filterFields));
            filterPanel.add(filterFields[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Theme.BG_DARKER);
        scrollPane.getViewport().setBackground(Theme.BG_DARKER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.BG_LIGHTER, 1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG_DARK);
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private JTextField createStyledFilterField() {
        JTextField field = new JTextField();
        field.setFont(Theme.NORMAL_FONT);
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Theme.FG_LIGHT);
        return field;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
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

        TableRowSorter<FoodTableModel> sorter = new TableRowSorter<>(model);
        sorter.setComparator(1, Comparator.comparingInt(Integer.class::cast));
        table.setRowSorter(sorter);

        return table;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private void applyFilter(JTable table, JTextField[] filterFields) {
        TableRowSorter<?> sorter = (TableRowSorter<?>) table.getRowSorter();
        if (sorter == null) return;

        StringBuilder regex = new StringBuilder();
        for (JTextField field : filterFields) {
            if (field != null && !field.getText().trim().isEmpty()) {
                regex.append("(?i)").append(Pattern.quote(field.getText().trim())).append("|");
            }
        }
        if (regex.length() > 0) {
            regex.setLength(regex.length() - 1);
            sorter.setRowFilter(RowFilter.regexFilter(regex.toString()));
        } else {
            sorter.setRowFilter(null);
        }
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
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
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private FoodTableModel getModelForTable(JTable table) {
        if (table == breakfastTable) return breakfastModel;
        if (table == lunchTable) return lunchModel;
        return dinnerModel;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
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
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private FoodTableModel getModelForMeal(String mealType) {
        if ("Breakfast".equals(mealType)) return breakfastModel;
        if ("Lunch".equals(mealType)) return lunchModel;
        return dinnerModel;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private void updateCalorieProgress() {
        int sum = 0;
        for (FoodEntry e : breakfastModel.getData()) sum += e.getCalories();
        for (FoodEntry e : lunchModel.getData()) sum += e.getCalories();
        for (FoodEntry e : dinnerModel.getData()) sum += e.getCalories();
        totalCalsSoFar = sum;
        calorieProgressBar.animateTo(Math.min(sum, DAILY_LIMIT));
        progressLabel.setText(getProgressText());
    }
}
