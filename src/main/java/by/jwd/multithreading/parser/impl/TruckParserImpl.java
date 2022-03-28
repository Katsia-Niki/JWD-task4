package by.jwd.multithreading.parser.impl;

import by.jwd.multithreading.entity.Truck;
import by.jwd.multithreading.parser.TruckParser;

import java.util.List;
import java.util.stream.Stream;

public class TruckParserImpl implements TruckParser {
    public static final String INFO_SPLITTER = ";";
    @Override
    public List<Truck> parseTuck(List<String> data) {
        List<Truck> trucks = data.stream()
                .map(l -> Stream.of(l.split(INFO_SPLITTER)).toList())
                .map(d -> new Truck(Long.parseLong(d.get(0)), Boolean.parseBoolean(d.get(1)), Integer.parseInt(d.get(2)),
                        Boolean.parseBoolean(d.get(3))))
                .toList();
        return trucks;
    }
}
