
public class A2 {
	public static void main(String[] args) {
	}

	static boolean fibbonaci(int n) {
		int curr = 1;
		int last = 0;
		while (curr < n) {
			int next = curr + last;
			last = curr;
			curr = next;
		}
		return n == curr;
	}

	static void swap(double a, double b) {
		double temp = a;
		a = b;
		b = temp;
	}

	static int round(double d) {
		return (int) (((d >= 0) ? d : -d) + 0.5);
	}

	static int smallerPrime(int n) {
		int biggest = 2;
		for (int i = 3; i < n; i++) {
			boolean isPrime = true;
			for (int test = 2; test * test <= i; test++) {
				if (i % test == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				biggest = i;
			}
		}
		return biggest;
	}

	static boolean contains7(int n) {
		if (n < 0) {
			n *= -1;
		}
		while (n > 0) {
			if (n % 10 == 7) {
				return true;
			}
			n /= 10;
		}
		return false;
	}

	static float clockSum(float c1, float c2) {
		int h = (int) c1 + (int) c2;
		System.out.println(h);
		c1 -= (int) c1;
		c2 -= (int) c2;
		int min = (int) ((c1 + c2) * 100);
		System.out.println(min + " " + c1 + " " + c2);
		h = h + (h / 60);
		h %= 24;
		min %= 60;
		return Float.parseFloat(h + "." + min);

	}

	static int[] difSum(int a, int b) {
		int[] ret = new int[2];
		ret[0] = a + b;
		ret[1] = a - b;
		return ret;
	}
	
	static float sum(int a; float... f) {
		
	}
}
