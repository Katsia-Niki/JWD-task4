package by.jwd.multithreading.entity;

public class Truck extends Thread {
    private long truckId;
    private boolean perishableGoods;
    private int cargoSize;
    private State truckState;
    private boolean uploading;

    public enum State {
        NEW, IN_PROCESS, FINISHED
    }

    public Truck(long truckId, boolean perishableGoods, int cargoSize, boolean uploading) {
        this.truckId = truckId;
        this.perishableGoods = perishableGoods;
        this.cargoSize = cargoSize;
        this.truckState = State.NEW;
        this.uploading = uploading;
    }

    public long getTruckId() {
        return truckId;
    }

    public void setTruckId(long truckId) {
        this.truckId = truckId;
    }

    public int getCargoSize() {
        return cargoSize;
    }

    public void setCargoSize(int cargoSize) {
        this.cargoSize = cargoSize;
    }

    public boolean isPerishableGoods() {
        return perishableGoods;
    }

    public void setPerishableGoods(boolean perishableGoods) {
        this.perishableGoods = perishableGoods;
    }

    public State getTruckState() {
        return truckState;
    }

    public void setTruckState(State truckState) {
        this.truckState = truckState;
    }

    public boolean isUploading() {
        return uploading;
    }

    public void setUploading(boolean uploading) {
        this.uploading = uploading;
    }

    @Override
    public void run() {
        truckState = State.IN_PROCESS;
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Terminal terminal = null;
        try {
            terminal = logisticsBase.acquireTerminal(perishableGoods);
            terminal.serveTruck(this);
        } finally {
            logisticsBase.releaseTerminal(terminal);
        }
        truckState = State.FINISHED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        if (truckId != truck.truckId) return false;
        if (perishableGoods != truck.perishableGoods) return false;
        if (uploading != truck.uploading) return false;
        return truckState == truck.truckState;
    }

    @Override
    public int hashCode() {
        int result = (int) (truckId ^ (truckId >>> 32));
        result = 31 * result + (perishableGoods ? 1 : 0);
        result = 31 * result + (truckState != null ? truckState.hashCode() : 0);
        result = 31 * result + (uploading ? 1 : 0);
        return result;
    }
}
