package ru.solomka.guard.core.flag.utils;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;

public class TPS extends BukkitRunnable {

    private transient long last;
    private final LinkedList<Double> history;

    public TPS() {
        this.last = System.nanoTime();
        (this.history = new LinkedList<>()).add(20.0);
    }

    @Override
    public void run() {
        final long startTime = System.nanoTime();
        long timeSpent = (startTime - this.last) / 1000L;
        if (timeSpent == 0L) {
            timeSpent = 1L;
        }
        if (this.history.size() > 10) {
            this.history.remove();
        }
        final double tps = 5.0E7 / timeSpent;
        if (tps <= 21.0) {
            this.history.add(tps);
        }
        this.last = startTime;
    }

    public double get() {
        double avg = 0.0;
        for (final Double f : this.history) {
            if (f != null) {
                avg += f;
            }
        }
        return avg / this.history.size();
    }
}
