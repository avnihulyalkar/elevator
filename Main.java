public class Main {
    public static void main(String[] args) {
        System.out.println("\n=== Test 1: Mixed Up + Down Requests ===");

        Elevator el = new Elevator();

        // Simulate requests
        el.addExternalRequest(5, Direction.UP);
        el.addInternalRequest(7);
        el.addExternalRequest(2, Direction.DOWN);

        // Run simulation for a bit
        for (int i = 0; i < 15; i++) {
            el.step();
        }

        System.out.println("\n=== Test 2: Internal Request While Moving Up ===");
        Elevator e2 = new Elevator();
        e2.addExternalRequest(6, Direction.UP);

        for (int i = 0; i < 2; i++) {
            e2.step();
        }

        System.out.println("Adding internal request to floor 4...");
        e2.addInternalRequest(4); // should get added to in-progress queue since elevator still going up

        for (int i = 0; i < 10; i++) {
            e2.step();
        }

        System.out.println("\n=== Test 3: Internal Request Behind Elevator ===");
        Elevator e3 = new Elevator();
        e3.addExternalRequest(8, Direction.UP);

        for (int i = 0; i < 4; i++) {
            e3.step();
        }

        System.out.println("Adding internal request to floor 2 (behind current floor)");
        e3.addInternalRequest(2); // should go to waitingDownQueue

        for (int i = 0; i < 15; i++) {
            e3.step();
        }

    }
}