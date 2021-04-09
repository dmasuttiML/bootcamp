package com.mercadolibre.desafio_spting.utils;

import com.mercadolibre.desafio_spting.dtos.ArticleDTO;
import com.mercadolibre.desafio_spting.exceptions.ApiException;
import com.mercadolibre.desafio_spting.exceptions.InternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelperCSV {
    private static final String pathResources = "/src/main/resources/";

    public static List<String[]> readCSV(String fileName) {
        List<String[]> dataLines = new ArrayList<>();
        Scanner reader = null;
        File file = new File("");

        try {
            file = new File(file.getAbsolutePath() + pathResources, fileName);

            reader = new Scanner(file);

            while (reader.hasNextLine()) {
                dataLines.add(reader.nextLine().split(","));
            }
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Error al leer datos");
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }

        return dataLines;
    }

    public static void writeCSV(List<String[]> dataLines, String fileName) {
        PrintWriter writer = null;
        File file = new File("");

        try {
            file = new File(file.getAbsolutePath() + pathResources, fileName);

            writer = new PrintWriter(file);
            dataLines.stream()
                     .map(d -> String.join(",", d))
                     .forEach(writer::println);
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Error al persistir datos");
        }
        finally {
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
