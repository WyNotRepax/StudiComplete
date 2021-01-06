
public class A3rekursiv {
	static int longestLength = 0;

	public static void main(String[] args) {
		printPasc(10);
	}

	static void printPasc(int r) {
		int[][] nums = new int[r][];
		for (int i = 0; i < r; i++) {
			nums[i] = pascRow(i);
		}
		for (int i = 0; i < nums.length; i++) {
			System.out.print(space(longestLength * (r - i)));
			for (int z = 0; z < nums[i].length; z++) {
				System.out.print(space(longestLength - String.valueOf(nums[i][z]).length()));
				System.out.print(nums[i][z]);
				System.out.print(space(longestLength));
			}
			System.out.println("");
		}
	}

	static int[] pascRow(int r) {
		int[] ret = new int[r + 1];
		for (int i = 0; i < ret.length; i++) {
			int next = binom(r, i);
			if (String.valueOf(next).length() > longestLength) {
				longestLength = String.valueOf(next).length();
			}
			ret[i] = next;
		}
		return ret;
	}

	static int binom(int n, int k) {
		if (n == k) {
			return 1;
		}
		if (k == 0) {
			return 1;
		}
		return binom(n - 1, k - 1) + binom(n - 1, k);
	}

	static String space(int n) {
		String ret = "";
		for (int i = 0; i < n; i++) {
			ret += " ";
		}
		return ret;
	}
}
