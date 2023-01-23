package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Race implements Runnable {
    public static final int BASE_LAP_TIME = 50;
    boolean is_running = true;
    List<Team> teams;

    public Race(List<Team> teams) {
        this.teams = teams;
    }

    private void lap(Team team, Pilot pilot) {
        try {
            if (pilot.fuel < 5) {
                if (!team.box.hasPilot()) {
                    Main.log.warning(String.format("Team: %s | Pilot: %s | Laps left: %d | Fuel left = %d | [ENTERING BOXES]\n", team.name, pilot.name, pilot.laps, pilot.fuel));
                    team.box.setPilot(pilot);
                    pilot.refuel();
                    team.box.clearPilot();
                    Main.log.warning(String.format("Team: %s | Pilot: %s | Laps left: %d | Fuel left = %d | [EXITING BOXES]\n", team.name, pilot.name, pilot.laps, pilot.fuel));
                } else {
                    Main.log.severe(String.format("Team: %s | Pilot: %s | Laps left: %d | Fuel left = %d | [COULD NOT ENTER BOXES]\n", team.name, pilot.name, pilot.laps, pilot.fuel));
                    pilot.lap();
                }
            } else {
                pilot.lap();
            }
        } catch (InterruptedException e) {
            return;
        }

        Main.log.info(String.format("Team: %s | Pilot: %s | Laps left: %d | Fuel left = %d\n", team.name, pilot.name, pilot.laps, pilot.fuel));

        if (pilot.laps == 0) {
            synchronized (this) {
                this.is_running = false;
            }
        }
    }

    public void run() {
        List<Thread> threads = new ArrayList<>();

        for (Team team: this.teams) {
            for (Pilot pilot : team.pilots) {
                Runnable runnable = new Runnable() {
                    private Team team;
                    private Pilot pilot;
                    private Race race;

                    @Override
                    public void run() {
                        while (this.race.is_running && this.pilot.laps > 0) {
                            this.race.lap(this.team, this.pilot);

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

                    public Runnable init(Team team, Pilot pilot, Race race) {
                        this.team = team;
                        this.pilot = pilot;
                        this.race = race;
                        return this;
                    }
                }.init(team, pilot, this);

                threads.add(new Thread(runnable));
            }
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
            for (Team team: this.teams) {
                for (Pilot pilot : team.pilots) {
                    if (pilot.laps == i) {
                        finished_pilots_sorted_set.add(pilot);
                    }
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