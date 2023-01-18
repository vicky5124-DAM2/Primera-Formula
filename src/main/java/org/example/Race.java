package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Race implements Runnable {
    boolean is_running = true;
    List<Pilot> pilots;

    public Race(List<Pilot> pilots) {
        this.pilots = pilots;
    }

    private void lap(Pilot pilot) {
        pilot.laps -= 1;

        try {
            int random = 1 + (int)(Math.random() * 50);
            pilot.time += 50 + random;

            Thread.sleep(50 + random);
        } catch (InterruptedException e) {
            return;
        }

        System.out.printf("Pilot: %s | Laps left: %d\n", pilot.name, pilot.laps);

        if (pilot.laps == 0) {
            synchronized (this) {
                this.is_running = false;
            }
        }
    }

    public void run() {
        List<Thread> threads = new ArrayList<>();

        for (Pilot pilot : this.pilots) {
            Runnable runnable = new Runnable() {
                private Pilot pilot;
                private Race race;

                @Override
                public void run() {
                    while (this.race.is_running && this.pilot.laps > 0) {
                        this.race.lap(this.pilot);

                        if (Thread.currentThread().isInterrupted()) {
                            synchronized (this.race) {
                                this.race.notifyAll();
                            }

                            return;
                        }
                    }

                    synchronized (this.race) {
                        this.race.is_running = false;
                        this.race.notifyAll();
                    }
                }

                public Runnable init(Pilot pilot, Race race) {
                    this.pilot = pilot;
                    this.race = race;
                    return this;
                }
            }.init(pilot, this);

            threads.add(new Thread(runnable));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        synchronized (this) {
            while (this.is_running) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        System.out.println("FINISHED");
    }

    @Override
    public String toString() {
        List<Pilot> finished_pilots_sorted = new ArrayList<>();
        List<Pilot> finished_pilots_sorted_set = new ArrayList<>();
        HashMap<Integer, Integer> time_lowest = new HashMap<>();

        for (int i = 0; i < 15; i++) {
            for (Pilot pilot : this.pilots) {
                if (pilot.laps == i) {
                    finished_pilots_sorted_set.add(pilot);
                }
            }

            finished_pilots_sorted_set.sort(Comparator.comparingInt(p -> p.time));

            try {
                time_lowest.put(i, finished_pilots_sorted_set.get(0).time);
            } catch (Exception ignored) {}

            finished_pilots_sorted.addAll(finished_pilots_sorted_set);

            finished_pilots_sorted_set.clear();
        }

        StringBuilder res = new StringBuilder();

        for (Pilot pilot : finished_pilots_sorted) {
            res.append(String.format("%s -> Laps left: %d | Time: +%d ms\n", pilot.name, pilot.laps, pilot.time - time_lowest.get(pilot.laps)));
        }

        return res.toString();
    }
}