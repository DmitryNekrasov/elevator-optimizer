package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;

public class Strategy extends BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {
        runFirstStrategy(myPassengers, myElevators, enemyPassengers, enemyElevators);
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


//    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {
//        System.out.println("myPassengers.size:" + myPassengers.size()
//                +"\nenemyPassengers.size" + enemyPassengers.size());
//        for (Elevator e : myElevators) {
//            for (Passenger lovely : enemyPassengers) {
//                setElevator(lovely, e, myElevators);
//            }
//            for (Passenger p : myPassengers) {
//                setElevator(p, e, myElevators);
//            }
//            if(e.getPassengers().size() == 20 || e.getFloor() != 1){
//
//                e.goToFloor(e.getPassengers().stream().map(Passenger::getDestFloor).min(Integer::compareTo).get());
//            }
//        }
//    }
//
//    private void setElevatorForEnemy(Passenger p, Elevator e, List<Elevator> elevators) {
//        if (p.getState() < 5) {
//            p.setElevator(e);
//        }
//    }
//
//    private void setElevatorForMine(Passenger p, Elevator e, List<Elevator> elevators) {
//        if (p.getState() < 5) {
//            p.setElevator(e);
//        }
//    }
//
//
//    private void setElevator(Passenger p, Elevator e, List<Elevator> elevators) {
//        if (p.getState() < 5) {
//            p.setElevator(e);
//        }
//    }

}
