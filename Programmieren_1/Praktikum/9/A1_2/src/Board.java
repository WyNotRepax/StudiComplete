
public class Board {
	private final int width;
	private final int height;

	private Car[][] field;

	private final char background;
	private final char border;
	private final int exitSide;

	private char[][] screen;

	private final char winningCar;
	private final int winningX;
	private final int winningY;

	Board(int width, int height, char background, char border, int exitSide, char mainCar) {
		this.width = width;
		this.height = height;
		this.background = background;
		this.border = border;
		this.exitSide = exitSide;
		switch (exitSide) {
		case Direction.NORTH:
			winningX = width / 2 - 1;
			winningY = 0;
			break;
		case Direction.EAST:
			winningX = width - 1;
			winningY = height / 2 - 1;
			break;
		case Direction.SOUTH:
			winningX = width / 2 - 1;
			winningY = height - 1;
			break;
		case Direction.WEST:
			winningX = 0;
			winningY = height / 2 - 1;
			break;
		default:
			winningX = -1;
			winningY = -1;
		}
		winningCar = mainCar;
		field = new Car[width][height];

	}

	public void addCar(char symbol, int x, int y, int direction, int length) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			if (Direction.isDirection(direction)) {
				int xStep = 0;
				int yStep = 0;
				switch (direction) {
				case Direction.NORTH:
					yStep = -1;
					break;
				case Direction.EAST:
					xStep = 1;
					break;
				case Direction.SOUTH:
					yStep = 1;
					break;
				case Direction.WEST:
					xStep = -1;
					break;
				}
				Car c = new Car(symbol, direction);
				for (int i = 0; i < length; i++) {
					field[x][y] = c;
					x += xStep;
					y += yStep;
				}
			}
		}
	}

	public void moveCarAt(int x, int y, int direction) {
		Car c = field[x][y];
		System.out.println("Selected: " + c.getSymbol());
		if (c != null && c.facing(direction)) {
			System.out.println("Passed Direction and Empty");
			int xStep = 0;
			int yStep = 0;
			switch (direction) {
			case Direction.NORTH:
				yStep = -1;
				break;
			case Direction.EAST:
				xStep = 1;
				break;
			case Direction.SOUTH:
				yStep = 1;
				break;
			case Direction.WEST:
				xStep = -1;
				break;
			}
			while (x >= 0 && x < width && y >= 0 && y < height && field[x][y] != null && field[x][y].equals(c)) {
				x += xStep;
				y += yStep;
				System.out.println(x + " " + y + " " + xStep + " " + yStep);
			}
			System.out.println("Moving to " + x + " " + y);
			if (x >= 0 && x < width && y >= 0 && y < height && field[x][y] == null) {
				xStep *= -1;
				yStep *= -1;
				while (x + xStep >= 0 && x + xStep < width && y + yStep >= 0 && y + yStep < height
						&& field[x + xStep][y + yStep] != null && field[x + xStep][y + yStep].equals(c)) {
					field[x][y] = c;
					field[x + xStep][y + yStep] = null;
					x += xStep;
					y += yStep;
				}
			}
		}

	}

	public void moveCarByChar(char c, int direction) {
		boolean found = false;
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] != null && field[x][y].getSymbol() == c) {
					moveCarAt(x, y, direction);
					found = true;
					break;
				}

			}
			if (found) {
				break;
			}
		}
	}

	public boolean won() {
		return field[winningX][winningY].equals(winningCar);
	}

	public void render() {
		screen = new char[width + 2][height + 2];
		drawBackground();
		drawBorder();
		drawExit();
		drawCars();
		print();
	}

	private void drawBackground() {
		for (int x = 0; x < screen.length; x++) {
			for (int y = 0; y < screen[x].length; y++) {
				screen[x][y] = background;
			}
		}
	}

	private void drawBorder() {
		for (int x = 0; x < screen.length; x++) {
			for (int y = 0; y < screen[x].length; y++) {
				if (x == 0 || x == screen.length - 1 || y == 0 || y == screen[x].length - 1) {
					screen[x][y] = border;
				}
			}
		}
	}

	private void drawExit() {

		int offsetX = 1;
		int offsetY = 1;
		switch (exitSide) {
		case Direction.NORTH:
			offsetY -= 1;
			break;
		case Direction.EAST:
			offsetX += 1;
			break;
		case Direction.SOUTH:
			offsetY += 1;
			break;
		case Direction.WEST:
			offsetX -= 1;
		}
		// System.out.println("Exit at " + (winningX + offsetX) + " " + (winningY +
		// offsetY)); // Debug Information
		screen[winningX + offsetX][winningY + offsetY] = background;
	}

	private void drawCars() {
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] != null) {
					screen[x + 1][y + 1] = field[x][y].getSymbol();
				}
			}
		}
	}

	private void print() {
		for (int y = 0; y < screen[0].length; y++) {
			for (int x = 0; x < screen.length; x++) {
				System.out.print(screen[x][y]);
			}
			System.out.println();
		}
	}

}
