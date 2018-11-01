package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;

public class MyStrategy extends BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {
        int pCount = myPassengers.size() + enemyPassengers.size();
        for (Elevator e : myElevators) {
            for (Passenger lovely : enemyPassengers) {
                setElevator(lovely, e, myElevators, pCount);
            }
            for (Passenger p : myPassengers) {
                setElevator(p, e, myElevators, pCount);
            }
            if (e.getPassengers().size() == 20 || e.getFloor() != 1) {

                e.goToFloor(e.getPassengers().stream().map(Passenger::getDestFloor).min(Integer::compareTo).get());
            }
        }
    }

    private void setElevatorForEnemy(Passenger p, Elevator e, List<Elevator> elevators) {
        if (p.getState() < 5) {
            p.setElevator(e);
        }
    }

    private void setElevatorForMine(Passenger p, Elevator e, List<Elevator> elevators) {
        if (p.getState() < 5) {
            p.setElevator(e);
        }
    }


    private void setElevator(Passenger p, Elevator e, List<Elevator> elevators, int pCount) {
        if (p.getState() < 5) {
            if (pCount < 100) {
                if (p.getDestFloor() > 5) {
                    p.setElevator(e);
                }
            } else {
                p.setElevator(e);
            }
        }
    }
}
