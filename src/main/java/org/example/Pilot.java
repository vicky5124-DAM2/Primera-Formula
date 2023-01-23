package org.example;

import static org.example.Race.BASE_LAP_TIME;

public class Pilot {
    String name;
    int fuel = 15;
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
        this.fuel = 15;
        Thread.sleep(BASE_LAP_TIME / 10);
    }
}
