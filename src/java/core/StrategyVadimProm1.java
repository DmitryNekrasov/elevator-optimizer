package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;

public class StrategyVadimProm1 extends BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {
        int pCount = myPassengers.size() + enemyPassengers.size();
        for (Elevator e : myElevators) {
            for (Passenger lovely : enemyPassengers) {
                setElevator(lovely, e, myElevators, pCount);
            }
            for (Passenger p : myPassengers) {
                setElevator(p, e, myElevators, pCount);
            }

            if (e.getPassengers().size() == 20 || totalPassengersOnFloor(e, myPassengers, enemyPassengers) <= e.getPassengers().size()) {
                e.goToFloor(nearestFloor(e));
            }
        }
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

    private void setElevator(Passenger p, Elevator e, List<Elevator> elevators, int pCount) {
        if (p.getState() < 5) {
            if (pCount < 100) {
                if (p.getDestFloor() > 6) {
                    p.setElevator(e);
                }
            } else {
                p.setElevator(e);
            }
        }
    }
}