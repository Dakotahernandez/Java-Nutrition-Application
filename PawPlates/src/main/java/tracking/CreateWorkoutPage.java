/**
 * =============================================================================
 * File: CreateWorkoutPage.java
 * Author: Joshua Carroll
 * Created: 3/29/2025
 * -----------------------------------------------------------------------------
 * Description:
 * GUI interface that allows users to create a new workout by selecting exercises
 * from their personal exercise list. Trainers can optionally convert the workout
 * into a class by entering a date. The form includes dynamic updating of labels,
 * validation of input, and integration with the database to persist the workout.
 *
 * Dependencies:
 * - javax.swing.*
 * - java.time.LocalDate
 * - java.util.List
 * - tracking.Exercise, Workout, TrainerClass
 * - tracking.ExerciseDatabase, WorkoutDatabase, TrainerClassDatabase
 * - frame.TemplateFrame, LoginPage, Theme
 *
 * Usage:
 * new CreateWorkoutPage(); // Launches the workout creation interface
 * =============================================================================
 */
package tracking;

import frame.LoginPage;
import frame.TemplateFrame;
import frame.Theme;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class CreateWorkoutPage extends TemplateFrame {
    private static final ExerciseDatabase exerciseDB = new ExerciseDatabase();
    private static final WorkoutDatabase workoutDB = new WorkoutDatabase();
    private static final TrainerClassDatabase trainerClassDB = new TrainerClassDatabase();


    DefaultListModel<Exercise> workoutDefaultList; //used to create JList
    DefaultListModel<Exercise> exerciseDefaultList; //used to create JList

    List<Exercise> exercises; //arrayList
    JList<Exercise> workoutJList; //used for scrollpane
    JList<Exercise> exerciseJList; //used for scrollpane

    JLabel workoutNameLbl;
    JLabel totalDurationLbl;
    JLabel totalCaloriesLbl;
    JLabel numExercisesLbl;
    JLabel nameLabel;
    JLabel focusLabel;
    JLabel repsLabel;
    JLabel durationLabel;
    JLabel caloriesLabel;
    JLabel descriptionLabel;

    Workout workout;

    /**
     * Constructs the CreateWorkoutPage GUI.
     * Users can build a workout from a list of exercises, name it, and optionally,
     * trainers can create a class from it. Data is stored via the appropriate database classes.
     */
    public CreateWorkoutPage() {
        setTitle("Create Workout");
        addMenuBarPanel();
        workout = new Workout();

        int userId = LoginPage.CURRENT_USER.getId();
        exercises = exerciseDB.loadExercisesForUser(userId);

        //centerPanel
        JTextField workoutName = new JTextField(15);
        addTextField("Workout Name:", workoutName, 0,0);

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

        if(LoginPage.CURRENT_USER.isTrainer()) {
            JButton createClass = new JButton("Create Class");
            c.gridx = 0;
            c.gridy = 8;
            c.gridwidth = 4;
            c.gridheight = 1;
            createClass.setBackground(Theme.BUTTON_BG);
            createClass.setForeground(Theme.BUTTON_FG);
            centerPanel.add(createClass, c);

            createClass.addActionListener(e -> {
                String name = workoutName.getText();
                if(name != null && !name.equals("")) {
                    if (workout.getExerciseCount() != 0) {
                        String input = JOptionPane.showInputDialog(this,
                                "Enter class date (yyyy-mm-dd):", "Class Creation Confirmation", JOptionPane.QUESTION_MESSAGE);
                        if (input != null) {
                            try {
                                LocalDate date = LocalDate.parse(input);
                                workout.setDate(date);
                                workout.setName(name);
                                JOptionPane.showMessageDialog(this,
                                        "Created Class: " + workout.getName(), "Class Creation", JOptionPane.INFORMATION_MESSAGE);

                                TrainerClass trainerClass = new TrainerClass(date,name,workout.getExercises(),
                                        LoginPage.CURRENT_USER.getId(), new ArrayList<>());
                                int classId = trainerClassDB.saveTrainerClass(trainerClass);
                                if (classId == -1) {
                                    System.out.println("TrainerClass Not Saved to Database");
                                } else {
                                    trainerClass.setId(classId);
                                }

                                workoutName.setText("");
                                workoutDefaultList.clear();
                                workout = new Workout();
                                totalDurationLbl.setText("Total Duration: ");
                                totalCaloriesLbl.setText("Total Calories Burned: ");
                                numExercisesLbl.setText("Number of Exercises: ");
                                nameLabel.setText("Selected Exercise: ");
                                focusLabel.setText("Focus: ");
                                caloriesLabel.setText("Calories Burned: ");
                                repsLabel.setText("Reps: ");
                                durationLabel.setText( "Duration: ");
                                descriptionLabel.setText("Description: ");
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(this,
                                        "Invalid date format. Please use yyyy-mm-dd.");
                            }
                        }
                    } else{
                        JOptionPane.showMessageDialog(this,
                                "Please add an exercise to the Class.");
                    }
                } else{
                    JOptionPane.showMessageDialog(this,
                            "Please add a Class name.");
                }
            });
        }


        //east panel
        Dimension labelSize = new Dimension(600, 25); // width, height
        workoutNameLbl = new JLabel("Workout Name: ");
        totalDurationLbl = new JLabel("Total Duration: ");
        totalCaloriesLbl = new JLabel("Total Calories Burned: ");
        numExercisesLbl = new JLabel("Number of Exercises: ");
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



        nameLabel = new JLabel("Selected Exercise: ");
        focusLabel = new JLabel("Focus: ");
        repsLabel = new JLabel("Reps: ");
        durationLabel = new JLabel("Duration (mins): ");
        caloriesLabel = new JLabel("Calories Burned: ");
        descriptionLabel = new JLabel("Description: ");
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


        //west panel
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

        exerciseDefaultList = toListModel(exercises);
        exerciseJList = new JList<>(exerciseDefaultList);
        exerciseJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sP = new JScrollPane(exerciseJList);
        sP.setPreferredSize(new Dimension(150, 300));
        sP.setBorder(BorderFactory.createTitledBorder("Your Exercises"));
        c.insets = new Insets(25, 5, 5, 5);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 1; c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        westPanel.add(sP, c);

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

        createWorkout.addActionListener(e->{
                    String name = workoutName.getText();
                    if(name != null && !name.equals("")) {
                        if (workout.getExerciseCount() != 0) {
                            String input = JOptionPane.showInputDialog(this,
                                    "Enter workout date (yyyy-mm-dd):", "Workout Creation Confirmation", JOptionPane.QUESTION_MESSAGE);
                            if (input != null) { // user does not cancel
                                try {
                                    LocalDate date = LocalDate.parse(input);
                                    workout.setDate(date);
                                    workout.setName(name);
                                    JOptionPane.showMessageDialog(this,
                                            "Created Workout: " + workout.getName(), "Workout Creation", JOptionPane.INFORMATION_MESSAGE);

                                    //save workout to the Database here
                                    int id = workoutDB.saveWorkout(workout);
                                    if(id == -1){
                                        System.out.println("Workout Not Saved to Database");
                                    } else{
                                        workout.setId(id);
                                    }

                                    //clearing current workout creation
                                    workoutName.setText("");
                                    workoutDefaultList.clear();
                                    workout = new Workout();
                                    totalDurationLbl.setText("Total Duration: ");
                                    totalCaloriesLbl.setText("Total Calories Burned: ");
                                    numExercisesLbl.setText("Number of Exercises: ");
                                    nameLabel.setText("Selected Exercise: ");
                                    focusLabel.setText("Focus: ");
                                    caloriesLabel.setText("Calories Burned: ");
                                    repsLabel.setText("Reps: ");
                                    durationLabel.setText( "Duration: ");
                                    descriptionLabel.setText("Description: ");
                                } catch (DateTimeParseException ex) {
                                    JOptionPane.showMessageDialog(this,
                                            "Invalid date format. Please use yyyy-mm-dd.");
                                }
                            }
                        } else{
                            JOptionPane.showMessageDialog(this,
                                    "Please add an exercise to the workout.");
                        }
                    } else{
                        JOptionPane.showMessageDialog(this,
                                "Please add a workout name.");
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

    /**
     * Converts a list of Exercise objects to a DefaultListModel,
     * allowing it to be used in JList components.
     *
     * @param list the list of Exercise objects
     * @return a DefaultListModel containing the same elements
     */
    private static DefaultListModel<Exercise> toListModel(List<Exercise> list){
        DefaultListModel<Exercise> model = new DefaultListModel<>();
        list.forEach(model::addElement);
        return model;
    }
}

