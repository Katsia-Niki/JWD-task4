package by.jwd.multithreading.util;

public class IdCounter {
    private static int idCounter;

    private IdCounter() {}

    public static int generateId() {
        return ++idCounter;
    }

}
