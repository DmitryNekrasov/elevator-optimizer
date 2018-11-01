package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;

public class MyStrategy extends BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {

        for (Elevator elevator : myElevators) {
            for (Passenger passenger : myPassengers) {
                if (passenger.getState() < 5) {
                    if (elevator.getState() != 1) {
                        elevator.goToFloor(passenger.getFromFloor());
                    }
                    if (elevator.getFloor() == passenger.getFromFloor()) {
                        passenger.setElevator(elevator);
                    }
                }
            }
            if (elevator.getPassengers().size() > 0 && elevator.getState() != 1) {
                elevator.goToFloor(elevator.getPassengers().get(0).getDestFloor());
            }
        }
    }
}
