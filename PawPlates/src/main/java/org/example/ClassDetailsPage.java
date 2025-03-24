package org.example;
import javax.swing.*;
import java.awt.*;

public class ClassDetailsPage extends JFrame{

    //will update to take a Class object as a parameter
    public ClassDetailsPage(String className, String instructor, String description, String schedule, int capacity, int availableSpots){
        setTitle("Class Details");
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblClassName = new JLabel(className, SwingConstants.CENTER);
        add(lblClassName, BorderLayout.NORTH);

        //still work in progress
    }

    public static void main(String[] args) {

        new ClassDetailsPage("Yoga Class", "Jane Doe",
                "A relaxing yoga session for all experience levels.",
                "Mon, Wed, Fri - 7:00 PM", 20, 12);
    }
}
