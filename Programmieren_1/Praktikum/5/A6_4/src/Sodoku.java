
public class Sodoku {
	public static void main(String[] args) {

		int[][] input = new int[9][];
		input[0] = new int[] { 5, 3, 0, 0, 7, 0, 0, 0, 0 };
		input[1] = new int[] { 6, 0, 0, 1, 9, 5, 0, 0, 0 };
		input[2] = new int[] { 0, 9, 8, 0, 0, 0, 0, 6, 0 };

		input[3] = new int[] { 8, 0, 0, 0, 6, 0, 0, 0, 3 };
		input[4] = new int[] { 4, 0, 0, 8, 0, 3, 0, 0, 1 };
		input[5] = new int[] { 7, 0, 0, 0, 2, 0, 0, 0, 6 };

		input[6] = new int[] { 0, 6, 0, 0, 0, 0, 2, 8, 0 };
		input[7] = new int[] { 0, 0, 0, 4, 1, 9, 0, 0, 5 };
		input[8] = new int[] { 0, 0, 0, 0, 8, 0, 0, 7, 9 };
		
		System.out.println(solvedNum(input));
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				System.out.print(input[y][x] + " ");
			}
			System.out.println("");
		}
		while (solvedNum(input) < 81) {
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					if (input[y][x] == 0) {
						boolean[] possible = new boolean[10];
						for (int i = 0; i < possible.length; i++) {
							possible[i] = true;
						}
						for (int xCheck = 0; xCheck < 9; xCheck++) {
							for (int yCheck = 0; yCheck < 9; yCheck++) {
								if (xCheck == x || y == yCheck || (xCheck / 3 == x / 3 && yCheck / 3 == y / 3)) {
									if (input[yCheck][xCheck] != 0) {
										possible[input[yCheck][xCheck]] = false;
									}
								}
							}
						}
						int possNum = 0;
						int poss = 0;
						for (int i = 1; i <= 9; i++) {
							if (possible[i]) {
								possNum++;
								poss = i;
							}
						}
						if (possNum == 1) {
							System.out.println("X:" + x + " Y:" + y + " -> " + poss);
							input[y][x] = poss;
						}
						else if(possNum >= 1){
							System.out.print("X:" + x + " Y:" + y + " ~ ");
							for(int i = 1; i < possible.length; i++){
								if (possible[i]) {
									System.out.print(i + " ");
								}
							}
							System.out.println("");
						}
					}
				}
			}
			System.out.println("----------------------------------");
			System.out.println(solvedNum(input));
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					System.out.print(input[y][x] + " ");
				}
				System.out.println("");
			}
		}
	}

	static int solvedNum(int[][] state) {
		int count = 0;
		for (int[] i : state) {
			for (int j : i) {
				if (j != 0) {
					count++;
				}
			}
		}
		return count;
	}
}
