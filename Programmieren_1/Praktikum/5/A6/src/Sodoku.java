
public class Sodoku {
	public static final int WIDTH = 9;
	public static final int HEIGHT = 9;
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 9;
	private int[][] field = new int[WIDTH][HEIGHT];

	Sodoku(int[][] field) {
		if (field.length != WIDTH) {
			throw new RuntimeException("Invalid field Size: Expected " + WIDTH + ", got " + field.length);
		}
		for (int i = 0; i < field.length; i++) {
			if (field[i].length != WIDTH) {
				throw new RuntimeException("Invalid field Size: Expected " + WIDTH + ", got " + field.length);
			}
		}
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				this.field[x][y] = field[x][y];
			}
		}
	}

	void set(int x, int y, int val) {
		if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
			throw new RuntimeException("Coordinates out of Range:(" + x + "|" + y + ")!");
		} else if (val < MIN_VALUE || val > MAX_VALUE) {
			throw new RuntimeException("Value out of Range:" + val + "!");
		}
		field[x][y] = val;
	}

	int get(int x, int y) {
		return field[x][y];
	}

	public boolean isValid() {
		return false;
	}

	private boolean validColumn(int c) {
		return checkSet(field[c]);
	}

	private boolean validRow(int r) {
		int[] toCheck = new int[WIDTH];
		for (int x = 0; x < WIDTH; x++) {
			toCheck[x] = field[r][x];
		}
		return checkSet(toCheck);
	}

	private static boolean checkSet(int[] set) {
		if (set.length != 9) {
			throw new RuntimeException("Invalid set length: Expected 9, got " + set.length);
		}
		int[] counter = new int[10];
		for (int n : set) {
			counter[n]++;
			if (n != 0 && counter[n] > 1) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		String ret = "";
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				ret += field[x][y];
				if (x == 2 || x == 5) {
					ret += "|";
				}
			}
			ret += "\n";
			if(y == 2 | y == 5) {
				ret += "---+---+---\n";
			}
		}
		return ret;
	}

	static int[][] transpose(int[][] input) {
		int[][] ret = new int[input[0].length][];
		for (int x = 0; x < ret.length; x++) {
			ret[x] = new int[input.length];
			for (int y = 0; y < ret[x].length; y++) {
				ret[x][y] = input[y][x];
			}
		}
		return ret;
	}
}
