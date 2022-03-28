package by.jwd.multithreading.main;


import by.jwd.multithreading.entity.CheckerTimerTask;
import by.jwd.multithreading.entity.Terminal;
import by.jwd.multithreading.entity.Truck;
import by.jwd.multithreading.exception.LogisticsBaseException;
import by.jwd.multithreading.parser.TruckParser;
import by.jwd.multithreading.parser.impl.TruckParserImpl;
import by.jwd.multithreading.reader.TruckReader;
import by.jwd.multithreading.reader.impl.TruckReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    static Logger logger = LogManager.getLogger();
    static final String PATH_TO_FILE = "data/trucks.txt";

    public static void main(String[] args) {

        TruckReader reader = new TruckReaderImpl();
        TruckParser parser = new TruckParserImpl();
        try {
            List<String> content = reader.readTruck(PATH_TO_FILE);
            List<Truck> trucks = parser.parseTuck(content);

            ExecutorService service = Executors.newFixedThreadPool(trucks.size());
            trucks.forEach(t -> service.execute(t));
            TimeUnit.SECONDS.sleep(3);
            service.shutdown();
        } catch (InterruptedException | LogisticsBaseException e) {
            logger.error(e);
        }
    }
}
