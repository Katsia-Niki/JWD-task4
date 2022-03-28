package by.jwd.multithreading.reader.impl;

import by.jwd.multithreading.exception.LogisticsBaseException;
import by.jwd.multithreading.reader.TruckReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TruckReaderImpl implements TruckReader {
    static Logger logger = LogManager.getLogger();
    @Override
    public List<String> readTruck(String pathToFile) throws LogisticsBaseException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToFile);
        if (inputStream == null) {
            logger.error("File " + pathToFile + " not found. ");
            throw new LogisticsBaseException("File " + pathToFile + " not found. ");
        }

        List<String> lines = new ArrayList<>();

        try {
            String fileContent = new String(inputStream.readAllBytes());
            lines = Arrays.stream(fileContent.split(System.lineSeparator())).toList();
        } catch (IOException e) {
            logger.error("Failed or interrupted I/O operations while working on the file" + pathToFile);
            throw new LogisticsBaseException("Failed or interrupted I/O operations while working on the file" + pathToFile);
        }
        return lines;
    }
}
