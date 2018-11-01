package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyStrategy extends core.BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {
        for (Elevator e : myElevators) {
            if (e.getState() == 2 || e.getState() == 3) {
//                e.getFloor()
                long myCount = myPassengers.stream()
                        .filter(passenger -> passenger.getFloor().equals(e.getFloor()))
                        .count();
                long enemyCount = enemyPassengers.stream()
                        .filter(passenger -> passenger.getFloor().equals(e.getFloor()))
                        .count();
                if ((myCount + enemyCount) == 0) {
                    if (e.getFloor() <= 1) {
                        e.goToFloor(e.getFloor()+1);
                        continue;
                    } else {
                        e.goToFloor(e.getFloor()-1);
                        continue;
                    }
                }

                if (e.getPassengers().size() <= 15 && (myCount+enemyCount) > 0){
                    myPassengers.stream()
                            .filter(passenger -> passenger.getState() < 5)
                            .filter(passenger -> passenger.getFromFloor().equals(e.getFloor()))
                            .forEach(passenger -> passenger.setElevator(e));


                    for (Passenger p : enemyPassengers) {
                        if (!((p.getState() == 1) || p.getState() == 3)) continue;
                        if (p.getElevator()==e.getId()) continue;
                        if (p.getFloor() == e.getFloor()) {
                            p.setElevator(e);
                        }
                    }

                } else {
                    List<Passenger> passengers = (List<Passenger>) e.getPassengers();
                    List<Integer> collect = passengers.stream()
                            .map(passenger -> passenger.getDestFloor())
                            .sorted((o1, o2) -> o1 - o2)
                            .collect(Collectors.toList());
                    e.goToFloor(collect.get(0));
                }
            }
        }
    }
}
