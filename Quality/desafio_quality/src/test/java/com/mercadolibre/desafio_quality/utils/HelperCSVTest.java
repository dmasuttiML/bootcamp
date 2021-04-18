package com.mercadolibre.desafio_quality.utils;

import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Tests of HelperCSV")
class HelperCSVTest {

    @Test
    @DisplayName("Read file")
    void readCSV() {
        List<String[]> listCorrect = new ArrayList<>();
        listCorrect.add(new String[]{"a", "b", "c"});
        listCorrect.add(new String[]{"d", "e", "f"});

        List<String[]> listResult = HelperCSV.readCSV(FileMockPath.FILE_MOCK_READ);

        Assertions.assertArrayEquals(listCorrect.toArray(), listResult.toArray());
    }

    @Test
    @DisplayName("Read unknown file")
    void readCSVUnknownFile() {
        InternalServerErrorException e = Assertions.assertThrows(InternalServerErrorException.class,
                                                                 () -> HelperCSV.readCSV(FileMockPath.FILE_MOCK_READ + "aaa"));
        Assertions.assertEquals("Error reading data", e.getDescription());
    }

    @Test
    @DisplayName("Write file")
    void writeCSV() {
        List<String[]> listCorrect = new ArrayList<>();
        listCorrect.add(new String[]{"g", "h", "i"});
        listCorrect.add(new String[]{"j", "k", "l"});

        HelperCSV.writeCSV(listCorrect, FileMockPath.FILE_MOCK_WRITE);

        List<String[]> listResult = HelperCSV.readCSV(FileMockPath.FILE_MOCK_WRITE);

        Assertions.assertArrayEquals(listCorrect.toArray(), listResult.toArray());
    }

    @Test
    @DisplayName("Write file without permissions")
    void writeCSVWithoutPermissions() {
        InternalServerErrorException e = Assertions.assertThrows(InternalServerErrorException.class,
                                                                 () -> HelperCSV.writeCSV(new ArrayList<>(), "/aaa/" + FileMockPath.FILE_MOCK_WRITE));
        Assertions.assertEquals("Failed to persist data", e.getDescription());
    }
}