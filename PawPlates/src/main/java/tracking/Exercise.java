package tracking; /**
 * =============================================================================
 * File:
 * Author:
 * Created:
 * -----------------------------------------------------------------------------
 * Description:
 *
 *
 * Dependencies:
 *
 *
 * Usage:
 *
 * =============================================================================
 */
import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Exercise {
    private String name;
    private String focus; //cardio, weight training
    private String description;
    private int durationReps;
    private int caloriesBurned;
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Exercise() {
        name ="";
        focus = "";
        description = "";
        durationReps = 0;
        caloriesBurned = 0;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Exercise(String name, String focus, int durationReps, int caloriesBurned,String description) {
        this.name = name;
        this.focus = focus;
        this.description = description;
        this.durationReps = durationReps;
        this.caloriesBurned = caloriesBurned;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getName() {
        return name;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getFocus() {
        return focus;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setFocus(String focus) {
        this.focus = focus;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getDescription() {
        return description;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationReps() {
        return durationReps;
    }

    public void setDurationReps(int durationReps) {
        this.durationReps = durationReps;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return durationReps == exercise.durationReps && caloriesBurned == exercise.caloriesBurned && Objects.equals(name, exercise.name) && Objects.equals(focus, exercise.focus) && Objects.equals(description, exercise.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, focus, description, durationReps, caloriesBurned);
    }

//    //returns 1 if written, 0 if it already exists, -1 if exception
//    /**
//     * Description
//     *
//     * @param
//     * @return
//     * @throws
//     */
//    public int writeCSV() {
//        if(!exists()) {
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/exercise.csv", true))) {
//                writer.write("\n" + name + "," + focus + "," + description);
//                return 1;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return -1;
//            }
//        }
//        return 0;
//    }
//    /**
//     * Description
//     *
//     * @param
//     * @return
//     * @throws
//     */
//    public List<Exercise> readCSV(){
//        List<Exercise> exercises = new ArrayList<>();
//        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/exercise.csv"))){
//            String line = reader.readLine();
//
//            while((line = reader.readLine()) != null) {
//                String[] val = line.split(",");
//                if (val.length >= 3) {
//                    exercises.add(new Exercise(val[0].trim(), val[1].trim(), val[2].trim()));
//                }
//            }
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//        return exercises;
//    }
//
//    //checks if this exercise already exists
//    /**
//     * Description
//     *
//     * @param
//     * @return
//     * @throws
//     */
//    public boolean exists(){
//        List<Exercise> exercises = readCSV();
//        return exercises.stream().anyMatch(e -> e.equals(this));
//    }
}
