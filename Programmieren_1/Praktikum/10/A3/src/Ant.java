
public class Ant {
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;

	private int column;
	private int row;
	private int direction;
	private World world;

	public Ant(int column, int row, World world) {
		this.column = column;
		this.row = row;
		this.direction = 0;
		this.world = world;
	}

	private void turnRight() {
		direction = (direction + 1) % 4;
	}

	private void turnLeft() {
		direction = (direction + 3) % 4;
	}

	private void forward() {
		// System.out.println("VORWÄRTS");
		switch (direction) {
		case NORTH:
			row -= 1;
			break;
		case EAST:
			column -= 1;
			break;
		case SOUTH:
			row += 1;
			break;
		case WEST:
			column += 1;
			break;
		}
		row = (row + world.getSize()) % world.getSize();
		column = (column + world.getSize()) % world.getSize();
	}

	public void nextStep() {
		// System.out.println("POSITION: " + row + " " + column);
		// System.out.println("FACING: " + direction);
		if (world.isCellBlack(row, column)) {
			world.white(row, column);
			turnRight();
			forward();
		} else {
			world.black(row, column);
			turnLeft();
			forward();
		}
	}
}
