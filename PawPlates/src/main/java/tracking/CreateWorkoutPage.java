package tracking;

import frame.TemplateFrame;
import frame.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class CreateWorkoutPage extends TemplateFrame {
    DefaultListModel<Exercise> currExercises;
    List<Exercise> exercises;

    public CreateWorkoutPage() {
        setTitle("Create Workout");
        addMenuBarPanel();

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

        currExercises = toListModel(exercises);
        JList<Exercise> workoutList = new JList<>(currExercises);
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(workoutList);
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

        JList<Exercise> exerciseList = new JList<>(currExercises);
        exerciseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sP = new JScrollPane(exerciseList);
        sP.setPreferredSize(new Dimension(180, 300));
        sP.setBorder(BorderFactory.createTitledBorder("Your Exercises"));
        c.insets = new Insets(25, 5, 5, 5);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 1; c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        westPanel.add(sP, c);

        //invisible button for spacing
        c.insets = new Insets(10, 10, 10, 10);
        JButton addButton = new JButton("Add Exercise");
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



        workoutList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int index = workoutList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Exercise selected = workoutList.getModel().getElementAt(index);
                        nameLabel.setText("Selected Exercise: " + selected.getName());
                        focusLabel.setText("Focus: " + selected.getFocus() );
                                caloriesLabel.setText("Calories Burned: " + selected.getCaloriesBurned());
                                repsLabel.setText("Reps: " + selected.getReps() );
                                durationLabel.setText( "Duration: " + selected.getDuration() );
                                descriptionLabel.setText("Description: " + selected.getDescription());

                    }
                }
            }
        });

        exerciseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int index = workoutList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Exercise selected = workoutList.getModel().getElementAt(index);
                        nameLabel.setText("Selected Exercise: " + selected.getName());
                        focusLabel.setText("Focus: " + selected.getFocus() );
                        caloriesLabel.setText("Calories Burned: " + selected.getCaloriesBurned());
                        repsLabel.setText("Reps: " + selected.getReps() );
                        durationLabel.setText( "Duration: " + selected.getDuration() );
                        descriptionLabel.setText("Description: " + selected.getDescription());

                    }
                }
            }
        });

        workoutList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                exerciseList.clearSelection();
            }
        });

        exerciseList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                workoutList.clearSelection();
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

