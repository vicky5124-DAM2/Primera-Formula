package org.example;

public class Team {
    public String name;
    public Pilot[] pilots;
    public Box box = new Box();

    public Team(String name, Pilot[] pilots) {
        this.name = name;
        this.pilots = pilots;
    }
}
