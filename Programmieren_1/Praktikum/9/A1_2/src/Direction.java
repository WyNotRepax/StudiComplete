
public class Direction {
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static boolean isDirection(int direction) {
		return (direction == NORTH || direction == EAST || direction == SOUTH || direction == WEST);
	}
}
