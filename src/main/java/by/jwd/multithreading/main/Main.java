package by.jwd.multithreading.main;

import by.jwd.multithreading.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        try {
            Truck truck1 = new Truck(100, true, 50, true);
            Truck truck2 = new Truck(101, false, 50, false);
            Truck truck3 = new Truck(102, false, 30, true);
            Truck truck4 = new Truck(103, true, 30, false);
            Truck truck5 = new Truck(104, false, 40, false);
            Truck truck6 = new Truck(105, false, 50, true);
            Truck truck7 = new Truck(106, false, 50, false);
            Truck truck8 = new Truck(107, true, 30, true);
            Truck truck9 = new Truck(108, false, 30, false);
            Truck truck10 = new Truck(109, false, 40, true);

            List<Truck> trucks = new ArrayList<>();
            trucks.add(truck1);
            trucks.add(truck2);
            trucks.add(truck3);
            trucks.add(truck4);
            trucks.add(truck5);
            trucks.add(truck6);
            trucks.add(truck7);
            trucks.add(truck8);
            trucks.add(truck9);
            trucks.add(truck10);

            ExecutorService service = Executors.newFixedThreadPool(trucks.size());

            trucks.forEach(t -> service.execute(t));
            TimeUnit.SECONDS.sleep(3);
            service.shutdown();

        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}
