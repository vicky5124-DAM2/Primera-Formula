package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Pilot> pilots = new ArrayList<>();
        pilots.add(new Pilot("Pilot 1"));
        pilots.add(new Pilot("Pilot 2"));
        pilots.add(new Pilot("Pilot 3"));
        pilots.add(new Pilot("Pilot 4"));
        pilots.add(new Pilot("Pilot 5"));
        pilots.add(new Pilot("Pilot 6"));
        pilots.add(new Pilot("Pilot 7"));
        pilots.add(new Pilot("Pilot 8"));
        pilots.add(new Pilot("Pilot 9"));
        pilots.add(new Pilot("Pilot 10"));
        pilots.add(new Pilot("Pilot 11"));
        pilots.add(new Pilot("Pilot 12"));
        pilots.add(new Pilot("Pilot 13"));
        pilots.add(new Pilot("Pilot 14"));
        pilots.add(new Pilot("Pilot 15"));
        pilots.add(new Pilot("Pilot 16"));
        pilots.add(new Pilot("Pilot 17"));
        pilots.add(new Pilot("Pilot 18"));
        pilots.add(new Pilot("Pilot 19"));
        pilots.add(new Pilot("Pilot 20"));
        pilots.add(new Pilot("Pilot 21"));
        pilots.add(new Pilot("Pilot 22"));
        pilots.add(new Pilot("Pilot 23"));
        pilots.add(new Pilot("Pilot 24"));
        pilots.add(new Pilot("Pilot 25"));
        pilots.add(new Pilot("Pilot 26"));
        pilots.add(new Pilot("Pilot 27"));
        pilots.add(new Pilot("Pilot 28"));
        pilots.add(new Pilot("Pilot 29"));
        pilots.add(new Pilot("Pilot 30"));

        Race race = new Race(pilots);
        Thread thread = new Thread(race);

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(race);
    }
}