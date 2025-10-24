# Elevator Simulation

# Assumptions:
1) The building has N floors
2) There’s one elevator
3) Each request comes as either:
    External call (someone presses “Up” or “Down” on a floor) => Floor + Direction
    Internal request (passenger inside chooses a floor) => Floor
4) Elevator can only move one floor per time step.
5) Elevator doors open/close instantly, requests happen instantly, and multiple requests can happen at one unit of time
6) If idle, the elevator waits at its current floor.

# General Strategy:
The elevator controller holds an in-progress queue and a current direction. It will complete all requests in the in-progress queue before switching directions. In addition to this, there are two other queues: a waiting_up queue and a waiting_down queue.

When an internal request is added (which has no direction associated), the controller checks whether it can be added to the current in-progress queue without requiring the elevator to go backwards. If it can, the request is added to the in-progress queue. Otherwise, it is added to the waiting queue for the opposite direction.
