/*
 * Filename: WeightedAvgDataAnalyzer.java
 * Short description: Class to ???
 * @author  Corey Williams
 * @version May 5, 2021
 */

package weightedavgdataanalyzer;

import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * @author clwil
 * @version 1.0 May 5, 2021
 */
public class WeightedAvgDataAnalyzer {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String fileName;
        ArrayList<Double> arr;

      // Keep trying until there are no more exceptions
        boolean done = false;
        while (!done) {
            
                System.out.print("Please enter the file name: ");
            String filename = in.next();
            try {
            ArrayList<Double> data = readFile(filename);
            
            // As an example for processing the data, we compute the sum

            double sum = 0;
            for (double d : data) { sum = sum + d; }
            System.out.println("The sum is " + sum);

            done = true;
            }
            
           
            
            catch (NoSuchElementException exception){
                System.out.println("File contents invalid");
            }
            
            catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }
    
    public static double[] readData(Scanner in) throws IOException {     
      int numberOfValues = in.nextInt(); // May throw NoSuchElementException
      double[] data = new double[numberOfValues];

      for (int i = 0; i < numberOfValues; i++)
      {
         data[i] = in.nextDouble(); // May throw NoSuchElementException
      }

      if (in.hasNext())
      {
         throw new IOException("End of file expected");
      }
      return data;
    }
    
    
    public static ArrayList<Double> readFile (String filename) throws IOException {

    }
    
    public static void writeData(List<Double> data) throws IOException {
        double sum = 0;
        double weight = data.get(0);
        double amountToDrop = data.get(1);
        
        data.remove(0);
        data.remove(0);
        
        String y = data.toString().replace("]","").replace("[", "");
        Collections.sort(data);
        
        for (int i = 0; i < amountToDrop ; i++){
            data.remove(0);
            }
        
        for (double i : data){
            sum = sum + (i * weight);
        }
        
        double x = sum/data.size();
        
        String result = "The weighted average of the numbers is " + x + ", when\n"
                + "using the data " + y + "where " + weight + " is the weight\n"
                + "used, and the average is computed after dropping the lowest \n" 
                + ((int) amountToDrop) + " values.";
                
                Scanner out = new Scanner(System.in);
                try {
                    System.out.println("Please enter the file name to output too: ");
                    String outputFilename = out.next();
                    
                    PrintWriter writer1 = new PrintWriter(new File(outputFilename));
                    writer1.write(result);
                    writer1.flush();
                    writer1.close();
                }
                catch (IOException exception){
                    exception.printStackTrace();
                }
    }

}
