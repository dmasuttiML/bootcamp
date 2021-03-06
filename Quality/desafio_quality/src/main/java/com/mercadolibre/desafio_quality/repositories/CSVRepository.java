package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.utils.HelperCSV;

import java.util.ArrayList;
import java.util.List;

/*
* Abstract class used so repositories can interact with CSV files.
* */
public abstract class CSVRepository<T> {
    private final String fileName;
    private String[] headFile;

    public CSVRepository(String fileName){
        this.fileName = fileName;
    }

    protected abstract T parseLine(String[] line);
    protected abstract String[] makeLine(T obj);

    /*
    * Read and parse a CSV file into a list of objects.
    * */
    protected List<T> loadData(){
        List<T> list = new ArrayList<T>();
        List<String[]> dataLines = HelperCSV.readCSV(fileName);

        try {
            if(dataLines.size() > 0) {
                headFile = dataLines.get(0);
                dataLines.remove(0);
            }

            for (String[] line: dataLines) {
                list.add(parseLine(line));
            }
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Database inconsistency", e);
        }

        return list;
    }

    /*
    * Write a list of objects to a CSV.
    * */
    protected void saveData(List<T> list) {
        List<String[]> dataLines = new ArrayList<>();

        dataLines.add(headFile);
        for (T obj: list) {
            dataLines.add(makeLine(obj));
        }

        HelperCSV.writeCSV(dataLines, fileName);
    }
}
