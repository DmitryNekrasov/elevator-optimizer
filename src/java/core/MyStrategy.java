package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.Comparator;
import java.util.List;

public class MyStrategy extends BaseStrategy {

    final int elevatorsNumber = 16;
    boolean[] flags = new boolean[elevatorsNumber];

    final int n = 20;

    int tickCount = 0;
    final int maxTickNumber = 3500;

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {

//        for (int i = 14; i >= 11; i--) {
//            flags[i] = true;
//        }

        tickCount++;

        for (Elevator elevator : myElevators) {
            if (!flags[elevator.getId()]) {
                //TODO: Use my strategy

                //Если много людей в лифте - едем
                if (elevator.getPassengers().size() >= n){
                    elevator.goToFloor(getOptimalFloor(elevator.getFloor(), elevator.getPassengers()));
                    flags[elevator.getId()] = true;
                } else { //Набираем людей
                    if (tickCount < maxTickNumber) {
                        for (Passenger passenger : enemyPassengers) {
                            if (passenger.getState() < 5 && passenger.getDestFloor() > 5) {
                                passenger.setElevator(elevator);
                            }
                        }

                        for (Passenger passenger : myPassengers) {
                            if (passenger.getState() < 5 && passenger.getDestFloor() > 5) {
                                passenger.setElevator(elevator);
                            }
                        }
                    } else {
                        for (Passenger passenger : enemyPassengers) {
                            if (passenger.getState() < 5) {
                                passenger.setElevator(elevator);
                            }
                        }

                        for (Passenger passenger : myPassengers) {
                            if (passenger.getState() < 5) {
                                passenger.setElevator(elevator);
                            }
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

    private int totalPassengersOnFloor(Integer floor, List<Passenger> myPassengers, List<Passenger> enemyPassengers) {
        int count;
        count = (int) myPassengers.stream().filter(p -> p.getFloor() == floor).count();
        count += (int) enemyPassengers.stream().filter(p -> p.getFloor() == floor).count();

        if (floor == 1) {
            System.err.println(count);
        }

        return count;
    }

    private int getOptimalFloor(final int elevatorFloor, List<Passenger> passengersInElevator) {
        passengersInElevator.sort(Comparator.comparingInt(p -> Math.abs(p.getDestFloor() - elevatorFloor)));
        return passengersInElevator.get(0).getDestFloor();
    }
}
