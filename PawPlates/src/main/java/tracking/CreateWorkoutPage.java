package tracking;

import frame.TemplateFrame;
import frame.Theme;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultTextUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class CreateWorkoutPage extends TemplateFrame {
    DefaultListModel<Exercise> workoutDefaultList; //used to create JList
    DefaultListModel<Exercise> exerciseDefaultList; //used to create JList

    List<Exercise> exercises; //arrayList
    JList<Exercise> workoutJList; //used for scrollpane
    JList<Exercise> exerciseJList; //used for scrollpane

    Workout workout;

    public CreateWorkoutPage() {
        setTitle("Create Workout");
        addMenuBarPanel();
        workout = new Workout();

        //test exercises will need to get user exercises from database
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Push-ups", "Chest", 20, 0, 100, "Standard bodyweight push-ups."));
        exercises.add(new Exercise("Running", "Cardio", 0, 30, 300, "Outdoor running session."));
        exercises.add(new Exercise("Squats", "Legs", 15, 0, 120, "Bodyweight squats."));
        exercises.add(new Exercise("Jump Rope", "Cardio", 0, 10, 150, "Continuous jumping rope."));
        exercises.add(new Exercise("Plank", "Core", 0, 5, 80, "Hold plank position."));

        exercises.add(new Exercise("Push-ups", "Chest", 20, 0, 100, "Standard bodyweight push-ups."));
        exercises.add(new Exercise("Running", "Cardio", 0, 30, 300, "Outdoor running session."));
        exercises.add(new Exercise("Squats", "Legs", 15, 0, 120, "Bodyweight squats."));
        exercises.add(new Exercise("Jump Rope", "Cardio", 0, 10, 150, "Continuous jumping rope."));
        exercises.add(new Exercise("Plank", "Core", 0, 5, 80, "Hold plank position."));
        exercises.add(new Exercise("Push-ups", "Chest", 20, 0, 100, "Standard bodyweight push-ups."));
        exercises.add(new Exercise("Running", "Cardio", 0, 30, 300, "Outdoor running session."));
        exercises.add(new Exercise("Squats", "Legs", 15, 0, 120, "Bodyweight squats."));
        exercises.add(new Exercise("Jump Rope", "Cardio", 0, 10, 150, "Continuous jumping rope."));
        exercises.add(new Exercise("Plank", "Core", 0, 5, 80, "Hold plank position."));
        exercises.add(new Exercise("Push-ups", "Chest", 20, 0, 100, "Standard bodyweight push-ups."));
        exercises.add(new Exercise("Running", "Cardio", 0, 30, 300, "Outdoor running session."));
        exercises.add(new Exercise("Squats", "Legs", 15, 0, 120, "Bodyweight squats."));
        exercises.add(new Exercise("Jump Rope", "Cardio", 0, 10, 150, "Continuous jumping rope."));
        exercises.add(new Exercise("Plank", "Core", 0, 5, 80, "Hold plank position."));

        //centerPanel
        JTextField workoutName = new JTextField(15);
        addTextField("Workout Name:", workoutName, 0,0);

//        workoutDefaultList = toListModel(exercises);
        workoutDefaultList = workout.getDefaultListModel();
        workoutJList = new JList<>(workoutDefaultList);
        workoutJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(workoutJList);
        scrollPane.setPreferredSize(new Dimension(180, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Workout"));
        c.insets = new Insets(25, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0; c.gridy = 3; c.gridwidth = 4; c.gridheight = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(scrollPane, c);

        c.insets = new Insets(10, 10, 10, 10);
        JButton removeExercise = new JButton("Remove Exercise");
        c.gridx = 0; c.gridy = 6; c.gridwidth = 4; c.gridheight = 1;
        removeExercise.setBackground(Theme.BUTTON_BG);
        removeExercise.setForeground(Theme.BUTTON_FG);
        centerPanel.add(removeExercise, c);

        JButton createWorkout = new JButton("Create Workout");
        c.gridx = 0; c.gridy = 7; c.gridwidth = 4; c.gridheight = 1;
        createWorkout.setBackground(Theme.BUTTON_BG);
        createWorkout.setForeground(Theme.BUTTON_FG);
        centerPanel.add(createWorkout, c);


        //east panel
        Dimension labelSize = new Dimension(600, 25); // width, height
        JLabel workoutNameLbl = new JLabel("Workout Name: ");
        JLabel totalDurationLbl = new JLabel("Total Duration: ");
        JLabel totalCaloriesLbl = new JLabel("Total Calories Burned: ");
        JLabel numExercisesLbl = new JLabel("Number of Exercises: ");
        workoutNameLbl.setPreferredSize(labelSize);
        totalDurationLbl.setPreferredSize(labelSize);
        totalCaloriesLbl.setPreferredSize(labelSize);
        numExercisesLbl.setPreferredSize(labelSize);

        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;

        eastPanel.add(workoutNameLbl,c); c.gridy++;
        eastPanel.add(totalDurationLbl,c); c.gridy++;
        eastPanel.add(totalCaloriesLbl,c); c.gridy++;
        eastPanel.add(numExercisesLbl,c); c.gridy++;



        JLabel nameLabel = new JLabel("Selected Exercise: ");
        JLabel focusLabel = new JLabel("Focus: ");
        JLabel repsLabel = new JLabel("Reps: ");
        JLabel durationLabel = new JLabel("Duration (mins): ");
        JLabel caloriesLabel = new JLabel("Calories Burned: ");
        JLabel descriptionLabel = new JLabel("Description: ");
        nameLabel.setPreferredSize(labelSize);
        focusLabel.setPreferredSize(labelSize);
        repsLabel.setPreferredSize(labelSize);
        durationLabel.setPreferredSize(labelSize);
        caloriesLabel.setPreferredSize(labelSize);
        descriptionLabel.setPreferredSize(labelSize);
        c.insets = new Insets(100, 5, 5, 5);
        eastPanel.add(nameLabel,c); c.gridy++;
        c.insets = new Insets(5, 5, 5, 5);
        eastPanel.add(focusLabel,c); c.gridy++;
        eastPanel.add(repsLabel,c); c.gridy++;
        eastPanel.add(durationLabel,c); c.gridy++;
        eastPanel.add(caloriesLabel,c); c.gridy++;
        eastPanel.add(descriptionLabel,c);
        add(eastPanel, BorderLayout.EAST);


        //west panel to center the panels
        JLabel blankLabel = new JLabel("");
        blankLabel.setPreferredSize(new Dimension(300,25));
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        westPanel.add(blankLabel);
        JLabel blankLabel2 = new JLabel("");
        blankLabel2.setPreferredSize(new Dimension(300,25));
        c.gridx = 0; c.gridy = 1;
        westPanel.add(blankLabel2);

        //for testing
        exerciseDefaultList = toListModel(exercises);
//        exerciseDefaultList= new DefaultListModel<>();
        exerciseJList = new JList<>(exerciseDefaultList);
        exerciseJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sP = new JScrollPane(exerciseJList);
        sP.setPreferredSize(new Dimension(180, 300));
        sP.setBorder(BorderFactory.createTitledBorder("Your Exercises"));
        c.insets = new Insets(25, 5, 5, 5);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 1; c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        westPanel.add(sP, c);

        //invisible button for spacing
        c.insets = new Insets(10, 10, 10, 10);
        JButton addButton = new JButton("Add Exercise To Workout");
        c.gridx = 1; c.gridy = 2; c.gridwidth = 2; c.gridheight = 1;
        addButton.setBackground(Theme.BUTTON_BG);
        addButton.setForeground(Theme.BUTTON_FG);
        westPanel.add(addButton, c);

        c.insets = new Insets(10, 10, 10, 10);
        JButton createExercise = new JButton("Create Exercise");
        c.gridx = 1; c.gridy = 3; c.gridwidth = 2; c.gridheight = 1;
        createExercise.setBackground(Theme.BUTTON_BG);
        createExercise.setForeground(Theme.BUTTON_FG);
        westPanel.add(createExercise, c);



        add(westPanel, BorderLayout.WEST);



        workoutName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                workoutNameLbl.setText("Workout Name: " + workoutName.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                workoutNameLbl.setText("Workout Name: " + workoutName.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        createWorkout.addActionListener(e->{ //FIXME do not allow creation if name is empty
                    String input = JOptionPane.showInputDialog(this,
                            "Enter workout date (yyyy-mm-dd):","Workout Creation Confirmation", JOptionPane.QUESTION_MESSAGE);
                    if (input != null) { // user does not cancel
                        try {
                            LocalDate date = LocalDate.parse(input);
                            workout.setDate(date);
                            workout.setName(workoutName.getText());
                            JOptionPane.showMessageDialog(this,
                                    "Created Exercise: " + workout.getName(), "Workout Creation",JOptionPane.INFORMATION_MESSAGE);
                            //FIXME save workout and clear selections
                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid date format. Please use yyyy-MM-dd.");
                        }
                    }
        });

        createExercise.addActionListener(e->{
                JFrame current = (JFrame) SwingUtilities.getWindowAncestor(createExercise);
                new CreateExercise();
                current.dispose();
        });

        addButton.addActionListener(e->{
            Exercise exercise = (exerciseJList.getSelectedValue());
            if(exercise != null) {
                workout.addExcercise(exercise);
                totalDurationLbl.setText("Total Duration: " + workout.getTotalDuration());
                totalCaloriesLbl.setText("Total Calories Burned: " + workout.getTotalCalories());
                numExercisesLbl.setText("Number of Exercises: " + workout.getExerciseCount());

                workoutDefaultList.addElement(exercise);
            }
            else{
                JOptionPane.showMessageDialog(this, "Please select an exercise to add.");
            }

        });

        removeExercise.addActionListener(e->{
            int index =  workoutJList.getSelectedIndex();
            if(index != -1) {
                workout.removeExercise(workoutJList.getSelectedValue());
                workoutDefaultList.remove(index);
                totalDurationLbl.setText("Total Duration: " + workout.getTotalDuration());
                totalCaloriesLbl.setText("Total Calories Burned: " + workout.getTotalCalories());
                numExercisesLbl.setText("Number of Exercises: " + workout.getExerciseCount());

                nameLabel.setText("Selected Exercise: ");
                focusLabel.setText("Focus: ");
                caloriesLabel.setText("Calories Burned: ");
                repsLabel.setText("Reps: ");
                durationLabel.setText( "Duration: ");
                descriptionLabel.setText("Description: ");
            }
            else{
                JOptionPane.showMessageDialog(this, "Please select an exercise to remove.");
            }
        });

        workoutJList.addListSelectionListener(e->{
            if (!e.getValueIsAdjusting()) {
                Exercise selected = workoutJList.getSelectedValue();
                if (selected != null) {
                    nameLabel.setText("Selected Exercise: " + selected.getName());
                    focusLabel.setText("Focus: " + selected.getFocus() );
                    caloriesLabel.setText("Calories Burned: " + selected.getCaloriesBurned());
                    repsLabel.setText("Reps: " + selected.getReps() );
                    durationLabel.setText( "Duration: " + selected.getDuration() );
                    descriptionLabel.setText("Description: " + selected.getDescription());
                }
            }
        });

        exerciseJList.addListSelectionListener(e->{
            if (!e.getValueIsAdjusting()) {
                Exercise selected = exerciseJList.getSelectedValue();
                if (selected != null) {
                    nameLabel.setText("Selected Exercise: " + selected.getName());
                    focusLabel.setText("Focus: " + selected.getFocus() );
                    caloriesLabel.setText("Calories Burned: " + selected.getCaloriesBurned());
                    repsLabel.setText("Reps: " + selected.getReps() );
                    durationLabel.setText( "Duration: " + selected.getDuration() );
                    descriptionLabel.setText("Description: " + selected.getDescription());
                }
            }
        });


        workoutJList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                exerciseJList.clearSelection();
            }
        });

        exerciseJList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                workoutJList.clearSelection();
            }
        });

        setVisible(true);
    }

    //creates a DefaultListModel of exercises given a List of exercises
    private static DefaultListModel<Exercise> toListModel(List<Exercise> list){
        DefaultListModel<Exercise> model = new DefaultListModel<>();
        list.forEach(model::addElement);
        return model;
    }
}

