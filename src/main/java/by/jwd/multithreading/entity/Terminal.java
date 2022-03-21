package by.jwd.multithreading.entity;


import by.jwd.multithreading.util.IdCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Terminal {
    static Logger logger = LogManager.getLogger();

    public static final int MAX_LOAD_UNLOAD_TIME = 5;
    private long terminalId;

    public Terminal() {
        this.terminalId = IdCounter.generateId();
    }

    public long getTerminalId() {
        return terminalId;
    }

    public void serveTruck(Truck truck) {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();

        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(MAX_LOAD_UNLOAD_TIME));
            if (truck.isUploading()) {
                logisticsBase.removeGoods(truck.getCargoSize());
            } else {
                logisticsBase.addGoods(truck.getCargoSize());
            }
        } catch (InterruptedException e) {
            logger.error("Thread" + Thread.currentThread().getName() + " was interrapted", e);
            Thread.currentThread().interrupt();
        }
    }
}
