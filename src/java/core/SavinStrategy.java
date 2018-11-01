package core;

import core.API.Elevator;
import core.API.Passenger;

import java.util.List;

public class SavinStrategy extends BaseStrategy {


    public void onTick(List<Passenger> myPassengers, List<Elevator> myElevators, List<Passenger> enemyPassengers, List<Elevator> enemyElevators) {

        for (Elevator elevator : myElevators) {
            Passenger bestPassenger = null;
            double bestGoalValue = 0.0;
            if (elevator.getState() == ElevatorStatus.opening) {
                // придумываем новый план для лифта
                for (Passenger passenger : myPassengers) {
                    if (!passenger.hasElevator() && goalFunction(elevator, passenger) > bestGoalValue) {
                        bestPassenger = passenger;
                    }
                }
            }
            // едем
            if (elevator.getFloor().equals(bestPassenger.getFloor())) {
                if (bestPassenger.getState() < 5) {
                    if (elevator.getState() != 1) {
                        elevator.goToFloor(bestPassenger.getFromFloor());
                    }
                    if (elevator.getFloor().equals(bestPassenger.getFromFloor())) {
                        bestPassenger.setElevator(elevator);
                    }
                }
            } else {
                elevator.goToFloor(bestPassenger.getFromFloor());
            }

            bestPassenger.setElevator(elevator);
        }

//        for (Elevator e : myElevators) {
//            for (Passenger p : myPassengers) {
//                if (p.getState() < 5) {
//                    if (e.getState() != 1) {
//                        e.goToFloor(p.getFromFloor());
//                    }
//                    if (e.getFloor() == p.getFromFloor()) {
//                        p.setElevator(e);
//                    }
//                }
//            }
//            if (e.getPassengers().size() > 4 && e.getState() != 1) {
//                e.goToFloor(e.getPassengers().get(0).getDestFloor());
//            }
//        }
    }

    public double goalFunction(Elevator elevator, Passenger passenger) {
        double time = 0;
        double deltaFloors = 0;
        double coins = 0;
        if (passengerWouldWaiting(passenger, 50)) {
            // state 4 Время чтобы закрыть двери state 4


            // state 0 Время + 1 тик

            // state 1 Время чтобы доехать до пассажира
            time += timeToRide(elevator.getFloor(), passenger.getFromFloor(), 50.0);

            // state 2 Время чтобы открыть двери

            // state 3 открыл двери. Время чтобы пассажир дошел до лифта

            // state 4 Время чтобы закрыть двери state 4

            // state 0 Время + 1 тик

            // state 1 Время перевезти пассажира до нужного ему места
            time += timeToRide(passenger.getFromFloor(), passenger.getDestFloor(), 50.0);

            // state 2 Время выгрузить пассажиров

            coins += Math.abs(passenger.getFromFloor() - passenger.getDestFloor());
        }
        return coins / time;
    }

    private double timeToRide(int currentFloor, int destFloor, Double elevatorSpeed) {
        return Math.abs(currentFloor - destFloor) * elevatorSpeed;
    }

    private boolean passengerWouldWaiting(Passenger passenger, int time) {
        return passenger.getState() == PassengerStatus.waiting_for_elevator;
    }


    private static class PassengerStatus {
        static int waiting_for_elevator = 0;
        static int moving_to_elevator = 1;
        static int returning = 2;
        static int moving_to_floor = 3;
        static int using_elevator = 4;
        static int exiting = 5;
    }

    private static class ElevatorStatus {
        static int waiting = 0;
        static int moving = 1;
        static int opening = 2;
        static int filling = 3;
        static int closing = 4;
    }

}
