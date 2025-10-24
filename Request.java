public class Request {
    private final int floor;
    private final Direction direction; // Only for external requests

    public Request(int f, Direction d)
    {
        floor = f;
        direction = d;
    }

    public int GetFloor()
    {
        return floor;
    }

    public Direction GetDirection()
    {
        return direction;
    }
}