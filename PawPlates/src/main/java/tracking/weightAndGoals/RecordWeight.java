package tracking.weightAndGoals;
/**
 * =============================================================================
 * File:        RecordWeight.java
 * Authors:     Dakota Hernandez, Eli Hall
 * Created:     04/24/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Page for tracking daily weight over time. Includes a scrollable table
 *   for past entries, a line graph displaying progress against a target
 *   weight (if set), and an input form to enter new data. The number of days
 *   shown is adjustable via the DAYS_SHOWN constant.
 *
 * Dependencies:
 *   - javax.swing.*
 *   - javax.swing.table.DefaultTableModel
 *   - java.awt.*
 *   - java.time.LocalDate
 *   - java.time.format.DateTimeFormatter
 *   - org.jfree.chart.*
 *   - org.jfree.data.time.*
 *
 * Usage:
 *   new RecordWeight(LocalDate selectedDate);
 *
 * =============================================================================
 */
import frame.TemplateFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import frame.Theme;
import frame.LoginPage;
import tracking.weightAndGoals.WeightDatabase;
import tracking.weightAndGoals.WeightDatabase.WeightEntry;
import tracking.weightAndGoals.WeightDatabase.WeightGoal;


public class RecordWeight extends TemplateFrame {
    private static int START_WEIGHT = 150;
    private double curWeight;
    private static final int DAYS_SHOWN = 10;
    private final LocalDate pageDate;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private TimeSeries weightSeries;
    private TimeSeries goalSeries;
    private TimeSeriesCollection dataset;
    private ChartPanel chartPanel;

    private DefaultTableModel tableModel;
    private JTable weightTable;

    public RecordWeight(LocalDate date) {
        this.pageDate = date;
        addMenuBarPanel();
        setTitle("Weight Tracker");

        // --- Database setup & load goals/entries ---
        WeightDatabase db = new WeightDatabase();
        int userId = LoginPage.CURRENT_USER.getId();
        double startWeight = START_WEIGHT;
        Double goalWeight = null;

        try {
            Optional<WeightGoal> gw = db.getWeightGoal(userId);
            if (gw.isPresent()) {
                startWeight = gw.get().startWeight;
                goalWeight = gw.get().goalWeight;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // --- Setup data series ---
        weightSeries = new TimeSeries("Weight");
        if (goalWeight != null) {
            goalSeries = new TimeSeries("Goal Weight");
        }
        tableModel = new DefaultTableModel(new Object[]{"Date", "Weight (lbs)"}, 0);

        // load existing entries
        Map<LocalDate, Double> entryMap = new HashMap<>();
        try {
            for (WeightEntry entry : db.getWeightEntries(userId)) {
                entryMap.put(entry.date, entry.weight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate past DAYS_SHOWN days
        LocalDate today = pageDate;
        LocalDate start = today.minusDays(DAYS_SHOWN - 1);
        double lastWeight = startWeight;

        for (int i = 0; i < DAYS_SHOWN; i++) {
            LocalDate d = start.plusDays(i);
            Day day = new Day(d.getDayOfMonth(), d.getMonthValue(), d.getYear());
            double weight = entryMap.getOrDefault(d, lastWeight);
            weightSeries.addOrUpdate(day, weight);
            if (goalWeight != null) {
                goalSeries.addOrUpdate(day, goalWeight);
            }
            tableModel.addRow(new Object[]{d.format(DATE_FMT), String.format("%.1f", weight)});
            lastWeight = weight;
        }
        curWeight = lastWeight;

        // --- Dataset and chart setup ---
        dataset = new TimeSeriesCollection();
        dataset.addSeries(weightSeries);
        if (goalWeight != null) {
            dataset.addSeries(goalSeries);
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Weight Progress", "Date", "Weight (lbs)", dataset, false, true, false
        );
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Theme.BG_DARK);
        plot.setDomainGridlinePaint(Theme.MID_GRAY);
        plot.setRangeGridlinePaint(Theme.MID_GRAY);
        plot.getRenderer().setSeriesPaint(0, Theme.ACCENT_COLOR);
        if (goalWeight != null) {
            plot.getRenderer().setSeriesPaint(1, Theme.GOAL_WEIGHT);
        }
        chart.setBackgroundPaint(Theme.BG_DARK);
        chart.getTitle().setPaint(Theme.FG_LIGHT);
        plot.getDomainAxis().setLabelPaint(Theme.FG_LIGHT);
        plot.getDomainAxis().setTickLabelPaint(Theme.FG_LIGHT);
        plot.getRangeAxis().setLabelPaint(Theme.FG_LIGHT);
        plot.getRangeAxis().setTickLabelPaint(Theme.FG_LIGHT);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Theme.BG_DARK);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Table setup ---
        weightTable = new JTable(tableModel);
        weightTable.setBackground(Theme.BG_DARK);
        weightTable.setForeground(Theme.FG_LIGHT);
        weightTable.setFont(Theme.NORMAL_FONT);
        weightTable.setGridColor(Theme.MID_GRAY);
        weightTable.setRowHeight(24);
        weightTable.getTableHeader().setForeground(Theme.FG_LIGHT);
        weightTable.getTableHeader().setBackground(Theme.BG_DARK);
        weightTable.getTableHeader().setFont(Theme.NORMAL_FONT);
        JScrollPane tableScroll = new JScrollPane(weightTable);
        tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Input panel ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Theme.BG_DARK);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        inputPanel.setPreferredSize(new Dimension(300, 300));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel dateLabel = new JLabel("Date: " + pageDate.format(DATE_FMT));
        dateLabel.setForeground(Theme.FG_LIGHT);
        dateLabel.setFont(Theme.NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(dateLabel, gbc);

        JLabel weightLabel = new JLabel("Current Weight (lbs):");
        weightLabel.setForeground(Theme.FG_LIGHT);
        weightLabel.setFont(Theme.NORMAL_FONT);
        gbc.gridy = 1;
        inputPanel.add(weightLabel, gbc);

        JTextField weightField = new JTextField(10);
        weightField.setBackground(Theme.BG_LIGHTER);
        weightField.setForeground(Theme.FG_LIGHT);
        weightField.setCaretColor(Theme.FG_LIGHT);
        weightField.setFont(Theme.NORMAL_FONT);
        gbc.gridx = 1;
        inputPanel.add(weightField, gbc);

        JButton confirm = new JButton("Confirm");
        confirm.setBackground(Theme.ACCENT_COLOR);
        confirm.setForeground(Theme.FG_LIGHT);
        confirm.setFont(Theme.NORMAL_FONT);
        confirm.setBorder(BorderFactory.createLineBorder(Theme.BUTTON_BORDER));
        gbc.gridx = 2;
        inputPanel.add(confirm, gbc);

        // --- Button logic ---
        Double finalGoalWeight = goalWeight;
        confirm.addActionListener(e -> {
            try {
                int weight = Integer.parseInt(weightField.getText());
                Day todayDay = new Day(pageDate.getDayOfMonth(), pageDate.getMonthValue(), pageDate.getYear());

                weightSeries.addOrUpdate(todayDay, weight);
                try {
                    db.addWeightEntry(userId, pageDate, weight);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                chartPanel.revalidate();
                chartPanel.repaint();
                tableModel.addRow(new Object[]{pageDate.format(DATE_FMT), weight});

                String message;
                if (finalGoalWeight != null && weight > finalGoalWeight && weight < curWeight) {
                    message = "Great work, you're making progress towards your weight goal";
                } else if (finalGoalWeight != null && weight <= finalGoalWeight) {
                    message = "Congrats, you've met your weight goal!";
                } else {
                    message = "Uh oh, double check your plan to stay on track";
                }
                JOptionPane.showMessageDialog(this, message);
                curWeight = weight;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Layout panels ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Theme.BG_DARK);
        leftPanel.add(tableScroll);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(inputPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, chartPanel);
        splitPane.setResizeWeight(0.3);
        splitPane.setBackground(Theme.BG_DARK);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        getContentPane().add(splitPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordWeight(LocalDate.now()));
    }
}
