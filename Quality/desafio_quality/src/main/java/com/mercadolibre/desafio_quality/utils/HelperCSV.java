package com.mercadolibre.desafio_quality.utils;

import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* Utility class for working with CSV files
* */
public class HelperCSV {
    /*
    * Read a CSV file with the path passed as an argument.
    * Returns a list of String[] with the information obtained.
    * */
    public static List<String[]> readCSV(String fileName) {
        List<String[]> dataLines = new ArrayList<>();
        Scanner reader = null;

        try {
            reader = new Scanner(new File(fileName));

            while (reader.hasNextLine()) {
                dataLines.add(reader.nextLine().split(","));
            }
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Error reading data", e);
        }
        finally {
            if(reader != null) reader.close();
        }

        return dataLines;
    }

    /*
    * Write the information passed by argument in a csv file with the path "fileName"
    * */
    public static void writeCSV(List<String[]> dataLines, String fileName) {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileName);
            dataLines.stream()
                     .map(d -> String.join(",", d))
                     .forEach(writer::println);
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Failed to persist data", e);
        }
        finally {
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
