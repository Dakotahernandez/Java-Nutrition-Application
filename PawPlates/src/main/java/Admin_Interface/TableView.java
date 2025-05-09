package Admin_Interface;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.regex.Pattern;

public class TableView extends JPanel {
    private JTable table;
    private MyTableModel tableModel;
    private JTextField filterText;
    private JTextField statusText;
    private TableRowSorter<MyTableModel> sorter;

    public TableView() {
        super(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tableModel = new MyTableModel();
        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80); // User Type
        columnModel.getColumn(1).setPreferredWidth(80); // First Name
        columnModel.getColumn(2).setPreferredWidth(80); // Last Name
        columnModel.getColumn(3).setPreferredWidth(150); // Email
        columnModel.getColumn(4).setPreferredWidth(80);  // ID Number
        columnModel.getColumn(5).setPreferredWidth(90); // Fitness Level
        columnModel.getColumn(6).setPreferredWidth(70);  // Gender
        columnModel.getColumn(7).setPreferredWidth(140); // Security Q1
        columnModel.getColumn(8).setPreferredWidth(140); // Security Q2
        columnModel.getColumn(9).setPreferredWidth(140); // Answer 1
        columnModel.getColumn(10).setPreferredWidth(140); // Answer 2
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //horizontal scroll

        //making year and power left center bc they were on the right weird
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        int[] leftAlignCols = {3, 4, 6};
        for (int col : leftAlignCols) {
            table.getColumnModel().getColumn(col).setCellRenderer(leftRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton removeButton = new JButton("Remove");
        JButton editButton = new JButton("Edit");
        JButton deleteSelectedButton = new JButton("Delete Selected Rows");
        removeButton.addActionListener(e -> removeFilter());
        editButton.addActionListener(e -> handleEdit());
        deleteSelectedButton.addActionListener(e -> deleteSelectedRows());
        buttonsPanel.add(deleteSelectedButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(editButton);
        add(buttonsPanel, BorderLayout.NORTH);
        JPanel filterPanel = new JPanel(new SpringLayout());
        JLabel filterLabel = new JLabel("Filter Text:", JLabel.TRAILING);
        filterText = new JTextField();
        filterLabel.setLabelFor(filterText);
        filterPanel.add(filterLabel);
        filterPanel.add(filterText);
        JLabel statusLabel = new JLabel("Status:", JLabel.TRAILING);
        statusText = new JTextField();
        statusText.setEditable(false);
        statusLabel.setLabelFor(statusText);
        filterPanel.add(statusLabel);
        filterPanel.add(statusText);
        SpringUtilities.makeCompactGrid(filterPanel, 2, 2, 6, 6, 6, 6);
        add(filterPanel, BorderLayout.SOUTH);
        filterText.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { newFilter(); }
            public void removeUpdate(DocumentEvent e) { newFilter(); }
            public void changedUpdate(DocumentEvent e) { newFilter(); }
        });
    }

    private void newFilter() {
        RowFilter<MyTableModel, Object> rf;
        try {
            rf = RowFilter.regexFilter("(?i)" + Pattern.quote(filterText.getText()));
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
        statusText.setText("Filtered rows: " + table.getRowCount());
    }

    private void removeFilter() {
        filterText.setText("");
        sorter.setRowFilter(null);
        statusText.setText("Filter cleared.");
    }

    private void deleteSelectedRows() {
        int[] selected = table.getSelectedRows();
        if (selected.length == 0) {
            JOptionPane.showMessageDialog(this, "No rows selected.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
                this, "Delete selected rows?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        for (int i = selected.length - 1; i >= 0; i--) {
            int modelRow = table.convertRowIndexToModel(selected[i]);
            tableModel.removeRow(modelRow);
        }
        statusText.setText(selected.length + " rows deleted.");
    }

    private void handleEdit() {
        int[] selected = table.getSelectedRows();
        if (selected.length == 0) {
            JOptionPane.showMessageDialog(this, "No row selected.");
            return;
        } else if (selected.length > 1) {
            JOptionPane.showMessageDialog(this, "Cannot edit multiple rows at once.");
            return;
        }

        int modelIndex = table.convertRowIndexToModel(selected[0]);
        Object[] original = tableModel.getRow(modelIndex);
        Object[] edited = showAddRowDialog(original.clone());
        if (edited != null) {
            tableModel.updateRow(modelIndex, edited); 
            statusText.setText("Row edited.");
        }
    }


    
private Object[] showAddRowDialog(Object[] data) {
    String[] userTypes = {"User", "Trainer"};
    String[] fitnessLevels = {"Beginner", "Intermediate", "Advanced", "Elite"};
    String[] genderTypes = {"Other", "Female", "Male"};
    SecurityQuestion[] securityQuestions = SecurityQuestion.values();

    boolean isFromSelectedRow = data != null;
    JComboBox<String> userTypeField = new JComboBox<>(userTypes);
    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField IDField = new JTextField();
    JComboBox<String> FitnessLevelField = new JComboBox<>(fitnessLevels);
    JComboBox<String> GenderField = new JComboBox<>(genderTypes);
    JComboBox<SecurityQuestion> q1Field = new JComboBox<>(securityQuestions);
    JComboBox<SecurityQuestion> q2Field = new JComboBox<>(securityQuestions);
    JTextField a1Field = new JTextField();
    JTextField a2Field = new JTextField();

    if (isFromSelectedRow) {
        userTypeField.setSelectedItem(data[0]);
        firstNameField.setText(data[1].toString());
        lastNameField.setText(data[2].toString());
        emailField.setText(data[3].toString());
        IDField.setText(data[4].toString());
        FitnessLevelField.setSelectedItem(data[5]);
        GenderField.setSelectedItem(data[6]);
        q1Field.setSelectedItem(SecurityQuestion.fromLabel(data[7].toString()));
        q2Field.setSelectedItem(SecurityQuestion.fromLabel(data[8].toString()));

        a1Field.setText(data[9].toString());
        a2Field.setText(data[10].toString());
    }

    JPanel panel = new JPanel(new GridLayout(15, 3));
    JLabel instruction;
    if (isFromSelectedRow) {
        instruction = new JLabel("You selected this row as initial inputs. Please revise them as needed and press [Add Row] button.");
    } else {
        instruction = new JLabel("No row selected; provide inputs to all fields and press [Add Row] button.");
    }
    panel.add(instruction);
    panel.add(new JLabel(""));
    panel.add(new JLabel("Trainer/User:"));
    panel.add(userTypeField);
    panel.add(new JLabel("First Name:"));
    panel.add(firstNameField);
    panel.add(new JLabel("Last Name:"));
    panel.add(lastNameField);
    panel.add(new JLabel("Email:"));
    panel.add(emailField);
    panel.add(new JLabel("ID Number:"));
    panel.add(IDField);
    panel.add(new JLabel("Fitness Level:"));
    panel.add(FitnessLevelField);
    panel.add(new JLabel("Gender:"));
    panel.add(GenderField);
    panel.add(new JLabel("Security Q1:"));
    panel.add(q1Field);
    panel.add(new JLabel("Security Q2:"));
    panel.add(q2Field); 
    panel.add(new JLabel("Answer 1:"));
    panel.add(a1Field);
    panel.add(new JLabel("Answer 2:"));
    panel.add(a2Field);

    int result = JOptionPane.showConfirmDialog(
            this, panel, "Add New Row", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        try {
            Object[] newData = new Object[11];
            newData[0] = userTypeField.getSelectedItem();
            newData[1] = firstNameField.getText().trim();
            newData[2] = lastNameField.getText().trim();
            newData[3] = emailField.getText().trim();
            newData[4] = Integer.parseInt(IDField.getText().trim());
            newData[5] = FitnessLevelField.getSelectedItem();
            newData[6] = GenderField.getSelectedItem();
            newData[7] = q1Field.getSelectedItem().toString(); 
            newData[8] = q2Field.getSelectedItem().toString(); 
            newData[9] = a1Field.getText().trim();
            newData[10] = a2Field.getText().trim();
            return newData;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number in ID field.");
            return null;
        }
    } else {
        return null;
    }
}

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Administrater Interface: Users and Trainers Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TableView newContentPane = new TableView();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.pack();
        frame.setVisible(true);
    }

    //main for testing
    /*
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }*/
}
