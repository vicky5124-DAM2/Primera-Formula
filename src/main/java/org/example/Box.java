package org.example;

import java.util.Optional;

public class Box {
    public Optional<Pilot> pilot = Optional.empty();

    public void setPilot(Pilot pilot) {
        this.pilot = Optional.of(pilot);
    }

    public boolean hasPilot() {
        return this.pilot.isPresent();
    }

    public void clearPilot() {
        this.pilot = Optional.empty();
    }
}
