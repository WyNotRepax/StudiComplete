
public class Test {
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
		
		input = Sodoku.transpose(input);
		
		Sodoku test = new Sodoku(input);
		System.out.println(test.toString());
	}

}
