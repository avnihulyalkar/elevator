// Represents the current floor and direction of an elevator

import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator {
    private int currentFloor = 0;
    static final int MAX_FLOORS = 10; // Valid floors can range from 0-10
    private Direction currentDirection = Direction.IDLE;
    // Active and waiting queues
    private PriorityQueue<Integer> inProgressQueue;
    private PriorityQueue<Integer> waitingUpQueue;
    private PriorityQueue<Integer> waitingDownQueue;


    public Elevator() {
        inProgressQueue = new PriorityQueue<>();
        waitingUpQueue = new PriorityQueue<>();
        waitingDownQueue = new PriorityQueue<>(Comparator.reverseOrder());
    }

    // getters/setters for the floor and direction
    public int GetFloor()
    {
        return currentFloor;
    }

    public Direction GetDirection()
    {
        return currentDirection;
    }

    public void SetDirection(Direction d)
    {
        currentDirection = d;
    }
    public void addExternalRequest(int floor, Direction direction) {
        if (currentDirection == Direction.IDLE) {
            // Start moving toward the first request
            currentDirection = (floor > currentFloor) ? Direction.UP : Direction.DOWN;
            inProgressQueue.add(floor);
            return;
        }

        if (direction == currentDirection) {
            // If the request is in the path of travel, handle now
            if ((direction == Direction.UP && floor >= currentFloor) ||
                (direction == Direction.DOWN && floor <= currentFloor)) {
                inProgressQueue.add(floor);
            } else {
                addToWaitingQueue(floor, direction);
            }
        } else {
            addToWaitingQueue(floor, direction);
        }
    }

    public void addInternalRequest(int floor) {
        if (currentDirection == Direction.IDLE) {
            currentDirection = (floor > currentFloor) ? Direction.UP : Direction.DOWN;
            inProgressQueue.add(floor);
            return;
        }

        // Try to add it to current path if it doesn't require reversing
        if ((currentDirection == Direction.UP && floor >= currentFloor) ||
            (currentDirection == Direction.DOWN && floor <= currentFloor)) {
            inProgressQueue.add(floor);
        } else {
            Direction opposite = (currentDirection == Direction.UP) ? Direction.DOWN : Direction.UP;
            addToWaitingQueue(floor, opposite);
        }
    }

    private void addToWaitingQueue(int floor, Direction dir) {
        if (dir == Direction.UP) waitingUpQueue.add(floor);
        else waitingDownQueue.add(floor);
    }

    public void step() {
        if (inProgressQueue.isEmpty()) {
            switchDirectionIfNeeded();
            return;
        }

        int target = inProgressQueue.peek();
        if (currentFloor == target) {
            // Serve the floor
            System.out.println("Stopping at floor " + currentFloor);
            inProgressQueue.poll();

            // If no more in-progress requests, consider switching
            if (inProgressQueue.isEmpty()) {
                switchDirectionIfNeeded();
            }
            return;
        }

        moveOneFloorToward(target);
    }

    private void moveOneFloorToward(int target) {
        if (target > currentFloor) {
            currentFloor++;
            currentDirection = Direction.UP;
        } else if (target < currentFloor) {
            currentFloor--;
            currentDirection = Direction.DOWN;
        }
        System.out.println("Elevator moved to floor " + currentFloor);
    }

    private void switchDirectionIfNeeded() {
        if (currentDirection == Direction.UP && !waitingDownQueue.isEmpty()) {
            currentDirection = Direction.DOWN;
            inProgressQueue = new PriorityQueue<>(Comparator.reverseOrder());
            inProgressQueue.addAll(waitingDownQueue);
            waitingDownQueue.clear();
        } else if (currentDirection == Direction.DOWN && !waitingUpQueue.isEmpty()) {
            currentDirection = Direction.UP;
            inProgressQueue = new PriorityQueue<>();
            inProgressQueue.addAll(waitingUpQueue);
            waitingUpQueue.clear();
        } else {
            currentDirection = Direction.IDLE;
            System.out.println("Elevator is idle at floor " + currentFloor);
        }
    }

}