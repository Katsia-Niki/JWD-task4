package by.jwd.multithreading.parser;

import by.jwd.multithreading.entity.Truck;
import by.jwd.multithreading.exception.LogisticsBaseException;

import java.util.List;

public interface TruckParser {
    List<Truck> parseTuck(List<String> data);
}
