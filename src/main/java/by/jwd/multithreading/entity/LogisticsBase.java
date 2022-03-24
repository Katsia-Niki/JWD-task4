package by.jwd.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticsBase {
    static Logger logger = LogManager.getLogger();

    public static int TERMINAL_QUANTITY = 2;
    public static int MAX_SIZE = 1000;
    public static int DEFAULT_SIZE = MAX_SIZE / 4;
    public static int MAX_TIME = 1000;

    private static LogisticsBase instance;
    private static Lock lockerCreator = new ReentrantLock();
    private static AtomicBoolean created = new AtomicBoolean(false);

    private AtomicInteger goodsQuantity = new AtomicInteger();
    private Deque<Terminal> freeTerminals = new ArrayDeque<>();
    private Lock lock = new ReentrantLock();
    private Deque<Condition> priorityQueue = new ArrayDeque<>();
    private Deque<Condition> nonPriorityQueue = new ArrayDeque<>();

    private LogisticsBase() {
        for (int i = 0; i < TERMINAL_QUANTITY; i++) {
            freeTerminals.add(new Terminal());
        }
        goodsQuantity.set(DEFAULT_SIZE);
    }

    public static LogisticsBase getInstance() {
        if (!created.get()) {
            try {
                lockerCreator.lock();
                if (instance == null) {
                    instance = new LogisticsBase();
                    created.set(true);
                }
            } finally {
                lockerCreator.unlock();
            }
        }
        return instance;
    }

    public void addGoods(int quantity) {
        goodsQuantity.addAndGet(quantity);
    }

    public void removeGoods(int quantity) {
        goodsQuantity.addAndGet(-1 * quantity);
    }

    public Terminal acquireTerminal(boolean perishableGoods) {
        Terminal terminal = null;
        try {
            lock.lock();
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(MAX_TIME));
            Condition condition = lock.newCondition();
            if (freeTerminals.isEmpty()) {
                if (perishableGoods) {
                    priorityQueue.addLast(condition);
                    logger.info("priority queue get truck with perishable goods = " + perishableGoods);
                } else {
                    nonPriorityQueue.addLast(condition);
                    logger.info("non-priority queue get truck with perishable goods = " + perishableGoods);
                }
                condition.await();
            }
            terminal = freeTerminals.removeFirst();
            logger.info(Thread.currentThread().getName() + " acquire terminal " + terminal.getTerminalId() +
                    " truck has perishable goods = " + perishableGoods);
        } catch (InterruptedException e) {
            logger.error("Thread " + Thread.currentThread().getName() + " was interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return terminal;
    }

    public void releaseTerminal(Terminal terminal) {
        Condition condition = null;
        lock.lock();
        freeTerminals.addLast(terminal);
        logger.info("Terminal " + terminal.getTerminalId() + " was released");
        condition = priorityQueue.isEmpty() ? nonPriorityQueue.pollFirst() : priorityQueue.pollFirst();

        if (condition != null) {
            condition.signal();
        }
        lock.unlock();
    }
}

