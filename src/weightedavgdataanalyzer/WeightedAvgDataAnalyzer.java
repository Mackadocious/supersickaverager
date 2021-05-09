package weightedavgdataanalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private static ArrayList<Double> copyList;




    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Keep trying until there are no more exceptions
        boolean done = false;
        while (!done) {
            try {
                System.out.print("Please enter the file name: ");
                String filename = in.next();

                ArrayList<Double> data = readFile(filename);


                WeightedAverage avg = new WeightedAverage(data);
                double avgFinal = avg.calc();
                System.out.print("The weighted average of the numbers is " + avgFinal +
                        " when using the data");
                        for(int i = 1; i < data.size(); i++){
                            System.out.print(" " + data.get(i));
                        }
                         System.out.print(" where " + weight +
                        " is the weight used, and the average is computed " +
                        "after dropping the lowest "+drop+" values.");


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
}

class WeightedAverage{
    double weight;
    double dropNumber;
    ArrayList<Double> arrayOfDoubles;
    public WeightedAverage(ArrayList<Double> data) {
        this.arrayOfDoubles = data;
    }

    public Double calc() {
        this.weight = arrayOfDoubles.get(0);
        arrayOfDoubles.remove(0);
        this.arrayOfDoubles = arrayOfDoubles;
        dropNumber = this.arrayOfDoubles.get(0);
        arrayOfDoubles.remove(0);
        Collections.sort(arrayOfDoubles);
        arrayOfDoubles = dropLowest(arrayOfDoubles);
        return calcAverage(arrayOfDoubles, weight);
    }



    private Double calcAverage(ArrayList<Double> arrayOfDoubles, double weight) {
        double sum = 0;
        for(int i = 0; i < arrayOfDoubles.size(); i++){
            sum += arrayOfDoubles.get(i);
        }
        System.out.println("weight " + weight + " size "+ arrayOfDoubles.size() + " drop number " + dropNumber);
        sum = sum * weight;
        sum = sum/arrayOfDoubles.size();
        return sum;
    }


    private ArrayList<Double> dropLowest(ArrayList<Double> arrayOfDoubles){
        if(arrayOfDoubles.size() > dropNumber){
            for(int i = 0; i < dropNumber; i++){
                arrayOfDoubles.remove(i);
            }
        }
        return arrayOfDoubles;
    }


}