
// This class represents an exercise

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Exercise {
    private String name;
    private String focus;
    private String description;

    public Exercise() {
        name ="";
        focus = "";
        description = "";
    }

    public Exercise(String name, String focus, String description) {
        this.name = name;
        this.focus = focus;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(name, exercise.name) && Objects.equals(focus, exercise.focus) && Objects.equals(description, exercise.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, focus, description);
    }

    //returns 1 if written, 0 if it already exists, -1 if exception
    public int writeCSV() {
        if(!exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/exercise.csv", true))) {
                writer.write("\n" + name + "," + focus + "," + description);
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    public List<Exercise> readCSV(){
        List<Exercise> exercises = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/exercise.csv"))){
            String line = reader.readLine();

            while((line = reader.readLine()) != null) {
                String[] val = line.split(",");
                if (val.length >= 3) {
                    exercises.add(new Exercise(val[0].trim(), val[1].trim(), val[2].trim()));
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return exercises;
    }

    //checks if this exercise already exists
    public boolean exists(){
        List<Exercise> exercises = readCSV();
        return exercises.stream().anyMatch(e -> e.equals(this));
    }
}
