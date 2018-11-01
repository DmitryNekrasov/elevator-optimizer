package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;

public class Strategy extends BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {

        String solutionId = System.getenv("SOLUTION_ID");
        if (solutionId.equals("1")) {
            runFirstStrategy(myPassengers, myElevators, enemyPassengers, enemyElevators);
        } else {
            runSecondStrategy(myPassengers, myElevators, enemyPassengers, enemyElevators);
        }
    }

    private void runFirstStrategy(List<Passenger> myPassengers,
                                  List<Elevator> myElevators,
                                  List<Passenger> enemyPassengers,
                                  List<Elevator> enemyElevators) {
        for (Elevator e : myElevators) {
            for (Passenger p : myPassengers) {
                if (p.getState() < 5) {
                    if (e.getState() != 1) {
                        e.goToFloor(p.getFromFloor());
                    }
                    if (e.getFloor() == p.getFromFloor()) {
                        p.setElevator(e);
                    }
                }
            }
            if (e.getPassengers().size() > 0 && e.getState() != 1) {
                e.goToFloor(e.getPassengers().get(0).getDestFloor());
            }
        }
    }

    private void runSecondStrategy(List<Passenger> myPassengers,
                                   List<Elevator> myElevators,
                                   List<Passenger> enemyPassengers,
                                   List<Elevator> enemyElevators) {

    }
}
