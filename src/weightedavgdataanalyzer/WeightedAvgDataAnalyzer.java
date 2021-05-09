package weightedavgdataanalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * This program processes a file containing a count followed by data values. If
 * the file doesn't exist or the format is incorrect, you can specify another
 * file.
 */
public class WeightedAvgDataAnalyzer {
    private static Double weight;
    private static Double drop;
    private static ArrayList<Double> copyList = new ArrayList<>();


/** Main Method. Prompts the user to input a file name that has to be located in the root directory of the project.
    Runs the readfile method, and reads in the values from the .txt file. Creates a new weighted average object that
    Runs the calc method of the weighted average object that calcs thew weighted average after dropping the second value
    amount of numbers that is located in the .txt file.
**/
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Keep trying until there are no more exceptions
        boolean done = false;
        while (!done) {
            try {
                System.out.print("Please enter the file name: ");
                String filename = in.next();
                System.out.println("Please enter an output file name");
                String output = in.next();

                ArrayList<Double> data = readFile(filename);
                for(int i = 2; i < data.size(); i++){
                    copyList.add(data.get(i))
;                }


                WeightedAverage avg = new WeightedAverage(data);
                double avgFinal = avg.calc();
                String finalData = ("The weighted average of the numbers is " + avgFinal +
                        " when using the data");
                        for(int i = 0; i < copyList.size(); i++) {
                            finalData += (" " + copyList.get(i));
                        }

                         finalData += (" where " + weight +
                        " is the weight used, and the average is computed " +
                        "after dropping the lowest "+drop+" values.");
                        writeData(finalData, output);


                done = true;
            } catch (FileNotFoundException exception) {
                System.out.println("File not found.");
            } catch (NoSuchElementException exception) {
                System.out.println("File contents invalid.");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Opens a file and reads a data set.
     *
     * @param filename the name of the file holding the data
     * @return the data in the file
     */
    public static ArrayList<Double> readFile(String filename) throws IOException {
        File inFile = new File(filename);
        Scanner in = new Scanner(inFile);
        try {
            return readData(in);
        } finally {
            in.close();
        }
    }

    /**
     * Reads a data set.
     *
     * @param in the scanner that scans the data
     * @return the data set
     */
    public static ArrayList<Double> readData(Scanner in) throws IOException {
        ArrayList<Double> data = new ArrayList<>();
        while(in.hasNextDouble()) {
            data.add(in.nextDouble()); // May throw NoSuchElementException
        }
        weight = data.get(0);
        drop = data.get(1);

        return data;
    }
        //Writes a string to a file based on the file name
    public static void writeData(String finalData, String fileName)throws IOException {
        PrintWriter out = new PrintWriter(fileName);
        System.out.println("final data " + finalData);
        out.println(finalData);
        out.close();
    }
}

/**
 * Weighted average object with constructor that sets the arrayOFDoubles to arrayList passed.
 */
class WeightedAverage{
    double weight;
    double dropNumber;
    ArrayList<Double> arrayOfDoubles;
    public WeightedAverage(ArrayList<Double> data) {
        this.arrayOfDoubles = data;
    }
/*
Main function of Weighted Average object. Get's the weight from the read in file, stores it as an object variable and then
removes that from the arrayOfDouble
Reads in the number to drop and stores it as variable within the object. Sorts the array from least to greatest
Runs the drops function, and the calc function and returns the calculated average after the drop.
 */
    public Double calc() {
        this.weight = arrayOfDoubles.get(0);
        arrayOfDoubles.remove(0);
        this.arrayOfDoubles = arrayOfDoubles;
        dropNumber = this.arrayOfDoubles.get(0);
        this.arrayOfDoubles.remove(0);
        Collections.sort(this.arrayOfDoubles);

        arrayOfDoubles = dropLowest(this.arrayOfDoubles);
        return calcAverage(this.arrayOfDoubles, this.weight);
    }

/*
Calculates the weighted average of the remaining values after the drop, based on the weight value stored in the object
 */

    private Double calcAverage(ArrayList<Double> arrayOfDoubles, double weight) {
        double sum = 0;

        for(int i = 0; i < this.arrayOfDoubles.size(); i++){
            sum += this.arrayOfDoubles.get(i);
        }
        sum = sum * weight;
        sum = sum/(weight * this.arrayOfDoubles.size());
        return sum;
    }
/*
Drops the lowest numbers from the array of doubles based on the drop numbers stored in the Object.
*/

    private ArrayList<Double> dropLowest(ArrayList<Double> arrayOfDoubles){
            for(int i = 0; i < dropNumber; i++){
                this.arrayOfDoubles.remove(0);

            }

        return this.arrayOfDoubles;
    }


}