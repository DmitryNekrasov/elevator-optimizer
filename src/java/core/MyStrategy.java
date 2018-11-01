package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.Comparator;
import java.util.List;

public class MyStrategy extends BaseStrategy {

    final int elevatorsNumber = 16;
    boolean[] flags = new boolean[elevatorsNumber];

    final int n = 20;

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {

//        for (int i = 0; i <= 2; i++) {
//            flags[i] = true;
//        }

        for (Elevator elevator : myElevators) {
            if (!flags[elevator.getId()]) {
                //TODO: Use my strategy

                //Если много людей в лифте - едем
                if (elevator.getPassengers().size() >= n) {
                    elevator.goToFloor(getOptimalFloor(elevator.getFloor(), elevator.getPassengers()));
                    flags[elevator.getId()] = true;
                } else { //Набираем людей
                    for (Passenger passenger : myPassengers) {
                        if (passenger.getState() < 5) {
                            passenger.setElevator(elevator);
                        }
                    }

                    for (Passenger passenger : enemyPassengers) {
                        if (passenger.getState() < 5) {
                            passenger.setElevator(elevator);
                        }
                    }

                }
            } else {
                //Use default strategy
                for (Passenger p : myPassengers) {
                    if (p.getState() < 5) {
                        if (elevator.getState() != 1) {
                            elevator.goToFloor(p.getFromFloor());
                        }
                        if (elevator.getFloor() == p.getFromFloor()) {
                            p.setElevator(elevator);
                        }
                    }
                }

                for (Passenger p : enemyPassengers) {
                    if (p.getState() < 5) {
                        if (elevator.getState() != 1) {
                            elevator.goToFloor(p.getFromFloor());
                        }
                        if (elevator.getFloor() == p.getFromFloor()) {
                            p.setElevator(elevator);
                        }
                    }
                }

                if (elevator.getPassengers().size() > 0 && elevator.getState() != 1) {
                    elevator.goToFloor(getOptimalFloor(elevator.getFloor(), elevator.getPassengers()));
                }
            }
        }
    }

    private int getOptimalFloor(final int elevatorFloor, List<Passenger> passengersInElevator) {
        passengersInElevator.sort(Comparator.comparingInt(p -> Math.abs(p.getDestFloor() - elevatorFloor)));
        return passengersInElevator.get(0).getDestFloor();
    }
}
