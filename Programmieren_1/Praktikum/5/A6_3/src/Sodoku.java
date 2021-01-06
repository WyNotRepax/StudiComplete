
public class Sodoku {

	/*static int[] startState = { 5, 3, 0, 0, 7, 0, 0, 0, 0, 6, 0, 0, 1, 9, 5, 0, 0, 0, 0, 9, 8, 0, 0, 0, 0, 6, 0, 8, 0,
			0, 0, 6, 0, 0, 0, 3, 4, 0, 0, 8, 0, 3, 0, 0, 1, 7, 0, 0, 0, 2, 0, 0, 0, 6, 0, 6, 0, 0, 0, 0, 2, 8, 0, 0, 0,
			0, 4, 1, 9, 0, 0, 5, 0, 0, 0, 0, 8, 0, 0, 7, 9 };*/
	
	static int[] startState = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	static LinkedPriorityQueue states = new LinkedPriorityQueue();

	public static void main(String[] args) {
		states.insert(startState, getPriority(startState));
		while (!states.isEmpty()) {
			addNextStates((int[]) states.pop());
			System.out.println(states.topPriority() + " " + states.length);
		}

	}

	static void addNextStates(int[] state) {
		for (int i = 0; i < state.length; i++) {
			if (state[i] == 0) {
				for (int z = 1; z <= 9; z++) {
					int[] copy = copy(state);
					copy[i] = z;
					if (validate(copy)) {
						states.insert(copy, getPriority(copy));
					}
				}
			}
		}
	}

	static int[] copy(int[] arr) {
		int[] ret = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = arr[i];
		}
		return ret;
	}

	static int getPriority(int[] state) {
		int count = 0;
		for (int i : state) {
			if (i != 0) {
				count++;
			}
		}
		return count;
	}

	static int getField(int[] state, int row, int col) {
		return state[(row - 1) * 9 + (col - 1)];
	}

	static boolean validate(int[] state) {
		for (int rowN = 1; rowN <= 9; rowN++) {
			int[] row = new int[9];
			for (int colN = 1; colN <= 9; colN++) {
				row[colN - 1] = getField(state, rowN, colN);
			}
			if (!partValidate(row)) {
				return false;
			}
		}
		for (int colN = 1; colN <= 9; colN++) {
			int[] col = new int[9];
			for (int rowN = 1; rowN <= 9; rowN++) {
				col[rowN - 1] = getField(state, rowN, colN);
			}
			if (!partValidate(col)) {
				return false;
			}
		}
		for (int xOffset = 0; xOffset <= 6; xOffset += 3) {
			for (int yOffset = 0; yOffset <= 6; yOffset += 3) {
				int[] part = new int[9];
				int n = 0;
				for (int x = 1; x <= 3; x++) {
					for (int y = 1; y <= 3; y++) {
						part[n] = getField(state, xOffset + x, yOffset + y);
						n++;
					}
				}
				if (!partValidate(part)) {
					return false;
				}
			}
		}
		return true;
	}

	static boolean partValidate(int[] part) {
		int[] counter = new int[10];
		for (int i : part) {
			counter[i]++;
		}
		for (int i = 1; i < counter.length; i++) {
			if (counter[i] > 1) {
				return false;
			}
		}
		return true;
	}

	static String arrPrint(int[] arr, String seperator) {
		String ret = "";
		for (int i = 0; i < arr.length; i++) {
			ret += arr[i] + seperator;
		}
		return ret;
	}
}
