package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args) {
        log.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();

        Formatter formatter = new LogFormatter();
        handler.setFormatter(formatter);

        log.addHandler(handler);

        log.info("Starting up!");

        List<Team> teams = new ArrayList<>();

        Pilot[] pilot1 = {new Pilot("Pilot 1"), new Pilot("Pilot 2")};
        Pilot[] pilot2 = {new Pilot("Pilot 3"), new Pilot("Pilot 4")};
        Pilot[] pilot3 = {new Pilot("Pilot 5"), new Pilot("Pilot 6")};
        Pilot[] pilot4 = {new Pilot("Pilot 7"), new Pilot("Pilot 8")};
        Pilot[] pilot5 = {new Pilot("Pilot 9"), new Pilot("Pilot 10")};
        Pilot[] pilot6 = {new Pilot("Pilot 11"), new Pilot("Pilot 12")};
        Pilot[] pilot7 = {new Pilot("Pilot 13"), new Pilot("Pilot 14")};
        Pilot[] pilot8 = {new Pilot("Pilot 15"), new Pilot("Pilot 16")};
        Pilot[] pilot9 = {new Pilot("Pilot 17"), new Pilot("Pilot 18")};
        Pilot[] pilot10 = {new Pilot("Pilot 19"), new Pilot("Pilot 20")};
        Pilot[] pilot11 = {new Pilot("Pilot 21"), new Pilot("Pilot 22")};
        Pilot[] pilot12 = {new Pilot("Pilot 23"), new Pilot("Pilot 24")};
        Pilot[] pilot13 = {new Pilot("Pilot 25"), new Pilot("Pilot 26")};
        Pilot[] pilot14 = {new Pilot("Pilot 27"), new Pilot("Pilot 28")};
        Pilot[] pilot15 = {new Pilot("Pilot 29"), new Pilot("Pilot 30")};

        teams.add(new Team("Team 1", pilot1));
        teams.add(new Team("Team 2", pilot2));
        teams.add(new Team("Team 3", pilot3));
        teams.add(new Team("Team 4", pilot4));
        teams.add(new Team("Team 5", pilot5));
        teams.add(new Team("Team 6", pilot6));
        teams.add(new Team("Team 7", pilot7));
        teams.add(new Team("Team 8", pilot8));
        teams.add(new Team("Team 9", pilot9));
        teams.add(new Team("Team 10", pilot10));
        teams.add(new Team("Team 11", pilot11));
        teams.add(new Team("Team 12", pilot12));
        teams.add(new Team("Team 13", pilot13));
        teams.add(new Team("Team 14", pilot14));
        teams.add(new Team("Team 15", pilot15));

        Race race = new Race(teams);
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