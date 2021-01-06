
public class Direction {
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	public static final int DIRECTIONX = 5;
	public static final int DIRECTIONY = 6;

	public static int convert(int direction) {
		switch (direction) {
		case UP:
		case DOWN:
			return DIRECTIONY;
		case LEFT:
		case RIGHT:
			return DIRECTIONX;
		default:
			return 0;
		}
	}

	public static int opposite(int direction) {
		switch (direction) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		case DIRECTIONX:
			return DIRECTIONY;
		case DIRECTIONY:
			return DIRECTIONX;
		default:
			return 0;
		}
	}
}
