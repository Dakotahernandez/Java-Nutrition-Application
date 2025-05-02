package tracking;

import frame.TemplateFrame;

import javax.swing.*;
import java.awt.*;
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Workout"));
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0; c.gridy = 3; c.gridwidth = 3; c.gridheight = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(scrollPane, c);

        //east panel
        JLabel nameLabel = new JLabel("Name: ");
        JLabel focusLabel = new JLabel("Focus: ");
        JLabel repsLabel = new JLabel("Reps: ");
        JLabel durationLabel = new JLabel("Duration (mins): ");
        JLabel caloriesLabel = new JLabel("Calories Burned: ");
        JLabel descriptionLabel = new JLabel("Description: ");
        Dimension labelSize = new Dimension(600, 25); // width, height
        nameLabel.setPreferredSize(labelSize);
        focusLabel.setPreferredSize(labelSize);
        repsLabel.setPreferredSize(labelSize);
        durationLabel.setPreferredSize(labelSize);
        caloriesLabel.setPreferredSize(labelSize);
        descriptionLabel.setPreferredSize(labelSize);
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        eastPanel.add(nameLabel,c); c.gridy++;
        eastPanel.add(focusLabel,c); c.gridy++;
        eastPanel.add(repsLabel,c); c.gridy++;
        eastPanel.add(durationLabel,c); c.gridy++;
        eastPanel.add(caloriesLabel,c); c.gridy++;
        eastPanel.add(descriptionLabel,c);
        add(eastPanel, BorderLayout.EAST);

        //west panel to center the panels
        JLabel blankLabel = new JLabel(" ");
        blankLabel.setPreferredSize(labelSize);
        westPanel.add(blankLabel);
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

        setVisible(true);
    }

    //creates a DefaultListModel of exercises given a List of exercises
    private static DefaultListModel<Exercise> toListModel(List<Exercise> list){
        DefaultListModel<Exercise> model = new DefaultListModel<>();
        list.forEach(model::addElement);
        return model;
    }
}

