package com.trishawrites;
/**
 * Hurricane Tester description: The purpose of this program is to convert WindSpeed from knots to mph. Then, we will
 * need to determine categories for all storms, assigning it to an array. Then, we prompt the user to enter two years
 * (start) and (end year). Then, we will print the data for years within that range.
 *
 * @author Trisha Ganesh
 * @version 6/22/2021
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class HurricaneTester {

    public static void main(String[] args) throws IOException {

        double averageOfCategory = 0.0;
        double averageOfWindSpeed = 0.0;
        double averageOfPressure = 0.0;
        int category1 = 0;
        int category2 = 0;
        int category3 = 0;
        int category4 = 0;
        int category5 = 0;

        Scanner in = new Scanner(System.in);

        //read data from text file & put in an array
        File fileName = new File("/Users/trishaganesh/Desktop/HurricaneData.txt");
        Scanner inFile = new Scanner(fileName);
        int numValues = 0;

        //count number of lines in text file
        while (inFile.hasNextLine()) {
            inFile.nextLine();
            numValues++;
        }
        //close file
        inFile.close();

        //initialize arrays based on lines counted in text file
        int[] years = new int[numValues];
        String[] months = new String[numValues];
        int[] pressures = new int[numValues];
        double[] windSpeeds = new double[numValues];
        String[] names = new String[numValues];
        //category array
        int[] category = new int[numValues];



        //read and assign data from text file to the arrays
        inFile = new Scanner(fileName);
        int index = 0;
        //while loop
        while (inFile.hasNext()) {
            years[index] = inFile.nextInt();
            months[index] = inFile.next();
            pressures[index] = inFile.nextInt();
            windSpeeds[index] = inFile.nextDouble();
            names[index] = inFile.next();
            index++;
        }
        //close file
        inFile.close();

        //convert the windspeed, calculate sums
        for (index = 0; index < windSpeeds.length; index++) {
            windSpeeds[index] = windSpeeds[index] * 1.15078;

            //if statement(s) determine category for all storms and assign to an array
            if (windSpeeds[index] >= 74 && windSpeeds[index] <= 95) {
                category[index] = 1;
                category1++;
            } else if (windSpeeds[index] >= 96 && windSpeeds[index] <= 110) {
                category[index] = 2;
                category2++;
            } else if (windSpeeds[index] >= 111 && windSpeeds[index] <= 129) {
                category[index] = 3;
                category3++;
            } else if (windSpeeds[index] >= 130 && windSpeeds[index] <= 156) {
                category[index] = 4;
                category4++;
            } else if (windSpeeds[index] >= 157) {
                category[index] = 5;
                category5++;
            }
        }

        //Hurricane ArrayList using the data above
        ArrayList<Hurricane> hurr = new ArrayList<Hurricane>();
        for (int i = 0; i < numValues; i++) {
            hurr.add(new Hurricane(years[i], names[i], months[i], category[i], pressures[i], windSpeeds[i]));
        }

        //user prompted for range of years
        int startYear;
        //do while loop to check whether user enters a valid start year
        do {
            System.out.print("Please enter the first year (any year between 1995-2019) : ");
            startYear = in.nextInt();
        } while (startYear < 1995 || startYear > 2019);

        int endYear;
        //do while loop to check whether user enters a valid end year
        do {
            System.out.print("Please enter the second year (any year between 1995-2019) : ");
            endYear = in.nextInt();
        } while (endYear < 1995 || endYear > 2019);

        //printing the format of the table
        System.out.println(" |                                        Hurricanes                                             |");
        System.out.println(" | Year | Hurricane  |    Month      | Category |     Pressure (mb)    |     Wind Speed (mph)    |");
        System.out.println(" |------|------------|---------------|----------|----------------------|-------------------------|");
        //print the data

        int categorySum=0;
        double windSpeedSum=0;
        int pressureSum=0;
        double windSpeedMaximum = Double.MIN_VALUE;
        double windSpeedMinimum = Double.MAX_VALUE;
        int catMinimum = Integer.MAX_VALUE;
        int catMaximum = Integer.MIN_VALUE;
        int pressureMinimum = Integer.MAX_VALUE;
        int pressureMaximum = Integer.MIN_VALUE;
        int numRecords=0;
        //for each loop
        for (Hurricane dataRecord : hurr) {
            //if statement to compare years the user enters and compare it to year range values in txt file
            if ((dataRecord.getYear() >= startYear) && (dataRecord.getYear() <= endYear)) {
                //print results inside if statement
                System.out.printf("%6s %11.5s %13.5s %11.5s %16.5s %25.7s", dataRecord.getYear(),
                        dataRecord.getName(), dataRecord.getMonth(), dataRecord.getCat(),
                        +dataRecord.getPressure(), dataRecord.getWindspeed());
                System.out.println();
                numRecords = numRecords + 1;
                categorySum = categorySum + dataRecord.getCat();
                windSpeedSum = windSpeedSum + dataRecord.getWindspeed();
                pressureSum = pressureSum + dataRecord.getPressure();
                if (dataRecord.getWindspeed() > windSpeedMaximum) {
                    windSpeedMaximum = dataRecord.getWindspeed();
                }
                if (dataRecord.getWindspeed() < windSpeedMinimum) {
                    windSpeedMinimum = dataRecord.getWindspeed();
                }
                if (dataRecord.getCat() > catMaximum) {
                    catMaximum = dataRecord.getCat();
                }
                if (dataRecord.getCat() < catMinimum) {
                    catMinimum = dataRecord.getCat();
                }
                if (dataRecord.getPressure() > pressureMaximum) {
                    pressureMaximum = dataRecord.getPressure();
                }
                if (dataRecord.getPressure() < pressureMinimum) {
                    pressureMinimum = dataRecord.getPressure();
                }
            }
        }
        System.out.println("=================================================================================================");
        System.out.println("\t\t" + "Average: " + ("\t" + categorySum / numRecords) + "\t\t" + (windSpeedSum / numRecords) + "\t\t" + (pressureSum / numRecords));
        System.out.println("Wind Min: " + windSpeedMinimum + "   Cat Min: " + catMinimum + "   Pres min: " + pressureMinimum);
        System.out.println("Maximum: " + windSpeedMaximum + "   cat max:" + catMaximum + "   pres max : " + pressureMaximum);
    }
}

