package org.example;

import static org.example.Race.BASE_LAP_TIME;

public class Pilot {
    private static final int MAX_FUEL = 25;
    String name;
    int fuel = MAX_FUEL;
    int laps = 69;
    int time = 0;

    public Pilot(String name) {
        this.name = name;
    }

    public synchronized void lap() throws InterruptedException {
        int random = 1 + (int)(Math.random() * BASE_LAP_TIME);

        this.time += BASE_LAP_TIME + random;

        this.laps -= 1;
        this.fuel -= 1;

        Thread.sleep(BASE_LAP_TIME + random);
    }
    public synchronized void refuel() throws InterruptedException {
        this.fuel = MAX_FUEL;
        Thread.sleep(BASE_LAP_TIME / 10);
    }
}
