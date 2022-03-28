package by.jwd.multithreading.reader;

import by.jwd.multithreading.exception.LogisticsBaseException;

import java.util.List;

public interface TruckReader {
    List<String> readTruck(String pathToFile) throws LogisticsBaseException;
}
