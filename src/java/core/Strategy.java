package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;
import java.util.Random;

public class Strategy extends BaseStrategy {
    private int ticks = 0;
    private Random rand = new Random(System.currentTimeMillis());

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {
        int pCount = myPassengers.size() + enemyPassengers.size();
        for (Elevator e : myElevators) {
            for (Passenger lovely : enemyPassengers) {
                setElevator(lovely, e, pCount);
            }
            for (Passenger p : myPassengers) {
                setElevator(p, e, pCount);
            }

            if (ticks < 2000 && (e.getPassengers().size() == 20 || e.getFloor() != 1)) {
                e.goToFloor(nearestFloor(e));
            } else if (ticks == 2000 && (e.getFloor() == 1) && (e.getId() == 11 || e.getId() == 12 || e.getId() == 13 || e.getId() == 14)) {
                e.goToFloor(getSomeFloor());
            } else if (e.getPassengers().size() == 20 || totalPassengersOnFloor(e, myPassengers, enemyPassengers) <= e.getPassengers().size()) {
                e.goToFloor(nearestFloor(e));
            }
        }
        ticks++;
    }

    private Integer getSomeFloor() {
        return rand.nextInt(7) + 2;
    }

    private int totalPassengersOnFloor(Elevator e, List<Passenger> myPassengers, List<Passenger> enemyPassengers) {
        int count;
        count = (int) myPassengers.stream()
                .filter(p -> (p.getFloor() == e.getFloor()) && (p.getElevator() == e.getId() || !p.hasElevator()))
                .count();
        count += (int) enemyPassengers.stream()
                .filter(p -> (p.getFloor() == e.getFloor()) && (p.getElevator() == e.getId() || !p.hasElevator()))
                .count();

        return count;
    }

    private int nearestFloor(Elevator e) {
        int nearest = e.getPassengers().get(0).getDestFloor();
        int minDiff = Math.abs(e.getFloor() - nearest);
        for (Passenger p : e.getPassengers()) {
            int diff = Math.abs(p.getDestFloor() - e.getFloor());
            if (diff < minDiff) {
                minDiff = diff;
                nearest = p.getDestFloor();
            }
        }
        return nearest;
    }

    private void setElevator(Passenger p, Elevator e, int pCount) {
        if (p.getState() < 5) {
            if (pCount < 100) {
                if (p.getDestFloor() > 4 || e.getId() == 11 || e.getId() == 12 || e.getId() == 13 || e.getId() == 14) {
                    p.setElevator(e);
                }
            } else {
                p.setElevator(e);
            }
        }
    }
}
