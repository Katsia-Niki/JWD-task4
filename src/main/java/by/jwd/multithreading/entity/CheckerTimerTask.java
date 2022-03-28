package by.jwd.multithreading.entity;

import java.util.TimerTask;

public class CheckerTimerTask extends TimerTask {
    @Override
    public void run() {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        logisticsBase.checkGoodsQuantity();
    }
}
