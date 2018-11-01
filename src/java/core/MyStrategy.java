package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyStrategy extends core.BaseStrategy {

    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {

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
            List<Passenger> passengers = (List<Passenger>) e.getPassengers();
            HashMap<Integer, List<Passenger>> map = new HashMap<>();
            if (passengers.size() > 5 && e.getState() != 1) {
                passengers.forEach(passenger ->
                        {
                            List<Passenger> aDefault = map.getOrDefault(passenger.getDestFloor(), new ArrayList<>());
                            aDefault.add(passenger);
                            map.put(passenger.getDestFloor(), aDefault);
                        }
                );
                Integer destD = map.entrySet().stream()
                        .sorted(Comparator.comparingInt(o -> o.getValue().size()))
                        .map(Map.Entry::getKey)
                        .findFirst().get();

                e.goToFloor(destD);
            }
        }
    }
}
